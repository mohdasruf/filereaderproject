package stepdefinition;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import filereader.pojo.FileDetails;
import filereader.pojo.VehicleDetails;
import filereader.service.FileReaderService;
import filereader.service.FileReaderServiceImpl;
import framework.pages.ErrorPage;
import framework.pages.RegEnterPage;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class FileReaderSteps {

    private FileReaderService fileReaderService;
    private List<FileDetails> fileDetailsList;
    private String[] vehicleInfo;
    private String fileName;
    private VehicleDetails vehicleDetails;
    private String[] info;
    private RegEnterPage regEnterPage;
    private ErrorPage errorPage;

    private static String DIRECTORY = System.getProperty("user.dir") + "/testfiles";

    @Before
    public void init() {
        System.setProperty(
                "webdriver.gecko.driver",
                "geckodriver"
        );
    }

    @Given("^the service has (-?\\d+) files to read in directory (.*)$")
    public void testServiceReadsDirectory(int number, String path) throws Exception {
        fileReaderService = new FileReaderServiceImpl();
        fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
    }

    @Given("^the service reads files from directory testfiles$")
    public void testServiceReadsDirectoryTestfiles1() throws Exception {
        fileReaderService = new FileReaderServiceImpl();
        fileDetailsList = fileReaderService.getFileContents(DIRECTORY);
    }

    @When("^the service is evoked$")
    public void testServiceIsEvoked() throws Exception {
        regEnterPage = new RegEnterPage();
    }

    @And("^the registration number (.*) from (.*) is entered into the website$")
    public void testCorrectNumberEntered(String plate, String file) throws Exception {
        vehicleInfo = regEnterPage.checkDetails(plate);
    }

    @And("^registration numbers from each file with (.*) is entered into the website inturn$")
    public void testRegistrationNumbersFromEachFile(String name) throws Exception {
        this.fileName = name;
    }

    @Then("^the service will produce a list of size (-?\\d+)$")
    public void testServiceProduceListSize(int result) throws Exception {
        assertEquals(fileDetailsList.size(), result);
    }

    @Then("^the website details will match the service with make of BMW with a colour of BLUE$")
    public void testWebsiteDetails() throws Exception {
        assertEquals(vehicleInfo[1], "BMW");
        assertEquals(vehicleInfo[2], "BLUE");
    }

    @Then("^the website details for registration (.*) with make of (.*) with a colour of (.*) will (.*) with the value in csv$")
    public void testWebsiteMatchOrNot(String regno,
                                      String make, String colour, String matchOrNot) throws Exception {
        switch (matchOrNot) {
            case "match":
                vehicleInfo = regEnterPage.checkDetails(regno);
                assertEquals(make, vehicleInfo[1]);
                assertEquals(colour, vehicleInfo[2]);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                regEnterPage.takeScreenshot();
                assertEquals(make, vehicleDetails.getMake());
                assertEquals(colour, vehicleDetails.getColour());
                break;
            case "not match":
                errorPage = regEnterPage.incorrectRegNo(regno);
                assertEquals(errorPage.getErrorContactInfoString(), errorPage.ErrorPage_CONTACT_INFO);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                regEnterPage.takeScreenshot();
                assertEquals(make, vehicleDetails.getMake());
                assertEquals(colour, vehicleDetails.getColour());
                break;
            case "not be found":
                vehicleInfo = regEnterPage.checkDetails(regno);
                assertEquals(make, vehicleInfo[1]);
                assertEquals(colour, vehicleInfo[2]);
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                regEnterPage.takeScreenshot();
                assertEquals(make, vehicleDetails.getMake());
                assertEquals(colour, vehicleDetails.getColour());
                break;
            case "error":
                regEnterPage.enterInvalidFormatRegNo(regno);
                assertEquals(regEnterPage.RegEnterPage_ERROR_HEADING, regEnterPage.getErrorStringHeading());
                assertEquals(regEnterPage.RegEnterPage_ERROR_STRING, regEnterPage.getErrorString());
                vehicleDetails = fileReaderService.getVehicleDetailsFromFile(fileName);
                regEnterPage.takeScreenshot();
                assertEquals(make, vehicleDetails.getMake());
                assertEquals(colour, vehicleDetails.getColour());
                break;
        }
    }

    @Given("^I am a user of the website$")
    public void testUserWebsite() throws Exception {
        regEnterPage = new RegEnterPage();
    }

    @When("^I enter a correct registration number of YS59ABZ")
    public void testCorrectRegistration() throws Exception {
        info = regEnterPage.checkDetails("YS59ABZ");
    }

    @When("^I enter a incorrectly formatted registration number of WE343467$")
    public void testIncorrectlyFormatRegistration() throws Exception {
        regEnterPage.enterInvalidFormatRegNo("WE343467");
    }

    @When("^I enter a correctly formatted invalid registration number of V765DPR$")
    public void testCorrectFormatedInvalidRegistration() throws Exception {
        errorPage = regEnterPage.incorrectRegNo("V765DPR");
    }

    @Then("^I will receive the correct make as BMW and colour of car as BLUE$")
    public void testCorrect() throws Exception {
        assertEquals(3, info.length);
        assertEquals(info[1], "BMW");
        assertEquals(info[2], "BLUE");
    }

    @Then("^I will receive an error messsage on the same page$")
    public void testErrorMessageSamePage() throws Exception {
        assertEquals(regEnterPage.getErrorString(), regEnterPage.RegEnterPage_ERROR_STRING);
    }

    @Then("^I will be taken to an error page$")
    public void testErrorPage() throws Exception {
        assertEquals(errorPage.getErrorContactInfoString(), errorPage.ErrorPage_CONTACT_INFO);
    }

    @After
    public void tearDown() {
        regEnterPage.quit();
    }
}