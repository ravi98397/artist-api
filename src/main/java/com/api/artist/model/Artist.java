package com.api.artist.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
public class Artist {
	
	@Id
	private String id;
	private String name;
	private int age;
	
	@OneToMany(targetEntity = ArtistSong.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "artistId", referencedColumnName = "id")
	private Set<ArtistSong> songs = new HashSet<ArtistSong> ();
	
}
