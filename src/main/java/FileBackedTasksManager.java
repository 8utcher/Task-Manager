import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public Task createTask(int id, String title, String description, Status status) {
        Task task = super.createTask(id, title, description, status);
        save();
        return task;
    }

    @Override
    public Epic createEpic(int id, String title, String description, Status status) {
        Epic epic = super.createEpic(id, title, description, status);
        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(int id, String title, String description, Status status, int epicId) {
        SubTask subTask = super.createSubTask(id, title, description, status, epicId);
        save();
        return subTask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubTask(int id) {
        super.removeSubTask(id);
        save();
    }


    protected void save() {

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {

            writer.write("id,type,name,status,description,epic");
            writer.newLine();

            for (Task task : getAllTasks()) {
                writer.write(toString(task));
                writer.newLine();  // Каждая задача в новой строке
            }

            // Сохраняем эпики
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic));
                writer.newLine();
            }

            // Сохраняем подзадачи для каждого эпика
            for (Epic epic : getAllEpics()) {
                for (Integer subTaskId : epic.getSubTasksId()) {
                    SubTask subTask = getSubTask(subTaskId);
                    writer.write(toString(subTask));
                    writer.newLine();
                }
            }

            writer.newLine();

            // Сохраняем историю
            String history = historyToString(getHistoryManager());
            writer.write(history);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных");
        }
    }

    private String toString(Task task) {
        String base = task.getId() + "," +
                getTaskType(task) + "," +
                task.getTitle() + "," +
                task.getStatus() + "," +
                task.getDescription();

        if (task instanceof SubTask) {
            SubTask subTask = (SubTask) task;
            return base + "," + subTask.getEpicId();
        } else {
            return base + ",";
        }
    }

    private TaskType getTaskType(Task task) {
        if (task instanceof Epic) return TaskType.EPIC;
        if (task instanceof SubTask) return TaskType.SUBTASK;
        return TaskType.TASK;
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");

        int id = Integer.parseInt(fields[0]);
        TaskType type = TaskType.valueOf(fields[1]);
        String title = fields[2];
        Status status = Status.valueOf(fields[3]);
        String description = fields[4];

        switch (type) {
            case TASK:
                return new Task(id, title, description, status);
            case EPIC:
                return new Epic(id, title, description, status);
            case SUBTASK:
                int epicId = Integer.parseInt(fields[5]);
                return new SubTask(id, title, description, status, epicId);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < history.size(); i++) {
            builder.append(history.get(i).getId());
            if (i < history.size() - 1) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public static List<Integer> historyFromString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        }

        String[] ids = value.split(",");
        List<Integer> result = new ArrayList<>();
        for (String id : ids) {
            result.add(Integer.parseInt(id));
        }
        return result;
    }
}
