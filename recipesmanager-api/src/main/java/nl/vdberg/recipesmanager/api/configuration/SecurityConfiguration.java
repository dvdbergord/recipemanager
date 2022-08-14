package nl.vdberg.recipesmanager.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .antMatchers(HttpMethod.DELETE, "/api/recipe/**").hasRole("USER")
                                .antMatchers(HttpMethod.POST, "/api/recipe").hasRole("USER")
                                .antMatchers(HttpMethod.PUT, "/api/recipe/**").hasRole("USER")
                                .antMatchers(HttpMethod.GET, "/api/recipe/**", "/api/recipe").hasRole("USER")
                )
                .httpBasic(withDefaults())
                .csrf().disable()
                .formLogin().disable();
        return http.build();
    }

}
