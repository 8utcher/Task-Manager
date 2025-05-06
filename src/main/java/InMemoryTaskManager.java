import lombok.Getter;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subtasks = new HashMap<>();
    @Getter
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Task createTask(int id, String title, String description, Status status) {
        Task task = new Task(id, title, description, status);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(int id, String title, String description, Status status) {
        Epic epic = new Epic(id, title, description, status);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubTask(int id, String title, String description, Status status, int epicId) {
        SubTask subTask = new SubTask(id, title, description, status, epicId);
        subtasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubTask(subTask.getId());
            updateEpicStatus(epic);
        }
        return subTask;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) historyManager.add(epic);
        return epic;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask != null) historyManager.add(subTask);
        return subTask;
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
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            if (epic != null) {
                for (Integer subtaskId : epic.getSubTasksId()) {
                    subtasks.remove(subtaskId);
                    historyManager.remove(subtaskId);
                }
                historyManager.remove(id);
            }
        }
    }

    @Override
    public void removeSubTask(int id) {
        if (subtasks.containsKey(id)) {
            SubTask subTask = subtasks.remove(id);
            historyManager.remove(id);
            if (subTask != null) {
                Epic epic = epics.get(subTask.getEpicId());
                if (epic != null) {
                    epic.removeBySubTaskId(id);
                    updateEpicStatus(epic);
                }
            }
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
        return epic != null ? epic.getSubTasksId() : new ArrayList<>();
    }

    @Override
    public void printAllTasks() {
        tasks.values().forEach(System.out::println);
    }

    public List<SubTask> getListSubTasksOfEpic(int epicID) {
        List<SubTask> subTasks = new ArrayList<>();
        Epic epic = epics.get(epicID);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubTasksId()) {
                subTasks.add(subtasks.get(subTaskId));
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

        if (epic.getSubTasksId().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (Integer subTaskId : epic.getSubTasksId()) {
            SubTask subTask = subtasks.get(subTaskId);
            if (subTask != null) {
                if (subTask.getStatus() == Status.NEW) {
                    subTaskNew++;
                } else if (subTask.getStatus() == Status.DONE) {
                    subTaskDone++;
                }
            }
        }

        if (subTaskDone == epic.getSubTasksId().size()) {
            epic.setStatus(Status.DONE);
        } else if (subTaskNew == epic.getSubTasksId().size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

}

