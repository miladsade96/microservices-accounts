package tech.miladsadeghi.accounts.services.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tech.miladsadeghi.accounts.dtos.CardsDTO;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDTO> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
