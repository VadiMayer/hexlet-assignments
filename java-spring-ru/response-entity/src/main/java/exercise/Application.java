package exercise;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();
    private final String HEADER = "X-Total-Count";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok()
                .header(HEADER, String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Optional<Post> post = posts.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        return post.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post post) throws URISyntaxException {
        posts.add(post);
        URI uri = new URI("/posts/" + post.getId());
        return ResponseEntity.created(uri)
                .body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> update(@PathVariable String id, @RequestBody Post post) {
        Optional<Post> postIfExist = posts.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        if (postIfExist.isPresent()) {
            Post postForUpdate = postIfExist.get();
            postForUpdate.setId(post.getId());
            postForUpdate.setTitle(post.getTitle());
            postForUpdate.setBody(post.getBody());
            return ResponseEntity.ok()
                    .body(post);
        } else
            return ResponseEntity.noContent()
                    .build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
