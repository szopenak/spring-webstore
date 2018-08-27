import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.CartRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CartRepositoryTest extends BaseTest  {

    @Autowired
    CartRepository repository;

    @Test
    public void testGetCartRepository() {
        // given
        String id = "123";

        // when
        Cart cart = repository.read(id);

        // then
        assertThat(cart)
                .isNotNull()
                .hasFieldOrPropertyWithValue("cartId", id);
    }

    @Test
    public void testAbsentCartRepository() {
        // given
        String id = "12223";

        // when
        Cart cart = repository.read(id);

        // then
        assertThat(cart)
                .isNull();
    }

    @Test
    public void testCreateCartRepository() {
        // given
        Cart cart = new Cart("321");
        cart.addCartItem(
                new CartItem(
                        Product.builder()
                                .category("Notebook")
                                .productId("55")
                                .name("Name")
                                .unitPrice(new BigDecimal("12.00"))
                                .build()
                )
        );
        cart.updateGrandTotal();

        // when
        Cart createdCart = repository.create(cart);

        // then
        assertThat(createdCart).isEqualTo(cart);
        assertThat(createdCart.getGrandTotal())
                .isEqualTo(new BigDecimal("12.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateCartRepositoryException() {
        // given
        Cart cart = new Cart("123");

        // when
        repository.create(cart);

        // then
        // exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAbsentCartRepositoryUpdate() {
        // given
        Cart cart = new Cart("321");

        // when
        repository.update("321", cart);

        // then
        // exception
    }

    @Test
    public void testCartRepositoryUpdate() {
        // given
        Cart cart = new Cart("123");
        cart.addCartItem(
                new CartItem(
                        Product.builder()
                                .category("Notebook")
                                .productId("55")
                                .name("Name")
                                .unitPrice(new BigDecimal("12.00"))
                                .build()
                )
        );
        cart.updateGrandTotal();

        // when
        repository.update("123", cart);

        // then
        // no exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAbsentCartRepositoryDelete() {
        // given
       String id = "321";

        // when
        repository.delete(id);

        // then
        // exception
    }

    @Test
    public void testCartRepositoryDelete() {
        // given
        String id = "123";

        // when
        repository.delete(id);

        // then
        // no exception
    }

}
