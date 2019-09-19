import { Component, Input, Output, EventEmitter } from '@angular/core';
import { EventsService } from './events.service';

@Component({
    selector: 'edit-event',
    template: `<event-form [event]="event" (onchange)="onFormValueChange($event)"></event-form>`,
    providers: [ EventsService ]
})
export class EditEventComponent{

    @Input() event: any;
    @Output() onUpdate = new EventEmitter<any>();

    constructor( private eventsService: EventsService ){ }

    updateEvent(){
        this.eventsService.updateEvent( this.event )
            .subscribe( data => {
                console.log(data);
                this.onUpdate.emit(data);
            } )
    }

    onFormValueChange(event){
        this.event = event;
    }

}
