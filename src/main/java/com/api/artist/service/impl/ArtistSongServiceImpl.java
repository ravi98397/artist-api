package com.api.artist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.artist.model.Artist;
import com.api.artist.model.ArtistSong;
import com.api.artist.model.ArtistSongPK;
import com.api.artist.mq.EventEmitter;
import com.api.artist.mq.Runner;
import com.api.artist.repository.ArtistSongRepository;
import com.api.artist.service.ArtistSongService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ArtistSongServiceImpl implements ArtistSongService{
	
	@Autowired
	private ArtistSongRepository repo;
	
	@Autowired
	private ArtistServiceImpl artistService;
	
	@Autowired
	private EventEmitter emitter;
	
	@Override
	public List<ArtistSong> listallArtistSongRel() {
		return repo.findAll();
	}
	
	@Override
	public List<String> getSongIdFormArtistId(String ArtistId) {
		List<String> songId = repo.findSongIdByArtistId(ArtistId);
		return songId;
	}


	@Override
	public List<String> getArtistIdFromSongId(String SongId) {
		List<String> artistId = repo.findArtistIdBySongId(SongId);
		
		return artistId;
	}
	

	@Override
	public ArtistSong newAristSongRel(ArtistSong rel) {
		ArtistSong obj = repo.save(rel);
		if(obj != null) {
			try {
				emitter.publishUpdateArtistSongRelEvent("REL_EXCHANGE", "song.artist.update", rel);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
		}
		return null;
	}

	@Override
	public void deleteArtistSongRel(ArtistSongPK id) {
		ArtistSong obj = repo.findById(id).orElse(null);
		if(obj == null) {
			repo.delete(obj);
			try {
				System.out.println("sending ArtistSong delete rel");
				emitter.publishDeleteArtistSongRelEvent("REL_EXCHANGE", "song.artist.delete", obj);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//this will be used when the rel table is updated based on events received
	public void updateArtistSongRel(ArtistSong obj) {
		ArtistSongPK pk = new ArtistSongPK();
		pk.setArtistId(obj.getArtistId());
		pk.setSongId(obj.getSongId());
		
		if(repo.findById(pk).orElse(null) == null && artistService.getById(obj.getArtistId()) != null) {
			repo.save(obj);
			System.out.println("Event Processed");
		}
	}

	public List<String> getAllArtistRelByArtistId(String artistId){
		return repo.findAllArtistRelToArtistId(artistId);
	}
	
}
