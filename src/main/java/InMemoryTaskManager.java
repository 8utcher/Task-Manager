import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, SubTask> subtasks = new HashMap<>();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    private int idCounter = 1;


    @Override
    public Task createTask(String title, String description, Status status) {
        Task task = new Task(idCounter++, title, description, status);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(String title, String description, Status status) {
        Epic epic = new Epic(idCounter++, title, description, status);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(String title, String description, Status status, int epicId) {
        SubTask subTask = new SubTask(idCounter++, title, description, status, epicId);
        subtasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(epicId);
        epic.addSubTask(subTask.getId());
        updateEpicStatus(epic);
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
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subtasks.containsKey(subTask.getId())) {
            subtasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            updateEpicStatus(epic);
        }
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {

        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            List<Integer> subTaskList = epic.getSubTasksId();
            for (Integer subtaskId : subTaskList) {
                subtasks.remove(subtaskId);
            }
        }
    }

    @Override
    public void removeSubTask(int id) {

        if (subtasks.containsKey(id)) {
            SubTask subTask = subtasks.get(id);
            subtasks.remove(id);
            int epicId = subTask.getEpicId();
            Epic epic = epics.get(epicId);
            epic.removeBySubTaskId(id);
            updateEpicStatus(epic);
        }
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

    @Override
    public List<SubTask> getListSubTasksOfEpic(int epicID) {
        List<SubTask> subTasks = null;

        if (epics.containsKey(epicID)) {
            Epic epic = epics.get(epicID);
            List<Integer> subTaskList = epic.getSubTasksId();
            subTasks = new ArrayList<>();
            for (Integer subTask : subTaskList) {
                subTasks.add(subtasks.get(subTask));
            }
        }

        return subTasks;
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void updateEpicStatus(Epic epic) {
        int subTaskNew = 0;
        int subTaskDone = 0;
        int subTaskInProgress = 0;

        if (epic.getSubTasksId().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (Integer subTaskId : epic.getSubTasksId()) {
            if (subtasks.get(subTaskId).getStatus() == Status.NEW) {
                subTaskNew++;
            } else if (subtasks.get(subTaskId).getStatus() == Status.DONE) {
                subTaskDone++;
            } else if (subtasks.get(subTaskId).getStatus() == Status.IN_PROGRESS) {
                subTaskInProgress++;
            }
        }

        System.out.println("subTaskNew: " + subTaskNew + ", subTaskDone: " + subTaskDone + ", subTaskInProgress: " + subTaskInProgress);
        if (subTaskDone == epic.getSubTasksId().size()) {
            epic.setStatus(Status.DONE);
        } else if (subTaskNew == epic.getSubTasksId().size()) {
            epic.setStatus(Status.NEW);
        } else if (subTaskInProgress > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(Status.NEW);
        }

        System.out.println("Epic status: " + epic.getStatus());
    }
}

