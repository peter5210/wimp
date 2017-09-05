package com.libertymutual.goforcode.wimp.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.wimp.models.Actor;
import com.libertymutual.goforcode.wimp.models.Movie;
import com.libertymutual.goforcode.wimp.repositories.ActorRepository;
import com.libertymutual.goforcode.wimp.repositories.MovieRepository;

public class MovieApiControllerTests {

		private MovieRepository movieRepo;
		private ActorRepository actorRepo;
		private MovieApiController controller;

		@Before
		public void setUp() {
			actorRepo = mock(ActorRepository.class);
			movieRepo = mock(MovieRepository.class);
			controller = new MovieApiController(movieRepo, actorRepo);
		}


		@Test
		public void testing_to_make_sure_getAll_returns_all_movies() {
			//arrange
			ArrayList<Movie> movies = new ArrayList<Movie>();
			movies.add(new Movie());
			movies.add(new Movie());

			when(movieRepo.findAll()).thenReturn(movies);

			//act
			List<Movie> actual = controller.getAll();

			//assert
			assertThat(actual.size()).isEqualTo(2);
			assertThat(actual.get(0)).isSameAs(movies.get(0));
			verify(movieRepo).findAll();
		}

		@Test
		public void make_sure_that_getOne_returns_one_movie() throws NoIdFoundException {
			//arrange
			Movie randomMovie = new Movie();
			when(movieRepo.findOne(4l)).thenReturn(randomMovie);

			//act
			Movie actual = controller.getOne(4l);

			//assert
			assertThat(actual).isSameAs(randomMovie);
			verify(movieRepo).findOne(4l);		
		}

		@Test
		public void make_sure_that_getOne_exception_can_be_handled() {
			//arrange


			//act
			try {
				controller.getOne(78l);

				//This line of code should not run
				fail("the controller did not throw the StuffNotFoundException");
			}catch (NoIdFoundException nife) {}

			//assert

		}


		@Test
		public void test_that_delete_removes_an_movie_from_the_repo() {
			//arrange
			Movie randomMovie = new Movie();
			when(movieRepo.findOne(5l)).thenReturn(randomMovie);

			//act
			Movie actual = controller.delete(5l);

			//assert
			assertThat(randomMovie).isSameAs(actual);
			verify(movieRepo).delete(5l);
			verify(movieRepo).findOne(5l);

		}


		@Test
		public void catch_the_exception_when_attempting_to_delete_an_actor_that_getFindOne_cant_find() {
			//arrange
			when(movieRepo.findOne(9l)).thenThrow(new EmptyResultDataAccessException(0));

			//act
			Movie actual = controller.delete(9l);


			//assert
			assertThat(actual).isNull();
			verify(movieRepo).findOne(9l);

		}


		@Test
		public void confirm_that_a_new_actor_can_be_created() {
			//arrange
			Movie randomMovie = new Movie();
			when(movieRepo.save(randomMovie)).thenReturn(randomMovie);

			//act
			Movie actual = controller.create(randomMovie);

			//assert
			assertThat(actual).isSameAs(randomMovie);


		}


		@Test
		public void make_sure_that_an_actor_can_be_udpated_on_the_repo() {
			//Arrange
			Movie randomMovie = new Movie();
			when(movieRepo.findOne(5l)).thenReturn(randomMovie);
			when(movieRepo.save(randomMovie)).thenReturn(randomMovie);

			//Act
			Movie actual = controller.update(randomMovie, 5l);

			//Assert
			verify(movieRepo).save(randomMovie);
			assertThat(actual).isSameAs(randomMovie);
			assertThat(actual.getId()).isSameAs(randomMovie.getId());

		}

		@Test
		public void confirm_a_movie_can_be_associated_to_an_actor() {
			//arrange
			Actor randomActor = new Actor();
			when(actorRepo.findOne(4l)).thenReturn(randomActor);
			Movie randomMovie = new Movie();
			randomMovie.setId(3l);
			when(movieRepo.findOne(3l)).thenReturn(randomMovie);

			//act
			Movie actual = controller.associateAnActor(3l, randomActor);		

			//Assert
			verify(movieRepo).save(randomMovie);
			assertThat(actual).isSameAs(randomMovie);
			assertThat(actual.getId()).isSameAs(randomMovie.getId());
		}

}
