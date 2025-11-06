// import dependencies
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Inheritence from JPanel
public class ExpensePage extends JPanel {
//    Excapsulation of fields for data safety
    private final ArrayList<ExpenseItem> items = new ArrayList<>();
    private final JPanel listPanel = new JPanel();
    private final JTextField noteField = new JTextField();
    private final JTextField amountField = new JTextField();
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Food","Medicine","Study","Others"});
    private final JTextField budgetField = new JTextField();
    private final JLabel totalLabel = new JLabel("Total: 0");
    private final JLabel warningLabel = new JLabel("");
    private double budget = 0;
//    adding a file name where expenses will be exported
    private final String FILE = "expenses.txt";

    private final MainApp app;

//    passing already rendered MainApp object (Homepage UI) instead of creating it from scratch again
//    remember the way back to the homepage using action listener
//    connect this panel to the main panel
    public ExpensePage(MainApp app) {
        this.app = app;
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(244,244,244));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Header panel with total & budget
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(244,244,244));
        JLabel header = new JLabel("₹₹₹ Expense Manager", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(Color.BLACK);
        headerPanel.add(header, BorderLayout.WEST);

        // Right side for total and budget
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(244,244,244));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);

        totalLabel.setFont(new Font("Segoe UI",Font.BOLD,16));
        gbc.gridx=0; gbc.gridy=0; rightPanel.add(totalLabel,gbc);

        addPlaceholder(budgetField,"Set Budget");
        budgetField.setColumns(8);
        gbc.gridx=0; gbc.gridy=1; rightPanel.add(budgetField,gbc);

        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font("Segoe UI",Font.BOLD,14));
        gbc.gridx=0; gbc.gridy=2; rightPanel.add(warningLabel,gbc);

        headerPanel.add(rightPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // List panel for items
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(244,244,244));
        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        add(sp, BorderLayout.CENTER);

        // bottom input panel
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setBackground(new Color(244,244,244));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);

        addPlaceholder(noteField,"Note");
        noteField.setColumns(10);
        gbc.gridx = 0; gbc.weightx=0.5; gbc.fill=GridBagConstraints.HORIZONTAL;
        bottom.add(noteField, gbc);

        addPlaceholder(amountField,"Amount");
        amountField.setColumns(8);
        gbc.gridx = 1; bottom.add(amountField, gbc);

        gbc.gridx = 2; bottom.add(categoryBox, gbc);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(76,175,80));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        gbc.gridx = 3; bottom.add(addBtn, gbc);

        JButton exportBtn = new JButton("Export");
        exportBtn.setBackground(new Color(33,150,243));
        exportBtn.setForeground(Color.WHITE);
        exportBtn.setFocusPainted(false);
        gbc.gridx = 4; bottom.add(exportBtn, gbc);

        JButton back = new JButton("Back");
        back.setBackground(new Color(158,158,158));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        gbc.gridx = 5; bottom.add(back, gbc);

        add(bottom, BorderLayout.SOUTH);

        // Actions
        addBtn.addActionListener(e -> {
            String note = noteField.getText().trim();
            String amtStr = amountField.getText().trim();
            String cat = categoryBox.getSelectedItem().toString();
            if(note.isEmpty() || note.equals("Note") || amtStr.isEmpty() || amtStr.equals("Amount")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }
            double amt = 0;
            try { amt = Double.parseDouble(amtStr); } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Amount must be a number"); return; }
            items.add(new ExpenseItem(cat,note,amt));
            noteField.setText(""); amountField.setText("");
//            refresh the UI
            refreshList();
        });

//        abstraction of actual file handling logic using storageUtil
        exportBtn.addActionListener(e -> {
            try {
                List<String> lines = new ArrayList<>();
                for(ExpenseItem it: items) lines.add(it.toString());
                StorageUtil.writeLines(FILE,lines);
                JOptionPane.showMessageDialog(this,"Expenses exported to data folder.");
            } catch(Exception ex){ JOptionPane.showMessageDialog(this,"Export failed: "+ex.getMessage()); }
        });

        back.addActionListener(e -> app.showCard("Home"));

        budgetField.addActionListener(e -> {
            try {
                budget = Double.parseDouble(budgetField.getText().trim());
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Invalid budget");
                budgetField.setText("");
            }
            refreshList();
        });

        load();
        refreshList();
    }

//    this to load data from the file, but not required since UI not created
    private void load() {
        List<String> lines = StorageUtil.readLines(FILE);
        items.clear();
        for(String l: lines) items.add(ExpenseItem.fromString(l));
    }

//    this is used to refresh the list whenever a new Item is added into the list
    private void refreshList() {
        listPanel.removeAll();
        double food=0,med=0,study=0,other=0,total=0;
        for(int i=0;i<items.size();i++){
            final int idx=i;
            ExpenseItem it = items.get(i);
            JPanel row = new JPanel(new BorderLayout(6,6));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE,48));
            row.setBackground(Color.WHITE);
            row.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));

            JLabel lbl = new JLabel(it.getCategory()+": "+it.getAmount()+" - "+it.getNote());
            lbl.setFont(new Font("Segoe UI",Font.PLAIN,15));
            row.add(lbl, BorderLayout.CENTER);

            JButton del = new JButton("Delete");
            del.setBackground(new Color(244,67,54));
            del.setForeground(Color.WHITE);
            del.setFocusPainted(false);
            row.add(del,BorderLayout.EAST);

            del.addActionListener(e->{
                int r = JOptionPane.showConfirmDialog(this,"Delete this expense?","Confirm",JOptionPane.YES_NO_OPTION);
                if(r==JOptionPane.YES_OPTION){ items.remove(idx); refreshList(); }
            });

            listPanel.add(Box.createVerticalStrut(6));
            listPanel.add(row);

//            dynamically add amount for total in each category
            switch(it.getCategory()){
                case "Food": food+=it.getAmount(); break;
                case "Medicine": med+=it.getAmount(); break;
                case "Study": study+=it.getAmount(); break;
                case "Others": other+=it.getAmount(); break;
            }
            total+=it.getAmount();
        }

        totalLabel.setText("Total: "+total);

        // Update budget warning
        if(budget>0){
            double remaining = budget-total;
            if(remaining<1000) warningLabel.setText("Low Budget !!! : ₹ "+ remaining);
            else warningLabel.setText("");
        }

        // Add summary panel (4 rectangles)
        JPanel chart = new JPanel(new GridLayout(2,2,6,6));
        chart.setMaximumSize(new Dimension(Integer.MAX_VALUE,150));
        chart.setOpaque(false);

        chart.add(createCategoryBox("Food",food,new Color(255,193,7)));
        chart.add(createCategoryBox("Medicine",med,new Color(244,67,54)));
        chart.add(createCategoryBox("Study",study,new Color(33,150,243)));
        chart.add(createCategoryBox("Others",other,new Color(76,175,80)));

        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(chart);

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createCategoryBox(String name,double amt,Color c){
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(c);
        JLabel l = new JLabel("<html><center>"+name+"<br>"+amt+"</center></html>",SwingConstants.CENTER);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI",Font.BOLD,14));
        p.add(l,BorderLayout.CENTER);
        return p;
    }

    private void addPlaceholder(JTextField field,String placeholder){
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if(field.getText().equals(placeholder)){ field.setText(""); field.setForeground(Color.BLACK); }
            }
            public void focusLost(java.awt.event.FocusEvent e){
                if(field.getText().isEmpty()){ field.setText(placeholder); field.setForeground(Color.GRAY); }
            }
        });
    }
}
