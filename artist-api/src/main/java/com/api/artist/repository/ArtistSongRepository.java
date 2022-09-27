package com.api.artist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.artist.model.ArtistSong;
import com.api.artist.model.ArtistSongPK;


public interface ArtistSongRepository extends JpaRepository<ArtistSong, ArtistSongPK> {
	List<ArtistSong> findByArtistId(String artistId);
	List<ArtistSong> findBySongId(String songId);
	
	@Query(
		value = "SELECT artist_id from artist_song where song_id in (select song_id from artist_song where artist_id = ?1)",
		nativeQuery = true)
	List<String> findAllArtistRelToArtistId(String artistId);

	@Query(
		value = "select song_id from artist_song where artist_id = ?1",
		nativeQuery = true)
	List<String> findSongIdByArtistId(String artistId);
	
	@Query(
		value = "select artist_id from artist_song where song_id = ?1",
		nativeQuery = true)
	List<String> findArtistIdBySongId(String songId);
}
