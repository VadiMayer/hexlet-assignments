package exercise.controller.users;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import static exercise.Data.*;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    private final String URI = "/users/{id}/posts";

    @GetMapping(URI)
    public ResponseEntity<List<Post>> getPostsForUser(@PathVariable int id) {
        List<Post> posts = getPosts().stream()
                .filter(e -> e.getUserId() == id)
                .toList();
        return ResponseEntity.ok()
                .body(posts);
    }

    @PostMapping(URI)
    public ResponseEntity<Post> create(@PathVariable int id, @RequestBody Post post) throws URISyntaxException {
        post.setUserId(id);
        getPosts().add(post);
        return ResponseEntity.created(new URI("/users/" + id + "/posts"))
                .body(post);
    }
}
// END
