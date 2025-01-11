import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subtasks = new HashMap<>();
    private int idCounter = 1;

    public Task createTask(String title, String description, Task.Status status) {
        Task task = new Task(idCounter++, title, description, status);
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(String title, String description, Task.Status status) {
        Epic epic = new Epic(idCounter++, title, description, status);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubTask(String title, String description, Task.Status status, Epic epic) {
        SubTask subTask = new SubTask(idCounter++, title, description, status, epic);
        subtasks.put(subTask.getId(), subTask);
        epic.addSubTask(subTask);
        return subTask;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public SubTask getSubTask(int id) {
        return subtasks.get(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask);
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeEpic(int id) {
        epics.remove(id);
    }

    public void removeSubTask(int id) {
        subtasks.remove(id);
    }
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<SubTask> getAllSubTasks(int epicID) {
        Epic epic = epics.get(epicID);
        if(epic != null) {
            return epic.getSubTasks();
        }
        return new ArrayList<>();
    }

    public void printAllTasks() {
        tasks.values().forEach(System.out::println);
    }
}
