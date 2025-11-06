public class TodoItem {
    private String title;
    private boolean done;

    public TodoItem(String title, boolean done) { this.title = title; this.done = done; }
    public String getTitle() { return title; }
    public void setTitle(String t) { title = t; }
    public boolean isDone() { return done; }
    public void setDone(boolean d) { done = d; }
    @Override
    public String toString() { return title.replace("|"," ") + "|" + (done ? "1" : "0"); }
    public static TodoItem fromString(String s) {
        if (s == null) return new TodoItem("", false);
        String[] p = s.split("\\|", 2);
        if (p.length < 2) return new TodoItem(p[0], false);
        return new TodoItem(p[0], "1".equals(p[1].trim()));
    }
}
