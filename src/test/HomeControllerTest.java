import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest extends BaseWebTest {

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void homeMappingTest() throws Exception{

        this.mockMvc.perform(get("/")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("greeting", "Witaj w sklepie internetowym!"))
                .andExpect(model().attribute("tagline", "Wyjątkowym i jedynym sklepie internetowym"));
    }

    @Test
    public void homeRedirectTest() throws Exception{

        this.mockMvc.perform(get("/welcome/greeting")
                .accept(MediaType.ALL))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/"));
    }
}
