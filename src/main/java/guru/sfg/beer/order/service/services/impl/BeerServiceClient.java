package guru.sfg.beer.order.service.services.impl;

import guru.sfg.beer.order.service.services.BeerService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceClient implements BeerService {

    private final String BEER_UPC_PATH = "/api/v1/beer/beerUpc/";
    private final String BEER_PATH = "/api/v1/beer/";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public void setBeerServiceClientHost(String beerServiceClientHost) {
        this.beerServiceHost = beerServiceClientHost;
    }

    public BeerServiceClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH + upc, BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH + id.toString(), BeerDto.class));
    }

    public void setBeerServiceHost(String beerServiceHost){
        this.beerServiceHost = beerServiceHost;
    }
}
