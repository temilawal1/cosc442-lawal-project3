import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    VendingMachine machine;
    VendingMachineItem item1;
    VendingMachineItem item2;

    @BeforeEach
    public void setUp() throws Exception {

        machine = new VendingMachine();

        item1 = new VendingMachineItem("oreos", 1.50);
        item2 = new VendingMachineItem("doritos", 2.50);
    }

    @AfterEach
    public void tearDown() {
        item1 = null;
        item2 = null;
    }

    @Test
    public void testAddItem() throws VendingMachineException {

        machine.addItem(item1, "A");
        assertEquals(item1, machine.getItem("A"), "test addItem");

        machine.addItem(item2, "B");
        assertEquals(item2, machine.getItem("B"), "test addItem");

    }

    @Test
    public void testAddItem_OccupiedSlot() throws VendingMachineException {

        machine.addItem(item1, "A"); // partial arrange

        try {
            machine.addItem(item2, "A");
        } catch (VendingMachineException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);

    }

    @Test
    public void testGetItem() {

        machine.addItem(item1, "A");
        assertEquals(item1, machine.getItem("A"), "test getItem");
    }

    @Test
    public void testGetItem_EmptySlot() throws VendingMachineException {

        assertEquals(null, machine.getItem("A"));
    }

    @Test
    public void testRemoveItem() throws VendingMachineException {
        machine.addItem(item1, "A");
        assertEquals(null, machine.removeItem("A"), "test removeItem");
    }

}
