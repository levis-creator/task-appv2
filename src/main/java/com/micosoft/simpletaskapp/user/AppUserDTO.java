package com.micosoft.simpletaskapp.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
public class AppUserDTO {
    private UUID uuid;
    private String email;
    private String username;
    @Enumerated(EnumType.STRING)
    private AppRole appRole;

}
