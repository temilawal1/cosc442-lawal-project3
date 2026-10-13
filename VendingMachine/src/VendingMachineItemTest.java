import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {

    VendingMachineItem item;

    @BeforeEach
    public void setUp() {
        item = new VendingMachineItem("oreos", 1.50);
    }

    @AfterEach
    public void tearDown() {
        item = null;
    }

    @Test
    public void testNegativePrice() {
        assertThrows(VendingMachineException.class, () -> {
            new VendingMachineItem("negative priced item", -10.00);
        });
    }

    @Test
    public void testGetName() {

        assertEquals("oreos", item.getName());
    }

    @Test
    public void testGetPrice() {

        assertEquals(1.50, item.getPrice());
    }
}
