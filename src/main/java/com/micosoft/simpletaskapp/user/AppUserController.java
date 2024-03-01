package com.micosoft.simpletaskapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {

    @Autowired
    private final AppUserService userService;
    @PostMapping("/create-user")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser){
        return userService.createAppUser(appUser);
    }
    @GetMapping("/fetch-users")
    public List<AppUserDTO> getallusers(){
        return userService.allAppUsers();
    }



}
