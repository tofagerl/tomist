package org.fagerland;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
public class Student extends BaseEntity {
    private int grade;
    @ManyToOne
    private Class studentClass;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_subject", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private List<Subject> subjects = new ArrayList<Subject>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_group", joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> groups = new ArrayList<Group>();

    public Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Student() {
    }

    public boolean addSubject(Subject subject, Group group) throws Exception {
        // If student already has subject, return false. This should probably throw an error, but this is outside the scope
        if (this.getSubjects().contains(subject)) return false;
        // If student already is in the group, perhaps learning another subject, obviously he can't join twice
        if (group.getStudents().contains(subject)) return false;
        // If we're making a mistake, and the subject isn't actually taught in the group, we need to throw an exception
        if(!subject.getGroups().contains(group) || !group.getSubjects().contains(subject)) {
            throw new Exception("Trying to add student to group with incompatible subject!");
        }
        // If group is full, return false
        if (!group.hasSpace()) return false;
        // In student_group, the Group.addStudent() method handles both sides of the relationship
        if (!group.addStudent(this)) {
            throw new Exception(String.format("Unable to add student %s to group%s !", this.toString(), group.toString()));
        }
        this.getSubjects().add(subject);
        if (!subject.getStudents().contains(this)) subject.getStudents().add(this); // bidirectionality
        return true;

    }
}
