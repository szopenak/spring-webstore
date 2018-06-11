import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.service.CustomerService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerServiceTest extends BaseTest {

    @Mock
    CustomerRepository repository;

    @Autowired
    @InjectMocks
    CustomerService service;

    @Test
    public void customerServiceTest(){

        initMocks(this);

        List<Customer> customers = Arrays.asList(new Customer[]{new Customer("1", "Artur0", "Somewhere 11-2", 0)});

        when(repository.getAllCustomers())
                .thenReturn(customers);

        assertThat(service.getAllCustomers())
                .hasSize(1)
                .containsSequence(customers);
    }
}
