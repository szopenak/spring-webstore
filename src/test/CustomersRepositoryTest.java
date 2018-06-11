import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomersRepositoryTest extends BaseTest {

    @Autowired
    CustomerRepository repository;

    @Test
    public void testCustomersRepository(){
        Customer[] customers = new Customer[]{
                new Customer("1", "Artur0", "Somewhere 11-2", 0),
                new Customer("2", "Chris", "Kickapoo 2-555", 0)
        };

        List<Customer> allCustomers = repository.getAllCustomers();
        assertThat(allCustomers)
                .hasSize(2)
                .contains(customers);
    }
}
