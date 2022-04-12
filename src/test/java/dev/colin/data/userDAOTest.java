package dev.colin.data;

import dev.colin.entities.User;
import dev.colin.utilities.list;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class userDAOTest {

    static userDAO userConn = new UserDAOPostgresImpl();

    @Test
    void get_user_by_id() {
        int userId = 1;

        User user = userConn.getUserById(userId);

        Assertions.assertEquals("colin", user.getFirstName());

    }

    @Test
    void create_new_user_no_type() {
        String username = "fdragon";
        String password = "password";
        String firstName = "fluffy";
        String lastName = "dragon";

        User user = userConn.createUser(username,password,firstName,lastName);

        Assertions.assertEquals("fdragon",user.getUsername());

    }

    @Test
    void create_new_user_type() {
        String username = "rkitten";
        String password = "password";
        String firstName = "rainbow";
        String lastName = "kitten";
        int type = 1;

        User user = userConn.createUser(username,password,firstName,lastName,type);

        Assertions.assertEquals("rkitten",user.getUsername());

    }

    @Test
    void user_login_fail() {
        String username = "Colin";
        String password = "Password";

        int userId = userConn.login(username,password);

        Assertions.assertEquals(0,userId);

    }

    @Test
    void user_login_pass() {
        String username = "bbarker";
        String password = "password";

        int userId = userConn.login(username,password);

        Assertions.assertEquals(2,userId);

    }

    @Test
    void get_students_in_class() {
        int classId = 1;

        list<User> users = userConn.getUsersInClass(classId);

        User user = users.get(1);

        Assertions.assertEquals("rainbow",user.getFirstName());

    }

    @Test
    void add_student_to_class() {
        int studentId = 6;
        int classId = 4;

        boolean insertPass = userConn.addStudentToClass(classId,studentId);

        Assertions.assertEquals(true,insertPass);

    }

    @Test
    void delete_student_from_class() {
        int studentId = 4;
        int classId = 3;

        boolean deletePass = userConn.removeStudentFromClass(studentId,classId);

        Assertions.assertEquals(true,deletePass);
    }

    @Test
    void username_exists() {
        String username = "cbuckley";

        boolean exists = userConn.usernameExists(username);

        Assertions.assertEquals(true, exists);

    }

}
