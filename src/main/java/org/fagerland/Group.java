package org.fagerland;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
@Table(name = "assembly")
public class Group extends BaseEntity {
    private int minStudents;
    private int maxStudents;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="subject_group", joinColumns = @JoinColumn(name="group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="subject_id", referencedColumnName = "id"))
    private List<Subject> subjects = new ArrayList<Subject>();

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Student> getStudents() {
        return students;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="group_student", joinColumns = @JoinColumn(name="group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="student_id", referencedColumnName = "id"))
    private List<Student> students = new ArrayList<Student>();

    public Group() {
    }

    public Group(String name, int minStudents, int maxStudents, Subject... subjects) {
        super(name);
        for (Subject s : subjects) {
            this.getSubjects().add(s);
            s.getGroups().add(this);
        }

        this.minStudents = minStudents;
        this.maxStudents = maxStudents;
    }

    public Group(String name, Subject... subjects) {
        super(name);
        for (Subject s : subjects) {
            this.subjects.add(s);
            s.getGroups().add(this);
        }
        // Default min and max students
        this.minStudents = 2;
        this.maxStudents = 3;
    }

    /*
    is there enough students in the group to make it viable
     */
    public boolean hasEnoughStudents() {
        if (this.students.size() >= minStudents) {
            return true;
        } else return false;
    }

    public boolean addStudent(Student s) {
        if (this.students.size() >= maxStudents) {
            return false; // No room in group.
        }
        if (this.students.contains(s)) {
            return false; // Student is already in group. Presumably he is learning another subject
        }
        s.getGroups().add(this);
        return this.students.add(s);
    }

    public boolean hasSpace() {
        if (this.getStudents().size() < this.maxStudents) return true;
        else return false;
    }
}
