package dev.colin.data;

import dev.colin.entities.User;
import dev.colin.utilities.list;

public interface userDAO {

    // get user by ID
    User getUserById(int UserId);

    // create default user AKA student
    User createUser(String username, String password, String firstName, String lastName);

    // create user with supplied type; 1: student 2: admin
    User createUser(String username, String password, String firstName, String lastName, int type);

    // return true if username exist
    boolean usernameExists(String username);

    // return user id if username and password match
    int login(String username, String password);

    // list of users in class
    list<User> getUsersInClass(int classId);

    // return true if student was added to class
    boolean addStudentToClass(int classId, int userId);

    // return true if student/class pair was deleted
    boolean removeStudentFromClass(int studentId, int classId);

    // return true if all students where removed from a class
    boolean removeStudentFromClass(int classId);

}
