import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class WorkStudyService {

    constructor(private http:HttpClient) {}

    private url = 'http://localhost:8080/workstudy';

    public createWorkStudy(workstudy, person_id){
        return this.http.post<any>(this.url + '/' + person_id, workstudy);
    }

    public updateWorkStudy(workstudy){
        return this.http.put<any>(this.url , workstudy);
    }

    public deleteWorkStudy(workstudy){
        return this.http.get<any>(this.url + '/delete/' + workstudy.id);
    }

}