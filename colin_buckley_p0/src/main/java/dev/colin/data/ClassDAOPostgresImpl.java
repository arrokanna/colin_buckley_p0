package dev.colin.data;

import dev.colin.entities.Class;
import dev.colin.utilities.ArrayList;
import dev.colin.utilities.ConnectionUtil;
import dev.colin.utilities.list;

import java.sql.*;

public class ClassDAOPostgresImpl implements classDAO{
    @Override
    public list<Class> getOpenClasses() {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from classes c \n" +
                    "where c.class_open = 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            list<Class> classes = new ArrayList<>();

            while (rs.next()) {
                Class listClass = new Class();
                listClass.setId(rs.getInt("class_id"));
                listClass.setName(rs.getString("class_name"));
                listClass.setDescription(rs.getString("class_description"));
                listClass.setOpen(rs.getInt("class_open"));
                classes.add(listClass);
            }

            return classes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public list<Class> getClosedClasses() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select *\n" +
                    "from classes c \n" +
                    "where c.class_open = 2";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            list<Class> classes = new ArrayList<>();

            while (rs.next()) {
                Class listClass = new Class();
                listClass.setId(rs.getInt("class_id"));
                listClass.setName(rs.getString("class_name"));
                listClass.setDescription(rs.getString("class_description"));
                listClass.setOpen(rs.getInt("class_open"));
                classes.add(listClass);
            }

            return classes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean openClass(int classId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update classes \n" +
                    "set class_open = 1\n" +
                    "where class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,classId);
            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean closeClass(int classId) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update classes \n" +
                    "set class_open = 2\n" +
                    "where class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,classId);
            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int addClass(Class newClass) {


        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into classes (class_name,class_description,class_open) \n" +
                    "values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,newClass.getName());
            ps.setString(2, newClass.getDescription());
            ps.setInt(3,newClass.getOpen());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int newClassId = 0;

            if (rs.next()) {
                newClassId = rs.getInt("class_id");
            }

            return newClassId;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public list<Class> userGetClasses(int userId) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select c.class_id, c.class_name, c.class_description, c.class_open \n" +
                    "from student_classes sc \n" +
                    "join classes c \n" +
                    "on sc.class_id = c.class_id \n" +
                    "where sc.student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,userId);

            ResultSet rs = ps.executeQuery();

            list<Class> classes = new ArrayList<>();

            while (rs.next()) {
                Class newClass = new Class();
                newClass.setId(rs.getInt("class_id"));
                newClass.setName(rs.getString("class_name"));
                newClass.setDescription(rs.getString("class_description"));
                newClass.setOpen(rs.getInt("class_open"));

                classes.add(newClass);

            }

            return classes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean updateClass(Class updatedClass) {

        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update classes\n" +
                    "set class_name = ?,\n" +
                    "class_description = ?\n" +
                    "where class_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,updatedClass.getName());
            ps.setString(2,updatedClass.getDescription());
            ps.setInt(3,updatedClass.getId());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
