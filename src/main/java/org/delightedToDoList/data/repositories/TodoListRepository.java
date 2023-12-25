package org.delightedToDoList.data.repositories;

import org.delightedToDoList.data.model.Task;
import org.delightedToDoList.data.model.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoListRepository extends MongoRepository<TodoList,String> {
    TodoList findByUsername(String username);

}
