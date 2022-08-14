package nl.vdberg.recipesmanager.core.domain.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonIngredients {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String quantity;

}
