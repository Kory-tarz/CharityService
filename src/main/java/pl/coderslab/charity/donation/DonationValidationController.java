package pl.coderslab.charity.donation;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/donation")
public class DonationValidationController {

    private final DonationValidationService validationService;

    public DonationValidationController(DonationValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/quantity")
    public DonationValidationResult valid(@RequestBody DonationDTO donationDto){
        return validationService.validateQuantity(donationDto);
    }

    @GetMapping("/quantity/{quantity}")
    public DonationValidationResult validateQuantity(@PathVariable int quantity) {
        DonationDTO donation = new DonationDTO();
        donation.setQuantity(quantity);
        return validationService.validateQuantity(donation);
    }

    @GetMapping("/institution/{id}")
    public DonationValidationResult validateInstitution(@PathVariable long id) {
        DonationDTO donation = new DonationDTO();
        donation.setInstitution(id);
        return validationService.validateInstitution(donation);
    }

    @GetMapping("/category/{categories}")
    public DonationValidationResult validatorCategories(@PathVariable Optional<long[]> categories) {
        DonationDTO donation = new DonationDTO();
        donation.setCategories(categories.orElse(new long[0]));
        return validationService.validateCategories(donation);
    }

    @GetMapping("/category/")
    public DonationValidationResult validatorCategories() {
        DonationDTO donation = new DonationDTO();
        return validationService.validateCategories(donation);
    }

    @GetMapping("/address/{city}/{street}/{zipCode}/{date}/{time}")
    public DonationValidationResult validateAddress(
            @PathVariable String city,
            @PathVariable String street,
            @PathVariable String zipCode,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @PathVariable @DateTimeFormat(pattern = "HH:mm") LocalTime time) {
        DonationDTO donation = new DonationDTO();
        donation.setCity(city);
        donation.setStreet(street);
        donation.setZipCode(zipCode);
        donation.setLocalDate(date);
        donation.setLocalTime(time);
        return validationService.validateAddress(donation);
    }

}
