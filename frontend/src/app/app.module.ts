import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { HttpClientModule } from "@angular/common/http";
import { PersonComponent } from './person/person.component';
import { PersonService } from './person/person.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddPersonComponent } from './person/add-person.component';
import { EditPersonComponent } from './person/edit-person.component';
import { ModalComponent } from './modal/modal.component';
import { AddressComponent } from './address/address.component';
import {NgxPaginationModule} from 'ngx-pagination';

import { ViewPersonComponent } from './person/view-person.component';
import { PersonDetailsComponent } from './components/person/person-details.component';
import { NgxMyDatePickerModule } from 'ngx-mydatepicker';

import { PersonWorkStudyComponent } from './components/person/workstudy/person-workstudy.component';
import { PersonEventsComponent } from './components/person/events/person-events.component';
import { PersonCorrespondencesComponent } from './components/person/correspondence/person-correspondences.component';
import { AddStudyWorkComponent } from './components/person/workstudy/add-workstudy.component';
import { AddEventComponent } from './components/person/events/add-event.component';
import { AddCorrespondenceComponent } from './components/person/correspondence/add-correspondence.component';
import { EditStudyWorkComponent } from './components/person/workstudy/edit-workstudy.component';
import { EditEventComponent } from './components/person/events/edit-event.component';
import { EditCorrespondenceComponent } from './components/person/correspondence/edit-correspondence.component';

import { WorkStudyFormComponent } from './components/person/workstudy/form.component';
import { EventFormComponent } from './components/person/events/form.component';
import { CorrespondenceFormComponent } from './components/person/correspondence/form.component';
 
@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    PersonComponent,
    AddPersonComponent,
    EditPersonComponent,
    ModalComponent,
    ViewPersonComponent,
    PersonDetailsComponent,
    PersonWorkStudyComponent,
    PersonEventsComponent,
    PersonCorrespondencesComponent,
    AddStudyWorkComponent,
    AddEventComponent,
    AddCorrespondenceComponent,
    EditStudyWorkComponent,
    EditEventComponent,
    EditCorrespondenceComponent,
    WorkStudyFormComponent,
    EventFormComponent,
    CorrespondenceFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgxPaginationModule,
    NgxMyDatePickerModule.forRoot()
  ],
  providers: [PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
