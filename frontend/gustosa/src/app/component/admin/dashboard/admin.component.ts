import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Statistics } from 'src/app/model/statistics';
import { DashboardService } from 'src/app/service/dashboard.service';

@Component({
  selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class AdminDashboardComponent {
  statistics : Observable<Statistics>
  currentPage = 1;
  
    constructor (private statisticsService: DashboardService){
      this.statistics = this.statisticsService.getStatistics();
    }
}