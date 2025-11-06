public class ExpenseItem {
//    private fields for no direct access to data - encapsulation
    private String category;
    private String note;
    private double amount;

//    getter and setter methods to access private fields
    public ExpenseItem(String category, String note, double amount) { this.category = category; this.note = note; this.amount = amount; }
    public String getCategory() { return category; }
    public String getNote() { return note; }
    public double getAmount() { return amount; }

//    constructor
    @Override
    public String toString() { return category + "|" + amount + "|" + note.replace("|"," "); }
    public static ExpenseItem fromString(String s) {
        if (s == null) return new ExpenseItem("Others","",0.0);
        String[] p = s.split("\\|",3);
        double amt = 0;
        try { amt = Double.parseDouble(p[1]); } catch (Exception ignored) {}
        return new ExpenseItem(p[0], p.length>2?p[2]:"", amt);
    }
}
