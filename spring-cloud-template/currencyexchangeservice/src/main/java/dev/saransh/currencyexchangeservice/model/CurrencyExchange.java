package dev.saransh.currencyexchangeservice.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class CurrencyExchange {
    @NonNull
    @Id
    private long id;
    @NonNull
    @Column(name = "currency_from")
    private String from;
    @NonNull
    @Column(name = "currency_to")
    private String to;
    @NonNull
    private BigDecimal conversionMultiple;
    private String environment;
}
