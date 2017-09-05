package com.libertymutual.goforcode.wimp.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.wimp.models.Actor;
import com.libertymutual.goforcode.wimp.models.Award;
import com.libertymutual.goforcode.wimp.repositories.ActorRepository;
import com.libertymutual.goforcode.wimp.repositories.AwardRepository;

public class ActorApiControllerTests {

	
	private ActorRepository actorRepo;
	private AwardRepository awardRepo;
	private ActorApiController controller;
	
	@Before
	public void setUp() {
		actorRepo = mock(ActorRepository.class);
		awardRepo = mock(AwardRepository.class);
		controller = new ActorApiController(actorRepo, awardRepo);
	}
	

	@Test
	public void testing_to_make_sure_getAll_returns_all_actors() {
		//arrange
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new Actor());
		actors.add(new Actor());
		
		when(actorRepo.findAll()).thenReturn(actors);
		
		//act
		List<Actor> actual = controller.getAll();
		
		//assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(actors.get(0));
		verify(actorRepo).findAll();
	}

	@Test
	public void make_sure_that_getOne_returns_one_actor() throws NoIdFoundException {
		//arrange
		Actor randomActor = new Actor();
		when(actorRepo.findOne(4l)).thenReturn(randomActor);
		
		//act
		Actor actual = controller.getOne(4l);
		
		//assert
		assertThat(actual).isSameAs(randomActor);
		verify(actorRepo).findOne(4l);		
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
	public void test_that_delete_removes_an_actor_from_the_repo() {
		//arrange
		Actor randomActor = new Actor();
		when(actorRepo.findOne(5l)).thenReturn(randomActor);
		
		//act
		Actor actual = controller.delete(5l);
		
		//assert
		assertThat(randomActor).isSameAs(actual);
		verify(actorRepo).delete(5l);
		verify(actorRepo).findOne(5l);
		
	}
	
	
	@Test
	public void catch_the_exception_when_attempting_to_delete_an_actor_that_getFindOne_cant_find() {
		//arrange
		when(actorRepo.findOne(9l)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Actor actual = controller.delete(9l);
		
		
		//assert
		assertThat(actual).isNull();
		verify(actorRepo).findOne(9l);
		
	}
	
	
	@Test
	public void confirm_that_a_new_actor_can_be_created() {
		//arrange
		Actor randomActor = new Actor();
		when(actorRepo.save(randomActor)).thenReturn(randomActor);

		//act
		Actor actual = controller.create(randomActor);
		
		//assert
		assertThat(actual).isSameAs(randomActor);
		
		
	}
	
	
	@Test
	public void make_sure_that_an_actor_can_be_udpated_on_the_repo() {
		//Arrange
		Actor randomActor = new Actor();
		when(actorRepo.findOne(5l)).thenReturn(randomActor);
		when(actorRepo.save(randomActor)).thenReturn(randomActor);
		
		//Act
		Actor actual = controller.update(randomActor, 5l);
		
		//Assert
		verify(actorRepo).save(randomActor);
		assertThat(actual).isSameAs(randomActor);
		assertThat(actual.getId()).isSameAs(randomActor.getId());
		
	}
	
	@Test
	public void confirm_an_award_can_be_associated_to_an_actor() {
		//arrange
		Actor randomActor = new Actor();
		when(actorRepo.findOne(4l)).thenReturn(randomActor);
		Award randomAward = new Award();
		randomAward.setId(3l);
		when(awardRepo.findOne(3l)).thenReturn(randomAward);
		
		//act
		Actor actual = controller.associateAnAward(4l, randomAward);		
		
		//assert
		verify(awardRepo).save(randomAward);
		assertThat(actual).isSameAs(randomActor);
		
		
	}


}
