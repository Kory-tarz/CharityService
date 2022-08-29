package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@Table(name = "donations")
@Entity
public class Donation extends BaseEntity {

    @Column(name = "quantity")
    @Min(value = 1)
    private int quantity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "donation_id")
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "street")
    @NotEmpty
    private String street;

    @Column(name = "city")
    @NotEmpty
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "pick_up_date")
    private LocalDate pickUpDate;

    @Column(name = "pick_up_time")
    private LocalTime pickUpTime;

    @Column(name = "pick_up_comment")
    private String pickUpComment;

}
