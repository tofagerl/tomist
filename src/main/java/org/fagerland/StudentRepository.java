package org.fagerland;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by tom on 10/10/2016.
 */
@RepositoryRestResource
public interface StudentRepository extends BaseRepository<Student> {
}
