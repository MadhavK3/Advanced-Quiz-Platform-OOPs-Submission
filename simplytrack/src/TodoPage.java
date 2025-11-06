import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TodoPage extends JPanel {
    private final ArrayList<TodoItem> items = new ArrayList<>();
    private final JPanel listPanel = new JPanel();
    private final JTextField inputField = new JTextField();
    private final String FILE = "todos.txt";
    private final MainApp app;

    public TodoPage(MainApp app) {
        this.app = app;
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(244,244,244));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel header = new JLabel("Todo List", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        add(header, BorderLayout.NORTH);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(244,244,244));
        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        add(sp, BorderLayout.CENTER);

        // bottom input area
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setBackground(new Color(244,244,244));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);

        inputField.setColumns(30);
        addPlaceholder(inputField, "Enter your task here...");
        gbc.gridx = 0; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        bottom.add(inputField, gbc);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(76,175,80));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        gbc.gridx = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        bottom.add(addBtn, gbc);

        JButton exportBtn = new JButton("Export");
        exportBtn.setBackground(new Color(33,150,243));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFocusPainted(false);
        gbc.gridx = 2; bottom.add(exportBtn, gbc);

        JButton back = new JButton("Back");
        back.setBackground(new Color(158,158,158));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        gbc.gridx = 3; bottom.add(back, gbc);

        add(bottom, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String t = inputField.getText().trim();
            if (t.isEmpty() || t.equals("Enter your task here...")) {
                JOptionPane.showMessageDialog(this, "Please enter a task.");
                return;
            }
            items.add(new TodoItem(t, false));
            inputField.setText("");
            refreshList();
        });

        exportBtn.addActionListener(e -> {
            try {
                List<String> lines = new ArrayList<>();
                for (TodoItem it : items) lines.add(it.toString());
                StorageUtil.writeLines(FILE, lines);
                JOptionPane.showMessageDialog(this, "Todos exported to data folder.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> app.showCard("Home"));

        load();
        refreshList();
    }

    private void load() {
        List<String> lines = StorageUtil.readLines(FILE);
        items.clear();
        for (String l : lines) items.add(TodoItem.fromString(l));
    }

    private void refreshList() {
        listPanel.removeAll();
        for (int i = 0; i < items.size(); i++) {
            final int idx = i;
            TodoItem it = items.get(i);
            JPanel row = new JPanel(new BorderLayout(6,6));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            row.setBackground(Color.WHITE);
            row.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

            JLabel lbl = new JLabel((it.isDone()? "✓" : "") + it.getTitle());
            lbl.setForeground(Color.BLACK);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            row.add(lbl, BorderLayout.CENTER);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
            actions.setOpaque(false);
            JButton doneBtn = new JButton(it.isDone() ? "Undo" : "Done");
            doneBtn.setBackground(new Color(33,150,243));
            doneBtn.setForeground(Color.WHITE);
            doneBtn.setFocusPainted(false);
            JButton delBtn = new JButton("Delete");
            delBtn.setBackground(new Color(244,67,54));
            delBtn.setForeground(Color.WHITE);
            delBtn.setFocusPainted(false);

            actions.add(doneBtn);
            actions.add(delBtn);
            row.add(actions, BorderLayout.EAST);

            doneBtn.addActionListener(e -> {
                it.setDone(!it.isDone());
                refreshList();
            });

            delBtn.addActionListener(e -> {
                int r = JOptionPane.showConfirmDialog(this, "Delete this todo?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    items.remove(idx);
                    refreshList();
                }
            });

            listPanel.add(Box.createVerticalStrut(6));
            listPanel.add(row);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) { field.setText(""); field.setForeground(Color.BLACK); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) { field.setForeground(Color.GRAY); field.setText(placeholder); }
            }
        });
    }
}
