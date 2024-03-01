package com.micosoft.simpletaskapp.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AppRole {
    ADMIN("admin"),
    USER("user");
    private  final String permission;


}
