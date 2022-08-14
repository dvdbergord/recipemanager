package nl.vdberg.recipesmanager.api.configuration;

import nl.vdberg.recipesmanager.core.configuation.CoreConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreConfiguration.class)
public class ApiConfiguration {

}
