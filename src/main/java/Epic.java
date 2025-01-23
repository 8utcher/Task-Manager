import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{

    private final List<Integer> subTasksId;

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
        this.subTasksId = new ArrayList<>();
    }

    public void addSubTask(int subTask) {
        subTasksId.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTasksId.remove(subTask);
    }

    public List<Integer> getSubTasksId() {
        return subTasksId;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        if (subTasksId.isEmpty()) {
            super.setStatus(Status.NEW);
        }else {
            boolean allDone = true;
            boolean allNew = true;
            for (Integer subTaskId : subTasksId) {
                if (subTaskId.getStatus() != Status.DONE) {
                    allDone = false;
                }
                if (subTaskId.getStatus() != Status.NEW) {
                    allNew = false;
                }
            }
            if (allDone) {
                super.setStatus(Status.DONE);
            } else if (allNew) {
                super.setStatus(Status.NEW);
            }else {
                super.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasksId=" + subTasksId +
                '}';
    }
}
