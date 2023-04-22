import { Component } from '@angular/core';
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
  
    constructor (private orderService: OrderService){
      this.orders = this.orderService.getOrders(0);
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
      }
      this.orders = this.orderService.getOrders((this.currentPage - 1))
    }

    onLeftArrow(){
      if (this.currentPage > 1){
        this.currentPage --
      }
      this.orders = this.orderService.getOrders(this.currentPage - 1)
    }
  
}