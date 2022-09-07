package pl.coderslab.charity.donation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

import static pl.coderslab.charity.donation.DonationValidationResult.*;

@FunctionalInterface
public interface DonationValidator extends Function<DonationDTO, DonationValidationResult> {

    static DonationValidator isQuantityValid() {
        return donation -> donation.getQuantity() > 0 ? SUCCESS : INVALID_QUANTITY;
    }

    static DonationValidator isInstitutionValid() {
        return donation -> donation.getInstitution() > 0 ? SUCCESS : NO_INSTITUTION;
    }

    static DonationValidator areCategoriesValid() {
        return donation -> (donation.getCategories() != null && donation.getCategories().length > 0) ? SUCCESS : NO_CATEGORY;
    }

    static DonationValidator isZipCodeValid() {
        return donation -> donation.getZipCode().matches("\\d{2}-\\d{3}") ? SUCCESS : INVALID_ZIP_CODE;
    }

    static DonationValidator isStreetValid() {
        return donation -> !donation.getStreet().isBlank() ? SUCCESS : INVALID_STREET;
    }

    static DonationValidator isCityValid() {
        return donation -> !donation.getCity().isBlank() ? SUCCESS : INVALID_CITY;
    }

    static DonationValidator isDateValid() {
        return donation ->
                donation.getLocalDate().minusDays(3).compareTo(LocalDate.now()) > 0
                        ? SUCCESS : INVALID_DATE;
    }

    static DonationValidator isTimeValid() {
        return donation -> donation.getLocalTime().isAfter(LocalTime.of(5, 59))
                && donation.getLocalTime().isBefore(LocalTime.of(19, 1))
                ? SUCCESS : INVALID_TIME;
    }

    default DonationValidator and(DonationValidator other) {
        return donation -> {
            DonationValidationResult result = this.apply(donation);
            return result.equals(SUCCESS) ? other.apply(donation) : result;
        };
    }
}
