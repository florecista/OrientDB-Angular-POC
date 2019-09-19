import { Component, OnInit, NgZone } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Person } from '../models/person.model';
import { PersonService } from './person.service';

@Component({
  selector: 'view-person',
  templateUrl: './view-person.component.html'
})
export class ViewPersonComponent implements OnInit {

  person: Person;
  constructor(private personService: PersonService, private route: ActivatedRoute, private ngzone: NgZone) {
    
  }

  ngOnInit(){
    this.getPerson();
  }

  loadPersonDetails(){
      this.person = new Person();
      this.getPerson();
  }

  private getPerson(){
    this.route.params.subscribe( params => {
      this.personService.getPerson( params.id ).subscribe( res => {
        this.person = res;
        this.ngzone.run(()=>{
          console.log("refresh success");
        });
      } )
    } )
  }

}
