package pl.coderslab.charity.donation;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Log4j2
@Controller
public class DonationController {

    private final InstitutionService institutionService;
    private final CategoryService categoryService;
    private final DonationService donationService;

    private static final String CATEGORIES = "categories";
    private static final String INSTITUTIONS = "institutions";
    private static final String DONATION = "donation";

    public DonationController(InstitutionService institutionService, CategoryService categoryService, DonationService donationService) {
        this.institutionService = institutionService;
        this.categoryService = categoryService;
        this.donationService = donationService;
    }

    @GetMapping("/donate")
    public String initDonationForm(Model model) {
        model.addAttribute(DONATION, new Donation());
        setUpDonationFormModelAttributes(model);
        return "donation/form";
    }

    private void setUpDonationFormModelAttributes(Model model) {
        model.addAttribute(CATEGORIES, categoryService.findAll());
        model.addAttribute(INSTITUTIONS, institutionService.findAll());
    }

    @PostMapping("/donate")
    public String processDonateForm(HttpServletRequest request, Model model, @Valid Donation donation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn(bindingResult.getAllErrors());
            model.addAttribute(DONATION, donation);
            setUpDonationFormModelAttributes(model);
            return "donation/form";
        }
        Donation saveDonation = donationService.save(donation);

        return "redirect:/confirmation/" + saveDonation.getId();
    }

    @GetMapping("/confirmation/{id}")
    public String initConfirmation(Model model, @PathVariable long id) {
        Donation donation = donationService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Did not find donation with id: " + id));
        model.addAttribute(DONATION, donation);
        return "donation/form-confirmation";
    }

}
