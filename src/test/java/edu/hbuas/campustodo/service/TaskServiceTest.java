package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();
        var task = service.addTask("完成需求评审");

        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class,
            () -> service.addTask(" "));
    }

    // 1.正常完成任务
    @Test
    void shouldCompleteTaskSuccess() {
        TaskService service = new TaskService();
        var task = service.addTask("写软件工程作业");
        service.completeTask(task.getId());
        assertTrue(task.isCompleted());
    }

    //2.不存在任务id抛出异常
    @Test
    void shouldThrowWhenIdNotExist() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class,
            () -> service.completeTask(999L));
    }

    //3.重复完成已完成任务抛出异常（实验必做！）
    @Test
    void shouldThrowWhenCompleteAlreadyDoneTask() {
        TaskService service = new TaskService();
        var task = service.addTask("复习考试");
        service.completeTask(task.getId());
        //第二次完成，需要抛出IllegalStateException
        assertThrows(IllegalStateException.class,
            () -> service.completeTask(task.getId()));
    }

    @Test
    void newTaskShouldDefaultToMediumPriority() {
        TaskService service = new TaskService();

        Task task = service.addTask("复习软件工程");

        assertEquals(Task.Priority.MEDIUM, task.getPriority());
    }

    @Test
    void filterByPriorityShouldReturnMatchedTasks() {
        TaskService service = new TaskService();
        Task highPriorityTask = service.addTask("高优先级任务");
        highPriorityTask.setPriority(Task.Priority.HIGH);
        Task mediumPriorityTask = service.addTask("普通任务");
        Task lowPriorityTask = service.addTask("低优先级任务");
        lowPriorityTask.setPriority(Task.Priority.LOW);

        assertEquals(List.of(highPriorityTask), service.filterByPriority(Task.Priority.HIGH));
        assertEquals(List.of(mediumPriorityTask), service.filterByPriority(Task.Priority.MEDIUM));
        assertEquals(List.of(lowPriorityTask), service.filterByPriority(Task.Priority.LOW));
    }

    @Test
    void filterByPriorityNoMatchShouldReturnEmptyList() {
        TaskService service = new TaskService();
        service.addTask("普通任务");

        assertTrue(service.filterByPriority(Task.Priority.HIGH).isEmpty());
    }

    @Test
    void filterByPriorityNullShouldThrowException() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
            () -> service.filterByPriority(null));
    }
}
