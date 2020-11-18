package com.playground.twitter;

import com.playground.twitter.controller.UserController;
import com.playground.twitter.errors.NickNameExistsError;
import com.playground.twitter.service.IDataStore;
import com.playground.twitter.model.User;
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
class TwitterApplicationTests {

	public static final User USER1 = new User("1", "Uno");
	public static final User USER2 = User.builder().nickName("Pepe").name("Jose Jose").build();
	@Autowired
	private UserController userController;

	@Autowired
	IDataStore dataStore;

	@BeforeEach
	private void init() {
		assertNotNull(userController);
		dataStore.clearAll();
		dataStore.putUser(USER1);
		dataStore.putUser(USER2);
	}

	@Test
	void registerUser() {
		final User newUser = new User("Test", "Test User");
		userController.register(newUser);
		final User registeredUser = dataStore.getUser(newUser.getNickName());
		assertNotNull(registeredUser);
		assertEquals(newUser, registeredUser);
	}

	@Test
	void failToRegisterUser() {
		final User newUser = new User(USER1.getNickName(), "Test User");
		assertThrows(NickNameExistsError.class, () -> userController.register(newUser));
	}

	@Test
	void updateRealName() {
		final String UPDATED_NAME = "Updated Name";
		userController.updateName(USER1.getNickName(), UPDATED_NAME);
		final User registeredUser = dataStore.getUser(USER1.getNickName());
		assertNotNull(registeredUser);
		assertEquals(UPDATED_NAME, registeredUser.getName());
	}

	@Test
	void addFollow() {
		userController.addFollow(USER1.getNickName(), USER2.getNickName());
		final User user = dataStore.getUser(USER1.getNickName());
		assertNotNull(user);
		final Set<String> expected = new HashSet<>(Arrays.asList(USER2.getNickName()));
		assertEquals(expected, USER1.getFollows());
	}

	@Test
	void getAll() {
		final Collection<User> all = userController.all();
		assertNotNull(all);
		assertEquals(2, all.size());
	}

	@Test
	void getOne() {
		final User one = userController.one(USER1.getNickName());
		assertNotNull(one);
		assertEquals(USER1, one);
	}
}

