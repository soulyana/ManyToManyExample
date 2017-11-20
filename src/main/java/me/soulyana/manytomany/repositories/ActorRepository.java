package me.soulyana.manytomany.repositories;

import me.soulyana.manytomany.entitites.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
