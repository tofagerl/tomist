package org.fagerland;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
public class Student extends BaseEntity {
    private int grade;
    @ManyToOne
    private Class studentClass;
    @ManyToMany
    private List<Subject> subjects;
    public Student(String name, int grade) {
        super(name);
        this.grade = grade;

    }

    public Student() {
    }

    public boolean addSubject(Subject s) {
        // TODO: Business logic!
        return false;
    }
}
