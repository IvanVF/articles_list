package com.fprojects.articles_list.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Entity of user
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    /**
     * Bcrypted id database
     */
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<RoleEntity> roles;

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     * true if the user's account is valid (ie non-expired), false if no longer valid (ie expired)
     */
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     * true if the user is not locked, false otherwise
     */
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication
     */
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     * true if the user is enabled, false otherwise
     */
    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

}
