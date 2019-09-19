import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class EventsService {

    constructor(private http:HttpClient) {}

    private url = 'http://localhost:8080/events';

    public createEvent(event, person_id){
        return this.http.post<any>(this.url + '/' + person_id, event);
    }

    public updateEvent(event){
        return this.http.put<any>(this.url , event);
    }

    public deleteEvent(event){
        return this.http.get<any>(this.url + '/delete/' + event.id);
    }

}