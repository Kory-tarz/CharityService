package pl.coderslab.charity.donation.status;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "status")
public class Status extends BaseEntity {

    @Column(name = "name")
    private String name;
}
