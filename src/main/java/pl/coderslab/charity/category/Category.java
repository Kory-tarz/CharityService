package pl.coderslab.charity.category;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "categories")
@Audited
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;
}
