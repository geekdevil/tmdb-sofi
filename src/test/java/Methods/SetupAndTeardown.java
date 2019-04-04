package Methods;

import io.restassured.response.ValidatableResponse;
import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;

/**
 *
 * This class functions as the beginning and end point for all tests
 */
public class SetupAndTeardown {

	private static final String urlBase = "https://api.themoviedb.org/";

	/**
	 * This method runs anything needed before the suite of tests is run
	 */
	@BeforeSuite
	private void beforeSuite() {
		Logger.log("Start test suite");
	}

	/**
	 * This method runs anything needed before each individual test is run
	 * Configures a logger (BasicConfigurator) Creates a ValidatableResponse
	 * interface that is passed into the Validation class
	 * 
	 * @param url   - The endpoint that will be appended on to the base url
	 * @param title - The title of the movie
	 * @return Validation class used for validating test parameters
	 */
	@BeforeTest
	public static Validation beforeTest(String url, String title) {
		Logger.log("Starting test");
		BasicConfigurator.configure();
		ValidatableResponse response;
		// If title isn't null, we need to append the title on to the end of the url and
		// replace the spaces with
		// plus signs to match the needed format from the API
		if (title != null) {
			String titleReformatted = title.replace(" ", "+").replace(":", "+");
			response = given().when().get(urlBase + url + titleReformatted).then();
		} else {
			response = given().when().get(urlBase + url).then();
		}
		return new Validation(response);
	}

	/**
	 * Creates a ValidatableResponse interface that is passed into the Validation
	 * class
	 * 
	 * @param url - The endpoint that will be appended on to the base url
	 * @return Validation class used for validating test parameters
	 */
	public static Validation beforeTest(String url) {
		return beforeTest(url, null);
	}

	/**
	 * This method runs anything needed after each individual test is run
	 */
	@AfterTest
	private void afterTest() {
		Logger.log("Ending test");
	}

	/**
	 * This method runs anything needed after the suite of tests is run
	 */
	@AfterSuite
	private void afterSuite() {
		Logger.log("Ending test suite");
	}

}
