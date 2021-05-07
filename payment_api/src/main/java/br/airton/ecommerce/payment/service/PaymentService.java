package br.airton.ecommerce.payment.service;

import br.airton.ecommerce.payment.entity.PaymentEntity;
import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;

import java.util.Optional;

public interface PaymentService {

    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
