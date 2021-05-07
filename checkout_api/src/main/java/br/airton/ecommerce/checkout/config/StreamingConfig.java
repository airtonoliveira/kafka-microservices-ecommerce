package br.airton.ecommerce.checkout.config;

import br.airton.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.airton.ecommerce.checkout.streaming.PaymentPaidSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        CheckoutCreatedSource.class,
        PaymentPaidSink.class
})
public class StreamingConfig {
}
