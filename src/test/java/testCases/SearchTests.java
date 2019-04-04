package testCases;

import io.restassured.http.ContentType;
import methods.*;

import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * This class provides the ability to search for a movie by name, release date, and it's adult status, and will let you
 * know if the parameters you provided match those retrieved via the API.
 */
public class SearchTests {

    private void search(String title, String releaseDate, boolean adult, int expectedResultNumber)
    {
        Validation validation = null;
        try
        {
            validation = SetupAndTeardown.beforeTest(SharedResources.urlSearch, title);
            validation.checkStatusCode(200);
            validation.checkContentType(ContentType.JSON);
            validation.validateMovieTitle(expectedResultNumber, title);
            if (releaseDate != "" || !releaseDate.isEmpty())
            	validation.validateMovieReleaseDate(expectedResultNumber, releaseDate);
            validation.validateMovieAdultStatus(expectedResultNumber, adult);
            
        }
        catch (Exception ex)
        {
            if(validation != null)
            {
                Logger.log("JSON Response: \n%s\n", validation.retrieveJSONDataAsString());
            }
            Logger.log(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            throw ex;
        }
        finally
        {
            Logger.attachTestNGlog(Reporter.getOutput(Reporter.getCurrentTestResult()));
        }
    }

    @Test(groups = {"TMDB"}, description = "Searches for the movie Fight Club")
    public void searchForFightClub() throws Exception
    {
        search(MovieNames.FIGHT_CLUB.toMovieName(), "1999-10-15", false, 0);
    }

    @Test(groups = {"TMDB"}, description = "Searches for the movie The Godfather: Part II")
    public void searchForTheGodFatherII()
    {
        search(MovieNames.THE_GODFATHER_II.toMovieName(), "", false, 0);
    }

    //I've included these 3 error tests so that examples of failed tests can be shown in the Allure report.
    @Test(groups = {"INTENTIONAL_ERROR"}, description = "Searches for a movie with a non-matching name")
    public void searchForGodfatherInGodFatherII()
    {
        search(MovieNames.THE_GODFATHER.toMovieName(), "", false, 2);
    }

    @Test(groups = {"INTENTIONAL_ERROR"}, description = "Searches for a movie with a non-matching date")
    public void searchForGodfatherIIWithWrongDate()
    {
        search(MovieNames.THE_GODFATHER_II.toMovieName(), "2004-12-20", false, 0);
    }

    @Test(groups = {"INTENTIONAL_ERROR"}, description = "Searches for a movie with a non-matching adult status")
    public void searchForGodfatherIIWithWrongAdultStatus()
    {
        search(MovieNames.THE_GODFATHER_II.toMovieName(), "", true, 0);
    }
}
