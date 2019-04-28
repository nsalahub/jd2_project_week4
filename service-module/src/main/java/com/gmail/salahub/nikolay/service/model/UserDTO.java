package com.gmail.salahub.nikolay.service.model;

import com.gmail.salahub.nikolay.repository.model.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {
    private Long id;
    @NotNull
    @Size(max = 70)
    private String userName;
    @NotNull
    @Size(max = 60)
    private String password;
    private Role role;

    public UserDTO() {
        role = new Role();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
