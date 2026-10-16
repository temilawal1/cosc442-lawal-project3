package game;

import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InventoryTest {
    private Inventory inventory;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory(3);
        item1 = new Item('%', Color.red, "Red", "Interesting");
        item2 = new Item('[', Color.green, "Green", "Calm");
        item3 = new Item(']', Color.blue, "Blue", "Bright");
        item4 = new Item(',', Color.yellow, "Yellow", "Weird");
    }

    @Test 
    public void testGetItems() {
        inventory.add(item1);

        assertEquals(item1, inventory.getItems()[0]);
    }

    @Test
    public void testAddItemToFullInventory() {
        inventory.add(item1);
        inventory.add(item2);
        inventory.add(item3);

        inventory.add(item4);

        assertTrue(inventory.isFull());
        assertFalse(inventory.contains(item4));
    }

    @Test
    public void testRemoveItemNotInInventory() {
        inventory.add(item1);
        inventory.remove(item2);

        assertTrue(inventory.contains(item1));
        assertFalse(inventory.contains(item2));
    }

    @Test
    public void testRemoveItemLeavesEmptySlot() {
        inventory.add(item1);
        inventory.add(item2);

        inventory.remove(item1);

        assertNull(inventory.get(0));
        assertEquals(item2, inventory.get(1));
    }

    @Test
    public void testAddReusesEmptySlot() {
        inventory.add(item1);
        inventory.add(item2);

        inventory.remove(item1);
        inventory.add(item3);

        assertEquals(item3, inventory.get(0));
        assertEquals(item2, inventory.get(1));
    }
}