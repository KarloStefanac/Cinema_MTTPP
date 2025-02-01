package com.zavrsni.Cinema.application.loader;

import com.zavrsni.Cinema.persistence.entity.*;
import com.zavrsni.Cinema.persistence.repository.HallRepository;
import com.zavrsni.Cinema.persistence.repository.MovieRepository;
import com.zavrsni.Cinema.persistence.repository.SeatRepository;
import com.zavrsni.Cinema.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private HallRepository hallRepository;
    private SeatRepository seatRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    public void populateSeats() {
        int numberOfSeats = 128;
        if (!seatRepository.existsData()) {
            for (int i = 0; i < numberOfSeats; i++) {
                SeatEntity seat = new SeatEntity();
                seatRepository.save(seat);
            }
        }
    }

    public void populateMovies() {
        MovieEntity movie = new MovieEntity();
        if (!movieRepository.existsByName("Memento")) {
            movie.setName("Memento");
            movie.setDirector("Christopher Nolan");
            movie.setDuration(113);
            movie.setPrice(10.0);
            movie.setGenres(List.of(Genre.Thriller, Genre.Action));
            movie.setImage("https://m.media-amazon.com/images/M/MV5BZTcyNjk1MjgtOWI3Mi00YzQwLWI5MTktMzY4ZmI2NDAyNzYzXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg");
            movieRepository.save(movie);
        }
    }

    public void addUsers() {
        UserEntity admin = new UserEntity();
        if (!userRepository.existsByUsername("admin")) {
            admin.setUsername("admin");
            admin.setPassword("$2a$12$CpL9q9txTdKn8PNYJe6az.6jFOwSsZt2jfqgvY0QCmOIbfO0UNfG.");
            admin.setRole("USER,ADMIN");
            admin.setFirstName("Admin");
            admin.setLastName("Adminski");
            userRepository.save(admin);
        }

        UserEntity user = new UserEntity();
        if (!userRepository.existsByUsername("user")) {
            user.setUsername("user");
            user.setPassword("$2a$12$QdDgJ25LVr.WGK/XQARdoOSyA7a54nEgqDayLbZ0LQ41QFJLWMZRq");
            user.setRole("USER");
            user.setFirstName("User");
            user.setLastName("Userinski");
            userRepository.save(user);
        }
    }

    public void populateHalls() {
        for (int i = 1; i <= 3; i++) {
            HallEntity hallEntity = new HallEntity();
            if (!hallRepository.existsByName("Hall " + i)) {
                hallEntity.setName("Hall " + i);
                hallEntity.setCapacity(30 * i);
                hallRepository.save(hallEntity);
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        populateHalls();
        populateSeats();
        populateMovies();
        addUsers();
    }
}
