import { Component, Output, EventEmitter } from '@angular/core';

import { EventsService } from './events.service';

@Component({
    selector: 'add-event',
    template: `<event-form [event]="event" (onchange)="onFormValueChange($event)"></event-form>`,
    providers: [ EventsService ]
})
export class AddEventComponent{

    event: any = {};
    @Output() onSave = new EventEmitter<any>();

    constructor( private eventsService: EventsService ){ }

    createEvent(person_id){
        this.eventsService.createEvent( this.event, person_id )
            .subscribe( data => {
                console.log( data );
                this.onSave.emit(data);
            } )
    }

    onFormValueChange(event){
        this.event = event;
    }

}
