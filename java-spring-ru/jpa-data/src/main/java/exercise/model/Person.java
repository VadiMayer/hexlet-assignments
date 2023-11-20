package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// BEGIN
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table()
public class Person {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column()
    private Long id;
    @Column()
    private String firstName;
    @Column()
    private String lastName;
}
// END
