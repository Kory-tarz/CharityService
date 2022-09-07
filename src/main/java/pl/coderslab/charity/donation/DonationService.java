package pl.coderslab.charity.donation;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.donation.status.Status;
import pl.coderslab.charity.donation.status.StatusRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final StatusRepository statusRepository;

    public DonationService(DonationRepository donationRepository, StatusRepository statusRepository) {
        this.donationRepository = donationRepository;
        this.statusRepository = statusRepository;
    }

    public long getTotalQuantityOfDonations(){
        return donationRepository.totalQuantityDonated();
    }

    public long getDonationsCount(){
        return donationRepository.count();
    }

    public Donation saveNewDonation(Donation donation){
        Status activeStatus = statusRepository.findByName("active");
        donation.setStatus(activeStatus);
        return save(donation);
    }

    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }

    public void deactivateCollectedDonations(){
        LocalDate now = LocalDate.now();
        List<Donation> collectedDonations = donationRepository.findAllByPickUpDateIsLessThan(now);
        collectedDonations.forEach(don -> System.out.println(don.getCity()));
        Status inactive = statusRepository.findByName("inactive");
        collectedDonations.stream().peek(donation -> donation.setStatus(inactive)).forEach(this::save);
    }

    public Optional<Donation> findById(long id){
        return donationRepository.findById(id);
    }
}
