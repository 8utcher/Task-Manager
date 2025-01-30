public class Main {


    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
//        Task taskOne = new Task(1, "Задача №1", "Описание задачи №1", Status.NEW);
//        Task taskTwo = new Task(2,"Задача №2", "Описание задачи №2", Status.NEW);
//        Epic epicOne = new Epic(3,"Большая задача (эпик) №3", "Описание задачи №3",Status.NEW);
//        SubTask SubTaskOne = new SubTask(4,"Подзадача №4", "Описание подзадачи №4",Status.NEW, 3);
//        SubTask SubTaskTwo = new SubTask(5,"Подзадача №5", "Описание подзадачи №5", Status.NEW, 3);
//        Epic epicTwo = new Epic(6,"Большая задача (эпик) №6", "Описание задачи №6", Status.NEW);
//        SubTask SubTaskThree = new SubTask(7,"Подзадача №7", "Описание подзадачи №7", Status.NEW, 6);
        Task task = inMemoryTaskManager.createTask("Задача №1", "Описание задачи №1", Status.NEW); // Внесение всех задач, эпиков, подзадач
        inMemoryTaskManager.createTask("Задача №2", "Описание задачи №2", Status.NEW);
        Epic epic = inMemoryTaskManager.createEpic("Большая задача (эпик) №3", "Описание задачи №3", Status.NEW);
        SubTask subTask = inMemoryTaskManager.createSubTask("Подзадача №4", "Описание подзадачи №4", Status.NEW, 3);
        inMemoryTaskManager.createSubTask("Подзадача №5", "Описание подзадачи №5", Status.NEW, 3);
        inMemoryTaskManager.createEpic("Большая задача (эпик) №6", "Описание задачи №6", Status.NEW);
        inMemoryTaskManager.createSubTask("Подзадача №7", "Описание подзадачи №7", Status.IN_PROGRESS, 6);
        inMemoryTaskManager.getTask(1);
        System.out.println(inMemoryTaskManager.getHistory());
        task.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.getEpic(6);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubTask(4);
        System.out.println(inMemoryTaskManager.getHistory());
        subTask.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        inMemoryTaskManager.getEpic(6);
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getSubTask(7);
        System.out.println(inMemoryTaskManager.getHistory());
    }
}

