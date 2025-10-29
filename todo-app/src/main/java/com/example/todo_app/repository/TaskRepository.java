package com.example.todo_app.repository;/*
package com.example.todo_app.repository;

import com.example.todo_app.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
*/
import com.example.todo_app.models.Task;
import com.example.todo_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
