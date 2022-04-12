package dev.colin.data;

import dev.colin.entities.Class;
import dev.colin.utilities.list;

public interface classDAO {

    list<Class> getOpenClasses();

    list<Class> getClosedClasses();

    boolean openClass(int classId);

    boolean closeClass(int classId);

    int addClass(Class newClass);

    list<Class> userGetClasses(int userId);

    boolean updateClass(Class updatedClass);

}
