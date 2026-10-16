
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest {

    private VendingMachineException exception1;
    private VendingMachineException exception2;

    @BeforeEach
    public void setUp() {
        exception1 = new VendingMachineException();
        exception2 = new VendingMachineException("error");
    }

    @AfterEach
    public void tearDown() {
        exception1 = null;
        exception2 = null;
    }

    @Test
    public void testDefaultConstructor() {
        assertNull(exception1.getMessage());
    }

    @Test
    public void testConstructorWithMessage() {
        assertEquals("error", exception2.getMessage());
    }

    @Test
    public void testConstructorWithEmptyMessage() {
        VendingMachineException exception =
                new VendingMachineException("");

        assertEquals("", exception.getMessage());
    }

    @Test
    public void testConstructorWithNullMessage() {
        VendingMachineException exception =
                new VendingMachineException(null);

        assertNull(exception.getMessage());
    }
}
