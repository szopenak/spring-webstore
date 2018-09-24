import com.packt.webstore.validator.CategoryValidator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryValidatorTest extends BaseTest{
    @Test
    public void getCorrect(){
        //given
        CategoryValidator validator = new CategoryValidator();
        validator.initialize(null);
        String category = "Notebook";

        //when
        boolean valid = validator.isValid(category, null);

        //then
        assertThat(valid).isTrue();
    }
    @Test
    public void getNotValid(){
        //given
        CategoryValidator validator = new CategoryValidator();
        validator.initialize(null);
        String category = "note2123book";

        //when
        boolean valid = validator.isValid(category, null);

        //then
        assertThat(valid).isFalse();
    }

}
