package dev.colin.data;

import dev.colin.entities.Class;
import dev.colin.utilities.list;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class classDAOTest {
    static classDAO classConn = new ClassDAOPostgresImpl();

    @Test
    void get_open_classes() {
        list<Class> openClass = classConn.getOpenClasses();

        Class firstClass = openClass.get(0);

        Assertions.assertEquals("open class",firstClass.getName());
    }

    @Test
    void get_closed_classes() {
        list<Class> closedClass = classConn.getClosedClasses();

        Class firstClass = closedClass.get(0);

        Assertions.assertEquals("closed class", firstClass.getName());

    }

    @Test
    void set_class_open() {
        int classId = 2;

        boolean classOpened = classConn.openClass(classId);

        Assertions.assertEquals(true, classOpened);

    }

    @Test
    void set_class_closed() {
        int classId = 2;

        boolean classClosed = classConn.closeClass(classId);

        Assertions.assertEquals(true, classClosed);

    }

    @Test
    void add_class() {
        Class addClass = new Class();
        addClass.setName("Spanish");
        addClass.setDescription("Basic Spanish class");
        addClass.setOpen(2);
        int classId = classConn.addClass(addClass);

        Assertions.assertEquals(8,classId);

    }

    @Test
    void get_student_classes() {
        int studentId = 4;
        list<Class> studentClasses = classConn.userGetClasses(studentId);

        Class secondClass = studentClasses.get(0);

        Assertions.assertEquals(1,secondClass.getId());

    }


}
