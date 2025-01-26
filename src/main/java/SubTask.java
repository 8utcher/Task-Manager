public class SubTask extends Task {

    private Epic epicId;

    public SubTask(int id, String title, String description, Status status, Epic epic) {
        super(id, title, description, status);
        this.epicId = epic;
    }

    public Epic getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                '}';
    }
}
