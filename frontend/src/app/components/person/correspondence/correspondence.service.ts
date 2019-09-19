import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class CorrespondenceService {

    constructor(private http:HttpClient) {}

    private url = 'http://localhost:8080/correspondence';

    public createCorrespondence(correspondence, person_id){
        return this.http.post<any>(this.url + '/' + person_id, correspondence);
    }

    public updateCorrespondence(correspondence){
        return this.http.put<any>(this.url , correspondence);
    }

    public deleteCorrespondence(correspondence){
        return this.http.get<any>(this.url + '/delete/' + correspondence.id);
    }

}