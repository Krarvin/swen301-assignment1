package nz.ac.vuw.swen301.assignment1;

import nz.ac.vuw.swen301.studentmemdb.StudentDB;

import java.sql.*;
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
            String sql = "SELECT * FROM STUDENTS WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String firstName = rs.getString("first_name");
                String degree = (rs.getString("degree"));
                String degreeName = (readDegree(degree).getName());
                ps.closeOnCompletion();
                con.close();
                return new Student(id,name,firstName,new Degree(degree,degreeName));
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
            String sql ="SELECT name FROM DEGREES WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                ps.close();
                con.close();
                return new Degree(id,name);

            }
            return null;
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
            String id = student.getId();
            String sql ="DELETE FROM STUDENTS WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,id);
            ps.executeUpdate();
            ps.close();
            con.close();
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
            String sql = "UPDATE STUDENTS SET NAME = ?, FIRST_NAME = ?, DEGREE = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            String id = student.getId();
            String name = student.getName();
            String first_name = student.getFirstName();
            Degree degree = student.getDegree();
            String degreeID = degree.getId();
            ps.setString(1,name);
            ps.setString(2,first_name);
            ps.setString(3,degreeID);
            ps.setString(4, id);
            if(name.length()<11 && first_name.length()<11){
                ps.executeUpdate();
                ps.close();
                con.close();
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
            String sql = "INSERT INTO STUDENTS VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            Collection<String> studentIDList = getAllStudentIds();
            int id = 0;
            for(int i = 0; i<studentIDList.size() + 1;i++){
                if(!studentIDList.contains("id" + i)){id = i;}
            }
            String Stringid = "id" + Integer.toString(id);
            ps.setString(1, Stringid);
            ps.setString(2, firstName);
            ps.setString(3, name);
            ps.setString(4, degree.getId());
            if(studentIDList.size() < 10000){
                ps.executeUpdate();
                ps.close();
                con.close();
            }
            return new Student(Stringid, name, firstName, degree);
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
        try {
            List<String> studentIDList = new ArrayList<String>();
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String sql = "SELECT id FROM STUDENTS";
            ResultSet rs = stmnt.executeQuery(sql);
            int count = 0;
            while(rs.next()){
                studentIDList.add(rs.getString("id"));
                count++;
            }
            stmnt.close();
            con.close();
            return studentIDList;
        }catch(Exception x){
            x.printStackTrace();
        }
        return null;
    }


    /**
     * Get all degree ids currently being used in the database.
     * @return
     */
    public static Iterable<String> getAllDegreeIds() {
        try{
            List<String> degreeIDList = new ArrayList<String>();
            Connection con = DriverManager.getConnection("jdbc:derby:memory:student_records");
            Statement stmnt = con.createStatement();
            String sql = "SELECT id FROM DEGREES";
            ResultSet rs = stmnt.executeQuery(sql);
            int count = 0;
            while(rs.next()){
                degreeIDList.add(rs.getString(1));
                count++;
            }
            stmnt.close();
            con.close();
            return degreeIDList;
        }catch(Exception x){
            x.printStackTrace();
        }
        return null;
    }


}
