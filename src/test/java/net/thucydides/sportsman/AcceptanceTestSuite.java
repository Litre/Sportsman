package net.thucydides.sportsman;

import java.io.File;
import java.lang.Override;import java.lang.String;import java.lang.System;import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.annotations.AfterStories;import org.jbehave.core.annotations.BeforeStories;import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.openqa.selenium.WebDriver;

import net.thucydides.jbehave.ThucydidesJUnitStories;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {
	
	WebDriver driver;

	@BeforeStories
	public void start(){
		File filechr = new File("lib\\chromedriver.exe");
	    System.setProperty("webdriver.chrome.driver", filechr.getAbsolutePath());
	}
	
	@Override
	public List<String> storyPaths() {
		List<String> storyPath = new ArrayList<String>();
		String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
		String storyName = System.getProperty("story");
		if (storyName != null) {
			String[] stories = storyName.split(";");
			storyPath = new StoryFinder().findPaths(codeLocation, Arrays.asList(stories), Arrays.asList(""));
		} else {
			storyPath = super.storyPaths();
		}
		return storyPath;
	}
	
	@AfterStories
	public void finish(){
		if(driver != null){
			driver.quit();
		}
	}
}
