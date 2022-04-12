package dev.colin.services;

import dev.colin.data.UserDAOPostgresImpl;
import dev.colin.entities.User;
import dev.colin.utilities.list;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.jws.soap.SOAPBinding;

public class userServiceTest {

    static userService UserService = new UserServiceImpl(new UserDAOPostgresImpl());

    // return user information from user id
    @Test
    void get_user_by_id() {
        User user = UserService.getUserById(1);

        Assertions.assertEquals("password",user.getPassword());
    }

    // create default user AKA student
    @Test
    void create_new_user_no_type() {
        String username = "Test";
        String password = "test";
        String firstName = "Test";
        String lastName = "Test";

        User newUser = UserService.createUser(username,password,firstName,lastName);

        Assertions.assertEquals(10,newUser.getId());

    }

    // create user with supplied type; 1: student 2: admin
    @Test
    void create_new_user_with_type() {
        String username = "Test2";
        String password = "test2";
        String firstName = "Test2";
        String lastName = "Test2";
        int type = 1;

        User newUser = UserService.createUser(username,password,firstName,lastName,type);

        Assertions.assertEquals(11,newUser.getId());
    }

    // return true if username exists
    @Test
    void check_if_username_exist() {
        String username = "dcarey";

        boolean test = UserService.usernameExists(username);

        Assertions.assertTrue(test);

    }

    // return user id if username + password exist
    @Test
    void get_user_from_username_password() {
        String username = "Test2";
        String password = "test2";

        int userId = UserService.login(username,password);

        Assertions.assertEquals(11, userId);

    }

    // return list of users in a class
    @Test
    void get_list_of_users_in_class() {
        int classId = 1;

        list<User> users = UserService.getUsersInClass(classId);

        Assertions.assertEquals(2,users.size());

    }

    // return true if student was added to class
    @Test
    void add_student_to_class() {
        int studentId = 4;
        int classId = 7;

        boolean test = UserService.addStudentToClass(classId,studentId);

        Assertions.assertTrue(test);

    }

    // return true if student was removed from class
    @Test
    void remove_student_from_class() {
        int studentId = 4;
        int classId = 7;

        boolean test = UserService.removeStudentFromClass(studentId,classId);

        Assertions.assertTrue(test);

    }

    // return true is all students were removed from a class
    @Test
    void remove_all_students_from_class() {
        int classId = 7;

        boolean test = UserService.removeStudentFromClass(classId);

        Assertions.assertTrue(test);

    }
}
