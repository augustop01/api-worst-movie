package com.worstmovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worstmovie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}