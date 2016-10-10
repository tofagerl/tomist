package org.fagerland;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Created by tom on 10/10/2016.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    protected long id;
    @NotNull
    private String name;

    public BaseEntity() {
    }

    public BaseEntity(String name) {
        this.name = name;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s %d:\t%s", this.getClass().getSimpleName(), this.getId(), this.getName());
    }
}
