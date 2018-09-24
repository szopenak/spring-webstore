import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.CartRepository;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartServiceTest extends BaseTest {

    @Mock
    CartRepository repository;

    @Autowired
    @InjectMocks
    CartService service;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void createCartServiceTest(){

        //given
        Cart cart = new Cart("123");
        when(repository.create(cart))
                .thenReturn(cart);

        //when
        Cart returned = service.create(cart);

        //then
        verify(repository).create(cart);
        assertThat(returned).hasFieldOrPropertyWithValue("cartId", "123");
    }
    @Test
    public void deleteCartServiceTest(){

        //given
        String cartId = "123";
        Mockito.doNothing()
                .when(repository)
                .delete(cartId);

        //when
        service.delete(cartId);

        //then
        verify(repository).delete(cartId);
    }
    @Test
    public void updateCartServiceTest(){

        //given
        Cart cart = new Cart("123");
        Mockito.doNothing()
                .when(repository)
                .update(cart.getCartId(), cart);

        //when
        service.update(cart.getCartId(), cart);

        //then
        verify(repository).update(cart.getCartId(), cart);
    }
    @Test
    public void readCartServiceTest(){

        //given
        Cart cart = new Cart("123");
        when(repository.read(cart.getCartId()))
                .thenReturn(cart);

        //when
        Cart read = service.read(cart.getCartId());

        //then
        verify(repository).read(cart.getCartId());
        assertThat(read).isEqualTo(cart);
    }
}
