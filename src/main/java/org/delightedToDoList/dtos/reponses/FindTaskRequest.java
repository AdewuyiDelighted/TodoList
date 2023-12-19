package org.delightedToDoList.dtos.reponses;

import lombok.Data;

@Data
public class FindTaskRequest {
    private String username;
    private String title;
    private String description;
}
