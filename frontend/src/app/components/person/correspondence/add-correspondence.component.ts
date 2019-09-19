import { Component, Output, EventEmitter } from '@angular/core';

import { CorrespondenceService } from './correspondence.service';

@Component({
    selector: 'add-correspondence',
    template: `<correspondence-form [correspondence]="correspondence" (onchange)="onFormValueChange($event)"></correspondence-form>`,
    providers: [ CorrespondenceService ]
})
export class AddCorrespondenceComponent{

    correspondence: any = {};

    @Output() onSave = new EventEmitter<any>();

    constructor(private correspondenceService: CorrespondenceService){}

    createCorrespondence(person_id){
        this.correspondenceService.createCorrespondence( this.correspondence, person_id )
            .subscribe( data => {
                console.log(data);
                this.onSave.emit(data);
            } )
    }

    onFormValueChange(correspondence){
        this.correspondence = correspondence;
    }

}
