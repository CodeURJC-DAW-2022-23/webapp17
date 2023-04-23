import { Component } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { Observable } from 'rxjs';
import { Product } from 'src/app/model/product.model';
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

    public barChartData(stats: Statistics): ChartData<'bar'> { 
        var label = stats.topProducts.map((x)=>x.title);
        return {
          labels: label,
          datasets: [
            { data: stats.topSales, label: 'Ventas totales' }
          ]
        };
    }
}