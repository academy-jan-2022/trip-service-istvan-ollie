package org.craftedsw.tripservicekata.Utils.Builders;

import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.user.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserBuilder {
	private final User user;
	private final List<Trip> trips;
	private final List<User> friends;

	private UserBuilder(User user, List<Trip> trips, List<User> friends) {
		this.user = user;
		this.trips = trips;
		this.friends = friends;
	}

	public UserBuilder() {
		this(new User(), List.of(), List.of());
	}

	public UserBuilder trips(List<Trip> tripsToAdd) {
		var newTrips = Stream
				  .concat(this.trips.stream(), tripsToAdd.stream())
				  .collect(Collectors.toList());
		return new UserBuilder(user, newTrips, friends);
	}

	public UserBuilder trips(Trip tripsToAdd) {
		var newTrips = Stream
				  .concat(this.trips.stream(), Stream.of(tripsToAdd))
				  .collect(Collectors.toList());
		return new UserBuilder(user, newTrips, friends);
	}

	public UserBuilder friends(List<User> friendsToAdd) {
		var newFriends = Stream
				  .concat(this.friends.stream(), friendsToAdd.stream())
				  .collect(Collectors.toList());
		return new UserBuilder(user, trips, newFriends);
	}

	public UserBuilder friends(User friendsToAdd) {
		var newFriends = Stream
				  .concat(this.friends.stream(), Stream.of(friendsToAdd))
				  .collect(Collectors.toList());
		return new UserBuilder(user, trips, newFriends);
	}

	public User build() {
		addTrips(this.user);
		addFriends(this.user);
		return this.user;
	}

	private void addFriends(User user) {
		for (var friend : friends) {
			user.addFriend(friend);
		}
	}

	private void addTrips(User user) {
		for (var trip : trips) {
			user.addTrip(trip);
		}
	}
}