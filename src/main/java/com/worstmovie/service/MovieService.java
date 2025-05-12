package com.worstmovie.service;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.worstmovie.dto.ProducerInterval;
import com.worstmovie.entity.Movie;
import com.worstmovie.repository.MovieRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void loadCsv() {
        logger.info("Started reading the file 'movielist.csv'...");
        try {
            movieRepository.deleteAll();
            logger.info("Finished removing all the movies previously registered in the dabatase;");
            try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(
                    new ClassPathResource("data/movielist.csv").getInputStream()))
                    .withCSVParser(new com.opencsv.CSVParserBuilder().withSeparator(';').build())
                    .build()) {
                logger.info("Finished loading the file 'movielist.csv';");
                String[] header = csvReader.readNext();
                if (header == null) {
                    logger.error("The file 'movielist.csv' is empty or has no header;");
                    return;
                }
                logger.info("File header: {}", Arrays.toString(header));
                List<Movie> movies = new ArrayList<>();
                int lineNumber = 1;
                for (String[] row : csvReader) {
                    lineNumber++;
                    try {
                        if (row.length < 5) {
                            logger.warn("Skipping row with insufficient data {}: {}", lineNumber, Arrays.toString(row));
                            continue;
                        }
                        Movie movie = new Movie();
                        movie.setYear(Integer.valueOf(row[0].trim()));
                        movie.setTitle(row[1].trim());
                        movie.setStudios(row[2].trim());
                        movie.setProducers(row[3].trim());

                        String winnerValue = row[4] != null ? row[4].trim() : "";
                        movie.setWinner("yes".equalsIgnoreCase(winnerValue));
                        movies.add(movie);
                        logger.debug("Analyzed the movie in row {}: year={}, title={}, studios={}, producers={}, winner={}",
                                lineNumber, movie.getYear(), movie.getTitle(), movie.getStudios(), movie.getProducers(), movie.getWinner());
                    } catch (NumberFormatException e) {
                        logger.error("Invalid format for the data 'year' on row {}: {}", lineNumber, row[0], e);
                    } catch (Exception e) {
                        logger.error("Failed to proccess row {}: {}", lineNumber, Arrays.toString(row), e);
                    }
                }
                if (!movies.isEmpty()) {
                    movieRepository.saveAll(movies);
                    logger.info("{} movies saved to the database;", movies.size());
                } else {
                    logger.warn("Found no valid movies in the file;");
                }
            } catch (Exception e) {
                logger.error("Failed to load 'movielist.csv';", e);
                throw new RuntimeException("Failed to load 'movielist.csv';", e);
            }
        } catch (Exception e) {
            logger.error("An error ocurred while trying to load the file 'movielist.csv';", e);
            throw new RuntimeException("An error ocurred while trying to load the file 'movielist.csv';", e);
        }
    }

    public Map<String, List<ProducerInterval>> getMinMaxIntervals() {
        logger.info("Searching for all winning movies to calculate the intervals...");
        List<Movie> winners = movieRepository.findAll().stream()
                .filter(Movie::getWinner)
                .collect(Collectors.toList());
        logger.info("Found {} winning movies;", winners.size());

        Map<String, List<Integer>> producerWins = new HashMap<>();
        for (Movie movie : winners) {
            String[] producers = movie.getProducers().split(",|and");
            for (String producer : producers) {
                producer = producer.trim();
                producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getYear());
            }
        }

        List<ProducerInterval> minIntervals = new ArrayList<>();
        List<ProducerInterval> maxIntervals = new ArrayList<>();
        int minInterval = Integer.MAX_VALUE;
        int maxInterval = Integer.MIN_VALUE;

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue().stream().sorted().collect(Collectors.toList());
            if (years.size() < 2) {
                continue;
            }

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);
                ProducerInterval intervalData = new ProducerInterval(
                        entry.getKey(),
                        interval,
                        years.get(i - 1),
                        years.get(i)
                );

                if (interval < minInterval) {
                    minIntervals.clear();
                    minIntervals.add(intervalData);
                    minInterval = interval;
                } else if (interval == minInterval) {
                    minIntervals.add(intervalData);
                }

                if (interval > maxInterval) {
                    maxIntervals.clear();
                    maxIntervals.add(intervalData);
                    maxInterval = interval;
                } else if (interval == maxInterval) {
                    maxIntervals.add(intervalData);
                }
            }
        }

        Map<String, List<ProducerInterval>> result = new HashMap<>();
        result.put("min", minIntervals);
        result.put("max", maxIntervals);
        logger.info("Intervalos: min={}, max={}", minIntervals.size(), maxIntervals.size());
        return result;
    }
}
