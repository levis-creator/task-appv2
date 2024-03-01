package com.micosoft.simpletaskapp.user;

import com.micosoft.simpletaskapp.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
    @Autowired
    private final AppUserRepository appUserRepository;


    public ResponseEntity<AppUser> createAppUser(AppUser appUser){
        SecurityConfig securityConfig=new SecurityConfig();
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return new  ResponseEntity<>(appUserRepository.save(appUser), HttpStatus.CREATED);
    }
    public List<AppUserDTO> allAppUsers(){
        List<AppUserDTO> appUserDTOS =new ArrayList<>();
        List<AppUser> appUsers=appUserRepository.findAll();
        for(AppUser appUser: appUsers){
            AppUserDTO appUserDTO=new AppUserDTO(appUser.getUuid(),
                    appUser.getEmail(),
                    appUser.getUsername(),
                    appUser.getAppRole());
            appUserDTOS.add(appUserDTO);
        }
        return ResponseEntity.ok(appUserDTOS).getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=appUserRepository.findByUsernameOrEmail(username, username).orElse(null);
        if (appUser!=null){
            return appUser;
        }else {
            throw new UsernameNotFoundException("invalid user name or password");
        }
    }

}
