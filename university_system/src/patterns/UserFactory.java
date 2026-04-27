package patterns;

import model.users.*;
import enums.TeacherPosition;
import enums.ManagerType;

import java.time.LocalDate;
import java.util.Map;

public final class UserFactory {

    private UserFactory() {
    }

    public static User createUser(String type, Map<String, String> data) {
        if (type == null) {
            throw new IllegalArgumentException("User type cannot be null.");
        }

        switch (type.toLowerCase()) {
            case "student":
                return createStudent(data);
            case "teacher":
                return createTeacher(data);
            case "manager":
                return createManager(data);
            case "admin":
                return createAdmin(data);
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }

    public static Student createStudent(Map<String, String> data) {
        return new Student(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                data.get("major"),
                Integer.parseInt(data.get("yearOfStudy"))
        );
    }

    public static Teacher createTeacher(Map<String, String> data) {
        return new Teacher(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                Double.parseDouble(data.get("salary")),
                data.get("department"),
                data.get("position"),
                LocalDate.parse(data.get("hiringDate")),
                TeacherPosition.valueOf(data.get("positionTitle").toUpperCase())
        );
    }

    public static Manager createManager(Map<String, String> data) {
        return new Manager(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                Double.parseDouble(data.get("salary")),
                data.get("department"),
                data.get("position"),
                LocalDate.parse(data.get("hiringDate")),
                ManagerType.valueOf(data.get("managerType").toUpperCase())
        );
    }

    public static Admin createAdmin(Map<String, String> data) {
        return new Admin(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                Double.parseDouble(data.get("salary")),
                data.get("department"),
                data.get("position"),
                LocalDate.parse(data.get("hiringDate"))
        );
    }
}