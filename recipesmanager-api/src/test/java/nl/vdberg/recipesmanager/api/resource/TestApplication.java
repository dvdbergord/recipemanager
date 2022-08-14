package nl.vdberg.recipesmanager.api.resource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"nl.vdberg.recipesmanager.core.service", "nl.vdberg.recipesmanager.core.converter"})
@EntityScan("nl.vdberg.recipesmanager.core.domain")
@EnableJpaRepositories("nl.vdberg.recipesmanager.core.repository")
public class TestApplication {


}