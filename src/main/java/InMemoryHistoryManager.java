import java.util.ArrayList;
import java.util.List;

    public class InMemoryHistoryManager implements HistoryManager {
        private List<Task> history = new ArrayList();

        public InMemoryHistoryManager() {
        }

        public void addTask(Task task) {
            if (this.history.size() == 10) {
                this.history.remove(0);
                this.history.add(task);
            } else {
                this.history.add(task);
            }

        }

        public List<Task> getHistory() {
            return this.history;
        }
    }
