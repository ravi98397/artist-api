package com.api.artist;

import java.io.IOException;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.api.artist.mq.Receiver;


@SpringBootApplication
@EnableEurekaClient
public class ArtistApiApplication {
	
		static final String topicExchangeName = "REL_EXCHANGE";

		static final String queueName = "ARTIST_QUEUE";
		
		@Bean
		Queue queue() {
		  return new Queue(queueName, false);
		}

		@Bean
		TopicExchange exchange() {
		  return new TopicExchange(topicExchangeName);
		}

		@Bean
		Binding binding(Queue queue, TopicExchange exchange) {
		   return BindingBuilder.bind(queue).to(exchange).with("*.artist.*");
		}
		

		  @Bean
		  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
		      MessageListenerAdapter listenerAdapter) {
		    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		    container.setConnectionFactory(connectionFactory);
		    container.setQueueNames(queueName);
		    container.setMessageListener(listenerAdapter);
		    return container;
		  }

		  @Bean
		  MessageListenerAdapter listenerAdapter(Receiver receiver) {
			 return new MessageListenerAdapter(receiver, "receiveMessage");
		  }
		  

		public static void main(String[] args) throws IOException {
			SpringApplication.run(ArtistApiApplication.class, args);
		}

}
