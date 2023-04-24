import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Order } from 'src/app/model/order.model';
import { Page } from 'src/app/model/pageable.model';
import { OrderService } from 'src/app/service/order.service';


@Component({
  selector: 'admin',
  templateUrl: './orders-admin.component.html',
  styleUrls: ['./../general-admin-style.css']
})
export class OrdersAdminComponent {

    orders: Observable<Page<Order>>;
    currentPage = 1;
    queryParam : string;
  
    constructor (private orderService: OrderService, private router : Router){
      this.queryParam = router.url.slice(19);
      if (this.queryParam.length != 0) {
        if (Number.parseInt(this.queryParam) > 0){
          this.currentPage = Number.parseInt(this.queryParam);
        }
      }
      this.orders = this.orderService.getOrders(this.currentPage - 1);
    }

    onDelete(orderId: string) {
      this.orderService.deleteOrder(orderId).subscribe(() => {
        this.orders = this.orderService.getOrders((this.currentPage - 1))
      });
    }

    onModifyStatus(orderId: string) {
      this.orderService.orderDone(orderId).subscribe(() => {
        this.orders = this.orderService.getOrders((this.currentPage - 1))
      });
    }

    onRightArrow(totalPages : number){
      if(this.currentPage < totalPages){
        this.currentPage ++
        this.router.navigate(['/admin/orders'], { queryParams: { page : this.currentPage } });
      }
      this.orders = this.orderService.getOrders((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
        this.router.navigate(['/admin/orders'], { queryParams: { page : this.currentPage } });
      }
      this.orders = this.orderService.getOrders(this.currentPage - 1)
    }
  
}