package edu.hbuas.campustodo.service;
import edu.hbuas.campustodo.model.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务应用服务。学生将在功能分支中逐步扩展该类。
 */
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;
    public Task addTask(String title) {
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }
    public List<Task> listAll() {
        return List.copyOf(tasks);
    }

    //====实验#1新增：按优先级筛选，故意不判null，便于后续评审指出问题====
    public List<Task> filterByPriority(Task.Priority priority){
        return tasks.stream()
            .filter(t -> t.getPriority() == priority)
            .toList();
    }
}
