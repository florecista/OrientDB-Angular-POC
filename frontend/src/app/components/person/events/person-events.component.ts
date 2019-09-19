import { Component, Input, ViewChild, OnInit, Output, EventEmitter  } from '@angular/core';
import { ModalComponent } from '../../../modal/modal.component';

import { EditEventComponent } from './edit-event.component';
import { AddEventComponent } from './add-event.component';

import { EventsService } from './events.service';

@Component({
    selector: 'person-details-events',
    templateUrl: './person-events.component.html',
    providers: [EventsService]
})
export class PersonEventsComponent implements OnInit{

    @Input() person: any;
    @ViewChild('add_new') addNewModal: ModalComponent;
    @ViewChild('edit') editModal: ModalComponent;

    @ViewChild(AddEventComponent) addEventComponent : AddEventComponent;
    @ViewChild(EditEventComponent) editEventComponent : EditEventComponent;

    @Output() success = new EventEmitter();

    page: number = 1;
    event : any = null;
    events: any[];

    constructor(private eventsService: EventsService){

    }

    ngOnInit(){
        if(this.person && this.person.events)
            this.events = this.person.events;
    }

    addNewEvent(){
        this.addNewModal.show();
    }
    saveEvent(){
        this.addEventComponent.createEvent(this.person.id);
    }

    editEvent(event){
        this.editModal.show();
        this.event = event;
    }

    updateEvent(){
        this.editEventComponent.updateEvent();
    }

    onSaveSuccess(){
        this.addNewModal.hide();
        this.success.emit();
        alert("Event successfully created");
    }
    onUpdateSuccess(){
        this.editModal.hide();
        this.success.emit();
        alert("Event successfully updated");
    }

    deleteEvent(event){
        if( window.confirm("Do you really want to delete?") ){
            this.eventsService.deleteEvent(event)
                .subscribe( res => {
                    this.success.emit();
                } )
        }
    }
}