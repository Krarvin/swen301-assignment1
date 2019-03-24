package test.nz.ac.vuw.swen301.assignment1;

import nz.ac.vuw.swen301.assignment1.Degree;
import nz.ac.vuw.swen301.assignment1.Student;
import nz.ac.vuw.swen301.assignment1.StudentManager;
import nz.ac.vuw.swen301.studentmemdb.StudentDB;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StudentManagerTest {

    // DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // INCLUDE THIS AS A STATIC FIXTURE (annotated with @BeforeClass) IN ALL TESTS
    // AND THE APPLICATION CAN CONNECT TO IT WITH JDBC
    @BeforeClass
    public static void init () {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    @Test
    public void dummyTest() throws Exception {
        Student student = new StudentManager().readStudent("id42");
        // THIS WILL INITIALLY FAIL
        assertNotNull(student);
    }

    @Test
    public void readStudentTest1() throws Exception{
        Student student = new StudentManager().readStudent("id42");
        assert(student.getId() == "id42");
    }

    @Test
    public void readStudentTest2() throws Exception{
        assertNull(StudentManager.readStudent("id10000"));
    }
    @Test
    public void readDegreeTest1() throws Exception{
        Degree degree = new Degree("deg13", "swen");
        assert(StudentManager.readDegree("deg13").getName().equals("Thomas"));

    }
    @Test
    public void readDegreeTest2() throws Exception{
        assertNull(StudentManager.readDegree("deg17"));
    }

    @Test
    public void deleteStudentTest() throws Exception{
        StudentManager sm = new StudentManager();
        Student student = sm.readStudent("id50");
        sm.delete(student);
        assertNull(sm.readStudent("id50"));
    }

    @Test
    public void UpdateStudentTest() throws Exception{
        Degree degree = StudentManager.readDegree("deg10");
        Student update = new Student("id42", "hoong", "kevin", degree);
        StudentManager.update(update);
        Student student = StudentManager.readStudent("id42");
        assert(student.getName().equals("hoong"));
        assert(student.getFirstName().equals("kevin"));
    }

    @Test
    public void createStudentTest() throws Exception{
        Student replace = StudentManager.readStudent("id100");
        StudentManager.delete(replace);
        Degree degree = new Degree("deg13", "Thomas");
        Student student = StudentManager.createStudent("hoong","kevin", degree);
        assertNotNull(student);
        String id = student.getId();
        System.out.println(StudentManager.readStudent(id).getFirstName());
        assert(StudentManager.readStudent(id).getName().equals("hoong"));
        assert(StudentManager.readStudent(id).getFirstName().equals("kevin"));
        assert(StudentManager.readStudent(id).getId().equals("id100"));
    }
}
