package br.airton.ecommerce.payment.listener;

import br.airton.ecommerce.payment.entity.PaymentEntity;
import br.airton.ecommerce.payment.service.PaymentService;
import br.airton.ecommerce.payment.streaming.CheckoutProcessor;
import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.hatanaka.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {

    @Autowired
    private final CheckoutProcessor checkoutProcessor;

    @Autowired
    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {
        log.info("checkoutCreatedEvent={}", checkoutCreatedEvent);
        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
