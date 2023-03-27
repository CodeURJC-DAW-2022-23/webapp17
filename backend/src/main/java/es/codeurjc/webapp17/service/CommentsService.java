package es.codeurjc.webapp17.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.CommentsRepo;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepo comments;

    public Page<Comment> getComments(Product product, int numPage, int pageSize) {
        Pageable pageable = PageRequest.of(numPage, pageSize);
        return comments.findByProduct(product, pageable);
    }

    public int getTotalPagesComments(List<Comment> comments) {
        int pageSize = 4;
        int totalComments = comments.size();
        int totalPages = totalComments / pageSize;
        if (totalComments % pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }

    public CommentsRepo getCommentsRepo(){
        return comments;
    }

    public Boolean removeComment(long id){
        Optional <Comment> comment = getCommentsRepo().findById(id);
        if(!comment.isEmpty()){
            getCommentsRepo().delete(comment.get());
            return true;
        }else{
            return false;
        }
    }

}
