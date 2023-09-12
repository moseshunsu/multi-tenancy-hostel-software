package net.hostelHub.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),
    OCCUPANT_READ("occupant:read"),
    OCCUPANT_UPDATE("occupant:update"),
    OCCUPANT_CREATE("occupant:create"),
    OCCUPANT_DELETE("occupant:delete")

    ;

    @Getter
    private final String permission;


}
