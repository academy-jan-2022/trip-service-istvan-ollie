package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TripServiceTest {

	private static final User GUEST = null;
	private static final User LOGGED_IN_USER = new User();
	private User LOGGED_IN_USER_RESULT = null;
	private static final Trip TO_LONDON = new Trip();
	private static final Trip TO_PARIS = new Trip();
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
	should_return_empty_list_when_requested_user_is_not_a_friend(){
		LOGGED_IN_USER_RESULT = LOGGED_IN_USER;
		var userToGetTripsFor = new User();
		userToGetTripsFor.addTrip(TO_LONDON);

		var userTrips = tripService.getTripsByUser(userToGetTripsFor);
		assertEquals(0, userTrips.size());
	}

	@Test
	public void
	should_return_only_requested_users_list_even_with_multiple_friends(){
		LOGGED_IN_USER_RESULT = LOGGED_IN_USER;
		var userToGetTripsFor = new User();
		var userWeAreNotInterestedIn = new User();
		userToGetTripsFor.addTrip(TO_LONDON);
		userToGetTripsFor.addFriend(LOGGED_IN_USER);
		userToGetTripsFor.addFriend(userWeAreNotInterestedIn);

		var userTrips = tripService.getTripsByUser(userToGetTripsFor);
		assertEquals(1, userTrips.size());
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

	@Test
	public void
	should_return_expected_trips_of_user_when_they_have_multiple_trips(){
		LOGGED_IN_USER_RESULT = LOGGED_IN_USER;
		var userToGetTripsFor = new User();
		userToGetTripsFor.addFriend(LOGGED_IN_USER);
		userToGetTripsFor.addTrip(TO_PARIS);
		userToGetTripsFor.addTrip(TO_LONDON);
		var result = tripService.getTripsByUser(userToGetTripsFor);
		assertEquals(2, result.size());

	}

	public class TripServiceTestable extends TripService {

		@Override
		protected User getLoggedUser() {
			return LOGGED_IN_USER_RESULT;
		}

		@Override
		protected List<Trip> getTripsFor(User user){
			return user.trips();
		}
	}


}
