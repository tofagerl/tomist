package org.fagerland;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
@Table(name="assembly")
public class Group extends BaseEntity {
    @ManyToMany
    private List<Subject> subjects;
    private int minStudents;
    private int maxStudents;
    @ManyToMany
    private List<Student> students;

    public Group(String name, int minStudents, int maxStudents, Subject... subjects) {
        super(name);
        this.subjects = new ArrayList<Subject>();
        for(Subject s: subjects){
            this.subjects.add(s);
        }
        this.minStudents = minStudents;
        this.maxStudents = maxStudents;
    }

    public Group(String name, Subject... subjects){
        super(name);
        this.subjects = new ArrayList<Subject>();
        for(Subject s: subjects){
            this.subjects.add(s);
        }
        // Default min and max students
        this.minStudents = 2;
        this.maxStudents = 3;
    }
    /*
    is there enough students in the group to make it viable
     */
    public boolean isViableGroup(){
        if(this.students.size()>=minStudents){
            return true;
        }
        else return false;
    }

    public boolean addStudent(Student s){
        if(this.students.size() >= maxStudents){
            return false; // No room in group.
        }
        if(this.students.contains(s)){
            return false; // Student is already in group. Presumably he is learning another subject
        }
        return this.students.add(s);
    }
}
