import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{

    List<SubTask> subTasks;

    public Epic(int id, String title, String description, Status status) {
        super(id, title, description, status);
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTasks.remove(subTask);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public void setStatus(Status status) {
        super.setStatus(status);
        if (subTasks.isEmpty()) {
            super.setStatus(Status.NEW);
        }else {
            boolean allDone = true;
            boolean allNew = true;
            for (SubTask subTask : subTasks) {
                if (subTask.getStatus() != Status.DONE) {
                    allDone = false;
                }
                if (subTask.getStatus() != Status.NEW) {
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
}
