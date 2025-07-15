package com.example.wex_challenge.rates_exchange;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RatesExchangeService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final UrlGenerator urlGenerator = new UrlGenerator();

    public List<RatesDto> fetchRates(FilterDto filterDto)  {

        String url = urlGenerator.buildUrl(filterDto);
        try{
            List<RatesDto> response = restTemplate.getForObject(url, ApiDto.class).getData();

            if(response == null){
                return new ArrayList<>();
            }
            return response;
        } catch (NullPointerException e){
            throw  e;
        
        } catch (Exception e){
            throw  e;
        }


    }

}
