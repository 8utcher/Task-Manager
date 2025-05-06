import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;
    Map<Integer, Node> historyMap = new HashMap<>();

    private static class Node {
        Task task;
        Node next;
        Node prev;

        public Node(Task task) {
            this.task = task;
        }
    }

    @Override
    public void add(Task task){
        if (task == null) return;

        remove(task.getId()); // Удаляем старый просмотр, если он есть

        Node newNode = new Node(task);
        linkLast(newNode);
        historyMap.put(task.getId(), newNode);

    }

    private void linkLast(Node node) {
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    @Override
    public void remove(int id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

//    public void addTask(Task task) {
//        if (task == null) {
//            throw new IllegalArgumentException("Task не может быть равен null");
//        }
//        if (history.size() == 10) {
//            history.remove(0);
//        }
//        history.add(task);
//    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }
}

