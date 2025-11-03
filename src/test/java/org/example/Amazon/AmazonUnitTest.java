package org.example.Amazon;

import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AmazonUnitTest {

    private ShoppingCart mockCart;
    private PriceRule mockRule;
    private Amazon amazon;

    @BeforeEach
    void setUp() {
        mockCart = mock(ShoppingCart.class);
        mockRule = mock(PriceRule.class);

        amazon = new Amazon(mockCart, Collections.singletonList(mockRule));
    }

    @Test
    @DisplayName("specification-based")
    void calculate_appliesPriceRuleCorrectly() {
        // Mock the cart items
        Item item = new Item(ItemType.OTHER, "Book A", 2, 10.0);
        when(mockCart.getItems()).thenReturn(Collections.singletonList(item));

        // Mock the price rule behavior
        when(mockRule.priceToAggregate(Collections.singletonList(item))).thenReturn(20.0);

        double total = amazon.calculate();

        assertThat(total).isEqualTo(20.0);

        // Verify that rule was called
        verify(mockRule, times(1)).priceToAggregate(Collections.singletonList(item));
    }

    @Test
    @DisplayName("structural-based")
    void addToCart_callsShoppingCartAdd() {
        Item item = new Item(ItemType.ELECTRONIC, "Headphones", 1, 50.0);

        amazon.addToCart(item);

        // Verify that the ShoppingCart add method was called
        verify(mockCart, times(1)).add(item);
    }
}