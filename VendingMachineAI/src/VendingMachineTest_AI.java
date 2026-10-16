
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VendingMachineTest_AI {

    private VendingMachine machine;
    private VendingMachineItem item;

    @BeforeEach
    public void setUp() {
        machine = new VendingMachine();
        item = new VendingMachineItem("Chips", 1.50);
    }

    // ---------- Constructor and initial state ----------

    @Test
    public void testConstructorInitialBalance() {
        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C", "D"})
    public void testAllSlotsInitiallyEmpty(String code) {
        assertNull(machine.getItem(code));
    }

    // ---------- addItem() ----------

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "C", "D"})
    public void testAddItemToEverySlot(String code) {
        machine.addItem(item, code);

        assertSame(item, machine.getItem(code));
    }

    @Test
    public void testAddDifferentItemsToDifferentSlots() {
        VendingMachineItem candy =
                new VendingMachineItem("Candy", 2.00);

        machine.addItem(item, "A");
        machine.addItem(candy, "B");

        assertSame(item, machine.getItem("A"));
        assertSame(candy, machine.getItem("B"));
    }

    @Test
    public void testAddItemToOccupiedSlotThrowsException() {
        machine.addItem(item, "A");

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(
                        new VendingMachineItem("Candy", 2.00), "A")
        );

        assertEquals(
                "Slot A already occupied",
                exception.getMessage()
        );

        // The original item must remain in the slot.
        assertSame(item, machine.getItem("A"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"E", "a", "1", "", "AA"})
    public void testAddItemWithInvalidCode(String code) {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.addItem(item, code)
        );

        assertEquals(
                "Invalid code for vending machine item",
                exception.getMessage()
        );
    }

    // ---------- getItem() ----------

    @ParameterizedTest
    @CsvSource({
        "A, Chips, 1.50",
        "B, Candy, 2.00",
        "C, Water, 0.00",
        "D, Gum, 0.50"
    })
    public void testGetItemReturnsCorrectItem(
            String code, String name, double price) {

        VendingMachineItem expected =
                new VendingMachineItem(name, price);

        machine.addItem(expected, code);

        assertSame(expected, machine.getItem(code));
    }

    @Test
    public void testGetItemFromEmptySlotReturnsNull() {
        assertNull(machine.getItem("A"));
    }

    @Test
    public void testGetItemWithInvalidCodeThrowsException() {
        assertThrows(
                VendingMachineException.class,
                () -> machine.getItem("X")
        );
    }

    // ---------- removeItem() ----------

    @Test
    public void testRemoveItemReturnsRemovedItem() {
        machine.addItem(item, "A");

        VendingMachineItem removed = machine.removeItem("A");

        assertSame(item, removed);
    }

    @Test
    public void testRemoveItemLeavesSlotEmpty() {
        machine.addItem(item, "A");

        machine.removeItem("A");

        assertNull(machine.getItem("A"));
    }

    @Test
    public void testRemoveItemFromEmptySlotThrowsException() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.removeItem("A")
        );

        assertEquals(
                "Slot A is empty -- cannot remove item",
                exception.getMessage()
        );
    }

    @Test
    public void testRemoveItemWithInvalidCodeThrowsException() {
        assertThrows(
                VendingMachineException.class,
                () -> machine.removeItem("X")
        );
    }

    @Test
    public void testCanAddItemAfterRemovingPreviousItem() {
        machine.addItem(item, "A");
        machine.removeItem("A");

        VendingMachineItem candy =
                new VendingMachineItem("Candy", 2.00);

        machine.addItem(candy, "A");

        assertSame(candy, machine.getItem("A"));
    }

    // ---------- insertMoney() ----------

    @Test
    public void testInsertPositiveMoney() {
        machine.insertMoney(5.00);

        assertEquals(5.00, machine.getBalance(), 0.001);
    }

    @Test
    public void testInsertMoneyMultipleTimes() {
        machine.insertMoney(1.00);
        machine.insertMoney(2.50);
        machine.insertMoney(0.50);

        assertEquals(4.00, machine.getBalance(), 0.001);
    }

    @Test
    public void testInsertZeroMoney() {
        machine.insertMoney(0.0);

        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    public void testInsertNegativeMoneyThrowsException() {
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> machine.insertMoney(-0.01)
        );

        assertEquals(
                "Invalid amount.  Amount must be >= 0",
                exception.getMessage()
        );

        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 0.50, 0.99, 1.00, 1.01})
    public void testInsertMoneyBoundaryValues(double amount) {
        machine.insertMoney(amount);

        assertEquals(amount, machine.getBalance(), 0.001);
    }

    // ---------- getBalance() ----------

    @Test
    public void testGetBalanceReturnsCurrentBalance() {
        machine.insertMoney(3.75);

        assertEquals(3.75, machine.getBalance(), 0.001);
    }

    @Test
    public void testGetBalanceDoesNotChangeBalance() {
        machine.insertMoney(2.00);

        machine.getBalance();
        machine.getBalance();

        assertEquals(2.00, machine.getBalance(), 0.001);
    }

    // ---------- makePurchase() ----------

    @Test
    public void testSuccessfulPurchase() {
        machine.addItem(item, "A");
        machine.insertMoney(2.00);

        assertTrue(machine.makePurchase("A"));
        assertEquals(0.50, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    public void testPurchaseWithExactAmount() {
        machine.addItem(item, "A");
        machine.insertMoney(1.50);

        assertTrue(machine.makePurchase("A"));
        assertEquals(0.0, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    public void testPurchaseWithInsufficientMoney() {
        machine.addItem(item, "A");
        machine.insertMoney(1.49);

        assertFalse(machine.makePurchase("A"));
        assertEquals(1.49, machine.getBalance(), 0.001);
        assertSame(item, machine.getItem("A"));
    }

    @Test
    public void testPurchaseFromEmptySlotReturnsFalse() {
        machine.insertMoney(5.00);

        assertFalse(machine.makePurchase("A"));
        assertEquals(5.00, machine.getBalance(), 0.001);
    }

    @Test
    public void testPurchaseWithInvalidCodeThrowsException() {
        assertThrows(
                VendingMachineException.class,
                () -> machine.makePurchase("X")
        );
    }

    @Test
    public void testPurchaseFreeItem() {
        VendingMachineItem free =
                new VendingMachineItem("Free item", 0.0);

        machine.addItem(free, "A");

        assertTrue(machine.makePurchase("A"));
        assertEquals(0.0, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    public void testFailedPurchaseDoesNotRemoveItem() {
        machine.addItem(item, "A");

        assertFalse(machine.makePurchase("A"));

        assertSame(item, machine.getItem("A"));
    }

    @Test
    public void testFailedPurchaseDoesNotChangeBalance() {
        machine.addItem(item, "A");
        machine.insertMoney(1.00);

        assertFalse(machine.makePurchase("A"));

        assertEquals(1.00, machine.getBalance(), 0.001);
    }

    @Test
    public void testMultipleSuccessfulPurchases() {
        VendingMachineItem candy =
                new VendingMachineItem("Candy", 2.00);

        machine.addItem(item, "A");
        machine.addItem(candy, "B");
        machine.insertMoney(5.00);

        assertTrue(machine.makePurchase("A"));
        assertTrue(machine.makePurchase("B"));

        assertEquals(1.50, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
        assertNull(machine.getItem("B"));
    }

    // ---------- returnChange() ----------

    @Test
    public void testReturnChangeReturnsBalance() {
        machine.insertMoney(4.50);

        assertEquals(4.50, machine.returnChange(), 0.001);
    }

    @Test
    public void testReturnChangeResetsBalanceToZero() {
        machine.insertMoney(4.50);

        machine.returnChange();

        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    public void testReturnChangeWhenBalanceIsZero() {
        assertEquals(0.0, machine.returnChange(), 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    public void testReturnChangeCanBeCalledMultipleTimes() {
        machine.insertMoney(3.00);

        assertEquals(3.00, machine.returnChange(), 0.001);
        assertEquals(0.0, machine.returnChange(), 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    public void testReturnChangeAfterPurchase() {
        machine.addItem(item, "A");
        machine.insertMoney(2.00);
        machine.makePurchase("A");

        assertEquals(0.50, machine.returnChange(), 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }
}
