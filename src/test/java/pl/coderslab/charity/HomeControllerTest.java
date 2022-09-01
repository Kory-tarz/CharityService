package pl.coderslab.charity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.charity.donation.DonationService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    private static final Long DONATIONS_COUNT = 5L;
    private static final Long QUANTITY_COUNT = 10L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationService donationService;

    @BeforeEach
    void setUp(){
        given(this.donationService.getDonationsCount()).willReturn(DONATIONS_COUNT);
        given(this.donationService.getTotalQuantityOfDonations()).willReturn(QUANTITY_COUNT);
    }

    @Test
    void initDonationsAndQuantityCount() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("totalQuantity"))
                .andExpect(model().attributeExists("donationsCount"))
                .andExpect(model().attributeExists("institutions"))
                .andExpect(model().attribute("totalQuantity", QUANTITY_COUNT))
                .andExpect(model().attribute("donationsCount", DONATIONS_COUNT))
                .andExpect(view().name("index"));
    }
}