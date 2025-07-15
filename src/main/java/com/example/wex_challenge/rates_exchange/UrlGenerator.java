package com.example.wex_challenge.rates_exchange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UrlGenerator {

    // has the question mark to acknowledge usage of query parameters
    private final String BASE_URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange?";

    private final String FIELDS_QUERY_PARAMS = "fields=effective_date,record_date,country,country_currency_desc,currency,exchange_rate";
    private final String SORT_QUERY_PARAMS = "sort=-effective_date";
    private final String FILTER_PREFIX = "filter=";

    public String buildUrl(FilterDto filtersDto) {

        String filterQuery = this.buildFilterQuery(filtersDto);

        String concatenatedQueries = String.join("&", FIELDS_QUERY_PARAMS, SORT_QUERY_PARAMS, filterQuery);
        return String.join("", BASE_URL, concatenatedQueries);
    }

    private boolean isStringNonNullOrNonEmpty(String text) {
        return text != null && !text.isEmpty();
    }

    private String buildFilterQuery(FilterDto filtersDto) {
        LocalDate transactionDate = filtersDto.getTransactionDate();

        if (transactionDate == null) {
            throw new FilterWithoutTransactionDateException();
        }

        String effectiveDateFilter = "effective_date:gte:"
                + transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String countryCurrency = filtersDto.getCountryCurrencyDesc();

        if (isStringNonNullOrNonEmpty(countryCurrency)) {
            String countryCurrencyFilter = "country_currency_desc:eq:" + countryCurrency;

            return String.join("", FILTER_PREFIX, String.join(",", effectiveDateFilter, countryCurrencyFilter));

        }
        String country = filtersDto.getCountry();
        String currency = filtersDto.getCurrency();

        if (!isStringNonNullOrNonEmpty(currency) && !isStringNonNullOrNonEmpty(country)) {
            throw new FilterWithoutCurrencyOrCountryException();
        }
        String countryFilter = isStringNonNullOrNonEmpty(country) ? "country:eq:" + country : "";
        String currencyFilter = isStringNonNullOrNonEmpty(currency) ? "currency:eq:" + currency : "";

        String usefulFilters = Arrays.asList(
                effectiveDateFilter,
                countryFilter,
                currencyFilter).stream().filter(s -> !s.trim().isEmpty()) // Removes empty and whitespace-only strings
                .collect(Collectors.joining(","));

        return String.join("", FILTER_PREFIX, usefulFilters);
    }

}