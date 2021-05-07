package br.airton.ecommerce.checkout.listener;

import br.airton.ecommerce.checkout.entity.CheckoutEntity;
import br.airton.ecommerce.checkout.service.CheckoutService;
import br.airton.ecommerce.checkout.streaming.PaymentPaidSink;
import br.airton.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    private final CheckoutService checkoutService;


    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent paymentCreatedEvent) {
        checkoutService.updateStatus(paymentCreatedEvent.getCheckoutCode().toString(), CheckoutEntity.Status.APPROVED);
    }
}
