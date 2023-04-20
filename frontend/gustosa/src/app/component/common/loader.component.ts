import { Component, ElementRef, EventEmitter, Output, ViewChild } from "@angular/core";

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

    constructor(){

    }

    doLoad(){
        if(this.more != null)
            this.more.nativeElement.hidden = true;
        if(this.spinner != null)
            this.spinner.nativeElement.hidden = false;
        this.load.emit(this);
    }

    setLoaded(){
        if(this.more != null)
            this.more.nativeElement.hidden = false;
        if(this.spinner != null)
            this.spinner.nativeElement.hidden = true;
    }

  }