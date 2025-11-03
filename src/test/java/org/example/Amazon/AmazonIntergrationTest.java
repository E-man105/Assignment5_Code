package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AmazonIntegrationTest {

    private Database database;
    private ShoppingCartAdaptor cart;
    private Amazon amazon;

    @BeforeEach
    void setUp() {
        // Initialize a fresh in-memory database before each test
        database = new Database();
        database.resetDatabase();
        cart = new ShoppingCartAdaptor(database);

        // Define a simple price rule that calculates total price
        PriceRule rule = items -> items.stream()
                .mapToDouble(i -> i.getPricePerUnit() * i.getQuantity())
                .sum();

        amazon = new Amazon(cart, Collections.singletonList(rule));
    }

    @Test
    @DisplayName("specification-based")
    void calculateTotalPrice_specificationBased() {
        // Add items to the cart
        cart.add(new Item(ItemType.OTHER, "Book A", 2, 10.0));
        cart.add(new Item(ItemType.ELECTRONIC, "Headphones", 1, 50.0));

        // Calculate total
        double total = amazon.calculate();

        // Expect: (2*10) + (1*50) = 70
        assertThat(total).isEqualTo(70.0);
    }

    @Test
    @DisplayName("structural-based")
    void calculateTotalPrice_structuralBased() {
        // Add items
        Item item1 = new Item(ItemType.OTHER, "Book B", 1, 15.0);
        Item item2 = new Item(ItemType.OTHER, "Toy Car", 3, 5.0);
        cart.add(item1);
        cart.add(item2);

        // Calculate total
        double total = amazon.calculate();

        // Verify total using structural reasoning (price rule applied to all items)
        assertThat(total).isEqualTo((1*15.0) + (3*5.0));
    }

    @Test
    @DisplayName("specification-based")
    void addToCart_addsItemCorrectly() {
        Item item = new Item(ItemType.OTHER, "Book C", 1, 12.5);
        amazon.addToCart(item);

        // Retrieve items from the database
        List<Item> itemsInCart = cart.getItems();
        assertThat(itemsInCart).containsExactly(item);
    }
}