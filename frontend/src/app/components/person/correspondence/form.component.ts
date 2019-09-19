import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import {INgxMyDpOptions, IMyDateModel} from 'ngx-mydatepicker';

@Component({
    selector: 'correspondence-form',
    template: `<div class="form-group">
        <label for="title">Title</label>
        <input [(ngModel)]="correspondence.title" type="text" class="form-control" id="title" placeholder="Correspondence Title" required />
    </div>
    <div class="form-group">
        <label for="type">Type</label>
        <select [(ngModel)]="correspondence.type" (ngModelChange)="onValueChange($event)" class="form-control" required>
            <option value="EMAIL">Email</option>
            <option value="PHONE">Phone</option>
            <option value="SMS">SMS</option>
        </select>
    </div>
    <div class="form-group">
        <label for="notes">Notes</label>
        <textarea [(ngModel)]="correspondence.notes" class="form-control" id="notes" placeholder="Enter some notes for correspondence"></textarea>
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
                        [(ngModel)]="endDate" [options]="datePickerOptions" #dp2="ngx-mydatepicker" (dateChanged)="onEndDateChanged($event)" />
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
export class CorrespondenceFormComponent implements OnInit{

    @Input() correspondence: any;
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

        if(this.correspondence && this.correspondence.startDate){
            let _date = this.correspondence.startDate.split(' ');
            let startDate = _date[0].split('-');
            this.startDate = { date: { year: parseInt(startDate[0]), month: parseInt(startDate[1]), day: parseInt(startDate[2]) } };
        }

        if(this.correspondence && this.correspondence.endDate){
            let _date = this.correspondence.endDate.split(' ');
            let endDate = _date[0].split('-');
            this.endDate = { date: { year: parseInt(endDate[0]), month: parseInt(endDate[1]), day: parseInt(endDate[2]) } };
        }

    }

    onStartDateChanged(event: IMyDateModel): void {
        // date selected
        this.correspondence.startDate = event.formatted + ' 00:00:00';

        this.onValueChange(true);
    }
    onEndDateChanged(event: IMyDateModel): void {
        this.correspondence.endDate = event.formatted + ' 00:00:00';

        this.onValueChange(true);
    }

    onValueChange($event){
        this.onchange.emit( this.correspondence );
    }

}
