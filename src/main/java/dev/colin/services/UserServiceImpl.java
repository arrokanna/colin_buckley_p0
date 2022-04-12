package dev.colin.services;

import dev.colin.data.userDAO;
import dev.colin.entities.User;
import dev.colin.utilities.LogLevel;
import dev.colin.utilities.Logger;
import dev.colin.utilities.list;

public class UserServiceImpl implements userService{

    private userDAO UserDAO;

    public UserServiceImpl(userDAO UserDAO){
        this.UserDAO = UserDAO;
    };

    @Override
    public User getUserById(int UserId) {
        return this.UserDAO.getUserById(UserId);
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName) {
        return this.UserDAO.createUser(username, password, firstName,lastName);
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName, int type) {
        if (type > 0 && type < 3) {
            return this.UserDAO.createUser(username, password, firstName,lastName,type);
        } else {
            String message = "invalid user type was passed: \n CALLED createUser(String username, String password, String firstName, String lastName, int type) \n VALUE: " + type;
            Logger.log(message, LogLevel.Warning);
            return null;
        }

    }

    @Override
    public boolean usernameExists(String username) {
        return this.UserDAO.usernameExists(username);
    }

    @Override
    public int login(String username, String password) {
        return this.UserDAO.login(username, password);
    }

    @Override
    public list<User> getUsersInClass(int classId) {
        return this.UserDAO.getUsersInClass(classId);
    }

    @Override
    public boolean addStudentToClass(int classId, int userId) {
        list<User> usersInClass = this.UserDAO.getUsersInClass(classId);
        boolean alreadyInClass = false;
        for (int i = 0; i < usersInClass.size(); i++) {
            User currentUser = usersInClass.get(i);
            if (currentUser.getId() == userId) {
                alreadyInClass = true;
            }
        }

        if (!alreadyInClass) {
            return this.UserDAO.addStudentToClass(classId,userId);
        } else {
            String message = "user already in class: \n CALLED: addStudentToClass(int classId, int userId) \n VALUE: " + userId;
            Logger.log(message, LogLevel.Warning);
            return false;
        }

    }

    @Override
    public boolean removeStudentFromClass(int studentId, int classId) {
        return this.UserDAO.removeStudentFromClass(studentId,classId);
    }

    @Override
    public boolean removeStudentFromClass(int classId) {
        return this.UserDAO.removeStudentFromClass(classId);
    }
}
