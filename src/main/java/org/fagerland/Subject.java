package org.fagerland;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
public class Subject extends BaseEntity {
    @ManyToMany
    private List<Group> groups;
    @ManyToMany
    private List<Student> students;
    public Subject(String name) {
        super(name);
    }


}
