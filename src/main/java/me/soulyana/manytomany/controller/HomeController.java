package me.soulyana.manytomany.controller;

import com.cloudinary.utils.ObjectUtils;
import me.soulyana.manytomany.configuration.CloudinaryConfig;
import me.soulyana.manytomany.entitites.Actor;
import me.soulyana.manytomany.entitites.Movie;
import me.soulyana.manytomany.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listActors(Model model) {
//        //Create an actor
//        Actor actor = new Actor();
//        actor.setName("Sandra Bullock");
//        actor.setRealname("Sandra Mae Bullowski");
//
//        //Create a movie
//        Movie movie = new Movie();
//        movie.setTitle("Emoji Movie");
//        movie.setYear(2017);
//        movie.setDescription("About Emojis...");
//
//        //Add the movie to an empty list
//        Set<Movie> movies = new HashSet<Movie>();
//        movies.add(movie);
//
//        //Add the list of movies to the actor's movie list
//        //Creares teh relationship between actor & particular moving that im setting
//        actor.setMovies(movies);
//
//        //Save the actor to the database
//        actorRepository.save(actor);

        //Grab all the actors from the database & send them to the template
        model.addAttribute("actors", actorRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newActor(Model model) {
        model.addAttribute("actor", new Actor());
        return "form";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            String filename = uploadResult.get("public_id").toString();
            System.out.println("This is the name of the cloudinary file: " + filename);
            String transformed = cloudc.createUrl(filename, 100, 100, "crop");
            String redborder = cloudc.transformThis(filename);
            System.out.println("this is the transformed image: " + transformed);
            System.out.println("this is the red border image: " + redborder);
            actorRepository.save(actor);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }
}
