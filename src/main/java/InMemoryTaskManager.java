import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subtasks = new HashMap<>();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private int idCounter = 1;


    @Override
    public Task createTask(String title, String description, Task.Status status) {
        Task task = new Task(idCounter++, title, description, status);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(String title, String description, Task.Status status) {
        Epic epic = new Epic(idCounter++, title, description, status);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(String title, String description, Task.Status status, Epic epic) {
        SubTask subTask = new SubTask(idCounter++, title, description, status, epic);
        subtasks.put(subTask.getId(), subTask);
        epic.addSubTask(subTask.getId());
        return subTask;
    }

    @Override
    public Task getTask(int id) {
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addTask(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.addTask(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        epics.remove(id);
    }

    @Override
    public void removeSubTask(int id) {
        subtasks.remove(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Integer> getAllSubTasks(int epicID) {
        Epic epic = epics.get(epicID);
        if (epic != null) {
            return epic.getSubTasksId();
        }
        return new ArrayList<>();
    }

    @Override
    public void printAllTasks() {
        tasks.values().forEach(System.out::println);
    }
}

