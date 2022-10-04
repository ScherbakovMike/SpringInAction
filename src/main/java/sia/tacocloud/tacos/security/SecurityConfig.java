package sia.tacocloud.tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.tacos.data.UserRepository;

@Configuration
//@EnableGlobalMethodSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return username -> {
      var user = userRepository.findByUsername(username);
      if (user != null) return user;

      throw new UsernameNotFoundException("User '" + username + "' not found!");
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeRequests()
            .antMatchers("/design", "/orders").access("hasRole('USER')")
            .antMatchers(HttpMethod.POST, "/ingredients").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("ADMIN")
            .antMatchers("/", "/**").permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            //.and()
            //.cors().and().csrf().disable()
            .and()
            .logout()
            .logoutSuccessUrl("/design")
            .and()
            .build();
  }

}
