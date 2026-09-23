package edu.hbuas.campustodo.service;
import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.Test;
import java.util.List;
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
            () -> service.addTask("   "));
    }

    //====A角色新增测试用例====
    @Test
    void newTaskShouldDefaultToMediumPriority(){
        TaskService service = new TaskService();
        Task task = service.addTask("复习软件工程");
        assertEquals(Task.Priority.MEDIUM, task.getPriority());
    }

    @Test
    void filterByPriorityShouldReturnMatchedTasks(){
        TaskService service = new TaskService();
        Task t1 = service.addTask("高优先级任务");
        t1.setPriority(Task.Priority.HIGH);
        service.addTask("普通任务");
        Task t3 = service.addTask("低优先级任务");
        t3.setPriority(Task.Priority.LOW);
        List<Task> high = service.filterByPriority(Task.Priority.HIGH);
        List<Task> medium = service.filterByPriority(Task.Priority.MEDIUM);
        List<Task> low = service.filterByPriority(Task.Priority.LOW);
        assertEquals(1, high.size());
        assertEquals(1, medium.size());
        assertEquals(1, low.size());
    }

    @Test
    void filterByPriorityNoMatchReturnEmptyList(){
        TaskService service = new TaskService();
        service.addTask("普通任务");
        List<Task> highList = service.filterByPriority(Task.Priority.HIGH);
        assertTrue(highList.isEmpty());
    }

    // =========评审要求新增：传入null抛出异常的测试=========
    @Test
    void filterByPriorityNullShouldThrowException(){
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.filterByPriority(null));
    }
}
