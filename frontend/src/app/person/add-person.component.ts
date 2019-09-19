import { Component, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

import { Person } from '../models/person.model';
import { PersonService } from './person.service';

@Component({
  selector: 'add-person',
  templateUrl: './add-person.component.html'
})
export class AddPersonComponent {

  person: Person = new Person();
  @Output() added = new EventEmitter<Person>();

  constructor(private router: Router, private personService: PersonService) {

  }

  createPerson(): void {
    this.personService.createPerson(this.person)
        .subscribe( data => {
          //alert("User created successfully.");
          this.person = new Person; //reset person var
          this.added.emit(data);
        });

  };

}
