package pl.coderslab.charity.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Setter
@Getter
@Entity(name = "users")
public class User extends BaseEntity {

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
