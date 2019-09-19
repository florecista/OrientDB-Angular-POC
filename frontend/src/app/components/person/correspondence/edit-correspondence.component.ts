import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CorrespondenceService } from './correspondence.service';

@Component({
    selector: 'edit-correspondence',
    template: `<correspondence-form [correspondence]="correspondence" (onchange)="onFormValueChange($event)"></correspondence-form>`,
    providers: [ CorrespondenceService ]
})
export class EditCorrespondenceComponent{

    @Input() correspondence: any;
    @Output() onUpdate = new EventEmitter<any>();

    constructor(private correspondenceService: CorrespondenceService){}

    updateCorrespondence(){
        this.correspondenceService.updateCorrespondence( this.correspondence )
            .subscribe( data => {
                console.log(data);
                this.onUpdate.emit(data);
            } )
    }

    onFormValueChange(correspondence){
        this.correspondence = correspondence;
    }

}
