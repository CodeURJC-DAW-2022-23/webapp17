import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  user: Observable<any>;
  constructor(private userService: UserService){
    this.user = userService.getTest();
  }
}