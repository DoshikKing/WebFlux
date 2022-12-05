package app.web.webflux.repo;

import app.web.webflux.data.Todo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Component
public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
    Mono<Todo> findById(Long id);
    Mono<Todo> findByComment(String comment);
    @Query("INSERT INTO todo(id,comment) values (:id, :comm)")
    Mono<Todo> addNewRecord(@Param("id") Long id,@Param("comm") String comment);
}
