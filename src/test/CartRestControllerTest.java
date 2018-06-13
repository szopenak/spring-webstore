import com.packt.webstore.controller.CartRestController;
import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Product;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.ProductService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartRestControllerTest extends BaseWebTest {

    @Mock
    CartService cartService;

    @Mock
    ProductService productService;

    @InjectMocks
    private CartRestController controller;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();

    }

    @Test
    public void getCartTest() throws Exception{
        // given
        Cart cart = new Cart("123123");
        when(cartService.read("123123")).thenReturn(cart);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/rest/cart/123123"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(cartService, only()).read("123123");
        verifyNoMoreInteractions(cartService);

        assertThat(response.getContentAsString()).isNotBlank();
        Cart respCart = objectMapper.readValue(response.getContentAsString(), Cart.class);
        assertThat(respCart).isEqualToComparingFieldByField(cart);
    }

    @Test
    public void getNotExistingCartTest() throws Exception{
        // given
        when(cartService.read("123123")).thenReturn(null);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/rest/cart/123123"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Bad data sent."))
                .andReturn().getResponse();

        // then
        verify(cartService, only()).read("123123");
        verifyNoMoreInteractions(cartService);
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void createCartTest() throws Exception{
        // given
        Cart cart = new Cart("123123");
        when(cartService.create(any(Cart.class))).thenReturn(cart);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/rest/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cart)))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        // then
        verify(cartService, only()).create(any(Cart.class));
        verifyNoMoreInteractions(cartService);
        assertThat(response.getContentAsString()).isNotBlank();
        Cart respCart = objectMapper.readValue(response.getContentAsString(), Cart.class);
        assertThat(respCart).isEqualToComparingFieldByField(cart);
    }

    @Test
    public void updateCartTest() throws Exception {
        // given
        Cart cart = new Cart("123123");
        Mockito.doNothing().when(cartService).update(any(String.class), any(Cart.class));

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/rest/cart/123123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(cart)))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // then
        verify(cartService, only()).update(eq("123123"), any(Cart.class));
        verifyNoMoreInteractions(cartService);
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void deleteCartTest() throws Exception {
        // given
        Mockito.doNothing().when(cartService).delete(any(String.class));

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/rest/cart/123123"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // then
        verify(cartService, only()).delete(eq("123123"));
        verifyNoMoreInteractions(cartService);
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void addItemTest() throws Exception {
        // given
        Cart cart = new Cart("123123");
        Product prod = new Product("3", "product", new BigDecimal("1.45"));
        CartItem cartItem = new CartItem(prod);
        when(cartService.read(any(String.class))).thenReturn(cart);
        when(productService.getProductById(any(String.class))).thenReturn(prod);

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/rest/cart/prod/3"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
        verify(cartService, never()).create(any(Cart.class));

        ArgumentCaptor<Cart> argument = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).update(any(String.class), argument.capture());

        Cart updatedCart = argument.getValue();
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getCartItems())
                .containsOnlyKeys("3");
        assertThat(updatedCart.getCartItems().values())
                .contains(cartItem);
    }

    @Test
    public void addItemNoCart() throws Exception {
        // given
        Cart cart = new Cart("123123");
        Product prod = new Product("3", "product", new BigDecimal("1.45"));
        CartItem cartItem = new CartItem(prod);
        when(cartService.read(any(String.class))).thenReturn(null);
        when(cartService.create(any(Cart.class))).thenReturn(cart);
        when(productService.getProductById(any(String.class))).thenReturn(prod);

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/rest/cart/prod/3"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
        verify(cartService).create(any(Cart.class));

        ArgumentCaptor<Cart> argument = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).update(any(String.class), argument.capture());

        Cart updatedCart = argument.getValue();
        assertThat(updatedCart)
                .isNotNull();
        assertThat(updatedCart.getCartItems())
                .containsOnlyKeys("3");
        assertThat(updatedCart.getCartItems().values())
                .contains(cartItem);
    }

    @Test
    public void addNotExistingItem() throws Exception {
        // given
        Cart cart = new Cart("123123");
        Product prod = new Product("3", "product", new BigDecimal("1.45"));
        CartItem cartItem = new CartItem(prod);
        when(cartService.read(any(String.class))).thenReturn(cart);
        when(productService.getProductById(any(String.class))).thenReturn(null);

        // when
        MockHttpServletResponse response = mockMvc.perform(put("/rest/cart/prod/3"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Bad product."))
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
        verify(cartService, never()).create(any(Cart.class));
    }

    @Test
    public void removeItemTest() throws Exception {
        // given
        Cart cart = new Cart("123123");
        Product prod = new Product("3", "product", new BigDecimal("1.45"));
        CartItem cartItem = new CartItem(prod);
        cart.addCartItem(cartItem);

        when(cartService.read(any(String.class))).thenReturn(cart);
        when(productService.getProductById(any(String.class))).thenReturn(prod);

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/rest/cart/prod/3"))
                .andExpect(status().isNoContent())
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
        verify(cartService, never()).create(any(Cart.class));

        ArgumentCaptor<Cart> argument = ArgumentCaptor.forClass(Cart.class);
        verify(cartService).update(any(String.class), argument.capture());

        Cart updatedCart = argument.getValue();
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getCartItems())
                .doesNotContainKeys("3");
        assertThat(updatedCart.getCartItems().values())
                .doesNotContain(cartItem);
    }

    @Test
    public void removeItemNoCartTest() throws Exception {
        // given
        when(cartService.read(any(String.class))).thenReturn(null);

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/rest/cart/prod/3"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Bad data sent."))
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
    }

    @Test
    public void removeNonExistingItemTest() throws Exception {
        // given
        Cart cart = new Cart("123123");

        when(cartService.read(any(String.class))).thenReturn(cart);
        when(productService.getProductById(any(String.class))).thenReturn(null);

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/rest/cart/prod/3"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Bad product."))
                .andReturn().getResponse();

        // then
        assertThat(response.getContentAsString()).isEmpty();
        verify(cartService).read(any(String.class));
        verify(productService).getProductById("3");
    }
}
