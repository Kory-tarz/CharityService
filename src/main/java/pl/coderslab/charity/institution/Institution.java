package pl.coderslab.charity.institution;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Table(name = "institutions")
@Entity
public class Institution extends BaseEntity {

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
