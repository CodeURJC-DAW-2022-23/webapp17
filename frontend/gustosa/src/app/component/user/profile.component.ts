import { UserService } from '../../service/user.service';
import { Observable, catchError, concat, map, of, retry } from 'rxjs';
import { Router } from '@angular/router';
import { SessionService } from '../../service/session.service';
import { UserProfile } from 'src/app/model/user.model';
import { Component, ElementRef, TemplateRef, ViewChild } from '@angular/core';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { CropperComponent } from 'angular-cropperjs';
import { LoaderComponent } from '../common/loader.component';

@Component({
  selector: 'user',
  templateUrl: 'profile.component.html',
  styleUrls: []
})
export class ProfileComponent {

    modalRef?: BsModalRef;

    @ViewChild('cropper') cropper?: CropperComponent;

    userProfile : Observable<UserProfile>;    
    constructor(private router: Router, private userService:UserService, 
        private sessionService : SessionService, private modalService: BsModalService){
        userService.isUserLoggedIn().subscribe((val)=>{
            if(!val){
                sessionService.updateProfile();
                router.navigateByUrl("");
            }
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

    changeImage(){
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

    }

}