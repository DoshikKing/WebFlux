package app.web.webflux.controller;

import app.web.webflux.data.Todo;
import app.web.webflux.repo.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {

    TodoRepository todoRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebFluxController.class);

    @Autowired
    public WebFluxController(TodoRepository todoRepo){
        this.todoRepo = todoRepo;
    }

    @GetMapping(value = "/getOne/{id}")
    Mono<Todo> getOneTodo(@PathVariable(name = "id") Long id){
        return todoRepo.findById(id).doOnNext(todo -> LOGGER.info("Get todo: {}", todo));
    }

    @GetMapping(value = "/getAll")
    Flux<Todo> getAllTodos(){
        return todoRepo.findAll().doOnNext(todo -> LOGGER.info("Get todo: {}", todo));
    }

    @DeleteMapping(value = "/deleteOne/{id}")
    Mono<String> deleteOneTodo(@PathVariable(name = "id") Long id){
        return todoRepo.deleteById(id).flatMap(s -> {Mono<String> todo = Mono.just("Deleted...");
            return todo;
        }).doOnNext(todo -> LOGGER.info("Deleted todo with id: {}", id));
    }

    @GetMapping(value = "/getOneByComment/{comment}")
    Mono<Todo> getOneByTime(@PathVariable(name = "comment") String comment){
        return todoRepo.findByComment(comment).doOnNext(todo -> LOGGER.info("Get todo: {}", todo));
    }

    @PostMapping(value = "/addOne/{id}/{comment}")
    Mono<Todo> addOneTodo(@PathVariable(name = "id") Long id, @PathVariable(name = "comment") String comment){
        Todo todo = new Todo();
        todo.setId(id);
        todo.setComment(comment);
        return todoRepo.save(todo).doOnNext(tod -> LOGGER.info("Saved todo: {}", tod));
    }

}
