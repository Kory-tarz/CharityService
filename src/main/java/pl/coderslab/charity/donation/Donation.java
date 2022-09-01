package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@ToString
@Table(name = "donations")
@Entity
public class Donation extends BaseEntity {

    @Column(name = "quantity")
    @Min(value = 1)
    private int quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "donations_categories",
            joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    @NotNull
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @Column(name = "pick_up_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime pickUpTime;

    @Column(name = "pick_up_comment")
    private String pickUpComment;

}
