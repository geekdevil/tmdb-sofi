package Methods;

/**
 * Stores the endpoints that need to be tested.
 */
public class SharedResources {
	// TODO: Always remove API key before committing file.
	private static final String apiKey = System.setProperty("api_key", "api_value");
	public static final String urlAuthenticate = String.format("3/authentication/token/new?api_key=%s", apiKey);
	public static final String urlSearch = String.format("3/search/movie?api_key=%s&query=", apiKey);
}
