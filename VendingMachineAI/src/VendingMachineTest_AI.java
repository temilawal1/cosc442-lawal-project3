import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineTest_AI {

    private VendingMachine_AI machine;
    private VendingMachineItem_AI itemA;
    private VendingMachineItem_AI itemB;

    @BeforeEach
    void setUp() {
        machine = new VendingMachine_AI();
        itemA = new VendingMachineItem_AI("Chips", 1.50);
        itemB = new VendingMachineItem_AI("Soda", 2.00);
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, machine.getBalance(), 0.001);
        assertThrows(VendingMachineException_AI.class, () -> machine.removeItem("A"));
    }

    @Test
    void testInsertMoneyValid() {
        machine.insertMoney(5.0);
        assertEquals(5.0, machine.getBalance(), 0.001);
        
        machine.insertMoney(2.50);
        assertEquals(7.50, machine.getBalance(), 0.001);
    }

    @Test
    void testInsertMoneyInvalidLessThanOne() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.insertMoney(0);
        });
        assertEquals("Invalid amount.  Amount must be >= 0", exception.getMessage());
    }

    @Test
    void testInsertMoneyInvalidNegative() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.insertMoney(-1.0);
        });
        assertEquals("Invalid amount.  Amount must be >= 0", exception.getMessage());
    }

    @Test
    void testAddItemValid() {
        assertDoesNotThrow(() -> machine.addItem(itemA, "A"));
        assertDoesNotThrow(() -> machine.addItem(itemB, "B"));
    }

    @Test
    void testAddItemAlreadyOccupiedThrowsException() {
        machine.addItem(itemA, "A");
        
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.addItem(itemB, "A");
        });
        assertEquals("Slot A already occupied", exception.getMessage());
    }

    @Test
    void testAddItemInvalidCodeThrowsException() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.addItem(itemA, "E");
        });
        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void testRemoveItemValid() {
        machine.addItem(itemA, "C");
        VendingMachineItem_AI removed = machine.removeItem("C");
        
        assertEquals(itemA, removed);
        assertThrows(VendingMachineException_AI.class, () -> machine.removeItem("C"));
    }

    @Test
    void testRemoveItemEmptySlotThrowsException() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.removeItem("B");
        });
        assertEquals("Slot B is empty -- cannot remove item", exception.getMessage());
    }

    @Test
    void testRemoveItemInvalidCodeThrowsException() {
        Exception exception = assertThrows(VendingMachineException_AI.class, () -> {
            machine.removeItem("Z");
        });
        assertEquals("Invalid code for vending machine item", exception.getMessage());
    }

    @Test
    void testMakePurchaseSuccessful() {
        machine.addItem(itemA, "A"); 
        machine.insertMoney(2.0);    

        boolean success = machine.makePurchase("A");
        
        assertTrue(success);
        assertEquals(0.50, machine.getBalance(), 0.001); 
        assertThrows(VendingMachineException_AI.class, () -> machine.removeItem("A"));
    }

    @Test
    void testMakePurchaseNotEnoughMoney() {
        machine.addItem(itemB, "B"); 
        machine.insertMoney(1.0);    

        boolean success = machine.makePurchase("B");
        
        assertFalse(success);
        assertEquals(1.0, machine.getBalance(), 0.001); 
        assertDoesNotThrow(() -> machine.removeItem("B"));
    }

    @Test
    void testMakePurchaseEmptySlot() {
        machine.insertMoney(5.0);
        boolean success = machine.makePurchase("D"); 
        
        assertFalse(success);
        assertEquals(5.0, machine.getBalance(), 0.001);
    }

    @Test
    void testReturnChange() {
        machine.insertMoney(3.50);
        assertEquals(3.50, machine.getBalance(), 0.001);

        double change = machine.returnChange();
        assertEquals(3.50, change, 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }
}