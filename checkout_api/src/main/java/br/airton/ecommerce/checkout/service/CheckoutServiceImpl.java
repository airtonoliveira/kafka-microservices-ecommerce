package br.airton.ecommerce.checkout.service;

import br.airton.ecommerce.checkout.entity.CheckoutEntity;
import br.airton.ecommerce.checkout.entity.CheckoutItemEntity;
import br.airton.ecommerce.checkout.entity.ShippingEntity;
import br.airton.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.airton.ecommerce.checkout.repository.CheckoutRepository;
import br.airton.ecommerce.checkout.resource.checkout.CheckoutRequest;
import br.airton.ecommerce.checkout.streaming.CheckoutCreatedSource;
import br.airton.ecommerce.checkout.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        log.info("M=create, checkoutRequest={}", checkoutRequest);
        String codeUUID = uuidUtil.createUUID().toString();
        CheckoutEntity.Status statusCreated = CheckoutEntity.Status.CREATED;
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(codeUUID)
                .status(statusCreated)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
                .shipping(ShippingEntity.builder()
                                  .address(checkoutRequest.getAddress())
                                  .complement(checkoutRequest.getComplement())
                                  .country(checkoutRequest.getCountry())
                                  .state(checkoutRequest.getState())
                                  .cep(checkoutRequest.getCep())
                                  .build())
                .build();
        checkoutEntity.setItems(checkoutRequest.getProducts()
                                        .stream()
                                        .map(product -> CheckoutItemEntity.builder()
                                                .checkout(checkoutEntity)
                                                .product(product)
                                                .build())
                                        .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(codeUUID)
                .setStatus(statusCreated.name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
