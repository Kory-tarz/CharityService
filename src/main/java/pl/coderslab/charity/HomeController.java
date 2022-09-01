package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.MappingService;

import java.util.List;


@Controller
public class HomeController {

    private final DonationService donationService;
    private final InstitutionService institutionService;
    private final MappingService mappingService;

    private static final String DONATIONS_COUNT = "donationsCount";
    private static final String TOTAL_QUANTITY = "totalQuantity";
    private static final String INSTITUTIONS = "institutions";

    public HomeController(DonationService donationService, InstitutionService institutionService, MappingService mappingService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.mappingService = mappingService;
    }

    @RequestMapping("/")
    public ModelAndView homeAction(Model model){
        ModelAndView mav = new ModelAndView("index");
        List<List<Institution>> institutionsTableView = mappingService.mapToTable(institutionService.findAll(), 2);
        mav.addObject(INSTITUTIONS, institutionsTableView);
        mav.addObject(DONATIONS_COUNT, donationService.getDonationsCount());
        mav.addObject(TOTAL_QUANTITY, donationService.getTotalQuantityOfDonations());
        return mav;
    }
}
