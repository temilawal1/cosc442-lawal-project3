
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest_AI {

    private VendingMachineException_AI exception1;
    private VendingMachineException_AI exception2;

    @BeforeEach
    public void setUp() {
        exception1 = new VendingMachineException_AI();
        exception2 = new VendingMachineException_AI("error");
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
        VendingMachineException_AI exception =
                new VendingMachineException_AI("");

        assertEquals("", exception.getMessage());
    }

    @Test
    public void testConstructorWithNullMessage() {
        VendingMachineException_AI exception =
                new VendingMachineException_AI(null);

        assertNull(exception.getMessage());
    }
}
