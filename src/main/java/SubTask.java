public class SubTask extends Task {

    private int epicId;

    public SubTask(int id, String title, String description, Status status, int epicID) {
        super(id, title, description, status);
        this.epicId = epicID;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
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
