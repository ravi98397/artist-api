package com.api.artist.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.artist.model.ArtistSong;
import com.api.artist.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventEmitter {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String createMessage(String Obj, String routingKey) throws JsonProcessingException {
		Message msg = new Message();
		msg.setROUTING_KEY(routingKey);
		msg.setOBJECT(Obj);
		return objectMapper.writeValueAsString(msg);
	}
	
	public void publishMsgEvent(String exchangeType, String routingKey, String msg) {
		try {
			rabbitTemplate.convertAndSend(exchangeType, routingKey, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("unable to public MsgEvent");
			e.printStackTrace();
		}
	}
	
	public void publishUpdateArtistSongRelEvent(String exchangeType, String routingKey, ArtistSong msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "artist.song.update";
		
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
		System.out.println("send ArtistSong update rel");
	}
	
	public void publishDeleteArtistSongRelEvent(String exchangeType, String routingKey, ArtistSong msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "artist.song.delete";
		
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
	}

}
