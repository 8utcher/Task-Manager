public class SubTask extends Task {

    private Epic epic;

    public SubTask(int id, String title, String description, Status status, Epic epic) {
        super(id, title, description, status);
        this.epic = epic;
    }
    public Epic getEpic() {
        return epic;
    }


}
