import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    Task createTask(String title, String description, Status status);

    Epic createEpic(String title, String description, Status status);

    SubTask createSubTask(String title, String description, Status status, int epicId);

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Integer> getAllSubTasks(int epicID);

    void printAllTasks();

    List<SubTask> getListSubTasksOfEpic(int epicID);

}

