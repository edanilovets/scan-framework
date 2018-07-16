package com.scan.framework.customerportal.model;

import java.util.Objects;

public class UserData {
  private String login;
  private String name;
  private String email;
  private String password;
  
  public String getLogin() {
    return login;
  }
  
  public UserData withLogin(String login) {
    this.login = login;
    return this;
  }
  
  public String getName() {
    return name;
  }
  
  public UserData withName(String name) {
    this.name = name;
    return this;
  }
  
  public String getEmail() {
    return email;
  }
  
  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }
  
  public String getPassword() {
    return password;
  }
  
  public UserData withPassword(String password) {
    this.password = password;
    return this;
  }
  
  @Override
  public String toString() {
    return "UserData{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserData)) return false;
    UserData userData = (UserData) o;
    return Objects.equals(getLogin(), userData.getLogin()) &&
            Objects.equals(getName(), userData.getName()) &&
            Objects.equals(getEmail(), userData.getEmail());
  }
  
  @Override
  public int hashCode() {
    
    return Objects.hash(getLogin(), getName(), getEmail());
  }
}
