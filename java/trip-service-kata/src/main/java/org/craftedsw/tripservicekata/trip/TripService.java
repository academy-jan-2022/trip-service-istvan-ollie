package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = getLoggedUser();
		if (loggedUser != null) {
			var isFriend = isFriendOfUser(loggedUser, user);
			if (isFriend) {
				tripList = getTripsFor(user);
			}
			return tripList;
		}

		throw new UserNotLoggedInException();
	}

	private boolean isFriendOfUser(User loggedUser, User user) {
		for (User friend : user.getFriends()) {
			var isAFriend = friend.equals(loggedUser);
			return isAFriend;
		}
		return false;
	}

	protected List<Trip> getTripsFor(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
