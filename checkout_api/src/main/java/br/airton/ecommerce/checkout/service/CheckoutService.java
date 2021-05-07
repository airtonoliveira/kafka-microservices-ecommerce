package br.airton.ecommerce.checkout.service;

import br.airton.ecommerce.checkout.entity.CheckoutEntity;
import br.airton.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
