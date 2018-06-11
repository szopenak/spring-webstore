import com.packt.webstore.controller.CartRestController;
import com.packt.webstore.controller.CustomerController;
import com.packt.webstore.domain.Cart;
import com.packt.webstore.service.CartService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartRestControllerTest extends BaseWebTest {

    @Mock
    CartService cartService;

    @InjectMocks
    private CustomerController controller;

    @Override
    public void setUp() {

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        List<ViewResolver> resolverList = new ArrayList<>();
        resolverList.add(new ViewResolver() {
            @Override
            public View resolveViewName(String s, Locale locale) throws Exception {
                MappingJackson2JsonView view = new MappingJackson2JsonView();
                view.setPrettyPrint(true);
                return view;
            }
        });

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        resolverList.add(viewResolver);

        resolver.setViewResolvers(resolverList);

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(resolver)
                .build();
    }

    @Test
    public void getCart() throws Exception{

        when(cartService.read("123")).thenReturn(new Cart());

        mockMvc.perform(get("/rest/cart/123")).andExpect(status().isOk());
    }
}
