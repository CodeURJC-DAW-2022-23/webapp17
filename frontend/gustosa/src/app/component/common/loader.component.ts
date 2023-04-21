import { Component, ElementRef, EventEmitter, Output, ViewChild } from "@angular/core";
import { Page } from "src/app/model/pageable.model";

@Component({
    selector: 'loader',
    templateUrl: 'loader.component.html',
    styleUrls: []
  })
  export class LoaderComponent {

    @ViewChild("spinner")
    spinner? : ElementRef<HTMLElement>;

    @ViewChild("more")
    more? : ElementRef<HTMLButtonElement>;

    @Output()
    load = new EventEmitter<LoaderComponent>();

    pageNumber : number = 0;

    constructor(){

    }

    doLoad(){
        if(this.more != null)
            this.more.nativeElement.hidden = true;
        if(this.spinner != null)
            this.spinner.nativeElement.hidden = false;
        this.load.emit(this);
    }

    setLoaded(page? : Page<any>){
        if(this.more != null){
            this.more.nativeElement.hidden = false;
            if(page != null && page.last) this.more.nativeElement.hidden = true;
        }
            
        if(this.spinner != null)
            this.spinner.nativeElement.hidden = true;
    }

  }