package todo.logic.bean;

import todo.logic.util.TodoLogicLogger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterBean {

	public void tweet(String message) {

		try {
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.updateStatus(message);

		} catch (Exception e) {

			TodoLogicLogger.severe(TwitterBean.class, "tweet", e, message);

			// Fail silently, it's not a big deal if we miss one update to
			// twitter
		}
	}

}
