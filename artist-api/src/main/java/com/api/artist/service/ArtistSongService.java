package com.api.artist.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.api.artist.model.ArtistSong;
import com.api.artist.model.ArtistSongPK;


public interface ArtistSongService {
	public List<ArtistSong> listallArtistSongRel();
	
	public List<String> getSongIdFormArtistId(String ArtistId);
	
	public List<String> getArtistIdFromSongId(String SongId);
	
	public void deleteArtistSongRel(ArtistSongPK id);
	
	public ArtistSong newAristSongRel(ArtistSong artist);
	
	// this method is only used to process the events received from mq
	public void updateArtistSongRel(ArtistSong obj);

	//List<String> getArtistIdFromSongIds(List<String> SongIds);
}
