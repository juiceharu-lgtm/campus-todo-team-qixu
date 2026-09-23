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

    //====实验#1新增：按优先级筛选，修复null判断，抛出非法参数异常====
    /**
     * 根据优先级筛选任务
     * @param priority 筛选优先级，不允许传入null
     * @return 匹配优先级的任务列表
     * @throws IllegalArgumentException priority为null抛出异常
     */
    public List<Task> filterByPriority(Task.Priority priority){
        if(priority == null){
            throw new IllegalArgumentException("priority不能为null");
        }
        return tasks.stream()
            .filter(t -> t.getPriority() == priority)
            .toList();
    }
}
