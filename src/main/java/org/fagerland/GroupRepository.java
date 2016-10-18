package org.fagerland;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by tom on 10/10/2016.
 */
@RepositoryRestResource
public interface GroupRepository extends BaseRepository<Group> {
    List<Group> findBySubjects(Subject subjects);

}
