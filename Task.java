public class Task {
    private String name;
    private String time;
    private boolean isDone;

    public Task(String name, String time) {
        this.name = name;
        this.time = time;
        this.isDone = false;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return name + " (" + time + ")" + (isDone ? " - Completed" : "");
    }
}
