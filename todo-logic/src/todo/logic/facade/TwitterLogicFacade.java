package todo.logic.facade;

import todo.logic.util.TodoLogicLogger;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterLogicFacade {

	public void tweet(String message) {

		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.updateStatus(message);

		} catch (Exception e) {

			TodoLogicLogger.severe(TwitterLogicFacade.class, "tweet", e,
					message);

			// Fail silently, it's not a big deal if we miss one update to
			// twitter
		}
	}

}
