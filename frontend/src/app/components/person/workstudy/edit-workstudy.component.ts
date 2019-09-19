import { Component, Input, Output, EventEmitter } from '@angular/core';

import { WorkStudyService } from './workstudy.service';

@Component({
    selector: 'edit-workstudy',
    template: `<workstudy-form [workstudy]="workstudy" (onchange)="onFormValueChange($event)"></workstudy-form>`,
    providers: [ WorkStudyService ]
})
export class EditStudyWorkComponent{

    @Input() workstudy: any;
    @Output() onUpdate = new EventEmitter<any>();

    constructor(private workStudyService: WorkStudyService){
        
    }

    ngOnInit(){
        if(this.workstudy.address == null){
            this.workstudy.address = {};
        }
    }

    updateWorkStudy(){
        //console.log( this.workstudy ); //updated value
        this.workStudyService.updateWorkStudy(this.workstudy)
            .subscribe( data => {
                console.log(data);
                this.onUpdate.emit(data);
            } )
    }

    onFormValueChange( workstudy ){
        this.workstudy = workstudy;
    }

}
