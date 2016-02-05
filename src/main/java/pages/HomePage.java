package pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@DefaultUrl("http://streamtv.net.ua/base")
public class HomePage extends PageObject {

    private Logger LOG = LoggerFactory.getLogger(HomePage.class);

    private static final String SEARCH_ROW = "//td [contains(text(), '%s')]";

    private static final String SEARCH_REGION = "//td [contains(text(), '%s')]//following-sibling::td[1]";

    private static final String ELEMENT_TO_FIND = "//tbody/tr/td[%s]";

    private static final String CLOSE_USER_TAB = "//div[contains (text(), '%s')]/div/ico";

    @FindBy(xpath = "//img[contains(@src, 'data/photo')]")
    private WebElementFacade photo;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade searchButton;

    @FindBy(xpath = "//td [contains(text(), 'Marley Bob')]")
    private WebElementFacade searchRow;

    @FindBy(xpath = "//fg-input[@id='username']/div/input")
    private WebElementFacade login;

    @FindBy(xpath = "//fg-input[@type='password']/div/input")
    private WebElementFacade pass;

    @FindBy(xpath = "//li[@id='current_account']/a")
    private WebElementFacade signInLink;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElementFacade submitForm;

    @FindBy(xpath = "//button[@ng-click='newWrestler()']")
    private WebElementFacade newButton;

    @FindBy(xpath = "//button[@ng-click='delete()']")
    private WebElementFacade deleteButton;

    @FindBy(xpath = "//button[@ng-click='ok()']")
    private WebElementFacade confirmButton;

    @FindBy(xpath = "//input[@placeholder='Last name']")
    private WebElementFacade lastNameField;

    @FindBy(xpath = "//input[@placeholder='Date of Birth']")
    private WebElementFacade dayOfBirthField;

    @FindBy(xpath = "//fg-select[@options='regions']/div/select")
    private WebElementFacade regionField;

    @FindBy(xpath = "//fg-select[@options='fsts']/div/select")
    private WebElementFacade fstField;

    @FindBy(xpath = "//input[@placeholder='Trainer']")
    private WebElementFacade trainerField;

    @FindBy(xpath = "//input[@placeholder='First name']")
    private WebElementFacade firstName;

    @FindBy(xpath = "//input[@placeholder='Middle name']")
    private WebElementFacade middleName;

    @FindBy(xpath = "//fg-select[@options='styles']/div/select")
    private WebElementFacade style;

    @FindBy(xpath = "//fg-select[@options='years']/div/select")
    private WebElementFacade year;

    @FindBy(xpath = "//fg-select[@options='lictypes']/div/select")
    private WebElementFacade age;

    @FindBy(xpath = "//button[contains(@ng-click,'save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//input[@ng-model='searchFor']")
    private WebElementFacade searchField;

    @FindBy(xpath = "//a[@ng-click='select()']")
    private WebElementFacade wrestlerTab;

    @FindBy(xpath = "//select[contains(@ng-model,'region')]")
    private WebElementFacade regionFilter;

    @FindBy(xpath = "//select[ contains(@ng-model,'year')]")
    private WebElementFacade yearFilter;

    @FindBy(xpath = "//input[@uploader='photoUploader']")
    private WebElementFacade browseToFileButton;


    @FindBy(xpath = "//input[@uploader='attachUploader']")
    private WebElementFacade attachmentButton;


    @FindBy(xpath = "//a[contains(@href,'data/attach')]")
    private WebElementFacade attachment;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnSearchButton() {
        searchButton.click();
        waitABit(3000);
    }

    public boolean doesWrestlerExist(String lastName) {
        boolean isPresent = true;
        String locator = String.format(SEARCH_ROW, lastName);
        if (findAll(locator).isEmpty()) {
            isPresent = false;
        }
        return isPresent;
    }


    public String isRegionUpdated(String wrestlerName) {
        String locator = String.format(SEARCH_REGION, wrestlerName);
        String regionValue = findBy(locator).getText();
        return regionValue;
    }

    public void clickToUpdate(String lastName) {
        String locator = String.format(SEARCH_ROW, lastName);
        findBy(locator).click();
        waitABit(1000);
    }


    public void selectRegionFilter(String valueToInput) {
        regionFilter.selectByVisibleText(valueToInput);

    }

    public void selectYearFilter(String valueToInput) {
        yearFilter.selectByVisibleText(valueToInput);

    }

    public void enterPass(String searchPass) {
        pass.type(searchPass);

    }

    public void enterLogin(String searchLogin) {
        login.type(searchLogin);
    }

    public void clickSignIn() {
        waitABit(2000);
        submitForm.click();
    }

    public void clickAddButton() {
        newButton.click();
    }

    public void clickDelete() {
        deleteButton.click();
    }

    public void confirmDelete() {
        confirmButton.click();
    }

    public void inputLastName(String valueToInput) {
        lastNameField.type(valueToInput);

    }

    public void inputDateOfBirth(String valueToInput) {
        dayOfBirthField.type(valueToInput);

    }

    public void selectRegion(String valueToSelect) {
        regionField.selectByVisibleText(valueToSelect);
    }

    public void selectFST(String valueToSelect) {
        fstField.selectByVisibleText(valueToSelect);
    }

    public void selectTrainer() {
        trainerField.type(RandomStringUtils.randomAlphabetic(5));
    }


    public void selectStyle(String valueToSelect) {
        style.selectByVisibleText(valueToSelect);
    }

    public void selectYear(String valueToSelect) {
        year.selectByVisibleText(valueToSelect);
    }

    public void selectAge(String valueToSelect) {
        age.selectByVisibleText(valueToSelect);
    }

    public void inputFirstName(String valueToSelect) {
        firstName.type(valueToSelect);
    }

    public void inputMiddleName(String valueToSelect) {
        middleName.type(valueToSelect);
    }

    public void clickSaveButton() {
        saveButton.click();
        waitABit(2000);
    }

    public void uploadFile(String pathFromImage) {
        upload(pathFromImage).to(browseToFileButton);
    }


    public void uploadAttachment(String filePath) {
        attachmentButton.sendKeys(filePath);
    }


    public List<String> getListOfValues(String row) {
        List<String> resultObtained = new ArrayList<String>();
        String locator = String.format(ELEMENT_TO_FIND, row);
        List<WebElementFacade> resultList = findAll(locator);
        for (WebElementFacade elementsText : resultList) {
            resultObtained.add(elementsText.getText());
        }
        return resultObtained;

    }

    public void closeUserTab(String user) {
        String locator = String.format(CLOSE_USER_TAB, user);
        findBy(locator).click();
    }

    public void inputSearchCriteria(String valueToFind) {
        searchField.type(valueToFind);
    }

    public void switchToWrestlerTab() {
        wrestlerTab.click();
    }

    public String downloadPhoto() throws IOException {
        String url = photo.getAttribute("src");
        BufferedImage bufImage = ImageIO.read(new URL(url));
        File file = new File("photoActual.png");
        ImageIO.write(bufImage, "png", new File("photoActual.png"));
        String path = file.getAbsolutePath();
        return path;

    }

    public String downloadAttachment() throws IOException {
        URL url = new URL(attachment.getAttribute("href"));
        File file = File.createTempFile("attachFromUI", ".pdf");
        FileUtils.copyURLToFile(url, file);
        return file.getAbsolutePath();
    }

}
