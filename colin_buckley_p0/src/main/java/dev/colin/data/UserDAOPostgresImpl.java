package dev.colin.data;

import dev.colin.entities.User;
import dev.colin.utilities.*;

import java.sql.*;

public class UserDAOPostgresImpl implements userDAO{

    // get user information by id
    @Override
    public User getUserById(int UserId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from users u \n" +
                    "where user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,UserId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setType(rs.getInt("type"));
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message, LogLevel.Error);
            return null;

        }

    }

    // create user with fields
    // creates a student user
    // username/password/first name/last name
    @Override
    public User createUser(String username, String password, String firstName, String lastName) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into users (username,password, first_name,last_name)\n" +
                    "values (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,firstName);
            ps.setString(4,lastName);

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setType(rs.getInt("type"));

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return null;
        }

    }

    //overloaded create use to include type if creating an admin user
    @Override
    public User createUser(String username, String password, String firstName, String lastName, int type) {
        if (type > 0 && type < 3) {
            try {
                Connection conn = ConnectionUtil.createConnection();
                String sql = "insert into users (username,password, first_name,last_name,type)\n" +
                        "values (?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1,username);
                ps.setString(2,password);
                ps.setString(3,firstName);
                ps.setString(4,lastName);
                ps.setInt(5,type);

                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setType(rs.getInt("type"));

                return user;

            } catch (SQLException e) {
                e.printStackTrace();
                String message = e.getMessage();
                Logger.log(message,LogLevel.Error);
                return null;
            }

        } else {
            return null;
        }
    }

    //check if a username exists
    @Override
    public boolean usernameExists(String username) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select count(username)\n" +
                    "from users u \n" +
                    "where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int count = rs.getInt("count");
            if (count > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return true;
        }

    }

    //get user id using username + password
    @Override
    public int login(String username, String password) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from users u \n" +
                    "where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            int userId = 0;

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }

            return userId;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return 0;

        }

    }

    // list of user registered to a class
    @Override
    public list<User> getUsersInClass(int classId) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select u.*\n" +
                    "from users u \n" +
                    "join student_classes sc \n" +
                    "on u.user_id = sc.student_id \n" +
                    "where sc.class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,classId);

            list<User> users = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setType(rs.getInt("type"));
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return null;

        }

    }

    // add student to class
    @Override
    public boolean addStudentToClass(int classId, int userId) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into student_classes (student_id,class_id) \n" +
                    "values (?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,userId);
            ps.setInt(2,classId);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }



        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return false;

        }
    }

    // remove student from class
    @Override
    public boolean removeStudentFromClass(int studentId, int classId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from student_classes \n" +
                    "where student_id = ? \n" +
                    "and class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,studentId);
            ps.setInt(2,classId);

            ps.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return false;
        }

    }

    // overloaded
    // remove all students from a class
    @Override
    public boolean removeStudentFromClass(int classId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from student_classes \n" +
                    "where class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,classId);

            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            String message = e.getMessage();
            Logger.log(message,LogLevel.Error);
            return false;
        }

    }
}
