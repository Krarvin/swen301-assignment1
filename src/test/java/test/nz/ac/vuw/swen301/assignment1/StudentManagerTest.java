package test.nz.ac.vuw.swen301.assignment1;

import nz.ac.vuw.swen301.assignment1.Degree;
import nz.ac.vuw.swen301.assignment1.Student;
import nz.ac.vuw.swen301.assignment1.StudentManager;
import nz.ac.vuw.swen301.studentmemdb.StudentDB;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

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
        assert(StudentManager.readStudent(id).getName().equals("hoong"));
        assert(StudentManager.readStudent(id).getFirstName().equals("kevin"));
        assert(StudentManager.readStudent(id).getId().equals("id100"));
    }

    @Test
    public void getStudentIDTest() throws Exception{
        assert(StudentManager.getAllStudentIds().size() == 9999);
        Student delete = StudentManager.readStudent("id8");
        StudentManager.delete(delete);
        assert(StudentManager.getAllStudentIds().size() == 9998);
        Student delete2 = StudentManager.readStudent("id9");
        StudentManager.delete(delete2);
        assert(StudentManager.getAllStudentIds().size() == 9997);
    }
    @Test
    public void getDegreeIDTest() throws Exception{
        int count = 0;
        for(String s : StudentManager.getAllDegreeIds()){
            count++;
        }
        assertEquals(16, count);
        }

    @Test
    public void getDegreeIDTest2() throws Exception{
        int count = 0;
        for(String s : StudentManager.getAllDegreeIds()){
            count++;
        }
        assertEquals(16, count);
    }

    @Test
    public void thousandQueryTest() throws Exception{
        long start = System.currentTimeMillis();
        for(int i = 0; i<1000; i++) {
            StudentManager.readStudent("id" + Integer.toString(i));
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        assert(time < 1000);
    }
}
