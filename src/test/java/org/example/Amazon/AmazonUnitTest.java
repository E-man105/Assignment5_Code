package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AmazonUnitTest {

    private ShoppingCart mockCart;
    private PriceRule mockRule;
    private Amazon amazon;

    @BeforeEach
    void setup() {
        mockCart = mock(ShoppingCart.class);
        mockRule = mock(PriceRule.class);

        // Return a dummy item list for the cart
        when(mockCart.getItems()).thenReturn(List.of());

        amazon = new Amazon(mockCart, List.of(mockRule));
    }

    @Test
    @DisplayName("specification-based")
    void calculateShouldCallPriceRule() {
        amazon.calculate();
        verify(mockRule, times(1)).priceToAggregate(any());
    }

    @Test
    @DisplayName("structural-based")
    void addToCartShouldCallCartAdd() {
        Item dummyItem = new Item(null, "Test", 1, 10.0);
        amazon.addToCart(dummyItem);
        verify(mockCart, times(1)).add(dummyItem);
    }
}