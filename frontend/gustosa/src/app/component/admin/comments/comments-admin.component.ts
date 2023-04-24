import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
    queryParam : string;
  
    constructor (private commentService: CommentService, private router: Router){
      this.queryParam = router.url.slice(21);
      if (this.queryParam.length != 0) {
        if (Number.parseInt(this.queryParam) > 0){
          this.currentPage = Number.parseInt(this.queryParam);
        }
      }
      this.comments = this.commentService.getComments(this.currentPage - 1);
    }

    onDelete(couponId: number) {
      this.commentService.deleteComment(couponId.toString()).subscribe(() => {
        this.comments = this.commentService.getComments((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
        this.router.navigate(['/admin/comments'], { queryParams: { page : this.currentPage } });
      }
      this.comments = this.commentService.getComments((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
        this.router.navigate(['/admin/comments'], { queryParams: { page : this.currentPage } });
      }
      this.comments = this.commentService.getComments(this.currentPage - 1)
    }
  
}