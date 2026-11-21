package com.company.loanrecovery.security;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableMethodSecurity
public class SecurityConfig {
 @Bean
 PasswordEncoder passwordEncoder(){
  return new BCryptPasswordEncoder(12);
 }
 @Bean
 SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter filter)throws Exception{
  return http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/login","/api/auth/refresh","/actuator/health").permitAll().anyRequest().authenticated())
   .exceptionHandling(e->e.authenticationEntryPoint((r,s,x)->s.sendError(401,"Unauthorized")).accessDeniedHandler((r,s,x)->s.sendError(403,"Forbidden")))
   .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
 }
}
