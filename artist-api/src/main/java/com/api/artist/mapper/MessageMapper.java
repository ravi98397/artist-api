package com.api.artist.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.artist.model.ArtistSong;
import com.api.artist.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageMapper {
	
	@Autowired
	ObjectMapper mapper;
	
	public Message getMassageObj(String strObj) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(strObj, Message.class);
	}
	
	public ArtistSong getSongArtistObj(String strObj) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(strObj, ArtistSong.class);
	}
}
