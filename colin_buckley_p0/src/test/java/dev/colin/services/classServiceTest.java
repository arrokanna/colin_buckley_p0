package dev.colin.services;

import dev.colin.data.ClassDAOPostgresImpl;
import dev.colin.entities.Class;
import dev.colin.entities.User;
import dev.colin.utilities.list;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class classServiceTest {
    static classService ClassService = new ClassServiceImpl(new ClassDAOPostgresImpl());

    // return list of open classes
    @Test
    void list_of_open_class() {
        list<Class> openClasses = ClassService.getOpenClasses();
        //Class testClass = openClasses.get(0);

        Assertions.assertEquals(5,openClasses.size());
    }

    // return list of closed classes
    @Test
    void list_of_closed_class() {
        list<Class> closedClasses = ClassService.getClosedClasses();
        Assertions.assertEquals(5,closedClasses.size());
    }

    // return true if set to open
    @Test
    void set_class_open() {
        boolean test = ClassService.openClass(2);
        Assertions.assertTrue(test);
    }

    //return true if set to close
    @Test
    void set_class_closed() {
        boolean test = ClassService.closeClass(2);
        Assertions.assertTrue(test);
    }

    // return class id create class
    @Test
    void create_new_class() {
        Class newClass = new Class();

        newClass.setName("Test");
        newClass.setDescription("Test");
        newClass.setOpen(2);

        newClass.setId(ClassService.addClass(newClass));

        Assertions.assertNotEquals(0,newClass.getId());

    }

    // return list of class student is registered in
    @Test
    void list_of_class_for_student() {
        list<Class> userClasses = ClassService.userGetClasses(4);

        Assertions.assertEquals(4,userClasses.size());
    }

    // return true if updated class info
    @Test
    void update_class_info() {
        Class updateClass = new Class();
        updateClass.setId(11);
        updateClass.setName("test 3");
        updateClass.setName("test 3");

        boolean test = ClassService.updateClass(updateClass);

        Assertions.assertTrue(test);

    }

}
