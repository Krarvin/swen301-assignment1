package nz.ac.vuw.swen301.assignment1.cli;

import nz.ac.vuw.swen301.assignment1.Student;
import nz.ac.vuw.swen301.assignment1.StudentManager;

public class FindStudentDetails {

    // THE FOLLOWING METHOD MUST IMPLEMENTED
    /**
     * Executable: the user will provide a student id as single argument, and if the details are found,
     * the respective details are printed to the console.
     * E.g. a user could invoke this by running "java -cp <someclasspath> nz.ac.vuw.swen301.assignment1.cli.FindStudentDetails id42"
     * @param arg
     */
    public static void main (String[] arg) {
        if(arg.length == 0){
            return;
        }
        String id = arg[0];
        Student student = StudentManager.readStudent(id);
        if(student != null) {
            System.out.println(student.getId() + " " + student.getFirstName() + " " + student.getName() + " " + student.getDegree().getId() + " " + student.getDegree().getName());
        }
    }
}
