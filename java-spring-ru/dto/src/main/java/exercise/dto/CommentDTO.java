package exercise.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class CommentDTO {

    private long id;

    private String body;
}
// END
