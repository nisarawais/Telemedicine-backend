package com.Telemedicine.Telemedicine.Services;

import com.Telemedicine.Telemedicine.Models.Role;
import com.Telemedicine.Telemedicine.Models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();

}
