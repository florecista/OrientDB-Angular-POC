import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import {INgxMyDpOptions, IMyDateModel} from 'ngx-mydatepicker';

@Component({
    selector: 'workstudy-form',
    template: `<div class="form-group">
        <label for="title">Title</label>
        <input  (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.title" type="text" class="form-control" id="title" placeholder="Work/Study Title" required />
    </div>
    <div class="form-group">
        <label for="position">Position</label>
        <input (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.position" type="text" class="form-control" id="position" placeholder="Position" required />
    </div>
    <div class="form-group">
        <label for="position">Type</label>
        <select placeholder="Work/Study Type" class="form-control" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.type" required>
            <option value="WORK">Work</option>
            <option value="STUDY">Study</option>
        </select>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="startDate">State Date</label>
                <div class="input-group">
                    <input id="startDate" class="form-control" placeholder="Select a date" ngx-mydatepicker name="startDate"
                        [(ngModel)]="startDate" [options]="datePickerOptions" #dp="ngx-mydatepicker" (dateChanged)="onStartDateChanged($event)"/>
                    <div class="input-group-append">
                        <button (click)="dp.toggleCalendar()" class="btn btn-icon btn-outline-secondary pl-2 pr-2" type="button">
                            <i class="material-icons">
                                calendar_today
                            </i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label for="endDate">End Date</label>
                <div class="input-group">
                    <input id="endDate" class="form-control" placeholder="Select a date" ngx-mydatepicker name="endDate"
                        [(ngModel)]="endDate" [options]="datePickerOptions" #dp2="ngx-mydatepicker" (dateChanged)="onEndDateChanged($event)"/>
                    <div class="input-group-append">
                        <button (click)="dp2.toggleCalendar()" class="btn btn-icon btn-outline-secondary pl-2 pr-2" type="button">
                            <i class="material-icons">
                                calendar_today
                            </i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="address">Address</label>
        <div class="row">
            <div class="col-md-6">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.streetNumber" name="streetNumber" class="form-control" placeholder="Street Number" />
            </div>
            <div class="col-md-6">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.streetName" name="streetName" class="form-control" placeholder="Street Name" />
            </div>
        </div>
        <div class="row mt-3 mb-3">
            <div class="col-md-4">
                <select placeholder="Street Type" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.streetType" name="streetType" class="form-control">
                    <option value="STREET">Street</option>
                    <option value="AVENUE">Avenue</option>
                    <option value="ROAD">Road</option>
                    <option value="ALLEY">Alley</option>
                    <option value="LANE">Lane</option>
                </select>
            </div>
            <div class="col-md-4">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.suburb" name="suburb" class="form-control" placeholder="Suburb" />
            </div>
            <div class="col-md-4">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.postcode" name="postcode" class="form-control" placeholder="Postcode" />
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.state" name="state" class="form-control" placeholder="State" />
            </div>
            <div class="col-md-6">
                <input type="text" (ngModelChange)="onValueChange($event)" [(ngModel)]="workstudy.address.country" name="country" class="form-control" placeholder="Country" />
            </div>
        </div>
    </div>`
})
export class WorkStudyFormComponent implements OnInit{

    @Input() workstudy: any;
    @Output() onchange = new EventEmitter();

    startDate: any;
    endDate: any;

    datePickerOptions: INgxMyDpOptions = {
        // other options...
        dateFormat: 'yyyy-mm-dd',
        showTodayBtn: false,
        openSelectorTopOfInput: true,
        showSelectorArrow: false
    };

    constructor(){ }

    ngOnInit(){

        if(this.workstudy && this.workstudy.startDate){
            let startDate = this.workstudy.startDate.split('-');
            this.startDate = { date: { year: parseInt(startDate[0]), month: parseInt(startDate[1]), day: parseInt(startDate[2]) } };
        }

        if(this.workstudy && this.workstudy.endDate){
            let endDate = this.workstudy.endDate.split('-');
            this.endDate = { date: { year: parseInt(endDate[0]), month: parseInt(endDate[1]), day: parseInt(endDate[2]) } };
        }
    }
    onStartDateChanged(event: IMyDateModel): void {
        // date selected
        this.workstudy.startDate = event.formatted;

        this.onValueChange(true);
    }
    onEndDateChanged(event: IMyDateModel): void {
        this.workstudy.endDate = event.formatted;

        this.onValueChange(true);
    }

    onValueChange($event){
        this.onchange.emit( this.workstudy );
    }

}
