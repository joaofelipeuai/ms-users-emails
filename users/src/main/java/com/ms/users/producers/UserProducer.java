package com.ms.users.producers;

import com.ms.users.dtos.EmailDto;
import com.ms.users.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.emails.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Usuário: " + userModel.getName() + ", foi realizado com sucesso!");
        emailDto.setTextBody("Olá, " + userModel.getName() + "! Seja bem-vindo(a)! Agradecemos o seu cadastro, aproveite todos os nossos recursos!");

        rabbitTemplate.convertAndSend( "", routingKey, emailDto);

    }
}
