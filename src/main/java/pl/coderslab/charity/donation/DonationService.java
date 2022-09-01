package pl.coderslab.charity.donation;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public long getTotalQuantityOfDonations(){
        return donationRepository.totalQuantityDonated();
    }

    public long getDonationsCount(){
        return donationRepository.count();
    }

    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }

    public Optional<Donation> findById(long id){
        return donationRepository.findById(id);
    }
}
