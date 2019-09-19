import { Component, Output, EventEmitter } from '@angular/core';

import { WorkStudyService } from './workstudy.service';

@Component({
    selector: 'add-workstudy',
    template: `<workstudy-form [workstudy]="workstudy" (onchange)="onFormValueChange($event)"></workstudy-form>`,
    providers: [ WorkStudyService ]
})
export class AddStudyWorkComponent{

    workstudy: any = null;
    @Output() onSave = new EventEmitter<any>();

    constructor( private workStudyService: WorkStudyService ){
        this.workstudy = {};
        this.workstudy.address = {};
    }

    createWorkStudy(person_id){
        console.log( this.workstudy ); //updated value
        this.workStudyService.createWorkStudy(this.workstudy, person_id)
            .subscribe( data => {
                console.log(data);
                this.onSave.emit( data );
            } )
    }

    onFormValueChange(workstudy){
        this.workstudy = workstudy; //update value on form change
    }

}
