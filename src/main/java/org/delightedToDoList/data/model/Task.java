package org.delightedToDoList.data.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean isCompleted = false;
    private String todoListId;

}
