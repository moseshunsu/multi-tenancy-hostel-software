package net.hostelHub.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.hostelHub.utils.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    OCCUPANT_READ,
                    OCCUPANT_UPDATE,
                    OCCUPANT_DELETE,
                    OCCUPANT_CREATE
            )
    ),
    OCCUPANT(
                    Set.of(
                            OCCUPANT_READ,
                            OCCUPANT_UPDATE,
                            OCCUPANT_DELETE,
                            OCCUPANT_CREATE
                    )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
