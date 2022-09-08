package pl.coderslab.charity.institution;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Table(name = "institutions")
@Entity
@Audited
public class Institution extends BaseEntity {

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
