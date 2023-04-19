import { Component } from '@angular/core';
import { Observable, Subject, catchError, map, of, retry } from 'rxjs';
import { SessionService } from '../service/session.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  user: Subject<any>;
  profile: any = false;
  isCollapsed:boolean;
  constructor(private sessionService : SessionService){
    this.user = sessionService.sessionProfile;
    sessionService.sessionProfile.subscribe({
      next: (res) => {console.log(res);this.profile = res},
      error: () => {console.log("errer");this.profile = false},
    });
    sessionService.updateProfile()
    this.isCollapsed = true;
  }
}