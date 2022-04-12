package dev.colin.services;

import dev.colin.data.classDAO;
import dev.colin.entities.Class;
import dev.colin.utilities.list;

public class ClassServiceImpl implements classService{

    private classDAO ClassDAO;

    public ClassServiceImpl(classDAO ClassDAO) {
        this.ClassDAO = ClassDAO;
    }

    @Override
    public list<Class> getOpenClasses() {
        return this.ClassDAO.getOpenClasses();
    }

    @Override
    public list<Class> getClosedClasses() {
        return this.ClassDAO.getClosedClasses();
    }

    @Override
    public boolean openClass(int classId) {
        return this.ClassDAO.openClass(classId);
    }

    @Override
    public boolean closeClass(int classId) {
        return this.ClassDAO.closeClass(classId);
    }

    @Override
    public int addClass(Class newClass) {
        return this.ClassDAO.addClass(newClass);
    }

    @Override
    public list<Class> userGetClasses(int userId) {
        return this.ClassDAO.userGetClasses(userId);
    }

    @Override
    public boolean updateClass(Class updateClass) {
        return this.ClassDAO.updateClass(updateClass);
    }
}
