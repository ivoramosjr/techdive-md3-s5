package br.senai.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "admin", "admin", "tcp://localhost:61616"
        );

        try{
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Destination destination = session.createQueue("fila");

            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    try{
                        System.out.println("Mensagem: "+textMessage.getText());
                        textMessage.acknowledge();
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
