package org.fagerland;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
public class Subject extends BaseEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="subject_group", inverseJoinColumns = @JoinColumn(name="group_id", referencedColumnName = "id"), joinColumns = @JoinColumn(name="subject_id", referencedColumnName = "id"))
    private List<Group> groups = new ArrayList<Group>();

    public List<Group> getGroups() {
        return groups;
    }
    public List<Student> getStudents() {
        return students;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="student_subject", joinColumns = @JoinColumn(name="student_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="subject_id", referencedColumnName = "id"))
    private List<Student> students = new ArrayList<Student>();
    public Subject(String name) {
        super(name);
    }

    public Subject() {
    }
}
