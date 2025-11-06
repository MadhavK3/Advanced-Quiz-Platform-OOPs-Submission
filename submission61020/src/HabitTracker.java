import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class HabitTracker extends JPanel {
    private MainApp mainApp;
    private JPanel listPanel;
    private JTextField habitField;
    private JButton addButton, exportButton, backButton;
    private java.util.List<Habit> habits = new ArrayList<>();

    public HabitTracker(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // --- Top Panel ---
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        habitField = new JTextField();
        addButton = new JButton("Add Habit");
        topPanel.add(habitField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // --- Habit List Panel ---
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel();
        exportButton = new JButton("Export Habits");
        backButton = new JButton("← Back");
        bottomPanel.add(exportButton);
        bottomPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Actions ---
        addButton.addActionListener(e -> addHabit());
        exportButton.addActionListener(e -> exportHabits());
        backButton.addActionListener(e -> mainApp.showCard("Home"));

        loadHabits();
    }

    // Habit inner model
    private static class Habit {
        String name;
        int streak;
        String lastDoneDate; // yyyy-MM-dd

        Habit(String name, int streak, String lastDoneDate) {
            this.name = name;
            this.streak = streak;
            this.lastDoneDate = lastDoneDate;
        }

        static Habit fromString(String line) {
            try {
                String[] parts = line.split("\\|");
                return new Habit(parts[0], Integer.parseInt(parts[1]), parts[2]);
            } catch (Exception e) {
                return new Habit(line, 0, "");
            }
        }

        String serialize() {
            return name + "|" + streak + "|" + lastDoneDate;
        }
    }

    private void addHabit() {
        String habit = habitField.getText().trim();
        if (habit.isEmpty()) return;
        Habit h = new Habit(habit, 0, "");
        habits.add(h);
        addHabitToPanel(h);
        habitField.setText("");
        saveHabits();
    }

    private void addHabitToPanel(Habit habit) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel(habit.name);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JLabel streakLabel = new JLabel("▲" + habit.streak);
        streakLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton doneBtn = new JButton("Done");
        doneBtn.setBackground(new Color(76, 175, 80));
        doneBtn.setForeground(Color.WHITE);
        doneBtn.setFocusPainted(false);
        doneBtn.setPreferredSize(new Dimension(80, 30));

        JButton removeBtn = new JButton("✖");
        removeBtn.setFocusPainted(false);
        removeBtn.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 0; gbc.weightx = 1.0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 0;
        panel.add(streakLabel, gbc);
        gbc.gridx = 2;
        panel.add(doneBtn, gbc);
        gbc.gridx = 3;
        panel.add(removeBtn, gbc);

        listPanel.add(panel);
        listPanel.revalidate();
        listPanel.repaint();

        // Actions
        doneBtn.addActionListener(e -> {
            String today = java.time.LocalDate.now().toString();
            if (!today.equals(habit.lastDoneDate)) {
                java.time.LocalDate yesterday = java.time.LocalDate.now().minusDays(1);
                if (yesterday.toString().equals(habit.lastDoneDate)) habit.streak++;
                else habit.streak = 1;
                habit.lastDoneDate = today;
                streakLabel.setText("▲" + habit.streak);
                saveHabits();
            } else {
                JOptionPane.showMessageDialog(this, "Already marked as done for today!");
            }
        });

        removeBtn.addActionListener(e -> {
            listPanel.remove(panel);
            habits.remove(habit);
            listPanel.revalidate();
            listPanel.repaint();
            saveHabits();
        });
    }

    private void exportHabits() {
        try {
            java.util.List<String> lines = new ArrayList<>();
            for (Habit h : habits)
                lines.add(h.name + " - Streak: " + h.streak);
            StorageUtil.writeLines("habits_export.txt", lines);
            JOptionPane.showMessageDialog(this, "Habits exported successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Export failed: " + e.getMessage());
        }
    }

    private void saveHabits() {
        try {
            java.util.List<String> lines = new ArrayList<>();
            for (Habit h : habits) lines.add(h.serialize());
            StorageUtil.writeLines("habits.txt", lines);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    private void loadHabits() {
        habits.clear();
        java.util.List<String> lines = StorageUtil.readLines("habits.txt");
        for (String line : lines) {
            Habit h = Habit.fromString(line);
            habits.add(h);
            addHabitToPanel(h);
        }
    }
}
