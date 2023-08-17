package com.example.productservice.command;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Builder
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private final String productId;;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;
}
