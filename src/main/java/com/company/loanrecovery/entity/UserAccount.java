package com.company.loanrecovery.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user_accounts", uniqueConstraints=@UniqueConstraint(name="uk_user_tenant_username", columnNames={"tenant_id","username"}))
public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
 private Long id;
 @Column(name="tenant_id",nullable=false)
 private Long tenantId;
 @Column(nullable=false,length=80)
 private String username;
 @Column(name="password_hash",nullable=false)
 private String passwordHash;
 @Column(nullable=false,length=40)
 private String role;
 @Column(nullable=false)
 private boolean enabled=true;
 @Column(name="employee_id")
 private Long employeeId;
 @Column(name="created_at",nullable=false)
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){
  return id;
 }
 public Long getTenantId(){
  return tenantId;
 }
 public void setTenantId(Long v){
  tenantId=v;
 }
 public String getUsername(){
  return username;
 }
 public void setUsername(String v){
  username=v;
 }
 public String getPasswordHash(){
  return passwordHash;
 }
 public void setPasswordHash(String v){
  passwordHash=v;
 }
 public String getRole(){
  return role;
 }
 public void setRole(String v){
  role=v;
 }
 public boolean isEnabled(){
  return enabled;
 }
 public void setEnabled(boolean v){
  enabled=v;
 }
 public Long getEmployeeId(){
  return employeeId;
 }
 public void setEmployeeId(Long v){
  employeeId=v;
 }
 public LocalDateTime getCreatedAt(){
  return createdAt;
 }
}
