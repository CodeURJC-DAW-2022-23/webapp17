import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Observable } from "rxjs";
import { CartPackage } from "src/app/model/cart.model";
import { UserService } from "src/app/service/user.service";

@Component({
    selector: 'order',
    templateUrl: 'order.component.html',
    styleUrls: []
  })
  export class OrderComponent {

    private id: number = 0;

    order? : Observable<CartPackage>;

    constructor(private route: ActivatedRoute, private userService : UserService) {

    }

    ngOnInit(){
        this.route.params.subscribe((params)=>{
            this.id = params["id"];
            this.order = this.userService.getOrder(this.id);
        });
    }

  }