import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from 'src/app/model/comment.model';
import { Page } from 'src/app/model/pageable.model';
import { CommentService } from 'src/app/service/comment.service';


@Component({
  selector: 'admin',
  templateUrl: './comments-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class CommentsAdminComponent {

    comments: Observable<Page<Comment>>;
    currentPage = 1;
  
    constructor (private commentService: CommentService){
      this.comments = this.commentService.getComments(0);
    }

    onDelete(couponId: number) {
      this.commentService.deleteComment(couponId.toString()).subscribe(() => {
        this.comments = this.commentService.getComments((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
      }
      this.comments = this.commentService.getComments((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.comments = this.commentService.getComments(this.currentPage - 1)
    }
  
}