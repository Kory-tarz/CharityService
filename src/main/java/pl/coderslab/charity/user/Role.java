package pl.coderslab.charity.user;


import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;
}
