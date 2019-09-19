import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import {INgxMyDpOptions, IMyDateModel} from 'ngx-mydatepicker';

@Component({
    selector: 'event-form',
    template: `<div class="form-group">
        <label for="title">Title</label>
        <input [(ngModel)]="event.title" (ngModelChange)="onValueChange($event)" type="text" class="form-control" id="title" placeholder="Event Title" required />
    </div>
    <div class="form-group">
        <label for="type">Type</label>
        <select [(ngModel)]="event.type" (ngModelChange)="onValueChange($event)" class="form-control" required>
            <option value="BORN">Born</option>
            <option value="STARTEDJOB">Started Job</option>
            <option value="FINISHEDJOB">Finished Job</option>
            <option value="GRADUATEDHIGHSCHOOL">Graduated High School</option>
        </select>
    </div>
    <div class="form-group">
        <label for="notes">Notes</label>
        <textarea [(ngModel)]="event.notes" (ngModelChange)="onValueChange($event)" class="form-control" id="notes" placeholder="Enter some notes for event"></textarea>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label for="startDate">State Date</label>
                <div class="input-group">
                    <input id="startDate" class="form-control" placeholder="Select a date" ngx-mydatepicker name="startDate"
                        [(ngModel)]="startDate" [options]="datePickerOptions" #dp="ngx-mydatepicker" (dateChanged)="onStartDateChanged($event)" required />
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
    </div>`
})
export class EventFormComponent implements OnInit{

    @Input() event: any;
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

    constructor(){}

    ngOnInit(){

        if(this.event && this.event.startDatetime){
            let _date = this.event.startDatetime.split('T');
            let startDate = _date[0].split('-');

            this.startDate = { date: { year: parseInt(startDate[0]), month: parseInt(startDate[1]), day: parseInt(startDate[2]) } };
        }

        if(this.event && this.event.endDatetime){
            let _date = this.event.endDatetime.split('T');
            let endDate = _date[0].split('-');

            this.endDate = { date: { year: parseInt(endDate[0]), month: parseInt(endDate[1]), day: parseInt(endDate[2]) } };
        }

    }

    onStartDateChanged(event: IMyDateModel): void {
        // date selected
        this.event.startDatetime = event.formatted + ' 00:00:00';

        this.onValueChange(true);
    }
    onEndDateChanged(event: IMyDateModel): void {
        this.event.endDatetime = event.formatted + ' 00:00:00';

        this.onValueChange(true);
    }

    onValueChange($event){
        this.onchange.emit( this.event );
    }

}
