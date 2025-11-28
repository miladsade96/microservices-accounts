package tech.miladsadeghi.accounts.services.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import tech.miladsadeghi.accounts.dtos.CardsDTO;

@FeignClient(name = "cards",fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards/fetch", consumes = "application/json")
    ResponseEntity<CardsDTO> fetchCardDetails(
            @RequestHeader("ms-correlation-id") String correlationId,
            @RequestParam String mobileNumber
    );

}
