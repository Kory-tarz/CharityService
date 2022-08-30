package pl.coderslab.charity.donation;

import org.springframework.stereotype.Service;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public long getTotalQuantityOfDonations(){
        return donationRepository.totalQuantityDonated();
    }

    public long getTotalDonationsCount(){
        return 1L;
    }
}
