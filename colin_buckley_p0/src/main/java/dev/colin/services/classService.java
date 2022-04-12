package dev.colin.services;

import dev.colin.entities.Class;
import dev.colin.utilities.list;

public interface classService {

    // return list of open classes
    list<Class> getOpenClasses();

    // return list of closed classes
    list<Class> getClosedClasses();

    // return true if set to open
    boolean openClass(int classId);

    //return true if set to close
    boolean closeClass(int classId);

    // return class id create class
    int addClass(Class newClass);

    // return list of registered class for user
    list<Class> userGetClasses(int userId);

    // return true if updated class info
    boolean updateClass(Class updateClass);

}
