import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VendingMachineItemTest_AI {

    @Test
    void testItemCreationValid() {
        VendingMachineItem_AI item = new VendingMachineItem_AI("Candy", 0.75);
        assertEquals("Candy", item.getName());
        assertEquals(0.75, item.getPrice(), 0.001);
    }

    @Test
    void testItemCreationZeroPrice() {
        VendingMachineItem_AI item = new VendingMachineItem_AI("Water", 0.0);
        assertEquals("Water", item.getName());
        assertEquals(0.0, item.getPrice(), 0.001);
    }

    @Test
    void testItemCreationNegativePriceThrowsException() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            new VendingMachineItem_AI("Bad Item", -0.01);
        });
        assertEquals("Price cannot be less than zero", exception.getMessage());
    }
}