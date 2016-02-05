package net.thucydides.sportsman;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Parameters;
import steps.SportsmanTestSteps;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tretykhlib on 02.02.2016.
 */
public class Test {


    @Steps
    SportsmanTestSteps searchSteps;

    @Given("user opens main page")
    @Aliases(values = {"user logs in"})
    public void userOpensMainPage() {
        searchSteps.userOpensStemtv();
    }

    @When("user inputs $mail and $password on main page")
    public void checkSignedUser(@Named("mail") String login,
                                @Named("password") String searchPassword) {
        searchSteps.enterLogin(login);
        searchSteps.enterPassword(searchPassword);
        searchSteps.submitForm();
    }

    @When("searches sportsman in $region region and for $year year")
    public void searchWithFilters(@Named("region") String region,
                                  @Named("year") String year) {
        searchSteps.selectRegionFilter(region);
        searchSteps.selectYearFilter(year);

    }

    @When("add new sportsman with credentials:$tableWithParameters")
    public void annNewSportsMan(ExamplesTable tableWithParameters) {
        for (int i = 0; i < tableWithParameters.getRowCount(); i++) {
            Parameters rowInstory = tableWithParameters.getRowAsParameters(i);
            String lastName = rowInstory.valueAs("LastName", String.class);
            String birthDate = rowInstory.valueAs("BirthDate", String.class);
            String region = rowInstory.valueAs("Region", String.class);
            String fst = rowInstory.valueAs("FST", String.class);
            String style = rowInstory.valueAs("Style", String.class);
            String year = rowInstory.valueAs("Year", String.class);
            String firstName = rowInstory.valueAs("FirstName", String.class);
            String middleName = rowInstory.valueAs("MiddleName", String.class);
            String age = rowInstory.valueAs("Age", String.class);
            searchSteps.clickAddButton();
            searchSteps.addNewComer(lastName, birthDate, region, fst, style,
                    year, age, firstName, middleName);
            TestUtil.putInSession("user", lastName);

        }
    }

    @When("update $sportsman region to $region")
    public void updateWrestler(@Named("wrestler") String wrestlerName,
                               @Named("region") String region) {
        searchSteps.clickToUpdate(wrestlerName);
        searchSteps.selectRegion(region);
        searchSteps.clickSaveButton();
        searchSteps.closeUserTab(wrestlerName);
    }


    @When("save changes")
    public void saveChanges() {
        searchSteps.clickSaveButton();
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.closeUserTab(user);

    }

    @When("upload photo")
    public void uploadPhoto() {
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.clickToUpdate(user);
        String imagePath = System.getProperty("user.dir") + "\\lib\\test.jpg";
        searchSteps.uploadFile(imagePath);
        searchSteps.closeUserTab(user);

    }

    @When("delete sportsman")
    public void delete() {
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.clickToUpdate(user);
        searchSteps.clickDelete();

    }

    @When("upload attachment")
    public void uploadAttachment() {
        String user=(String)TestUtil.getFromSession("user");
        String filePath=System.getProperty("user.dir") + "\\lib\\attach.pdf";
        searchSteps.clickToUpdate(user);
        searchSteps.uploadAttachment(filePath);
        searchSteps.closeUserTab(user);

    }

    @Then("check proper displaying:$tableWithParameters")
    public void checkFilterDisplayed(ExamplesTable tableWithParameters) {
        for (int i = 0; i < tableWithParameters.getRowCount(); i++) {
            Parameters rowInstory = tableWithParameters.getRowAsParameters(i);
            String row = rowInstory.valueAs("rowNumber", String.class);
            String valueToCheck = rowInstory.valueAs("valueToCheck",
                    String.class);
            List<String> resultFromUi = searchSteps.getListOfValues(row);
            assertThat("List of values is not as expected", resultFromUi,
                    everyItem(is(valueToCheck)));
            assertThat("List is empty", resultFromUi, is(not(empty())));
            assertThat("List is empty", resultFromUi, is(not(emptyIterable())));
        }

    }

    @Then("check that sportsman was added")
    public void checkAddedWresler() {
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.switchToWrestlerTab();
        searchSteps.inputSearchCriteria(user);
        searchSteps.clickOnSearchButton();
        boolean isAdded = searchSteps.checkWrestlerWasAdded(user);
        assertTrue("Wrestler was not added", isAdded);

    }

    @Then("check that $sportsman doesn't exist")
    public void checkWreslerDoesntExist(@Named("wrestler") String wrestlerName) {
        searchSteps.switchToWrestlerTab();
        searchSteps.clickOnSearchButton();
        boolean isAdded = searchSteps.checkWrestlerWasAdded(wrestlerName);
        assertFalse("Wrestler exists", isAdded);

    }

    @Then("check that $sportsman has region $region")
    public void checkRegion(@Named("wrestler") String wrestlerName,
                            @Named("region") String region) {
        searchSteps.switchToWrestlerTab();
        searchSteps.clickOnSearchButton();
        String actualRegion = searchSteps.checkRegion(wrestlerName);
        assertThat("Wrestler region is not as expected", region,
                equalTo(actualRegion));
    }


    @Then("check that right photo was uploaded")
    public void photoComarison()
            throws Throwable {
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.switchToWrestlerTab();
        searchSteps.inputSearchCriteria(user);
        searchSteps.clickOnSearchButton();
        searchSteps.clickToUpdate(user);
        String actualPhoto = searchSteps.downloadPhoto();
        String expectedPhoto= System.getProperty("user.dir") + "\\lib\\photoExpected.png";
        boolean comparedPhotos = TestUtil.compareImages(actualPhoto, expectedPhoto);
        assertTrue("Photos differ", comparedPhotos);
        searchSteps.clickDelete();
    }



    @Then("check that right attachment was uploaded")
    public void attachmentComparison() throws IOException {
        String user=(String)TestUtil.getFromSession("user");
        searchSteps.switchToWrestlerTab();
        searchSteps.inputSearchCriteria(user);
        searchSteps.clickOnSearchButton();
        searchSteps.clickToUpdate(user);
        String fileExpected=System.getProperty("user.dir") + "\\lib\\attach.pdf";
        String fileActual= searchSteps.downloadAttachment();
        boolean compareAttachments = TestUtil.comparePdf(fileActual, fileExpected);
        assertTrue("Attachments differ", compareAttachments);
        searchSteps.clickDelete();
    }

}
