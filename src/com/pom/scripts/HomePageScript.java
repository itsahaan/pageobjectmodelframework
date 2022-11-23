package com.pom.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pom.utilities.BaseClass;

public class HomePageScript extends BaseClass {

	@Test
	public void clickSignUpButton() throws InterruptedException
	{
		extentTest.info("User is able to click on sign up button");
		String actual = homePageHelper.clickSignUpButton();
		Assert.assertEquals(actual, "Create an Account");
	}
}
