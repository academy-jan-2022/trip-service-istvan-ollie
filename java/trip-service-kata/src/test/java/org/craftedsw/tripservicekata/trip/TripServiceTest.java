package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TripServiceTest {

	private static final User GUEST = null;
	private static final User LOGGED_IN_USER = new User();
	private User LOGGED_IN_USER_RESULT = null;
	private static final Trip TO_LONDON = new Trip();
	private TripServiceTestable tripService;

	@BeforeEach
	void setUp(){
		tripService = new TripServiceTestable();
		LOGGED_IN_USER_RESULT = null;
	}

	@Test
	public void
	should_throw_exception_when_no_user_logged_in(){
		LOGGED_IN_USER_RESULT = GUEST;
		assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(new User()));
	}


	@Test
	public void
	should_return_trips_of_users_friends(){
		LOGGED_IN_USER_RESULT = LOGGED_IN_USER;
		var userToGetTripsFor = new User();

		userToGetTripsFor.addFriend(LOGGED_IN_USER);
		userToGetTripsFor.addTrip(TO_LONDON);

		var userTrips = tripService.getTripsByUser(userToGetTripsFor);
		assertEquals(1, userTrips.size());
	}

	public class TripServiceTestable extends TripService {

		@Override
		protected User getLoggedUser() {
			return LOGGED_IN_USER_RESULT;
		}

		@Override
		protected List<Trip> getTripsFor(User user){
			return List.of(new Trip());
		}
	}


}
