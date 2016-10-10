package org.fagerland;

import javax.persistence.Entity;

/**
 * Created by tom on 10/10/2016.
 */
@Entity
public class Class extends BaseEntity {
    public Class(String name) {
        super(name);
    }
}
