package pl.coderslab.charity.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
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
class UserAuthorizationControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private User testUser() {
        User user = new User();
        user.setEmail("test@tester.com");
        user.setPassword("123");
        return user;
    }

    private User testUserWithId() {
        User user = testUser();
        user.setId(1L);
        return user;
    }

    @BeforeEach
    void setUp() {
        when(userService.saveUser(any())).thenReturn(testUserWithId());
    }

    @Test
    void initLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorization/login"));
    }

    @Test
    void initRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorization/register"));
    }

    @Test
    void registrationFormSuccess() throws Exception {
        User user = testUser();
        mockMvc.perform(post("/register")
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .with(csrf()))
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/donate"));
    }
}