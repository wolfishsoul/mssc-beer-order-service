package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.BeerService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerInventoryService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setMapper(BeerOrderLineMapper mapper) {
        this.beerOrderLineMapper = mapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto orderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
        Optional<BeerDto> beerDto = beerService.getBeerByUpc(line.getUpc());

        beerDto.ifPresent(returnedBeerDto -> {
            orderLineDto.setBeerName(returnedBeerDto.getName());
            orderLineDto.setBeerStyle(returnedBeerDto.getStyle());
            orderLineDto.setUpc(returnedBeerDto.getUpc());
            orderLineDto.setPrice(returnedBeerDto.getPrice());
        });

        return orderLineDto;
    }

}
