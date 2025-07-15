package com.example.wex_challenge.ratesExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlGeneratorTests {

    @InjectMocks
    private UrlGenerator urlGenerator;

    private final String COUNTRY_EXAMPLE = "Brazil";
    private final String CURRENCY_EXAMPLE = "Real";
    private final String COUNTRY_CURRENCY_DESC_EXAMPLE = String.join("-", COUNTRY_EXAMPLE, CURRENCY_EXAMPLE);
    private final LocalDate TRANSACTION_DATE_EXAMPLE = LocalDate.of(2025, 1, 1);

    private final String BASE_URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange?";

    private final String FIELDS_QUERY_PARAMS = "fields=effective_date,record_date,country,country_currency_desc,currency,exchange_rate";
    private final String SORT_QUERY_PARAMS = "sort=-effective_date";

    @Test
    public void generateUrlWhenAllFieldsAreSetup() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                CURRENCY_EXAMPLE,
                COUNTRY_CURRENCY_DESC_EXAMPLE,
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,country_currency_desc:eq:" + COUNTRY_CURRENCY_DESC_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCountryCurrencyIsNonProvidedWithNull() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                CURRENCY_EXAMPLE,
                null,
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,country:eq:" + COUNTRY_EXAMPLE + ",currency:eq:"
                + CURRENCY_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCountryCurrencyIsNonProvidedWithEmptyString() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                CURRENCY_EXAMPLE,
                "",
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,country:eq:" + COUNTRY_EXAMPLE + ",currency:eq:"
                + CURRENCY_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCurrencyIsProvidedWithNullScenario() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                null,
                CURRENCY_EXAMPLE,
                null,
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,currency:eq:" + CURRENCY_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCurrencyIsProvidedWithEmptyStrignScenario() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                "",
                CURRENCY_EXAMPLE,
                "",
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,currency:eq:" + CURRENCY_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCountryIsProvidedNullScenario() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                null,
                null,
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,country:eq:" + COUNTRY_EXAMPLE);

    }

    @Test
    public void generateUrlWhenOnlyCountryIsProvidedEmptyStringScenario() {

        // precondition
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                "",
                "",
                TRANSACTION_DATE_EXAMPLE);

        // when
        String url = urlGenerator.buildUrl(filtersDto);

        // assert
        assertThat(url).isEqualTo(BASE_URL + FIELDS_QUERY_PARAMS + "&" + SORT_QUERY_PARAMS
                + "&filter=effective_date:gte:2025-01-01,country:eq:" + COUNTRY_EXAMPLE);

    }

    @Test
    public void throwAnErrorWhenOnlyTransactionDateIsProvidedEmptyStringScenarios() {
        FilterDto filtersDto = new FilterDto(
                "",
                "",
                "",
                TRANSACTION_DATE_EXAMPLE);
        FilterWithoutCurrencyOrCountry expectedError = Assertions.assertThrows(
                FilterWithoutCurrencyOrCountry.class,
                () -> urlGenerator.buildUrl(filtersDto));

        assertEquals(expectedError.getMessage(), "Filter does not have neither a country or a currency setup");

    }

    @Test
    public void throwAnErrorWhenOnlyTransactionDateIsProvidedNullScenarios() {
        FilterDto filtersDto = new FilterDto(
                null,
                null,
                null,
                TRANSACTION_DATE_EXAMPLE);
        FilterWithoutCurrencyOrCountry expectedError = Assertions.assertThrows(
                FilterWithoutCurrencyOrCountry.class,
                () -> urlGenerator.buildUrl(filtersDto));

        assertEquals(expectedError.getMessage(), "Filter does not have neither a country or a currency setup");

    }

    @Test
    public void throwAnErrorWhenTransactionDateIsNotProvidedNullScenario() {
        FilterDto filtersDto = new FilterDto(
                COUNTRY_EXAMPLE,
                CURRENCY_EXAMPLE,
                COUNTRY_CURRENCY_DESC_EXAMPLE,
                null);
        FilterWithoutTransactionDate expectedError = Assertions.assertThrows(
                FilterWithoutTransactionDate.class,
                () -> urlGenerator.buildUrl(filtersDto));

        assertEquals(expectedError.getMessage(), "Filter does not have a transaction date setup");

    }
}
