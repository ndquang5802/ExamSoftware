package com.assignment.testing;

import com.assignment.model.Course;
import com.assignment.model.CourseDetail;
import com.assignment.model.Teacher;
import com.lib.XFile;
import com.lib.XUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AdminTest {
    String fileCoursePath = "src/com/assignment/file/course.dat";
    List<Course> listCourse;
    String fileTeacherPath = "src/com/assignment/file/teacher.dat";
    List<Teacher> listTeacher;
    String fileCourseDetailPath = "src/com/assignment/file/courseDetail.dat";
    List<CourseDetail> listCourseDetail;

    @BeforeEach
    public void setUp() {
        //Arrange
        listCourse = (List<Course>) XFile.readObject(fileCoursePath);
        listTeacher = (List<Teacher>) XFile.readObject(fileTeacherPath);
        listCourseDetail = (List<CourseDetail>) XFile.readObject(fileCourseDetailPath);
    }

    @Test
    public void testAddCourse(){
        int s = listCourse.size();
        Course c = new Course("1623", "Security");
        listCourse.add(c);
        int expected = s + 1;
        Assertions.assertEquals(expected, listCourse.size());
    }

    @Test
    public void testUpdateCourse(){
        Course c = null;
        for (Course course : listCourse) {
            if (course.getCourseID().equals("WEBG301") && course.getName().equals("Project Web")) {
                c = course;
            }
        }
        c.setName("Advance Project Web");
        String expected = c.getName();
        Assertions.assertEquals(expected, "Advance Project Web");
    }

    @Test
    public void testDeleteCourse(){
        int s = listCourse.size();
        listCourse.removeIf(course -> course.getCourseID().equals("WEBG301") && course.getName().equals("Project Web"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listCourse.size());
    }

    @Test
    public void testAddTeacher(){
        int s = listTeacher.size();
        Teacher t = new Teacher("dungnh", "12345", "Nguyen Hung Dung", "1980-01-01", true, "dungnh@fpt.edu.vn", "Head of Office");
        listTeacher.add(t);
        int expected = s + 1;
        Assertions.assertEquals(expected, listTeacher.size());
    }

    @Test
    public void testUpdateTeacher(){
        Teacher t = null;
        for (Teacher teacher : listTeacher) {
            if (teacher.getUsername().equals("khanhttk")) {
                t = teacher;
            }
        }
        t.setPass("123456789");
        String expected = t.getPass();
        Assertions.assertEquals(expected, "123456789");
    }

    @Test
    public void testDeleteTeacher(){
        int s = listTeacher.size();
        listTeacher.removeIf(teacher -> teacher.getUsername().equals("khanhttk"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listTeacher.size());
    }

    @Test
    public void testAddCourseDetail(){
        int s = listCourseDetail.size();
        CourseDetail cd = new CourseDetail(listTeacher.get(0), listCourse.get(0), "2022-08-01", "2022-09-01");
        listCourseDetail.add(cd);
        int expected = s + 1;
        Assertions.assertEquals(expected, listCourseDetail.size());
    }

    @Test
    public void testUpdateCourseDetail(){
        CourseDetail cd = null;
        for (CourseDetail courseDetail : listCourseDetail) {
            if (courseDetail.getTeacher().getUsername().equals("khanhttk") && courseDetail.getCourse().getCourseID().equals("PROG191")) {
                cd = courseDetail;
            }
        }
        cd.setCourseTo("2022-10-01");
        String expected = XUtils.convertDatetoString(cd.getCourseTo());
        Assertions.assertEquals(expected, "2022-10-01");
    }

    @Test
    public void testDeleteCourseDetail(){
        int s = listCourseDetail.size();
        listCourseDetail.removeIf(courseDetail -> courseDetail.getTeacher().getUsername().equals("khanhttk") && courseDetail.getCourse().getCourseID().equals("PROG191"));
        int expected = s - 1;
        Assertions.assertEquals(expected, listCourseDetail.size());
    }
}
