package com.hai.employeemanagement.service;

import com.hai.employeemanagement.entity.UserEntity;
import com.hai.employeemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (username.length() < 1 ) throw new BadCredentialsException("Bad credentials");
        UserEntity userEntity = userRepository.findOneByUsername(username);
        if (userEntity == null) throw new BadCredentialsException("Bad credentials");
        List<SimpleGrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        UserDetails userDetails = new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
//        if (!userEntity.isEnabled()) throw new Exception403("Unconfirmed account");
        return userDetails;
    }

    //    public boolean checkUserId(Long id) {
//        User user = accountService.loadUserByUsername(
//                SecurityContextHolder.getContext().getAuthentication().getName());
//        Long employeeID = (user.get() == null) ? 0 : user.getStudent().getId();
//        Long teacherID = (user.getTeacher() == null) ? 0 : user.getTeacher().getId();
//        Long userId = studentID + teacherID;
//        return userId == id || SecurityContextHolder.getContext().getAuthentication()
//                .getAuthorities().toString().contains("ADMIN");
//    }

}
