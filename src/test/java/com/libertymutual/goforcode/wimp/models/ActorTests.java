package com.libertymutual.goforcode.wimp.models;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class ActorTests {

	@Test
	public void checking_to_make_sure_my_getters_and_setters_work() {
		BeanTester tester = new BeanTester();
		Configuration configuration = new ConfigurationBuilder()
//				.ignoreProperty("birthDate")
				.build();
		tester.testBean(Actor.class, configuration);
	}
	
	
	@Test
	public void if_this_is_the_first_award_then_create_a_new_list_and_add_the_award() {
		//arrange
		Award randomAward = new Award();
		Actor randomActor = new Actor();

		//act
		randomActor.addAward(randomAward);

		//assert
		assertThat(randomActor.getAwards()).contains(randomAward);
	}

	@Test
	public void if_there_is_already_an_array_list_confirm_that_a_new_award_is_added() {
		//arrange
		Actor randomActor = new Actor();
		ArrayList<Award> awards = new ArrayList<Award>();
		awards.add(new Award());
		awards.add(new Award());
		awards.add(new Award());		
		Award randomAward = new Award();
		randomActor.setAwards(awards);
		

		//act
		randomActor.addAward(randomAward);

		//assert
		assertThat(awards.size()).isEqualTo(4);
		assertThat(awards).contains(randomAward);
	}
}
