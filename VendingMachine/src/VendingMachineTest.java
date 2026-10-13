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

    // checks all valid codes!
    @ParameterizedTest
    @ValueSource(strings = { "A", "B", "C", "D" })
    public void testAddItem_ValidCode(String code) throws VendingMachineException {
        machine.addItem(item1, code);
        assertEquals(item1, machine.getItem(code));
    }

    @Test
    public void testAddItem_InvalidCode() {

        assertThrows(VendingMachineException.class, () -> {
            machine.addItem(item1, "E");
        });
    }

    @Test
    public void testAddItem_OccupiedSlot() {

        machine.addItem(item1, "A"); // partial arrange

        assertThrows(VendingMachineException.class, () -> {
            machine.addItem(item2, "A");
        });

    }

    @ParameterizedTest
    @ValueSource(strings = { "A", "B", "C", "D" })
    public void testGetItem_ValidCode(String code) throws VendingMachineException {

        machine.addItem(item1, code);

        assertEquals(item1, machine.getItem(code), "test getItem");
    }

    @Test
    public void testGetItem_InvalidCode() {
        assertThrows(VendingMachineException.class, () -> {
            machine.getItem("E");
        });
    }

    @Test
    public void testGetItem_EmptySlot() throws VendingMachineException {

        assertEquals(null, machine.getItem("A"));
    }


    @ParameterizedTest
    @ValueSource(strings = { "A", "B", "C", "D" })
    public void testRemoveItem_ValidCode(String code) throws VendingMachineException {
        machine.addItem(item1, code);
        assertEquals(item1, machine.removeItem(code), "returns removed item");
        assertEquals(null, machine.getItem(code), "returns null: slot empty");
    }

    @Test
    public void testRemoveItem_InvalidCode() {
        assertThrows(VendingMachineException.class, () -> {
            machine.removeItem("E");
        });
    }

    @Test
    public void testRemoveItem_EmptySlot() {
        assertThrows(VendingMachineException.class, () -> {
            machine.removeItem("A");
        });
    }

    @ParameterizedTest
    @CsvSource({
            "-10.00, false",
            "0.00, true",
            "0.99, true",
            "1.00, true",
            "1.02, true",
            "10.00, true",
    })
    public void testInsertMoney(double funds, boolean accepted) throws VendingMachineException {
        if (accepted) {
            machine.insertMoney(funds);
            assertEquals(funds, machine.getBalance()); // checks the balance of machine which should go up
        } else {
            assertThrows(VendingMachineException.class, () -> {
                machine.insertMoney(funds);
            });
        } // after money is inserted
          // initial test passed
          // also tests getBalance after money is inserted
          // doesnt need to be another test? cause the code would just be the same
    } // changed testInsertMoney to parameterized test to add boundaries
    // test passed

    

    @Test
    public void testGetBalance() {
        assertEquals(0.0, machine.getBalance());
    }

    @Test
    public void testMakePurchase() throws VendingMachineException {
        machine.addItem(item1, "A");
        machine.insertMoney(5.00);

        assertTrue(machine.makePurchase("A"));
        // initial test passed
        assertEquals(3.50, machine.getBalance()); // also checks that money was removed
        assertEquals(null, machine.getItem("A")); // also checks that item was removed
        // updated test passed boom
    }

    @Test
    public void testMakePurchase_ExactFunds() throws VendingMachineException {
        machine.addItem(item1, "A");
        machine.insertMoney(1.50);

        assertTrue(machine.makePurchase("A"));
    }

    @Test
    public void testMakePurchase_NotEnoughFunds() throws VendingMachineException {
        machine.addItem(item1, "A");
        machine.insertMoney(1.00);

        assertFalse(machine.makePurchase("A"));
        // initial test failed
        // assert throws not needed because assert false is used (same as make purchase)
        // updated test passed yay
    }

    @Test
    public void testMakePurchase_EmptySlot() throws VendingMachineException {
        assertFalse(machine.makePurchase("A"));
        // no slot initialized so should just return false
        // initial test passed
    }

    @Test
    public void testReturnChange() throws VendingMachineException {
        machine.insertMoney(5.00); // add initial monayz

        assertEquals(5.00, machine.returnChange());
        assertEquals(0.00, machine.getBalance()); // balance should be 0 cause all change is returned?
        // intiial test passed yay
    }

    @Test
    public void testReturnChange_NoBalance() {
        assertEquals(0.00, machine.returnChange()); // i assume this will work since above test worked
        assertEquals(0.00, machine.getBalance());
        // bang initial test passed
    }

    // return change

}
