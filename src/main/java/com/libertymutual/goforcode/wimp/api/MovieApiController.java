package com.libertymutual.goforcode.wimp.api;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.wimp.models.Actor;
import com.libertymutual.goforcode.wimp.models.Movie;
import com.libertymutual.goforcode.wimp.repositories.ActorRepository;
import com.libertymutual.goforcode.wimp.repositories.MovieRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RestController
@RequestMapping("/api/movies")
@Api(description="Use this to get and create movies and add actors to movies")
public class MovieApiController {

	private MovieRepository movieRepo;
	private ActorRepository actorRepo;
	
	public MovieApiController (MovieRepository movieRepo, ActorRepository actorRepo) {
		this.movieRepo = movieRepo;
		this.actorRepo = actorRepo;
	}
	
	@ApiOperation(value = "This will get ALL of the movies")
	@GetMapping("")
	public List<Movie> getAll() {
		return movieRepo.findAll();
	}
	
	@ApiOperation(value = "This will retrieve ONE movie")
	@GetMapping("{id}")
	public Movie getOne(@PathVariable long id) throws NoIdFoundException {
		Movie movie = movieRepo.findOne(id);
		if (movie == null) {
			throw new NoIdFoundException();
		}
		return movie; 
	}
	
	@ApiOperation(value = "Delete a movie")
	@DeleteMapping("{id}")
	public Movie delete(@PathVariable long id) {
	try {
		Movie movie = movieRepo.findOne(id);
		movieRepo.delete(id);
		return movie;
	} catch (EmptyResultDataAccessException erdae) {
		return null;
	}
}
	@ApiOperation(value = "This will create a movie")
	@PostMapping("")
	public Movie create(@RequestBody Movie movie) {
		return movieRepo.save(movie);	
	}
	
	@ApiOperation(value = "This will update a movie")
	@PutMapping("{id}")
	public Movie update(@RequestBody Movie movie, @PathVariable long id) {
		movie.setId(id);
		return movieRepo.save(movie);
	}
	
	@ApiOperation(value = "This will add an actor to a movie")
	@PostMapping("{movieId}/actors")
	public Movie associateAnActor(@PathVariable long movieId, @RequestBody Actor actor) {
		Movie movie = movieRepo.findOne(movieId);
		actor = actorRepo.findOne(actor.getId());
		
		movie.addActor(actor);
		movieRepo.save(movie);
		
		return movie;
	}
	
	
}
