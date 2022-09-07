package pl.coderslab.charity.donation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class DonationDTO {
    private long institution;
    private long[] categories;
    private int quantity;
    private String street;
    private String city;
    private String zipCode;
    private LocalDate localDate;
    private LocalTime localTime;
    private String pickUpComment;
}
