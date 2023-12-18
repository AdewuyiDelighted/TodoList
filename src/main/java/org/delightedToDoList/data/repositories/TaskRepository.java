package org.delightedToDoList.data.repositories;

import org.delightedToDoList.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> findAllById(String id);
}
