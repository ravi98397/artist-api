package com.api.artist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.artist.exception.InvalidRoutingKey;
import com.api.artist.mapper.MessageMapper;
import com.api.artist.model.ArtistSong;
import com.api.artist.model.ArtistSongPK;
import com.api.artist.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class ActionMapperService {
	
	@Autowired
	private MessageMapper mapper;
	
	@Autowired
	private ArtistSongServiceImpl saService;
	
	
	public void processMessage(String message) {
			
		try {
			Message msg = mapper.getMassageObj(message);
			identifyMessage(msg);
			
		} catch (JsonProcessingException | InvalidRoutingKey e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid Message format");
			e.printStackTrace();
		}
		
	}

	private void identifyMessage(Message msg) throws InvalidRoutingKey, JsonMappingException, JsonProcessingException {
		String []keys = msg.getROUTING_KEY().split("[.]");
		System.out.println(msg.getROUTING_KEY());
		String source;
		String action;
		
		if(keys.length < 3) {
			throw new InvalidRoutingKey("Routing key foramt is invalid, it should follow from.to.action : " + msg.getROUTING_KEY());
		}else {
			source = keys[0];
			action = keys[2];
		}
		
		switch (source) {
		case "song":
			ArtistSong artistSong = mapper.getSongArtistObj(msg.getOBJECT());
			takeAction(artistSong, action);
			break;
		default:
			System.out.println("unknown source: no action taken");
			break;
		}
		
	}

	private void takeAction(ArtistSong artistSong, String action) {
		
		switch (action) {
		case "update":
			saService.updateArtistSongRel(artistSong);
			break;
		case "delete":
			ArtistSongPK pk = new ArtistSongPK();
			pk.setArtistId(artistSong.getArtistId());
			pk.setSongId(artistSong.getSongId());
			saService.deleteArtistSongRel(pk);
			break;
		default:
			System.out.println("unknown action: no action taken");
			break;
		}
		
	}
	
}
