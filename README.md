# kafka-microservices-ecommerce

> Project Information
- [x] Spring Boot 2.3.4.RELEASE
- [x] Apache Kafka
- [x] Apache Avro
- [x] Schema Registry
- [x] Gradle
- [x] Docker (`checkout_api -> docker -> docker-compose.yml`)
- [x] Adminer (`http://localhost:8080/`)  
- [ ] Swagger
- [ ] Unit Tests

> Endpoints
#### `http://localhost:8085/v1/checkout`
#### Body (`Exemple`):
```
{
"firstName": "Airton",
"lastName": "Oliveira",  
"email": "",  
"address": "",
"complement": "",
"country": "",
"state": "",
"cep": "",
"saveAddress": "true",
"saveInfo": "true",
"paymentMethod": "",
"cardName": "",
"cardNumber": "",
"cardDate": "05/2020",
"cardCvv": "123",
"products": [ "Mouse", "Keyboard", "Headset" ]
}
```

