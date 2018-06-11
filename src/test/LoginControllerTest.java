import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LoginControllerTest extends BaseWebTest {

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void loginTest() throws Exception{

        this.mockMvc.perform(get("/login")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void logoutTest() throws Exception{
        this.mockMvc.perform(get("/logout")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void badLoginTest() throws Exception{
        this.mockMvc.perform(get("/loginfailed")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("error", "true"));
    }

}
