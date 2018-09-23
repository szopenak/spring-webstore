import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends BaseTest {
    @Autowired
    ProductRepository repository;

    @Test
    public void getProductsTest(){
        //given

        //when
        List<Product> allProducts = repository.getAllProducts();

        //then
        assertThat(allProducts).hasSize(3);
    }

    @Test
    public void getProductByIdTest(){
        //given
        String id = "1";

        //when
        Product productById = repository.getProductById(id);

        //then
        assertThat(productById).hasFieldOrPropertyWithValue("productId", id);
    }
    @Test(expected = ProductNotFoundException.class)
    public void getProductByIdNotFoundTest(){
        //given
        String id = "1222";

        //when
        Product productById = repository.getProductById(id);

        //then
        assertThat(productById).hasFieldOrPropertyWithValue("productId", id);
    }

    @Test
    public void getProductByCategoryTest(){
        //given
        final String category = "tablet";

        //when
        List<Product> productsByCategory = repository.getProductsByCategory(category);

        //then
        assertThat(productsByCategory)
                .allSatisfy(p -> assertThat(p.getCategory()).isEqualToIgnoringCase(category));
    }
    @Test
    public void getProductsByManufacturerTest(){
        //given
        final String manufacturer = "Apple";

        //when
        Set<Product> productsByManufacturer = repository.getProductsByManufacturer(manufacturer);

        //then
        assertThat(productsByManufacturer)
                .allSatisfy(p -> assertThat(p.getManufacturer()).isEqualTo(manufacturer));
    }
    @Test(expected = IllegalArgumentException.class)
    public void getProductsByManufacturerNotFoundTest() {
        //given
        final String manufacturer = "Apple2";

        //when
        Set<Product> productsByManufacturer = repository.getProductsByManufacturer(manufacturer);

        //then
    }

}
