package myapps.bookloan_app.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                //
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )

                // 🖼 Allow H2 console frames
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                .authorizeHttpRequests(auth -> auth

                        //H2 console
                        .requestMatchers("/h2-console/**").permitAll()

                        // public
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                        // USER + ADMIN
                        .requestMatchers("/loans/my").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/books").hasAnyRole("ADMIN", "USER")
                        // USER only
                        .requestMatchers("/wishlist/**").hasRole("USER")

                        // ADMIN only
                        .requestMatchers("/books/add").hasRole("ADMIN")
                        .requestMatchers("/books/edit/**").hasRole("ADMIN")
                        .requestMatchers("/books/borrow/**").hasRole("ADMIN")
                        .requestMatchers("/loans/borrow").hasRole("ADMIN")
                        .requestMatchers("/loans/return/**").hasRole("ADMIN")
                        .requestMatchers("/loans").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/books", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}
