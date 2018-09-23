import com.packt.webstore.controller.ProductController;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest extends BaseWebTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void getProductsTest() throws Exception {
        //given
        List<Product> productList = getSampleProducts();

        when(productService.getAllProducts()).thenReturn(productList);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/products")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", productList))
                .andReturn().getResponse();

        //then
        verify(productService, only()).getAllProducts();
    }
    @Test
    public void getProductsByCategoryTest() throws Exception {
        //given
        List<Product> productList = getSampleProducts();
        String category = "tablet";

        when(productService.getProductsByCategory(anyString())).thenReturn(productList);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/products/" + category)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", productList))
                .andReturn().getResponse();

        //then
        verify(productService, only()).getProductsByCategory(category);
    }
    @Test
    public void getProductsByCategoryBadNameTest() throws Exception {
        //given
        String category = "telephone";

        when(productService.getProductsByCategory(anyString())).thenReturn(new ArrayList<>());

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/products/" + category)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", Collections.emptyList()))
                .andReturn().getResponse();

        //then
        verify(productService, only()).getProductsByCategory(category);
    }

    @Test
    public void getProductsByIdTest() throws Exception {
        //given
        Product product = getSampleProducts().get(0);
        String id = "1";

        when(productService.getProductById(anyString())).thenReturn(product);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/products/product?id=" + id)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product))
                .andReturn().getResponse();

        //then
        verify(productService, only()).getProductById(id);
    }

    @Test
    public void getProductsByIdBadIdTest() throws Exception {
        //given
        String id = "1234";

        when(productService.getProductById(anyString())).thenThrow(new ProductNotFoundException(id));

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/products/product?id=" + id)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("productNotFound"))
                .andReturn().getResponse();

        //then
        verify(productService, only()).getProductById(id);
    }

    private List<Product> getSampleProducts(){
        Product iphone = new Product("1","iPhone 5s", new BigDecimal(500));
        iphone.setDescription("Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczo�ci 640x1136 i 8-megapikselowym aparatem");
        iphone.setCategory("Smartfon");
        iphone.setManufacturer("Apple");
        iphone.setUnitsInStock(1000);
        List<Product> productList = new ArrayList<>();
        productList.add(iphone);
        return productList;
    }

}
