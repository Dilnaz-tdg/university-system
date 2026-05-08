package model.academic;

import core.DataStorage;

public class ScheduleManager {

    public void addLesson(Lesson lesson) {

        for (Lesson existing : DataStorage.getInstance().getLessons()) {

            if (isConflict(existing, lesson)) {
            	throw new IllegalStateException("Schedule conflict");
            }
        }
        DataStorage.getInstance().addLesson(lesson);
    }

    private boolean isConflict(Lesson l1, Lesson l2) {
        boolean sameTime = l1.getSchedule().equals(l2.getSchedule());

        boolean sameTeacher = l1.getTeacher() != null &&
                              l1.getTeacher().equals(l2.getTeacher());

        boolean sameRoom = l1.getRoom() != null &&
                           l1.getRoom().equals(l2.getRoom());
        
        return sameTime && (sameTeacher || sameRoom);
    }
}