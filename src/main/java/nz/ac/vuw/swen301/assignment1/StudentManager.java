package nz.ac.vuw.swen301.assignment1;

import nz.ac.vuw.swen301.studentmemdb.StudentDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Random;
import java.util.*;

/**
 * A student managers providing basic CRUD operations for instances of Student, and a read operation for instanbces of Degree.
 * @author jens dietrich
 */
public class StudentManager {

    //  DO NOT REMOVE THE FOLLOWING -- THIS WILL ENSURE THAT THE DATABASE IS AVAILABLE
    // AND THE APPLICATION CAN CONNECT TO IT WITH JDBC
    static {
        StudentDB.init();
    }
    // DO NOT REMOVE BLOCK ENDS HERE

    // THE FOLLOWING METHODS MUST IMPLEMENTED :

    /**
     * Return a student instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * return null if there is no database record with this id.
     * @param id
     * @return
     */
    public static Student readStudent(String id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String sql = "SELECT * FROM STUDENTS WHERE ID=\'"+id+"\'";
            ResultSet rs = stmnt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String firstName = rs.getString("first_name");
                return new Student(id,name,firstName,null);
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }

    /**
     * Return a degree instance with values from the row with the respective id in the database.
     * If an instance with this id already exists, return the existing instance and do not create a second one.
     * return null if there is no database record with this id.
     * @param id
     * @return
     */
    public static Degree readDegree(String id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String sql ="SELECT * FROM DEGREE WHERE ID =\'"+id+"\'";
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                return new Degree(id,name);

            }
        }catch(Exception x){
            x.printStackTrace();
        }
        return null;
    }

    /**
     * Delete a student instance from the database.
     * I.e., after this, trying to read a student with this id will return null.
     * @param student
     */
    public static void delete(Student student) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String id = student.getId();
            String sql ="DELETE FROM STUDENT WHERE ID =\'"+id+"\'";
            stmnt.executeQuery(sql);
        }catch(Exception x) {
            x.printStackTrace();
        }
    }

    /**
     * Update (synchronize) a student instance with the database.
     * The id will not be changed, but the values for first names or degree in the database might be changed by this operation.
     * After executing this command, the attribute values of the object and the respective database value are consistent.
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param student
     */
    public static void update(Student student) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String id = student.getId();
            String name = student.getName();
            String first_name = student.getFirstName();
            Degree degree = student.getDegree();
            String degreeName = degree.getName();
            String sql = "UPDATE FROM STUDENT SET name = \'"+name+"\', firstName = \'"+first_name+"\', degree = \'"+degreeName+"\' WHERE ID = \'"+id+"\'";
            if(name.length()<11 && first_name.length()<11){
                stmnt.executeQuery(sql);
            }
            else{System.out.println("name and first name cannot be longer than 10 characters");}
        }catch(Exception x) {
            x.printStackTrace();
        }
    }


    /**
     * Create a new student with the values provided, and save it to the database.
     * The student must have a new id that is not been used by any other Student instance or STUDENTS record (row).
     * Note that names and first names can only be max 1o characters long.
     * There is no special handling required to enforce this, just ensure that tests only use values with < 10 characters.
     * @param name
     * @param firstName
     * @param degree
     * @return a freshly created student instance
     */
    public static Student createStudent(String name,String firstName,Degree degree) {
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            Random rand = new Random();
            String sql = "SELECT MAX(ID) FROM STUDENTS";
            ResultSet rs = stmnt.executeQuery(sql);
            int newid = rs.getInt(0)+1;
            String stringid = Integer.toString(newid);
            return new Student(stringid, name, firstName, degree);
        }catch(Exception x){
            x.printStackTrace();
        }
        return null;
    }

    /**
     * Get all student ids currently being used in the database.
     * @return
     */
    public static Collection<String> getAllStudentIds() {

        return null;
    }


    /**
     * Get all degree ids currently being used in the database.
     * @return
     */
    public static Iterable<String> getAllDegreeIds() {
        return null;
    }


}
