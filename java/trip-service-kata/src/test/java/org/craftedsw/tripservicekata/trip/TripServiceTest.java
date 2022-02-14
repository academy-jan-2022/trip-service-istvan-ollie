package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class TripServiceTest {

	private static final User GUEST = null;
	private static final User NOT_LOGGED_IN_USER = null;


	@Rule
	public final ExpectedException exception = ExpectedException.none();


	@Test
	public void
	should_throw_exception_when_no_user_logged_in(){
		TripServiceTestable tripService = new TripServiceTestable();
		User user = GUEST;

		assertThrows(UserNotLoggedInException.class, () -> tripService.getTripsByUser(user));
	}

	public class TripServiceTestable extends TripService {

		@Override
		protected User getLoggedUser() {
			return NOT_LOGGED_IN_USER;
		}

	}


}
