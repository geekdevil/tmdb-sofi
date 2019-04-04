package Methods;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Used to validate all returned JSON data, could be broken up into more
 * specific types of validation as the suite grows.
 */
public class Validation {

	private ValidatableResponse response;

	/**
	 * Constructor to build the Validation class
	 * 
	 * @param validatableResponse - Response used to retrieve the JSON data
	 */
	public Validation(ValidatableResponse validatableResponse) {
		response = validatableResponse;
	}

	/**
	 * Validates the request token passed in is not null.
	 */
	public void validateRequestToken() {
		findValueIn(ValueType.BODY, "request_token", not(""));
	}

	/**
	 * Validates the status code matches the intended code
	 * 
	 * @param statusCode - Any standard HTTP status code
	 */
	public void checkStatusCode(int statusCode) {
		response.assertThat().statusCode(statusCode);
	}

	/**
	 * Validates the ContentType matches the intended ContentType
	 * 
	 * @param contentType - Enumeration of common content-types.
	 */
	public void checkContentType(ContentType contentType) {
		response.assertThat().contentType(contentType);
	}

	/**
	 * Validates the Movie title matches what is returned in the JSON.
	 * 
	 * @param expectedResultNumber - The position in the returned JSON array the
	 *                             entry should be in.
	 * @param title                - The movie title
	 */
	public void validateMovieTitle(int expectedResultNumber, String title) {
		findValueIn(ValueType.BODY, String.format("results[%d].title", expectedResultNumber), equalTo(title));
	}

	/**
	 * Validates the Movie release date matches what is returned in the JSON.
	 * 
	 * @param expectedResultNumber - The position in the returned JSON array the
	 *                             entry should be in.
	 * @param releaseDate          - The movie release date
	 */
	public void validateMovieReleaseDate(int expectedResultNumber, String releaseDate) {
		findValueIn(ValueType.BODY, String.format("results[%d].release_date", expectedResultNumber),
				equalTo(releaseDate));
	}

	/**
	 * Validates the Movie adult status matches what is returned in the JSON.
	 * 
	 * @param expectedResultNumber - The position in the returned JSON array the
	 *                             entry should be in.
	 * @param adult                - Whether the movie is marked as adult or not
	 */
	public void validateMovieAdultStatus(int expectedResultNumber, boolean adult) {
		findValueIn(ValueType.BODY, String.format("results[%d].adult", expectedResultNumber), equalTo(adult));
	}

	/**
	 * Used to assert the various passed in values from JSON data
	 * 
	 * @param valueType                 - Enumeration of common html types
	 * @param path                      - The body path
	 * @param matcher                   - The hamcrest matcher that must response
	 *                                  body must match.
	 * @param additionalKeyMatcherPairs - Optionally additional hamcrest matchers
	 *                                  that must return <code>true</code>.
	 */
	private void findValueIn(ValueType valueType, String path, Matcher<?> matcher,
			Object... additionalKeyMatcherPairs) {
		switch (valueType) {
		case BODY:
			response.assertThat().body(path, matcher, (additionalKeyMatcherPairs));
			break;
		case CONTENT:
			response.assertThat().content(path, matcher, (additionalKeyMatcherPairs));
			break;
		case HEADER:
			response.assertThat().header(path, matcher);
			break;
		}

	}

	/**
	 * @return String - JSON response in string format for logging purposes
	 */
	public String retrieveJSONDataAsString() {
		return response.extract().response().asString();
	}

}
