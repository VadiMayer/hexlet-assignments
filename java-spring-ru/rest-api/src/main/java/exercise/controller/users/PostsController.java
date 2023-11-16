package exercise.controller.users;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();
    private final String URI = "/users/{id}/posts";

    @GetMapping(URI)
    public ResponseEntity<List<Post>> getPostsForUser(@PathVariable int id) {
        return ResponseEntity.ok(posts.stream()
                .filter(e -> e.getUserId() == id)
                .toList());
    }

    @PostMapping(URI)
    public ResponseEntity<Post> create(@PathVariable int id, @RequestBody Post post) throws URISyntaxException {
        post.setUserId(id);
        posts.add(post);
        return ResponseEntity.created(new URI("/users/" + id + "/posts"))
                .body(post);
    }
}
// END
