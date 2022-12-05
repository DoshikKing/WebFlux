package app.web.webflux.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("todo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class Todo {
    @Id
    @Column("id")
    private Long id;

    @Column("comment")
    private String comment;

}
