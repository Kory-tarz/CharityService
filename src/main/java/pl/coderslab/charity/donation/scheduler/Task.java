package pl.coderslab.charity.donation.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.donation.DonationService;

@Component
public class Task {

    private final DonationService donationService;

    public Task(DonationService donationService) {
        this.donationService = donationService;
    }

    @Scheduled(cron = "* * 8 * * *")
    public void doATask(){
        donationService.deactivateCollectedDonations();
    }
}
