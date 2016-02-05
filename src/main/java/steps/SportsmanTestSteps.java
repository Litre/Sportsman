package steps;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;
import pages.HomePage;

public class SportsmanTestSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory
			.getLogger(SportsmanTestSteps.class);

	HomePage homePage;

	public SportsmanTestSteps(Pages pages) {
		super(pages);
		homePage = getPages().get(HomePage.class);
	}

	@Step
	public void userOpensStemtv() {
		getDriver().manage().deleteAllCookies();
		homePage.open();
		homePage.waitForTextToAppear("Login");
		getDriver().manage().window().maximize();
		LOG.info("++++ Site was opened successfully ++++");
	}

	@Step
	public void selectRegionFilter(String valueToSelect) {
		homePage.selectRegionFilter(valueToSelect);

	}

	@Step
	public void selectYearFilter(String valueToSelect) {
		homePage.selectYearFilter(valueToSelect);

	}

	@Step
	public void enterPassword(String searchPass) {
		homePage.enterPass(searchPass);
	}

	@Step
	public void enterLogin(String searchLogin) {
		homePage.enterLogin(searchLogin);
	}

	@Step
	public void submitForm() {
		homePage.clickSignIn();
		waitABit(2000);
		//homePage.waitForTextToAppear("Wrestlers");
	}

	@Step
	public void clickAddButton() {
		homePage.clickAddButton();
	}

	@Step
	public void clickDelete() {
		homePage.clickDelete();
		homePage.confirmDelete();
	}

	@Step
	public void addNewComer(String lastName, String birthDate, String region,
			String fst, String style, String year, String age,
			String firstName, String middleName) {
		homePage.inputLastName(lastName);
		homePage.inputDateOfBirth(birthDate);
		homePage.selectRegion(region);
		homePage.selectFST(fst);
		homePage.selectStyle(style);
		homePage.selectYear(year);
		homePage.selectAge(age);
		homePage.inputFirstName(firstName);
		homePage.inputMiddleName(middleName);

	}

	@Step
	public void selectRegion(String region) {
		homePage.selectRegion(region);

	}

	@Step
	public void clickSaveButton() {
		homePage.clickSaveButton();
	}

	@Step
	public void closeUserTab(String user){
		homePage.closeUserTab(user);
	}

	@Step
	public void uploadFile(String imagePath) {
		homePage.uploadFile(imagePath);
		homePage.selectTrainer();
		homePage.clickSaveButton();
	}


	@Step
	public void uploadAttachment(String filePath){
		homePage.uploadAttachment(filePath);
		homePage.selectTrainer();
		homePage.clickSaveButton();
	}


	@Step
	public List<String> getListOfValues(String row) {
		return homePage.getListOfValues(row);
	}

	@Step
	public void inputSearchCriteria(String valueToInput) {
		homePage.inputSearchCriteria(valueToInput);
	}

	@Step
	public void switchToWrestlerTab() {
		homePage.switchToWrestlerTab();
	}

	@Step
	public void clickOnSearchButton() {
		homePage.clickOnSearchButton();
	}

	@Step
	public boolean checkWrestlerWasAdded(String wrestlerName) {
		return homePage.doesWrestlerExist(wrestlerName);
	}

	@Step
	public String checkRegion(String wrestlerName) {
		return homePage.isRegionUpdated(wrestlerName);
	}

	@Step
	public void clickToUpdate(String lastName) {
		homePage.clickToUpdate(lastName);
	}

	@Step
	public String downloadPhoto() throws IOException {
		return homePage.downloadPhoto();
	}

	@Step
	public void selectTrainer(){
		homePage.selectTrainer();
	}

	@Step
	public String  downloadAttachment() throws IOException {
	 return homePage.downloadAttachment();

	}
}
