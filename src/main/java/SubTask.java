public class SubTask extends Task {

    private final int epicId;

    public SubTask(int id, String title, String description, Status status, int epicID) {
        super(id, title, description, status);
        this.epicId = epicID;
    }

    public int getEpicId() {
        return  epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", sabTaskId=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
