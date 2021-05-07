package br.airton.ecommerce.checkout.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CheckoutCreatedSource {

    String OUTPUT = "checkout-created-input";

    @Output(OUTPUT)
    MessageChannel output();
}
