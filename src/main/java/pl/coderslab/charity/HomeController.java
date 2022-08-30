package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.ColumnDisplay;


@Controller
public class HomeController {

    private final DonationService donationService;
    private final InstitutionService institutionService;

    private static final String DONATIONS_COUNT = "donationsCount";
    private static final String TOTAL_QUANTITY = "totalQuantity";
    private static final String INSTITUTIONS = "institutions";

    public HomeController(DonationService donationService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @RequestMapping("/")
    public ModelAndView homeAction(Model model){
        ModelAndView mav = new ModelAndView("index");
        ColumnDisplay<Institution> institutionColumnDisplay = new ColumnDisplay<>(institutionService.findAll(), 2);
        mav.addObject(INSTITUTIONS, institutionColumnDisplay.getRows());
        mav.addObject(DONATIONS_COUNT, donationService.getTotalDonationsCount());
        mav.addObject(TOTAL_QUANTITY, donationService.getTotalQuantityOfDonations());
        return mav;
    }
}
