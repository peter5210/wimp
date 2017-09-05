package com.libertymutual.goforcode.wimp.models;

import static org.junit.Assert.*;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class AwardTests {

	@Test
	public void checking_to_make_sure_my_getters_and_setters_work() {
		BeanTester tester = new BeanTester();
		Configuration configuration = new ConfigurationBuilder()
//				.ignoreProperty("actor")
				.build();
		tester.testBean(Award.class, configuration);
	}
}
