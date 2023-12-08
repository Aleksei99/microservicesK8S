package com.smuraha.customer;

import com.smuraha.amqp.RabbitMQMessageProducer;
import com.smuraha.clients.fraud.FraudCheckResponse;
import com.smuraha.clients.fraud.FraudClient;
import com.smuraha.clients.notification.NotificationClient;
import com.smuraha.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        RestTemplate restTemplate,
        FraudClient fraudClient,
        NotificationClient notificationClient,
        RabbitMQMessageProducer rabbitMQMessageProducer
) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        final NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                String.format("some message that user %s  has been registered", customer.getFirstName())
        );
//        notificationClient.sendNotification(
//                notificationRequest
//        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");

    }
}
