package restapi.spring.project.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import restapi.spring.project.Enum.Role;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")

@Data
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "TOKEN")
    private String token;    // sera Retirado ao implementar o Cache
    @Column(name = "expired_at")
    private Date expiredAt;

   /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )*/

    @Enumerated(EnumType.STRING)
    private Role role;


    //
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthoritites();
    }

    @Override
    public String getUsername() { // returns the email address because it is unique information about the user
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
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /*public void setRoles(Set<RolesModel> roles) {
        this.roles = roles;*/
    }
