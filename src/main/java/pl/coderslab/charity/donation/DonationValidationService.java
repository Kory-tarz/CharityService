package pl.coderslab.charity.donation;

import org.springframework.stereotype.Service;

import static pl.coderslab.charity.donation.DonationValidator.*;

@Service
public class DonationValidationService {

    public DonationValidationResult validateQuantity(DonationDTO donation){
        return isQuantityValid().apply(donation);
    }

    public DonationValidationResult validateInstitution(DonationDTO donation){
        return isInstitutionValid().apply(donation);
    }

    public DonationValidationResult validateCategories(DonationDTO donation) {
        return areCategoriesValid().apply(donation);
    }

    public DonationValidationResult validateAddress(DonationDTO donation){
        return isStreetValid()
                .and(isCityValid())
                .and(isZipCodeValid())
                .and(isDateValid())
                .and(isTimeValid())
                .apply(donation);
    }
}
