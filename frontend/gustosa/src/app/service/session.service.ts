import { Injectable, Optional, SkipSelf } from "@angular/core";
import { Subject } from "rxjs";
import { UserService } from "./user.service";

@Injectable({ providedIn: 'root' })
export class SessionService {
    sessionProfile: Subject<any>;

    constructor(private userService: UserService, @Optional() @SkipSelf() parent?: SessionService) {
        if (parent) {
          throw Error(
            `[SessionService]: trying to create multiple instances,
            but this service should be a singleton.`
          );
        }
        this.sessionProfile = new Subject<any>;
      }

    updateProfile(){
        this.userService.getUser().subscribe({
            next: (res) => {this.sessionProfile.next(res)},
            error: (e) => this.sessionProfile.next(null)
        });
    }
}