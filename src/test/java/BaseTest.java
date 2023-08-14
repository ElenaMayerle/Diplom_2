import org.junit.After;
import org.junit.Before;

public class BaseTest {
    Steps steps = new Steps();
    @Before
    public void init(){
        steps.createUserAndGetToken();
    }

    @After
    public void tearDown(){
        steps.deleteUser();
    }
}
