package com.api.artist.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;



@Entity
@Table
@Data
@IdClass(ArtistSongPK.class)
public class ArtistSong {
	
	@Id
	private String artistId;
	
	@Id
	private String songId;
	
}

