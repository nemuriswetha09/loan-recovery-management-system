package com.company.loanrecovery.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwt;
 public JwtAuthenticationFilter(JwtService jwt){this.jwt=jwt;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null && h.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication()==null){
   try{
    var c=jwt.parse(h.substring(7)); String username=c.getSubject(); String role=c.get("role",String.class);
    var auth=new UsernamePasswordAuthenticationToken(username,null,java.util.List.of(new SimpleGrantedAuthority(role.startsWith("ROLE_")?role:"ROLE_"+role)));
    auth.setDetails(c); SecurityContextHolder.getContext().setAuthentication(auth);
   }catch(Exception ignored){SecurityContextHolder.clearContext();}
  }
  chain.doFilter(req,res);
 }
}
