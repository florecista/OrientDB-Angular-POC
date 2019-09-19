import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Person } from '../models/person.model';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class PersonService {

  constructor(private http:HttpClient) {}

  private userUrl = 'http://localhost:8080/persons';

  public getPersons() {
    return this.http.get<Person[]>(this.userUrl + "/all");
  }

  public getPerson(id){
    return this.http.get<Person>(this.userUrl + "/" + id);
  }

  public deletePerson(person) {
    //var id = person.id.substring(1);
    return this.http.get<Person>(this.userUrl + "/delete/"+ person.id);
  }

  public createPerson(person) {
    return this.http.post<Person>(this.userUrl, person);
  }

  public updatePerson(person){
    return this.http.put<Person>(this.userUrl + "/" + person.id , person);
  }

}
