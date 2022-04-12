package dev.colin.data;

import dev.colin.entities.Class;
import dev.colin.utilities.list;

public interface classDAO {

    // list of open classes
    list<Class> getOpenClasses();

    // list of closed classes
    list<Class> getClosedClasses();

    // return true if class is close
    boolean openClass(int classId);

    // return true is class is opened
    boolean closeClass(int classId);

    // return class id of new class
    int addClass(Class newClass);

    // list of class student is registered in
    list<Class> userGetClasses(int userId);

    //return true if class is updated
    boolean updateClass(Class updatedClass);

}
