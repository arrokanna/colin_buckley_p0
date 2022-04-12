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

    public static void main(String[] args) {
        homepage();
    }

    public static void login() {
        System.out.println("Please enter your username.");
        String enteredUsername = scanner.next();

        System.out.println("Please enter your password");
        String enteredPassword = scanner.next();

        int loginCheck = APP.UserService.login(enteredUsername,enteredPassword);

        if (loginCheck > 0) {
            APP.currentUser = APP.UserService.getUserById(loginCheck);
        } else {
            System.out.println("Invalid username + password");
            System.out.println("Returning to homepage");
            homepage();
        }

    }

    public static void homepage() {
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
                case 3:{
                    System.exit(0);
                }
            }
        } catch (InputMismatchException exception) {
            System.out.println("Please enter a number");
        }

    }

    public static void registerUser() {
        System.out.println("Please enter new username");
        String username = scanner.next();

        boolean usernameExists = APP.UserService.usernameExists(username);

        if (usernameExists) {
            System.out.println("please use a different username");
            homepage();
        }

        System.out.println("Please enter your password");
        String password = scanner.next();
        System.out.println("Please enter your first name");
        String firstName = scanner.next();
        System.out.println("Please enter your last name");
        String lastName = scanner.next();

        APP.currentUser = APP.UserService.createUser(username,password,firstName,lastName);

    }

    public static void logOut() {
        APP.currentUser = null;
        homepage();
    }

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

    public static void viewClassData() {

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
                    case 1:{
                        list<Class> openClasses = APP.ClassService.getOpenClasses();

                        for (int i = 0; i < openClasses.size(); i++) {
                            Class currentClass = openClasses.get(i);
                            System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                        }

                        returnToChoices();

                    } break;
                    case 2:{
                        System.out.println("Enter number to register for class");
                        list<Class> openClasses = APP.ClassService.getOpenClasses();
                        for (int i = 0; i < openClasses.size(); i++) {
                            Class currentClass = openClasses.get(i);
                            System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                        }
                        int registerClass = scanner.nextInt();
                        Class selectedClass = openClasses.get(registerClass);

                        boolean checkPass = APP.UserService.addStudentToClass(selectedClass.getId(),APP.currentUser.getId());

                        if (checkPass) {
                            System.out.println("You have been added to this class");
                        } else {
                            System.out.println("Issue adding you to this class.");
                            System.out.println("You may already be added to this class.");
                        }

                        viewClassData();

                    } break;
                    case 3:{
                        list<Class> userClasses = APP.ClassService.userGetClasses(APP.currentUser.getId());
                        if (userClasses.size() > 0) {
                            System.out.println("Registered classes");
                            System.out.println("enter number to remove class");
                            for (int i = 0; i < userClasses.size(); i++) {
                                Class currentClass = userClasses.get(i);
                                System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            int registeredClass = scanner.nextInt();
                            Class selectClass = userClasses.get(registeredClass);
                            boolean updatedClass = APP.UserService.removeStudentFromClass(APP.currentUser.getId(),selectClass.getId());

                            if (updatedClass) {
                                System.out.println("Class removed");
                            } else {
                                System.out.println("Error removing class");
                            }
                            viewClassData();

                        } else {
                            System.out.println("Not currently registered for a class");
                            returnToChoices();
                        }

                    } break;
                    case 4:{
                        list<Class> userClasses = APP.ClassService.userGetClasses(APP.currentUser.getId());
                        if (userClasses.size() > 0) {
                            System.out.println("Registered classes");
                            for (int i = 0; i < userClasses.size(); i++) {
                                Class currentClass = userClasses.get(i);
                                System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                        } else {
                            System.out.println("Not currently registered for a class");
                        }

                        returnToChoices();

                    } break;
                    case 5:{
                        logOut();
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a number");
            }

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
                        list<Class> openClasses = APP.ClassService.getOpenClasses();
                        if (openClasses.size() > 0) {
                            System.out.println("Open Classes");
                            for (int i = 0; i < openClasses.size(); i++) {
                                Class currentClass = openClasses.get(i);
                                System.out.println("Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                        }

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
                        newClass.setOpen(classOpen);

                        int newClassId = APP.ClassService.addClass(newClass);

                        if (newClassId > 0) {
                            System.out.println("New class created");
                        } else {
                            System.out.println("error creating class");
                        }

                        viewClassData();

                    } break;
                    // edit classes
                    case 3:{
                        list<Class> allClasses = APP.ClassService.getOpenClasses();
                        list<Class> closedClasses = APP.ClassService.getClosedClasses();

                        if (closedClasses.size() > 0) {
                            for (int i = 0; i < closedClasses.size(); i ++) {
                                allClasses.add(closedClasses.get(i));
                            }
                        }

                        if (allClasses.size() > 0) {
                            System.out.println("Classes");
                            System.out.println("Enter number to edit");

                            for (int i = 0; i < allClasses.size(); i++) {
                                Class currentClass = allClasses.get(i);
                                System.out.println(i + ": Class: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            int registerClass = scanner.nextInt();
                            Class selectedClass = allClasses.get(registerClass);

                            System.out.println("Enter class name");
                            selectedClass.setName(scan.next());
                            System.out.println("Enter class description");
                            selectedClass.setDescription(scan.next());

                            boolean updateClass = APP.ClassService.updateClass(selectedClass);

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
                        list<Class> closedClasses = APP.ClassService.getClosedClasses();

                        if (closedClasses.size() > 0) {
                            System.out.println("Closed Classes to open");
                            System.out.println("Enter number of the class to open");
                            for (int i = 0; i < closedClasses.size(); i++) {
                                Class currentClass = closedClasses.get(i);
                                System.out.println(i + ": Class name: " + currentClass.getName() + ", Class description: " +currentClass.getDescription());
                            }
                            int registerClass = scanner.nextInt();
                            Class selectedClass = closedClasses.get(registerClass);

                            boolean checkPass = APP.ClassService.openClass(selectedClass.getId());

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
                        list<Class> openClass = APP.ClassService.getOpenClasses();
                        if (openClass.size() > 0) {
                            System.out.println("Open Classes");
                            System.out.println("enter number to close class");
                            for (int i = 0; i < openClass.size(); i++){
                                Class currentClass = openClass.get(i);
                                System.out.println(i + ": Class Name: " + currentClass.getName() + ", Class Description: " + currentClass.getDescription());
                            }
                            int registeredClass = scanner.nextInt();
                            Class selectedClass = openClass.get(registeredClass);

                            list<User> usersInClass = APP.UserService.getUsersInClass(selectedClass.getId());

                            if (usersInClass.size() > 0) {
                                System.out.println("There are student registered to this class");
                                System.out.println("Remove Students");
                                System.out.println("1: yes");
                                System.out.println("2: no");
                                int removeStudents = scanner.nextInt();
                                if (removeStudents == 2) {
                                    viewClassData();
                                }

                                boolean checkRemoveStudents = APP.UserService.removeStudentFromClass(selectedClass.getId());

                                if(checkRemoveStudents) {
                                    System.out.println("Removed students from class");
                                } else {
                                    System.out.println("Error removing students from class");
                                }

                            }

                            boolean updatedClass = APP.ClassService.closeClass(selectedClass.getId());

                            if (updatedClass) {
                                System.out.println("Class closed");
                            } else {
                                System.out.println("Error updating class");
                            }

                            viewClassData();

                        }
                    } break;
                    //log out
                    case 6:{
                        logOut();
                    }

                }
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a number");
            }

        } else {
            System.out.println("Issue with your account please contact a school admin");
            logOut();
        }

    }

}


