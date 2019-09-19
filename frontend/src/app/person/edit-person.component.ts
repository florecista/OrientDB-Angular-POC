import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

import { Person } from '../models/person.model';
import { PersonService } from './person.service';

import {INgxMyDpOptions, IMyDateModel} from 'ngx-mydatepicker';

@Component({
    selector: 'edit-person',
    templateUrl: './edit-person.component.html'
})
export class EditPersonComponent implements OnInit{

    @Input() person: Person;
    @Output() onsave = new EventEmitter<Boolean>();

    datePickerOptions: INgxMyDpOptions = {
        // other options...
        dateFormat: 'yyyy-mm-dd',
        showTodayBtn: false,
        openSelectorTopOfInput: true,
        showSelectorArrow: false
    };

    dateOfBirth: any;

    constructor(private personService: PersonService){
    
    }

    ngOnInit(){
        if( this.person.dateOfBirth ){
            let dob = this.person.dateOfBirth.split('-');
            this.dateOfBirth = { date: { year: parseInt(dob[0]), month: parseInt(dob[1]), day: parseInt(dob[2]) } };
        }
    }

    savePerson(person: Person): void{

        //filter data
        let _person = {
            firstName: person.firstName,
            lastName: person.lastName,
            email: person.email,
            dateOfBirth: person.dateOfBirth
        }

        //this.personService.updatePerson(_person).subscribe( data => {
            this.onsave.emit(true);
        //} )
    }

    onDateChanged(event: IMyDateModel): void {
        // date selected
        this.person.dateOfBirth = event.formatted;
    }

}