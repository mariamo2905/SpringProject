package ge.ibsu.demo.entities.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
public enum Role {
    EMPLOYEE_ADMIN(List.of(Permission.EMPLOYEE_READ, Permission.EMPLOYEE_ADD)),
    EMPLOYEE_READ(List.of(Permission.EMPLOYEE_READ));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = permissions
                .stream()
                .map(i -> new SimpleGrantedAuthority(i.getKeyword()))
                .toList();

        //list.add(new SimpleGrantedAuthority(name()));

        return list;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
