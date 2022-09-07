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
    public DonationValidationResult valid(@RequestBody DonationDTO donation){
        return validationService.validateQuantity(donation);
    }

    @PostMapping("/institution")
    public DonationValidationResult validateInstitution(@RequestBody DonationDTO donation) {
        return validationService.validateInstitution(donation);
    }

    @PostMapping("/category")
    public DonationValidationResult validateCategories(@RequestBody DonationDTO donation) {
        return validationService.validateCategories(donation);
    }

    @PostMapping("/address")
    public DonationValidationResult validateAddress(@RequestBody DonationDTO donation) {
        return validationService.validateAddress(donation);
    }

}
