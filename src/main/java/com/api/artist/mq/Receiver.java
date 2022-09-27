package com.api.artist.mq;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.artist.model.ArtistSong;
import com.api.artist.mq.model.ArtistSongRel;
import com.api.artist.service.impl.ActionMapperService;
import com.api.artist.service.impl.ArtistSongServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Receiver {
	
	@Autowired
	private ActionMapperService msgProcessor;
	
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	//@RabbitHandler
	public void receiveMessage(String message) {
	  System.out.println("Received <" + message + ">");
	  msgProcessor.processMessage(message);
	  latch.countDown();
	}

	public CountDownLatch getLatch() {
	  return latch;
	}
}
