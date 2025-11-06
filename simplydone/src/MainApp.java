import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;

    public MainApp() {
        super("SimplyDone - 3 in 1 Productivity Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        TodoPage todoPage = new TodoPage(this);
        ExpensePage expensePage = new ExpensePage(this);
        HabitTracker habittracker = new HabitTracker(this);

        cards.add(homePanel(), "Home");
        cards.add(todoPage, "Todo");
        cards.add(expensePage, "Expense");
        cards.add(habittracker, "Habit Tracker");

        add(cards, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel homePanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(244, 244, 244));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("SimplyTracker", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        gbc.gridy = 0;
        p.add(title, gbc);

        // Vertical stacking of buttons
        JButton todoBtn = new JButton("Todo List");
        JButton expenseBtn = new JButton("Expense Manager");
        JButton reminderBtn = new JButton("Habit Tracker");

        reminderBtn.addActionListener(e -> cardLayout.show(cards, "Habit Tracker"));


        JButton[] buttons = {todoBtn, expenseBtn, reminderBtn};
        for (int i = 0; i < buttons.length; i++) {
            JButton btn = buttons[i];
            btn.setBackground(new Color(33, 150, 243));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(250, 60));
            gbc.gridy = i + 1;
            p.add(btn, gbc);
        }

        // Button actions
        todoBtn.addActionListener(e -> cardLayout.show(cards, "Todo"));
        expenseBtn.addActionListener(e -> cardLayout.show(cards, "Expense"));
        reminderBtn.addActionListener(e -> cardLayout.show(cards, "Reminder"));

        return p;
    }


    public void showCard(String name) { cardLayout.show(cards, name); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}
