package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.charity.donation.DonationService;


@Controller
public class HomeController {

    private final DonationService donationService;

    private static final String DONATIONS_COUNT = "donationsCount";
    private static final String TOTAL_QUANTITY = "totalQuantity";

    public HomeController(DonationService donationService) {
        this.donationService = donationService;
    }

    @RequestMapping("/")
    public ModelAndView homeAction(Model model){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
