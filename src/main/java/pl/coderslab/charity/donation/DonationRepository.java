package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT coalesce(SUM(d.quantity), 0) FROM Donation d")
    long totalQuantityDonated();

    List<Donation> findAllByPickUpDateIsLessThan(LocalDate date);

}
