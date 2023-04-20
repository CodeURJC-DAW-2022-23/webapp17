import { Injectable, OnInit, Optional, SkipSelf } from "@angular/core";
import { Subject, take } from "rxjs";
import { UserService } from "./user.service";
import { NavigationEnd, Router } from "@angular/router";

@Injectable({ providedIn: 'root' })
export class SessionService{
    sessionProfile: Subject<any>;
    hasFirstNavigation = false;

    constructor(private router: Router, private userService: UserService, @Optional() @SkipSelf() parent?: SessionService) {
      if (parent) {
        throw Error(
          `[SessionService]: trying to create multiple instances,
          but this service should be a singleton.`
        );
      }
      this.sessionProfile = new Subject<any>;

      this.router.events.pipe(take(1)).subscribe((initval)=>{
        var nv = initval as NavigationEnd;
        this.hasFirstNavigation = true;
        // Load last session
        if(nv.url == "/"){
          this.router.navigateByUrl(localStorage.getItem("lastUrl")!);
        }

      });

      this.router.events.subscribe((val)=>{
        if(val instanceof NavigationEnd && this.hasFirstNavigation){
          localStorage.setItem("lastUrl", val.url);
        }
      });

    }

    updateProfile(){
        this.userService.getUser().subscribe({
            next: (res) => {this.sessionProfile.next(res)},
            error: (e) => this.sessionProfile.next(null)
        });
    }
}