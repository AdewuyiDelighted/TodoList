package org.delightedToDoList.dtos.reponses;

import lombok.Data;
import org.delightedToDoList.data.model.Task;

import java.util.List;

@Data
public class FindAllTaskResponse {
    private List<Task> tasks;
}
