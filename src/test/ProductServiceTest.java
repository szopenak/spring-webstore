import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest extends BaseTest {

    @Mock
    ProductRepository repository;

    @Autowired
    @InjectMocks
    ProductService service;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void productAllServiceTest(){

        //given
        when(repository.getAllProducts())
                .thenReturn(new ArrayList<Product>());

        //when
        service.getAllProducts();

        //then
        verify(repository).getAllProducts();
    }
    @Test
    public void productByCategoryServiceTest(){

        //given
        String cat = "Phone";
        when(repository.getProductsByCategory(cat))
                .thenReturn(new ArrayList<Product>());

        //when
        service.getProductsByCategory(cat);

        //then
        verify(repository).getProductsByCategory(cat);
    }
    @Test
    public void productByIdServiceTest(){

        //given
        String id = "123";
        when(repository.getProductsByCategory(id))
                .thenReturn(new ArrayList<Product>());

        //when
        service.getProductById(id);

        //then
        verify(repository).getProductById(id);
    }
    @Test
    public void productByManufacturerServiceTest(){

        //given
        String manufact = "123";
        when(repository.getProductsByManufacturer(manufact))
                .thenReturn(new HashSet<Product>());

        //when
        service.getProductsByManufacturer(manufact);

        //then
        verify(repository).getProductsByManufacturer(manufact);
    }
    @Test
    public void productAddServiceTest(){

        //given
        Product product = new Product("P1234", "Some", new BigDecimal("12.3"));
        Mockito.doNothing().when(repository).addProduct(product);

        //when
        service.addProduct(product);

        //then
        verify(repository).addProduct(product);
    }
}
