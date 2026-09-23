package edu.hbuas.campustodo.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}
