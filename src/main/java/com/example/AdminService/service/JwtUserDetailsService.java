package com.example.AdminService.service;

import com.example.AdminService.entity.Admin;
import com.example.AdminService.interfaces.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUserName(username);
        if(admin == null)throw new UsernameNotFoundException("User not found");
        return new User(admin.getUsername(), admin.getPassword(), true, true, true, true, new ArrayList<>());
    }
}
