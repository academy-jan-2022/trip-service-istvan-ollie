package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = getLoggedUser();

		if (loggedUser == null)
			throw new UserNotLoggedInException();

		return isFriendOfUser(loggedUser, user)
				? getTripsFor(user)
				: new ArrayList<>();
	}

	private boolean isFriendOfUser(User loggedUser, User user) {
		boolean isFriend = false;
		for (User friend : user.getFriends()) {
			isFriend = friend.equals(loggedUser);
			break;
		}
		return isFriend;
	}

	protected List<Trip> getTripsFor(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		var userSessionInstance = UserSession.getInstance();
		return userSessionInstance.getLoggedUser();
	}

}
