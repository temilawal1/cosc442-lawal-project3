import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendingMachineExceptionTest_AI {

    @Test
    void testDefaultConstructor() {
        VendingMachineException_AI ex = new VendingMachineException_AI();
        assertNull(ex.getMessage());
    }

    @Test
    void testMessageConstructor() {
        String message = "Custom error reason";
        VendingMachineException_AI ex = new VendingMachineException_AI(message);
        assertEquals(message, ex.getMessage());
    }
}