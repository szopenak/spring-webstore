import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/webcontext/DispatcherServlet-context.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

}
