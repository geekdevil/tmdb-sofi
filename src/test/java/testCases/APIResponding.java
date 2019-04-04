package testCases;

import io.restassured.http.ContentType;
import methods.Logger;
import methods.SetupAndTeardown;
import methods.SharedResources;
import methods.Validation;

import org.testng.Reporter;
import org.testng.annotations.Test;



/**
 * Simple test to confirm The Movie DB API is returning a request_token for your provided API key.
 */
public class APIResponding {

    @Test(groups = {"TMDB"}, description = "Simple test to confirm The Movie DB API is returning a request_token for your provided API key.")
    public void apiResponding() throws Exception
    {
        try
        {
        	Validation validation = SetupAndTeardown.beforeTest(SharedResources.urlAuthenticate);
            validation.checkStatusCode(200);
            validation.checkContentType(ContentType.JSON);
            validation.validateRequestToken();
        }
        catch (Exception ex)
        {
        	Logger.log(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            throw ex;
        }
        finally
        {
            Logger.attachTestNGlog(Reporter.getOutput(Reporter.getCurrentTestResult()));
        }
    }
}
