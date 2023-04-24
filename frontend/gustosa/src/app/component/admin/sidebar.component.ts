import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { SessionService } from 'src/app/service/session.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./general-admin-style.css'],
})
export class SidebarComponent {
  constructor(private router: Router, private userService:UserService, 
    private sessionService : SessionService, private modalService: BsModalService){
    userService.isUserLoggedIn().subscribe((val)=>{
        if(!val){
            sessionService.updateProfile();
            router.navigateByUrl("login");
        }
    });
  }
}