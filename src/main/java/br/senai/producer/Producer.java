package br.senai.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

public class Producer {

    public static void main(String[] args) {

        ConnectionFactory factory = new ActiveMQConnectionFactory(
            "admin", "admin", "tcp://localhost:61616"
        );

        try{
            Connection connection = factory.createConnection();

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Destination destination = session.createQueue("fila");

            List<String> mensagens = new ArrayList<>();
            mensagens.add("Olá :D");
            mensagens.add("Nesta lista");
            mensagens.add("Contém as minhas mensagens!");

            MessageProducer producer = session.createProducer(destination);

            mensagens.stream().forEach(m -> {
                try {
                    TextMessage textMessage = session.createTextMessage(m);
                    producer.send(textMessage);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.printf("Mensagem Enviada!");

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
