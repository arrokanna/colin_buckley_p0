// Colin Buckley
// project school registry
// 4/12/2022

package dev.colin.api;

import dev.colin.data.ClassDAOPostgresImpl;
import dev.colin.data.UserDAOPostgresImpl;
import dev.colin.entities.Class;
import dev.colin.entities.User;
import dev.colin.services.ClassServiceImpl;
import dev.colin.services.UserServiceImpl;
import dev.colin.services.classService;
import dev.colin.services.userService;
import dev.colin.utilities.list;

import java.util.InputMismatchException;
import java.util.Scanner;

public class APP {

    public static classService ClassService = new ClassServiceImpl(new ClassDAOPostgresImpl());
    public static userService UserService = new UserServiceImpl(new UserDAOPostgresImpl());
    public static Scanner scanner = new Scanner(System.in);
    public static Scanner scan = new Scanner(System.in).useDelimiter("\n");
    public static User currentUser;

    // call homepage
    public static void main(String[] args) {
        homepage();
    }
    // function to try to login
    public static void login() {
        // get username password
        System.out.println("Please enter your username.");
        String enteredUsername = scanner.next();

        System.out.println("Please enter your password");
        String enteredPassword = scanner.next();

        //fetch user id based on username + password
        int loginCheck = APP.UserService.login(enteredUsername,enteredPassword);

        // validate an userid was returned
        if (loginCheck > 0) {
            APP.currentUser = APP.UserService.getUserById(loginCheck);
        } else {
            System.out.println("Invalid username + password");
            System.out.println("Returning to homepage");
            homepage();
        }

    }

    // default app page
    public static void homepage() {
        // allow user from default page to
        // login, create new user, close app
        System.out.println("Welcome to school class registry");
        System.out.println("1: login");
        System.out.println("2: create user");
        System.out.println("3: close application");

        try {
            int userInPut = scanner.nextInt();
            switch (userInPut) {
                case 1:{
                    login();
                    viewClassData();
                } break;
                case 2:{
                    registerUser();
                    viewClassData();
                } break;
                default:{
                    System.exit(0);
                }
            }
        } catch (InputMismatchException exception) {
            System.out.println("Please enter a number");
        }

    }

    // create a new student user
    public static void registerUser() {
        // get a new username
        System.out.println("Please enter new username");
        String username = scanner.next();

        boolean usernameExists = APP.UserService.usernameExists(username);

        // validate the username doesn't exist in db
        if (usernameExists) {
            System.out.println("please use a different username");
            homepage();
        }

        // get additional user information
        System.out.println("Please enter your password");
        String password = scanner.next();
        System.out.println("Please enter your first name");
        String firstName = scanner.next();
        System.out.println("Please enter your last name");
        String lastName = scanner.next();

        // create new user
        // set new user to current logged in user
        APP.currentUser = APP.UserService.createUser(username,password,firstName,lastName);

    }

    // remove stored user information from logging user
    // return to homepage
    public static void logOut() {
        APP.currentUser = null;
        homepage();
    }

    // function to allow users to view page before logging out or going back to class information page
    public static void returnToChoices() {
        System.out.println("\n1: return to choices.");
        System.out.println("2: log out");
        try {
            int userNextChoice = scanner.nextInt();

            if (userNextChoice == 1) {
                viewClassData();
            } else {
                logOut();
            }
        } catch (InputMismatchException exception) {
        System.out.println("Please enter a number");
        }


    }

    // allow user to view class data based on permissions
    public static void viewClassData() {

        // student type user
        if (APP.currentUser.getType() == 1) {
            System.out.println("Welcome " + APP.currentUser.getFirstName());
            System.out.println("1: view open classes");
            System.out.println("2: register for open class");
            System.out.println("3: cancel register for class");
            System.out.println("4: view current registered classes");
            System.out.println("5: log out");

            try {
                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    // view open classes
                    case 1:{
                        // get a list of open classes and print them out
                        list<Class> openClasses = APP.ClassService.getOpenClasses();

                        for (int i = 0; i < openClasses.size(); i++) {
                            Class currentClass = openClasses.get(i);
                            System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                        }

                        returnToChoices();

                    } break;
                    // register for open class
                    case 2:{
                        // get a list of open class and print them out
                        System.out.println("Enter number to register for class");
                        list<Class> openClasses = APP.ClassService.getOpenClasses();
                        for (int i = 0; i < openClasses.size(); i++) {
                            Class currentClass = openClasses.get(i);
                            System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                        }
                        //ask user to select a class
                        int registerClass = scanner.nextInt();
                        if (registerClass > openClasses.size() || registerClass < 0) {
                            logOut();
                        }
                        Class selectedClass = openClasses.get(registerClass);

                        // add student to class
                        boolean checkPass = APP.UserService.addStudentToClass(selectedClass.getId(),APP.currentUser.getId());

                        // check to make sure student was successfully add to the class
                        if (checkPass) {
                            System.out.println("You have been added to this class");
                        } else {
                            System.out.println("Issue adding you to this class.");
                            System.out.println("You may already be added to this class.");
                        }

                        viewClassData();

                    } break;
                    // cancel registration
                    case 3:{
                        // get a list of classes the student is currently in
                        list<Class> userClasses = APP.ClassService.userGetClasses(APP.currentUser.getId());
                        if (userClasses.size() > 0) {
                            System.out.println("Registered classes");
                            System.out.println("enter number to remove class");
                            for (int i = 0; i < userClasses.size(); i++) {
                                Class currentClass = userClasses.get(i);
                                System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            // select a class that the student is in and remove the class
                            int registeredClass = scanner.nextInt();
                            if (registeredClass > userClasses.size() || registeredClass < 0) {
                                logOut();
                            }
                            Class selectClass = userClasses.get(registeredClass);
                            boolean updatedClass = APP.UserService.removeStudentFromClass(APP.currentUser.getId(),selectClass.getId());

                            // verify that class was deleted correctly
                            if (updatedClass) {
                                System.out.println("Class removed");
                            } else {
                                System.out.println("Error removing class");
                            }
                            viewClassData();

                        //if student doesnt have a registered class
                        } else {
                            System.out.println("Not currently registered for a class");
                            returnToChoices();
                        }

                    } break;
                    // view classes student is registered in
                    case 4:{
                        // list of classes the student is in
                        list<Class> userClasses = APP.ClassService.userGetClasses(APP.currentUser.getId());
                        if (userClasses.size() > 0) {
                            System.out.println("Registered classes");
                            for (int i = 0; i < userClasses.size(); i++) {
                                Class currentClass = userClasses.get(i);
                                System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                        // if student doesnt have any classes currently registered
                        } else {
                            System.out.println("Not currently registered for a class");
                        }

                        returnToChoices();

                    } break;
                    // log out
                    default:{
                        logOut();
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a number");
            }

            // display class information for admin users
        } else if (APP.currentUser.getType() == 2) {
            System.out.println("Welcome " + APP.currentUser.getFirstName());
            System.out.println("1: view existing classes");
            System.out.println("2: add class");
            System.out.println("3: edit class");
            System.out.println("4: open class");
            System.out.println("5: close class");
            System.out.println("6: log out");

            try{
                int userChoice = scanner.nextInt();

                switch (userChoice) {
                    // list of open and closed classes
                    case 1: {
                        // get a list of open classes and display them
                        list<Class> openClasses = APP.ClassService.getOpenClasses();
                        if (openClasses.size() > 0 ) {
                            System.out.println("Open Classes");
                            for (int i = 0; i < openClasses.size(); i++) {
                                Class currentClass = openClasses.get(i);
                                System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                        }

                        // get a list of closed classes and display them
                        list<Class> closedClasses = APP.ClassService.getClosedClasses();
                        if (closedClasses.size() > 0) {
                            System.out.println("Closed Classes");
                            for (int i = 0; i < closedClasses.size(); i++ ) {
                                Class currentClass = closedClasses.get(i);
                                System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                        }

                        returnToChoices();

                    } break;
                    // create new class
                    case 2: {
                        // ask user for class name, class description, and if open/closed
                        Class newClass = new Class();
                        System.out.println("Enter class name");
                        String className = scan.next();
                        newClass.setName(className);

                        System.out.println("Enter class description");
                        String classDescription = scan.next();
                        newClass.setDescription(classDescription);

                        System.out.println("Class open");
                        System.out.println("1: yes");
                        System.out.println("2: no");
                        int classOpen = scanner.nextInt();
                        if (classOpen > 2 || classOpen < 0) {
                            logOut();
                        }
                        newClass.setOpen(classOpen);

                        // create the new class base on user inputs
                        int newClassId = APP.ClassService.addClass(newClass);

                        // verify class was created
                        if (newClassId > 0) {
                            System.out.println("New class created");
                        } else {
                            System.out.println("error creating class");
                        }

                        viewClassData();

                    } break;
                    // edit classes
                    case 3:{
                        // create a list of all class, and display list
                        list<Class> allClasses = APP.ClassService.getOpenClasses();
                        list<Class> closedClasses = APP.ClassService.getClosedClasses();

                        if (closedClasses.size() > 0) {
                            for (int i = 0; i < closedClasses.size(); i ++) {
                                allClasses.add(closedClasses.get(i));
                            }
                        }

                        // have the user select a class, and get input information
                        if (allClasses.size() > 0) {
                            System.out.println("Classes");
                            System.out.println("Enter number to edit");

                            for (int i = 0; i < allClasses.size(); i++) {
                                Class currentClass = allClasses.get(i);
                                System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            int registerClass = scanner.nextInt();
                            if (registerClass > allClasses.size() || registerClass < 0) {
                                logOut();
                            }
                            Class selectedClass = allClasses.get(registerClass);

                            System.out.println("Enter class name");
                            selectedClass.setName(scan.next());
                            System.out.println("Enter class description");
                            selectedClass.setDescription(scan.next());

                            // update class information
                            boolean updateClass = APP.ClassService.updateClass(selectedClass);

                            //verify class information was submitted correctly
                            if (updateClass) {
                                System.out.println("Class updated");
                            } else {
                                System.out.println("Error updating class");
                            }

                            viewClassData();
                        }

                    } break;
                    //open class
                    case 4:{
                        // get a list of currently closed classes
                        list<Class> closedClasses = APP.ClassService.getClosedClasses();

                        if (closedClasses.size() > 0) {
                            System.out.println("Closed Classes to open");
                            System.out.println("Enter number of the class to open");
                            for (int i = 0; i < closedClasses.size(); i++) {
                                Class currentClass = closedClasses.get(i);
                                System.out.println(i + ": Class name: " + currentClass.getName() + ", Class description: " +currentClass.getDescription());
                            }
                            // have a user select a closed class to open
                            int registerClass = scanner.nextInt();
                            if (registerClass > closedClasses.size()) {
                                logOut();
                            }
                            Class selectedClass = closedClasses.get(registerClass);

                            //update class to open
                            boolean checkPass = APP.ClassService.openClass(selectedClass.getId());

                            // verify class was update correctly
                            if (checkPass) {
                                System.out.println("Class Open");
                            } else {
                                System.out.println("Issue opening class");
                            }

                        } else {
                            System.out.println("There are no closed classes");
                        }

                        viewClassData();

                    } break;
                    // close class
                    case 5:{
                        // list of open classes
                        list<Class> openClass = APP.ClassService.getOpenClasses();
                        if (openClass.size() > 0) {
                            System.out.println("Open Classes");
                            System.out.println("enter number to close class");
                            for (int i = 0; i < openClass.size(); i++){
                                Class currentClass = openClass.get(i);
                                System.out.println(i + ": Class Name: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            // have user select a class
                            int registeredClass = scanner.nextInt();
                            if (registeredClass > openClass.size() || registeredClass < 0) {
                                logOut();
                            }
                            Class selectedClass = openClass.get(registeredClass);

                            list<User> usersInClass = APP.UserService.getUsersInClass(selectedClass.getId());

                            // verify there are no users in class
                            //if users exist in class display warning message
                            if (usersInClass.size() > 0) {
                                System.out.println("There are student registered to this class");
                                System.out.println("Remove Students");
                                System.out.println("1: yes");
                                System.out.println("2: no");
                                int removeStudents = scanner.nextInt();
                                // escape if user didnt want to deleted students
                                if (removeStudents != 1) {
                                    viewClassData();
                                }

                                // remove students from class if you selected
                                boolean checkRemoveStudents = APP.UserService.removeStudentFromClass(selectedClass.getId());

                                // verify student were removed from class
                                if(checkRemoveStudents) {
                                    System.out.println("Removed students from class");
                                } else {
                                    System.out.println("Error removing students from class");
                                }

                            }

                            //set class to closed
                            boolean updatedClass = APP.ClassService.closeClass(selectedClass.getId());

                            // verify class was closed
                            if (updatedClass) {
                                System.out.println("Class closed");
                            } else {
                                System.out.println("Error updating class");
                            }

                            viewClassData();

                        }
                    } break;
                    //log out
                    default:{
                        logOut();
                    }

                }
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a number");
            }
        // if user type isn't student or admin return error
        } else {
            System.out.println("Issue with your account please contact a school admin");
            logOut();
        }

    }

}


