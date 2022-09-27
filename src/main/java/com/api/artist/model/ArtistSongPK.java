package com.api.artist.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistSongPK implements Serializable{
	private String artistId;
	private String songId;
}
