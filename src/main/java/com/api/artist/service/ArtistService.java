package com.api.artist.service;

import java.util.List;
import java.util.Set;

import com.api.artist.model.Artist;

public interface ArtistService {
	
	public List<Artist> listall();
	
	public List<Artist> getByList(List<String> ids);
	
	public List<Artist> getBySongId(String id);

	public List<Artist> getAllSongRelArtist(String id);
	
	public Artist getById(String id);
	
	public void deleteArtist(String id);
	
	public Artist newArist(Artist artist);

	public Artist updateArtist(Artist artist);

}
