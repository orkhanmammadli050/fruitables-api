package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.models.Comment;
import az.edu.itbrains.fruitables.models.Product;
import az.edu.itbrains.fruitables.repositories.CommentRepository;
import az.edu.itbrains.fruitables.repositories.ProductRepository;
import az.edu.itbrains.fruitables.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentsByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }


    @Override
    public void addComment(String name, String email, String content) {
        Comment comment = new Comment();
        comment.setName(name);
        comment.setEmail(email);
        comment.setContent(content);
        comment.setProduct(null);
        commentRepository.save(comment);
    }

    @Override
    public void addComment(Long productId, String name, String email, String content,int rating) {
        Product product = productRepository.findById(productId).orElse(null);

        Comment comment = new Comment();
        comment.setName(name);
        comment.setEmail(email);
        comment.setContent(content);
        comment.setRating(rating);
        comment.setProduct(product);

        commentRepository.save(comment);
    }

}
