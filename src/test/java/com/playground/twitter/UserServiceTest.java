package com.playground.twitter;

import com.playground.twitter.domain.UserService;
import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.errors.UserNotFound;
import com.playground.twitter.models.Post;
import com.playground.twitter.services.IDataStore;
import com.playground.twitter.models.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

	public static final User USER1 = new User("1", "Uno");
	public static final User USER2 = User.builder().nickName("Pepe").name("Jose Jose").build();

	@Autowired
	private UserService userService;

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
	void registerUser() throws NickNameExistsError {
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
	void updateRealName() throws UserNotFound {
		final String UPDATED_NAME = "Updated Name";
		userService.updateUserName(USER1.getNickName(), UPDATED_NAME);
		final User registeredUser = dataStore.getUser(USER1.getNickName());
		assertNotNull(registeredUser);
		assertEquals(UPDATED_NAME, registeredUser.getName());
	}

	@Test
	void addPosts() throws UserNotFound {
		assertEquals(dataStore.getPosts(USER1.getNickName()).size(), 0);
		userService.addPost(USER1.getNickName(), new Post("Post Test"));
		assertEquals(dataStore.getPosts(USER1.getNickName()).size(), 1);
	}

	@Test
	void addPostUserNotFound() {
		assertThrows(UserNotFound.class, () -> userService.addPost("Invalid Nick", new Post("Valid Post")));
	}

	@Test
	void getPosts() throws UserNotFound {
		final Post valid_post = new Post("Valid Post");
		dataStore.addPost(USER1.getNickName(), valid_post);
		final HashSet<Post> res = userService.getPosts(USER1.getNickName());
		final HashSet<Post> expected = new HashSet<>(Arrays.asList(valid_post));
		assertEquals(expected, res);
	}

	@Test
	void getPostsValidUserEmpty() throws UserNotFound {
		final HashSet<Post> res = userService.getPosts(USER1.getNickName());
		assertEquals(Collections.EMPTY_SET, res);
	}

	@Test
	void getFollowers() throws UserNotFound {
		dataStore.addFollower(USER1.getNickName(), USER2.getNickName());
		final HashSet<String> res = userService.getFollowers(USER1.getNickName());
		final HashSet<String> expected = new HashSet<>(Arrays.asList(USER2.getNickName()));
		assertEquals(expected, res);
	}

	@Test
	void getFollowersUserNotFound() {
		assertThrows(UserNotFound.class, () -> userService.getFollowers("Invalid Nick"));
	}

	@Test
	void addFollow() throws UserNotFound {
		final User user = userService.addFollow(USER1.getNickName(), USER2.getNickName());
		assertNotNull(user);
		Set<String> expected = new HashSet<>(Arrays.asList(USER2.getNickName()));
		assertEquals(expected, user.getFollows());

		expected = new HashSet<>(Arrays.asList(USER1.getNickName()));
		final Collection<String> followers = userService.getFollowers(USER2.getNickName());
		assertEquals(expected, followers);
	}

	@Test
	void addFollow_FollowerNotFound() {
		assertThrows(UserNotFound.class, () -> userService.addFollow("Invalid Nick", USER2.getNickName()));
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
	void getOne() throws UserNotFound {
		final User one = userService.getUser(USER1.getNickName());
		assertNotNull(one);
		assertEquals(USER1, one);
	}
}

