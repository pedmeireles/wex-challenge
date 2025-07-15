package com.example.wex_challenge.ratesExchange;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.wex_challenge.transaction.TransactionDto;

@ExtendWith(MockitoExtension.class)
class EvaluateExchangeRateTest {

    @ParameterizedTest
    @MethodSource("provideRatesAndTransactionsWithExpectedOutput")
    void computeCorrectlyTheExchangeRate(String exchangeRate, String transactionAmount, String expectedValue) {

        RatesDto ratesDto = RatesDto.builder()
                .exchangeRate(new BigDecimal(exchangeRate))
                .build();

        TransactionDto transaction = TransactionDto.builder()
                .amount(new BigDecimal(transactionAmount))
                .build();

        assertEquals(EvaluateExchangeRate.evaluateExchangeRate(ratesDto, transaction), new BigDecimal(expectedValue));

    }

    static Stream<Arguments> provideRatesAndTransactionsWithExpectedOutput() {
        return Stream.of(
                Arguments.of("1.00", "1.00", "1.00"),
                Arguments.of("2.00", "1.00", "2.00"),
                Arguments.of("3.00", "1.00", "3.00"),
                Arguments.of("1.00", "1.250", "1.25"),
                Arguments.of("2.00", "1.250", "2.50"),
                Arguments.of("3.00", "1.250", "3.75"),
                Arguments.of("1.00", "1.413", "1.41"),
                Arguments.of("2.00", "1.413", "2.83"),
                Arguments.of("3.00", "1.413", "4.24"),
                Arguments.of("1.00", "1.531", "1.53"),
                Arguments.of("2.00", "1.531", "3.06"),
                Arguments.of("3.00", "1.531", "4.59"),
                Arguments.of("1.00", "1.997", "2.00"),
                Arguments.of("2.00", "1.997", "3.99"),
                Arguments.of("3.00", "1.997", "5.99"),
                Arguments.of("1.00", "1.50", "1.50"),
                Arguments.of("2.00", "1.50", "3.00"),
                Arguments.of("3.00", "1.50", "4.50")
        );
    }

}
