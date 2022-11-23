package com.pom.pagHelpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pom.identifiers.HomePageobjects;
import com.pom.identifiers.SignupPageObjects;
import com.pom.utilities.SeleniumMethods;

public class HomePageHelper extends SeleniumMethods {

	public HomePageHelper(WebDriver driver) {
		super(driver);
	}

	public String clickSignUpButton() throws InterruptedException {
		clickOn(HomePageobjects.signUpButton);
		return getText(SignupPageObjects.signupPageHeading);

	}

}
