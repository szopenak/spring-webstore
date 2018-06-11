import com.packt.webstore.controller.CustomerController;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.service.CustomerService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomersControllerTest extends BaseWebTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    @Test
    public void getCustomersTest() throws Exception{

        List<Customer> customers = Arrays.asList(new Customer[]{new Customer("1", "Artur0", "Somewhere 11-2", 0)});

        when(customerService.getAllCustomers())
                .thenReturn(customers);

        MockHttpServletResponse response = this.mockMvc.perform(get("/customers")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"))
                .andExpect(MockMvcResultMatchers.model().attribute("customers", customers))
                .andReturn().getResponse();

        verify(customerService, times(1)).getAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }
}
