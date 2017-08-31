package com.libertymutual.goforcode.wimp.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActorWithMovies extends Actor {
	
	@JsonProperty
	public List<Movie> noReallyMovies() {
		return getMovies();
	}

}
