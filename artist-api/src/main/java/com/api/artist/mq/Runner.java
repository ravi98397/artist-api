package com.api.artist.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.api.artist.mq.model.ArtistSongRel;

@Component
public class Runner implements CommandLineRunner {
	
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;
	
	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending Message...");
		if(args.length > 0) {
			rabbitTemplate.convertAndSend(args[0], args[1], new ArtistSongRel("123", "1234"));
		}
	}

}
