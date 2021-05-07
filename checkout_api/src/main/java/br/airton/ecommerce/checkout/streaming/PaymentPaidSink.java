package br.airton.ecommerce.checkout.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentPaidSink {

    String INPUT = "payment-paid-output";

    @Input(INPUT)
    SubscribableChannel input();
}
