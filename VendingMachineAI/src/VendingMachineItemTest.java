
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VendingMachineItemTest {

    private VendingMachineItem item;

    @BeforeEach
    public void setUp() {
        item = new VendingMachineItem("Chips", 1.50);
    }

    @Test
    public void testConstructor() {
        assertEquals("Chips", item.getName());
        assertEquals(1.50, item.getPrice(), 0.001);
    }

    @Test
    public void testConstructorWithZeroPrice() {
        VendingMachineItem free = new VendingMachineItem("Free", 0.0);

        assertEquals("Free", free.getName());
        assertEquals(0.0, free.getPrice(), 0.001);
    }

    @Test
    public void testConstructorWithNullName() {
        VendingMachineItem unnamed = new VendingMachineItem(null, 1.0);

        assertNull(unnamed.getName());
        assertEquals(1.0, unnamed.getPrice(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
        "0.01, 0.01",
        "0.50, 0.50",
        "1.00, 1.00",
        "1.50, 1.50",
        "100.00, 100.00"
    })
    public void testConstructorWithValidPrices(
            double price, double expected) {

        VendingMachineItem valid =
                new VendingMachineItem("Item", price);

        assertEquals(expected, valid.getPrice(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.01, -1.0, -100.0})
    public void testConstructorWithNegativePriceThrowsException(
            double price) {

        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> new VendingMachineItem("Item", price)
        );

        assertEquals(
                "Price cannot be less than zero",
                exception.getMessage()
        );
    }

    @Test
    public void testGetNameDoesNotChangePrice() {
        assertEquals("Chips", item.getName());
        assertEquals(1.50, item.getPrice(), 0.001);
    }

    @Test
    public void testGetPriceDoesNotChangeName() {
        assertEquals(1.50, item.getPrice(), 0.001);
        assertEquals("Chips", item.getName());
    }
}
