import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VendingMachineExceptionTest {
    
    VendingMachineException exception1;
    VendingMachineException exception2;

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
    public void testVendingMachineExceptionConstructor() {
        assertEquals(null, exception1.getMessage());
        assertEquals("error", exception2.getMessage());
        
    }
}
