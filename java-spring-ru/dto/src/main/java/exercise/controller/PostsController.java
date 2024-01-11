package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/posts")
    public List<PostDTO> getPosts() {
        List<Comment> comments = commentRepository.findAll();
        List<Post> posts = postRepository.findAll();
        Map<Long, List<CommentDTO>> postListMap = new HashMap<>();
        for (Post post : posts) {
            List<CommentDTO> commentsForPost = new ArrayList<>();
            for (Comment comment : comments) {
                if (post.getId() == comment.getPostId()) {
                    commentsForPost.add(toCommentDTO(comment));
                }
            } if (commentsForPost.size() != 0) {
                postListMap.put(post.getId(), commentsForPost);
            } else
                postListMap.put(post.getId(), new ArrayList<>());
        }
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Map.Entry<Long, List<CommentDTO>> entry: postListMap.entrySet()) {
            for (Post post: posts) {
                if (entry.getKey() == post.getId()) {
                    postDTOS.add(toPostDTO(post, entry.getValue()));
                }
            }
        }
        return postDTOS;
    }

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        List<CommentDTO> commentDTOS = commentRepository
                .findByPostId(post.getId())
                .stream()
                .map(this::toCommentDTO).toList();
        return toPostDTO(post, commentDTOS);
    }

    private CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private PostDTO toPostDTO(Post post, List<CommentDTO> comments) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());
        postDTO.setComments(comments);
        return postDTO;
    }
}
// END
