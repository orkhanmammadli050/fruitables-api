package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.models.Comment;
import az.edu.itbrains.fruitables.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {
    List<Comment> getAllComments();
    List<Comment> getCommentsByProductId(Long productId);
    void addComment(Long productId, String name, String email, String content, int rating);
    void addComment(String name, String email, String content);
}
