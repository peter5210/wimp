package com.libertymutual.goforcode.wimp.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class MovieTests {

	@Test
	public void checking_to_make_sure_my_getters_and_setters_work() {
		BeanTester tester = new BeanTester();
		Configuration configuration = new ConfigurationBuilder()
				//				.ignoreProperty("releaseDate")
				//				.ignoreProperty("birthDate")
				.build();
		tester.testBean(Movie.class, configuration);
	}

	@Test
	public void if_this_is_the_first_actor_then_create_a_new_list_and_add_the_actor() {
		//arrange
		Actor randomActor = new Actor();
		Movie randomMovie = new Movie();

		//act
		randomMovie.addActor(randomActor);

		//assert
		assertThat(randomMovie.getActors()).contains(randomActor);
	}

	@Test
	public void if_there_is_already_an_array_list_confirm_that_a_new_actor_is_added() {
		//arrange
		Movie randomMovie = new Movie();
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new Actor());
		actors.add(new Actor());
		actors.add(new Actor());		
		Actor randomActor = new Actor();
		randomMovie.setActors(actors);
		

		//act
		randomMovie.addActor(randomActor);

		//assert
		assertThat(actors.size()).isEqualTo(4);
		assertThat(actors).contains(randomActor);
	}
}
