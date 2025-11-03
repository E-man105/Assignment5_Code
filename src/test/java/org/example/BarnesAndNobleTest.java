package org.example;

import org.example.Barnes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BarnesAndNobleTest {

    private BookDatabase mockDatabase;
    private BuyBookProcess mockProcess;
    private BarnesAndNoble barnes;

    @BeforeEach
    void setUp() {
        mockDatabase = mock(BookDatabase.class);
        mockProcess = mock(BuyBookProcess.class);

        barnes = new BarnesAndNoble(mockDatabase, mockProcess);
    }

    @Test
    @DisplayName("specification-based")
    void getPriceForCart_returnsCorrectTotal() {
        // Setup books
        Book book1 = new Book("ISBN1", 10, 5);
        Book book2 = new Book("ISBN2", 15, 3);

        // Mock database behavior
        when(mockDatabase.findByISBN("ISBN1")).thenReturn(book1);
        when(mockDatabase.findByISBN("ISBN2")).thenReturn(book2);

        // Create order
        Map<String, Integer> order = new HashMap<>();
        order.put("ISBN1", 2);
        order.put("ISBN2", 1);

        // Execute
        PurchaseSummary summary = barnes.getPriceForCart(order);

        // Verify total price
        assertThat(summary.getTotalPrice()).isEqualTo((2*10) + (1*15));

        // Verify unavailable books are empty
        assertThat(summary.getUnavailable()).isEmpty();

        // Verify that buyBook was called
        verify(mockProcess, times(1)).buyBook(book1, 2);
        verify(mockProcess, times(1)).buyBook(book2, 1);
    }

    @Test
    @DisplayName("structural-based")
    void getPriceForCart_handlesUnavailableBooks() {
        // Setup books with limited quantity
        Book book1 = new Book("ISBN1", 10, 1);

        when(mockDatabase.findByISBN("ISBN1")).thenReturn(book1);

        // Order more than available
        Map<String, Integer> order = new HashMap<>();
        order.put("ISBN1", 3);

        // Execute
        PurchaseSummary summary = barnes.getPriceForCart(order);

        // Verify total price only for available quantity
        assertThat(summary.getTotalPrice()).isEqualTo(1 * 10);

        // Verify unavailable quantity recorded correctly
        assertThat(summary.getUnavailable().get(book1)).isEqualTo(2);

        // Verify buyBook only called with available quantity
        verify(mockProcess, times(1)).buyBook(book1, 1);
    }

    @Test
    @DisplayName("specification-based")
    void getPriceForCart_returnsNullForNullOrder() {
        assertThat(barnes.getPriceForCart(null)).isNull();
    }
}