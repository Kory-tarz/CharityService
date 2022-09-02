package pl.coderslab.charity.donation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionRepository;
import pl.coderslab.charity.institution.InstitutionService;
import pl.coderslab.charity.model.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationService donationService;

    @SpyBean
    FormattingConversionService formattingConversionService;

    private final static long TEST_DONATION_ID = 1L;

    private Category getCategory() {
        Category category = new Category();
        category.setName("Atrykuły Chemiczne");
        category.setId(1L);
        return category;
    }

    private Institution getInstitution() {
        Institution institution = new Institution();
        institution.setName("fundacja");
        institution.setDescription("pomocy");
        institution.setId(1L);
        return institution;
    }

    private Donation getDonation() {
        Donation donation = new Donation();

        donation.setQuantity(3);
        donation.setCategories(List.of(getCategory()));
        donation.setInstitution(getInstitution());
        donation.setStreet("Ulica");
        donation.setCity("Miasto");
        donation.setZipCode("01-234");
        donation.setPickUpDate(LocalDate.of(2022, 12, 12));
        donation.setPickUpTime(LocalTime.of(15, 30));
        donation.setPickUpComment("komentarz");

        return donation;
    }

    @BeforeEach
    void setUp() {

        Donation donationWithId = getDonation();
        donationWithId.setId(TEST_DONATION_ID);

        doReturn(true)
                .when(formattingConversionService).canConvert(
                argThat(argument -> argument.getObjectType().equals(String.class)),
                argThat((ArgumentMatcher<TypeDescriptor>) argument -> argument.getObjectType().equals(Institution.class)));

        doReturn(getInstitution())
                .when(formattingConversionService)
                .convert(eq("1"), any(TypeDescriptor.class), any(TypeDescriptor.class));


        given(donationService.save(any())).willReturn(donationWithId);
        given(donationService.findById(eq(TEST_DONATION_ID))).willReturn(Optional.of(donationWithId));
        given(donationService.findById(not(eq(TEST_DONATION_ID)))).willReturn(Optional.empty());
    }

    @Test
    void initDonationFormTest() throws Exception {
        mockMvc.perform(get("/donate"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("institutions"))
                .andExpect(model().attributeExists("donation"))
                .andExpect(view().name("donation/form"));
    }

    @Test
    void processDonationFormSuccessTest() throws Exception {
        Donation donation = getDonation();
        performDonatePost(donation)
                .andExpect(model().attributeDoesNotExist("donation")) // doesn't exist if no errors occurred
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/confirmation/" + TEST_DONATION_ID));
    }

    @Test
    void processDonationFormErrorTest() throws Exception {
        Donation donation = getDonation();
        donation.setCity("");
        donation.setQuantity(0);

        performDonatePost(donation)
                .andExpect(model().attributeHasErrors("donation"))
                .andExpect(model().attributeHasFieldErrors("donation", "city"))
                .andExpect(model().attributeHasFieldErrors("donation", "quantity"))
                .andExpect(status().isOk())
                .andExpect(view().name("donation/form"));
    }

    private ResultActions performDonatePost(Donation donation) throws Exception {
        return mockMvc.perform(post("/donate")
                .param("quantity", String.valueOf(donation.getQuantity()))
                .param("street", donation.getStreet())
                .param("city", donation.getCity())
                .param("zipCode", donation.getZipCode())
                .param("pickUpDate", donation.getPickUpDate().toString())
                .param("pickUpTime", donation.getPickUpTime().toString())
                .param("category", "1")
                .param("_categories", "on")
                .param("institution", "1")
                .param("pickUpComment", donation.getPickUpComment())
                .with(csrf()));
    }

    @Test
    void initDonationConfirmation() throws Exception {
        mockMvc.perform(get("/confirmation/{id}", TEST_DONATION_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("donation"))
                .andExpect(view().name("donation/form-confirmation"));
    }

    @Test
    void intDonationConfirmationOnUnknownDonation() throws Exception {
        mockMvc.perform(get("/confirmation/0"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Did not find donation with id: 0", result.getResolvedException().getMessage()));
    }
}