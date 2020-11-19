package com.playground.twitter;

import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.models.User;
import com.playground.twitter.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
class UserServiceTest {

	public static final User USER1 = new User("1", "Uno");
	public static final User USER2 = User.builder().nickName("Pepe").name("Jose Jose").build();

	@Autowired
	private IUserService userService;

	@Autowired
	IDataStore dataStore;

	@BeforeEach
	private void init() {
		assertNotNull(userService);
		dataStore.clearAll();
		dataStore.addUser(USER1);
		dataStore.addUser(USER2);
	}

	@Test
	void registerUser() {
		final User newUser = new User("Test", "Test User");
		userService.registerUser(newUser);
		final User registeredUser = dataStore.getUser(newUser.getNickName());
		assertNotNull(registeredUser);
		assertEquals(newUser, registeredUser);
	}

	@Test
	void failToRegisterUser() {
		final User newUser = new User(USER1.getNickName(), "Test User");
		assertThrows(NickNameExistsError.class, () -> userService.registerUser(newUser));
	}

	@Test
	void updateRealName() {
		final String UPDATED_NAME = "Updated Name";
		userService.updateUserName(USER1.getNickName(), UPDATED_NAME);
		final User registeredUser = dataStore.getUser(USER1.getNickName());
		assertNotNull(registeredUser);
		assertEquals(UPDATED_NAME, registeredUser.getName());
	}

	@Test
	void addFollow() {
		userService.addFollow(USER1.getNickName(), USER2.getNickName());
		final User user = dataStore.getUser(USER1.getNickName());
		assertNotNull(user);
		Set<String> expected = new HashSet<>(Arrays.asList(USER2.getNickName()));
		assertEquals(expected, USER1.getFollows());

		expected = new HashSet<>(Arrays.asList(USER1.getNickName()));
		final Collection<String> followers = userService.getFollowers(USER2.getNickName());
		assertEquals(expected, followers);
	}

	@Test
	void addFollow_FollowerNotFound() {
		assertThrows(UserNotFound.class, () -> userService.addFollow("notFound", USER2.getNickName()));
	}

	@Test
	void addFollow_FollowedNotFound() {
		assertThrows(UserNotFound.class, () -> userService.addFollow(USER2.getNickName(), "notFound"));
	}

	@Test
	void getAll() {
		final Collection<User> all = userService.getAllUsers();
		assertNotNull(all);
		assertEquals(2, all.size());
	}

	@Test
	void getOne() {
		final User one = userService.getUserByNick(USER1.getNickName());
		assertNotNull(one);
		assertEquals(USER1, one);
	}
}

