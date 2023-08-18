package com.example.productservice.command;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @Nonnull
    @Override
    public CommandMessage<?> handle(@Nonnull CommandMessage<?> message) {
        return MessageDispatchInterceptor.super.handle(message);
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            log.info("Intercepted command: " + command.getPayloadType());
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Price must be greater than zero");
                }
                if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isEmpty()) {
                    throw new IllegalArgumentException("Title cannot be empty");
                }
            }
            return command;
        };
    }
}
