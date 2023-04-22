import { UserService } from '../../service/user.service';
import { Observable, catchError, concat, map, of, retry } from 'rxjs';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';
import { UserProfile } from 'src/app/model/user.model';
import { Component, ElementRef, TemplateRef, ViewChild } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { CropperComponent } from 'angular-cropperjs';
import { LoaderComponent } from '../common/loader.component';
import { CartPackage } from 'src/app/model/cart.model';
import { Page } from 'src/app/model/pageable.model';

@Component({
  selector: 'user',
  templateUrl: 'profile.component.html',
  styleUrls: []
})
export class ProfileComponent {

    modalRef?: BsModalRef;

    @ViewChild('cropper') cropper?: CropperComponent;

    @ViewChild('image') image?: HTMLImageElement;

    @ViewChild('orders') ordersComponent?: LoaderComponent;

    userProfile : Observable<UserProfile>;    

    orders? : Page<CartPackage>;
    constructor(private router: Router, private userService:UserService, 
        private sessionService : SessionService, private modalService: BsModalService){
        userService.isUserLoggedIn().subscribe((val)=>{
            if(!val){
                sessionService.updateProfile();
                router.navigateByUrl("login");
            }
        });

        userService.getOrders().subscribe((page)=>{
            this.orders = page;
        });
        
        this.userProfile = userService.getUser();

    }

    bioMessage(bio?:string){
        if(bio == "" || bio == null){
            return "Bio vacía";
        }else{
            return bio;
        }
    }

    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    changeBio(newBio : string){
        this.userService.modifyUser(undefined, undefined, undefined, newBio).subscribe(()=>{
            this.userProfile = this.userService.getUser();
        });
    }

    changePassword(newPassword : string){
        this.userService.modifyUser(undefined, newPassword, undefined, undefined).subscribe(()=>{
            this.userProfile = this.userService.getUser();
        });
    }

    changeName(name : string){
        this.userService.modifyUser(undefined, undefined, name, undefined).subscribe(()=>{
            this.userProfile = this.userService.getUser();
            this.sessionService.updateProfile();
        });
    }

    deleteUser(){}

    time : string = "2=2";
    changeImage(id: number, cropper : CropperComponent){
        cropper.cropper.getCroppedCanvas({
            width: 512,
            height: 512,
            fillColor: '#000'
        }).toBlob((blob) => {
            if(blob != null)
                this.userService.modifyUserImage(id, blob).subscribe(()=>
                {
                    this.userProfile = this.userService.getUser()
                    this.modalRef?.hide();
                    this.time = new Date().getTime().toString();
                }
                );
        });
    }

    openImage(event : Event, cropper : CropperComponent){
        var input = event.target as HTMLInputElement;
        var reader = new FileReader();
        this.cropper = cropper;
        reader.onload = (res)=>{
            
            if(this.cropper != null){
                this.cropper.imageElement.src = res.target?.result?.toString()!;
                this.cropperData = res.target?.result?.toString()!;
                this.cropper.cropper.replace(res.target?.result?.toString()!);
                cropper.cropper.destroy();
            }
        };
        reader.readAsDataURL(input.files![0]);
    }

    cropperData = "";

    dummyFun(input : string){
        if(this.cropperData == "")
            return input;
        else
            return this.cropperData;
    }

    
    loadMoreOrders(loader : LoaderComponent){
        loader.pageNumber++;
        this.userService.getOrders(loader.pageNumber).subscribe((page)=>{
            if(this.orders != null){
                page.content = this.orders.content.concat(page.content);
            }
                
            page.numberOfElements = page.content.length;
            this.orders = page;
            loader.setLoaded(page);
        });
        
    }

}