import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{

    private final List<Integer> subTasksId = new ArrayList<>();
    SubTask subTask;

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    public void addSubTask(int subTask) {
        subTasksId.add(subTask);
    }

    public void removeBySubTaskId(int subTaskId) {
        subTasksId.remove(subTaskId);
    }

    public List<Integer> getSubTasksId() {
        return subTasksId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + subTasksId +
                ", epicId=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
