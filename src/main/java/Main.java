import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {


    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        TaskManager taskManager = new InMemoryTaskManager();


//        Task task1 = taskManager.createTask(1, "Task 1", "Описание задачи 1", Status.NEW);
//        Task task2 = taskManager.createTask(2, "Task 2", "Описание задачи 2", Status.NEW);
//        Epic epic1 = taskManager.createEpic(3, "Epic 1", "Описание Epic 3", Status.NEW);
//        SubTask subTask1 = taskManager.createSubTask(4, "SubTask 1", "Описание SubTask 4", Status.NEW, epic1.getId());
//        SubTask subTask2 = taskManager.createSubTask(5, "SubTask 2", "Описание SubTask 5", Status.NEW, epic1.getId());
//        SubTask subTask3 = taskManager.createSubTask(6, "SubTask 3", "Описание SubTask 6", Status.IN_PROGRESS, epic1.getId());
//        Epic epic2 = taskManager.createEpic(7, "Epic 2", "Описание Epic 7", Status.NEW);
//
//        taskManager.getTask(1);
//        task1.setStatus(Status.IN_PROGRESS);
//        printHistory(taskManager);
//
//        taskManager.getTask(2);
//        task2.setStatus(Status.IN_PROGRESS);
//        printHistory(taskManager);
//
//        taskManager.getEpic(3);
//        printHistory(taskManager);
//
//        taskManager.getSubTask(4);
//        subTask1.setStatus(Status.IN_PROGRESS);
//        printHistory(taskManager);
//
//        taskManager.getSubTask(5);
//        subTask2.setStatus(Status.IN_PROGRESS);
//        printHistory(taskManager);
//
//        taskManager.getSubTask(6);
//        subTask3.setStatus(Status.IN_PROGRESS);
//        printHistory(taskManager);
//
//        taskManager.getEpic(7);
//        printHistory(taskManager);
//
//        taskManager.getTask(1);
//        printHistory(taskManager);
//        taskManager.getTask(1);
//        printHistory(taskManager);
//        taskManager.getTask(1);
//        printHistory(taskManager);
//
//        taskManager.getTask(2);
//        printHistory(taskManager);
//        taskManager.removeTask(1);
//        printHistory(taskManager);
//
//        taskManager.removeEpic(3);
//        printHistory(taskManager);
//
//        taskManager.removeEpic(7);
//        printHistory(taskManager);
//    }
//
//    private static void printHistory(TaskManager taskManager) {
//        System.out.println("История просмотров:");
//        for (Task task : taskManager.getHistory()) {
//            System.out.println(task);
//        }
//        System.out.println("-------------------");
//    }


//        Task taskOne = new Task(1, "Задача №1", "Описание задачи №1", Status.NEW);
//        Task taskTwo = new Task(2,"Задача №2", "Описание задачи №2", Status.NEW);
//        Epic epicOne = new Epic(3,"Большая задача (эпик) №3", "Описание задачи №3",Status.NEW);
//        SubTask SubTaskOne = new SubTask(4,"Подзадача №4", "Описание подзадачи №4",Status.NEW, 3);
//        SubTask SubTaskTwo = new SubTask(5,"Подзадача №5", "Описание подзадачи №5", Status.NEW, 3);
//        Epic epicTwo = new Epic(6,"Большая задача (эпик) №6", "Описание задачи №6", Status.NEW);
//        SubTask SubTaskThree = new SubTask(7,"Подзадача №7", "Описание подзадачи №7", Status.NEW, 6);

//        Task task = inMemoryTaskManager.createTask(1,"Задача №1", "Описание задачи №1", Status.NEW);
//        Task task1 = inMemoryTaskManager.createTask(2,"Задача №2", "Описание задачи №2", Status.NEW);
//        Epic epic = inMemoryTaskManager.createEpic(3,"Большая задача (эпик) №3", "Описание задачи №3", Status.NEW);
//        SubTask subTask = inMemoryTaskManager.createSubTask(4,"Подзадача №4", "Описание подзадачи №4", Status.NEW, 3);
//        inMemoryTaskManager.createSubTask(5,"Подзадача №5", "Описание подзадачи №5", Status.NEW, 3);
//        inMemoryTaskManager.createEpic(6,"Большая задача (эпик) №6", "Описание задачи №6", Status.NEW);
//        inMemoryTaskManager.createSubTask(7,"Подзадача №7", "Описание подзадачи №7", Status.NEW, 6);
//
//        inMemoryTaskManager.getTask(1);
//        System.out.println(inMemoryTaskManager.getHistory());
//        task.setStatus(Status.IN_PROGRESS);
//        inMemoryTaskManager.getEpic(6);
//        System.out.println(inMemoryTaskManager.getHistory());
//        inMemoryTaskManager.getSubTask(4);
//        System.out.println(inMemoryTaskManager.getHistory());
//        subTask.setStatus(Status.IN_PROGRESS);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        inMemoryTaskManager.getEpic(6);
//        System.out.println(inMemoryTaskManager.getHistory());
//        inMemoryTaskManager.getSubTask(7);
//        System.out.println(inMemoryTaskManager.getHistory());

        File file = new File("tasks.csv");
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        Task task1 = manager.createTask(1, "Task1", "Description1", Status.NEW);
        Epic epic1 = manager.createEpic(2, "Epic1", "Epic description", Status.NEW);
        SubTask subTask1 = manager.createSubTask(3, "Sub1", "Sub description", Status.DONE, 2);

        manager.getTask(1);
        manager.getEpic(2);
        manager.getSubTask(3);

        // Менеджер автоматически сохраняет всё в файл
        System.out.println("Менеджер сохранён");

        System.out.println(" ");

        try {
            String s = Files.readString(Path.of(String.valueOf(file)));
            System.out.println("Содержимое файла tasks:");
            System.out.println(s);
        } catch (IOException e) {
            throw new ManagerSaveException("Файл не найден или пуст");
        }
    }
}



