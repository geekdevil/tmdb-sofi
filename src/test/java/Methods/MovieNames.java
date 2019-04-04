package Methods;

/**
 * Created by spyderweiss on 7/17/17.
 */
public enum MovieNames {

	FIGHT_CLUB, THE_GODFATHER_II, THE_GODFATHER;

	/**
	 * Method to return the movie name string from the enum
	 *
	 * @return Movie name string
	 */
	public String toMovieName() {
		String value;
		switch (this) {
		case FIGHT_CLUB:
			value = "Fight Club";
			break;
		case THE_GODFATHER:
			value = "The Godfather";
			break;
		case THE_GODFATHER_II:
			value = "The Godfather: Part II";
			break;
		default:
			throw new RuntimeException("Movie title not provided.");
		}
		return value;
	}

}
