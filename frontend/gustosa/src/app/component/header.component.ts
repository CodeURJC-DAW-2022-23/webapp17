import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Observable, catchError, map, of } from 'rxjs';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  user: Observable<any>;
  isCollapsed:boolean;
  constructor(private userService: UserService){
    this.user = userService.getUser();
    this.isCollapsed = true;

    // Test line
    userService.getUsers().subscribe({next : response => response, error : e => e});
  }
}