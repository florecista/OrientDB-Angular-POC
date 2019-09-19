import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { Person } from '../models/person.model';
import { PersonService } from './person.service';
import { ModalComponent } from '../modal/modal.component';
import { AddPersonComponent } from '../person/add-person.component';


@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styles: []
})
export class PersonComponent implements OnInit {

  persons: Person[];
  p: number = 1;
  @ViewChild('add_person') addPersonModal: ModalComponent;
  @ViewChild(AddPersonComponent) addPersonComponent: AddPersonComponent;
 

  constructor(private router: Router, private personService: PersonService) {
    
  }

  private getPersons(){
    this.personService.getPersons()
      .subscribe( data => {
        this.persons = data;
      });
  }

  ngOnInit() {
    this.getPersons();
  };

  delete(person: Person): void {
    if( window.confirm("Do you really want to delete?") ){
      this.personService.deletePerson(person)
        .subscribe( data => {
          this.persons = this.persons.filter(u => u !== person);
        })
    }
  };

  createPerson(): void{
    this.addPersonModal.show();
  }
  savePerson(): void{
    this.addPersonComponent.createPerson();
  }
  onPersonAdded(data): void{
    this.getPersons(); //refresh persons list
    this.addPersonModal.hide(); //hide the modal
    alert("User successfully created");
  }


}
