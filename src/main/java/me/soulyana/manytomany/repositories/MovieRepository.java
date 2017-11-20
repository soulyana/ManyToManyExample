package me.soulyana.manytomany.repositories;

import me.soulyana.manytomany.entitites.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
