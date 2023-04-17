import { Component } from '@angular/core';
import { UserService } from '../service/user.service';
import { Observable, catchError, map, of } from 'rxjs';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent {}