package com.api.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.artist.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, String>{
}
