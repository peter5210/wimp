package com.libertymutual.goforcode.wimp.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.wimp.models.Award;
import com.libertymutual.goforcode.wimp.repositories.AwardRepository;

public class AwardApiControllerTests {

	private AwardRepository awardRepo;
	private AwardApiController controller;
	
	@Before
	public void setUp() {
		awardRepo = mock(AwardRepository.class);
		controller = new AwardApiController(awardRepo);
	}
	

	@Test
	public void testing_to_make_sure_getAll_returns_all_actors() {
		//arrange
		ArrayList<Award> awards = new ArrayList<Award>();
		awards.add(new Award());
		awards.add(new Award());
		
		when(awardRepo.findAll()).thenReturn(awards);
		
		//act
		List<Award> actual = controller.getAll();
		
		//assert
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(0)).isSameAs(awards.get(0));
		verify(awardRepo).findAll();
	}

	@Test
	public void make_sure_that_getOne_returns_one_actor() throws NoIdFoundException {
		//arrange
		Award randomAward = new Award();
		when(awardRepo.findOne(4l)).thenReturn(randomAward);
		
		//act
		Award actual = controller.getOne(4l);
		
		//assert
		assertThat(actual).isSameAs(randomAward);
		verify(awardRepo).findOne(4l);		
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
		Award randomAward = new Award();
		when(awardRepo.findOne(5l)).thenReturn(randomAward);
		
		//act
		Award actual = controller.delete(5l);
		
		//assert
		assertThat(randomAward).isSameAs(actual);
		verify(awardRepo).delete(5l);
		verify(awardRepo).findOne(5l);
		
	}
	
	
	@Test
	public void catch_the_exception_when_attempting_to_delete_an_actor_that_getFindOne_cant_find() {
		//arrange
		when(awardRepo.findOne(9l)).thenThrow(new EmptyResultDataAccessException(0));
		
		//act
		Award actual = controller.delete(9l);
		
		
		//assert
		assertThat(actual).isNull();
		verify(awardRepo).findOne(9l);
		
	}
	
	
	@Test
	public void confirm_that_a_new_actor_can_be_created() {
		//arrange
		Award randomAward = new Award();
		when(awardRepo.save(randomAward)).thenReturn(randomAward);

		//act
		Award actual = controller.create(randomAward);
		
		//assert
		assertThat(actual).isSameAs(randomAward);
		
		
	}
	
	
	@Test
	public void make_sure_that_an_actor_can_be_udpated_on_the_repo() {
		//Arrange
		Award randomAward = new Award();
		when(awardRepo.findOne(5l)).thenReturn(randomAward);
		when(awardRepo.save(randomAward)).thenReturn(randomAward);
		
		//Act
		Award actual = controller.update(randomAward, 5l);
		
		//Assert
		verify(awardRepo).save(randomAward);
		assertThat(actual).isSameAs(randomAward);
		assertThat(actual.getId()).isSameAs(randomAward.getId());
		
	}


}
