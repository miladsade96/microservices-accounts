package tech.miladsadeghi.accounts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    private String statusCode;
    private String statusMessage;
}
