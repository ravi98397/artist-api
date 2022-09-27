package com.api.artist.controller;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.artist.model.Artist;
import com.api.artist.model.ArtistSong;
import com.api.artist.mq.Runner;
import com.api.artist.requests.UriDiscovery;
import com.api.artist.service.impl.ArtistSongServiceImpl;
import com.api.artist.service.impl.ArtistServiceImpl;

@RestController
public class Controller {
	
	@Autowired
	private Runner mqrunner;
	
	@Autowired
	private ArtistServiceImpl artistService;
	
	@Autowired
	private ArtistSongServiceImpl relService;
	
	@Autowired
	private UriDiscovery dis;
	
	@GetMapping("/api/geturi")
	public String geturi() throws MalformedURLException {
		return dis.getServiceURI("SONGAPI").toURL().toString();
	}
	
	@GetMapping("/api/artist/getAll")
	public List<Artist> getAllArtist() throws Exception{
		return artistService.listall();
	}
	
	
	//due to limitations on get with body I am using post.
	@PostMapping("/api/artist/getAllInIdList")
	public List<Artist> getAllArtistInIdList(@RequestBody List<String> ids) throws Exception{
		//System.out.println(ids.toString());
		return artistService.getByList(ids);
	}
	
	//this is to get all the artist related to the artistid received artist -> songid -> artist_id
	@GetMapping("api/artist/getSongRelArtist")
	public List<Artist> getAllSongRelArtist(@RequestParam String id){
		List<String> artist_id = relService.getAllArtistRelByArtistId(id);
		return artistService.getByList(artist_id);
	}
	
	@GetMapping("api/artist/getById")
	public Artist getArtistById(@RequestParam String id) {
		return artistService.getById(id);
	}
	
	@GetMapping("api/artist/getAllBySongId")
	public List<Artist> getAllBySongId(@RequestParam String id){
		return artistService.getBySongId(id);
	}
	
	
	
	@PostMapping("api/aritist/add")
	public Artist addArtist(@RequestBody Artist artist) {
		Artist artist2 = artistService.newArist(artist);
		if(artist2 == null) {
			//need to throw a exception 
			return null;
		}else {
			return artist2;
		}
	}
	
	@PostMapping("api/artist/artistSongRel/add")
	public Artist addArtistSongRel(@RequestBody ArtistSong rel) {
		ArtistSong newrel = relService.newAristSongRel(rel);
		if(newrel == null) {
			return null;
		}
		return artistService.getById(rel.getArtistId());
	}
	
	@PutMapping("api/artist/udpate")
	public Artist updateArtist(@RequestBody Artist artist) {
		Artist artist2 = artistService.updateArtist(artist);
		if(artist2 == null) {
			//need to throw a exception 
			return null;
		}else {
			return artist2;
		}
	}
	
	
}
