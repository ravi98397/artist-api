package com.api.artist.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.artist.model.Artist;
import com.api.artist.repository.ArtistRepository;
import com.api.artist.service.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService{
	
	@Autowired
	private ArtistRepository repo;
	
	@Autowired
	private ArtistSongServiceImpl artistSong;
	
	@Override
	public List<Artist> listall() {
		return repo.findAll();
	}

	@Override
	public Artist getById(String id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void deleteArtist(String id) {
		repo.deleteById(id);
	}

	@Override
	public Artist newArist(Artist artist) {
		return repo.save(artist);
	}

	@Override
	public Artist updateArtist(Artist artist) {
		if(repo.findById(artist.getId()).orElse(null) != null) {
			return repo.save(artist);
		}else {
			return null;
		}
	}

	@Override
	public List<Artist> getByList(List<String> ids) {
		if(ids.size() > 0) {
			return repo.findAllById(ids);
		}
		return null;
	}

	@Override
	public List<Artist> getBySongId(String id) {
		List<String> ids = artistSong.getArtistIdFromSongId(id);
		return getByList(ids);
	}

	@Override
	public List<Artist> getAllSongRelArtist(String id) {
		List<String> ids = artistSong.getSongIdFormArtistId(id);
		
		return null;
	}
	
}
