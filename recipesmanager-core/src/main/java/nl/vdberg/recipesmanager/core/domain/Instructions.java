package nl.vdberg.recipesmanager.core.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INSTRUCTIONS")
public class Instructions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String step;

    @NonNull
    private String description;

    @ManyToOne
    private Recipe recipe;

}
