package com.agatap.veshje.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String password;
    @Transient
    private String confirmPassword;
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    private Boolean subscribedNewsletter;
    private UserRole userRole;
    private boolean enabled;
    private OffsetDateTime createDate;
    private OffsetDateTime updateDate;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payments> userPayments = new ArrayList<>();
    @ManyToMany(mappedBy = "users")
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();
    @ManyToOne
    private Newsletter newsletter;
    @OneToMany(mappedBy = "userOrders")
    @Builder.Default
    private List<Orders> orders = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(userRole)));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
