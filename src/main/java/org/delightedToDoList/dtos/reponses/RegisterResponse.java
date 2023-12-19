package org.delightedToDoList.dtos.reponses;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data

public class RegisterResponse {
    private String message;
}
