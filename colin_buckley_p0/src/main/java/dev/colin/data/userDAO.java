package dev.colin.data;

import dev.colin.entities.User;
import dev.colin.utilities.list;

public interface userDAO {

    User getUserById(int UserId);

    // create default user AKA student
    User createUser(String username, String password, String firstName, String lastName);

    // create user with supplied type; 1: student 2: admin
    User createUser(String username, String password, String firstName, String lastName, int type);

    boolean usernameExists(String username);

    int login(String username, String password);

    list<User> getUsersInClass(int classId);

    boolean addStudentToClass(int classId, int userId);

    boolean removeStudentFromClass(int studentId, int classId);

    boolean removeStudentFromClass(int classId);

}
