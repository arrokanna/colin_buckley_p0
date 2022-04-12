package dev.colin.services;

import dev.colin.entities.User;
import dev.colin.utilities.list;

public interface userService {

    // return user information from user id
    User getUserById(int UserId);

    // create default user AKA student
    User createUser(String username, String password, String firstName, String lastName);

    // create user with supplied type; 1: student 2: admin
    User createUser(String username, String password, String firstName, String lastName, int type);

    // return true if username exists
    boolean usernameExists(String username);

    // return user id if username + password exist
    int login(String username, String password);

    // return list of users in a class
    list<User> getUsersInClass(int classId);

    // return true if student was added to class
    boolean addStudentToClass(int classId, int userId);

    // return true if student was removed from class
    boolean removeStudentFromClass(int studentId,int classId);

    // return true is all students were removed from a class
    boolean removeStudentFromClass(int classId);

}
