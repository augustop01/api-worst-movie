package com.worstmovie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_year")
    private Integer year;

    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStudios() { return studios; }
    public void setStudios(String studios) { this.studios = studios; }
    public String getProducers() { return producers; }
    public void setProducers(String producers) { this.producers = producers; }
    public Boolean getWinner() { return winner; }
    public void setWinner(Boolean winner) { this.winner = winner; }
}