package app.web.webflux;

import app.web.webflux.controller.WebFluxController;
import app.web.webflux.data.Todo;
import app.web.webflux.repo.TodoRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WebFluxController.class)
class WebFluxApplicationTests {
    @MockBean
    TodoRepository todoRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void getAllTest() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setComment("New task todo");

        List<Todo> todos = new ArrayList<Todo>();
        Flux<Todo> todoFlux = Flux.fromIterable(todos);

        Mockito.when(todoRepository.findAll()).thenReturn(todoFlux);

        webTestClient.get()
                .uri("/getAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo[].class);

        Mockito.verify(todoRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    public void getOneById() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setComment("New task todo");

        Mono<Todo> todoMono = Mono.just(todo);

        Mockito.when(todoRepository.findById(1L)).thenReturn(todoMono);

        webTestClient.get()
                .uri("/getOne/"+1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo.class);

        Mockito.verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    @Order(3)
    public void getOneByComment(){
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setComment("New task todo");


        Mono<Todo> todoMono = Mono.just(todo);

        Mockito.when(todoRepository.findByComment("New task todo")).thenReturn(todoMono);

        webTestClient.get()
                .uri("/getOneByComment/New task todo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo.class);

        Mockito.verify(todoRepository, times(1)).findByComment("New task todo");
    }

    @Test
    @Order(4)
    public void insertNewOne(){
        Todo todo = new Todo();
        todo.setId(14L);
        todo.setComment("Test");

        Mono<Todo> todoMono = Mono.just(todo);

        Mockito.when(todoRepository.save(todo)).thenReturn(todoMono);

        webTestClient.post()
                .uri("/addOne/14/Test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo.class);

        Mockito.verify(todoRepository, times(1)).save(todo);
    }

    @Test
    @Order(5)
    public void deleteTestTodo(){
        Mockito.when(todoRepository.deleteById(2L)).thenReturn(Mono.just("Deleted...").then());

        webTestClient.delete()
                .uri("/deleteOne/"+2L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

}
