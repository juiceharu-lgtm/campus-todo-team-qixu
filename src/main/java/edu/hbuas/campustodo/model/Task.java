package edu.hbuas.campustodo.model;
import java.util.Objects;

/**
 * 校园待办任务。
 */
public class Task {
    //实验#1新增：优先级枚举
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    private final long id;
    private final String title;
    private boolean completed;
    //新增优先级字段
    private Priority priority;

    public Task(long id, String title) {
        if (id <= 0) {
            throw new IllegalArgumentException("任务编号必须为正数");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("任务标题不能为空");
        }
        this.id = id;
        this.title = title.trim();
        //新建任务默认MEDIUM
        this.priority = Priority.MEDIUM;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void complete() {
        completed = true;
    }

    //新增getter、setter
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task task)) {
            return false;
        }
        return id == task.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
