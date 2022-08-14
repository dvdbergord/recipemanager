package nl.vdberg.recipesmanager.core.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INGREDIENTS")
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String quantity;

    @ManyToOne
    private Recipe recipe;


}
