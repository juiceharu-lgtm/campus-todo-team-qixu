package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class TaskService {
    // 内存模拟存储
    private final Map<Long, Task> taskStore = new HashMap<>();
    // 自动生成任务id
    private final AtomicLong nextId = new AtomicLong(1);

    /**
     * 新增任务，返回创建好的Task
     */
    public Task addTask(String title) {
        long id = nextId.getAndIncrement();
        Task task = new Task(id, title);
        taskStore.put(id, task);
        return task;
    }

    /**
     * 查询全部任务
     */
    public List<Task> listAll() {
        return new ArrayList<>(taskStore.values());
    }

    /**
     * 将指定id任务标记为完成
     * @param taskId 任务id
     * @return 更新完成后的Task
     */
    public Task completeTask(long taskId) {
        Optional<Task> taskOptional = Optional.ofNullable(taskStore.get(taskId));
        if (taskOptional.isEmpty()) {
            // 任务不存在，抛出 IllegalArgumentException，匹配测试预期
            throw new IllegalArgumentException("任务不存在");
        }
        Task task = taskOptional.get();
        if(task.isCompleted()){
            // 任务已完成，禁止重复完成，抛出 IllegalStateException
            throw new IllegalStateException("任务已经完成，不能再次完成");
        }
        task.complete();
        taskStore.put(taskId, task);
        return task;
    }

    public Task getTaskById(long taskId) {
        return Optional.ofNullable(taskStore.get(taskId))
            .orElseThrow(() -> new IllegalArgumentException("任务不存在"));
    }

    // 给测试用，存入任务到内存
    public void save(Task task) {
        taskStore.put(task.getId(), task);
    }

    public List<Task> filterByPriority(Task.Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("任务优先级不能为空");
        }
        return taskStore.values().stream()
            .filter(task -> task.getPriority() == priority)
            .toList();
    }
}
