import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PersonComponent } from './person/person.component';
import { AddPersonComponent } from './person/add-person.component';
import { DashboardComponent } from './dashboard/dashboard.component';

import { ViewPersonComponent } from './person/view-person.component';

const routes: Routes = [
  { path : '', component: DashboardComponent },
  { path: 'persons', component: PersonComponent },
  { path: 'persons/:id', component: ViewPersonComponent },
  { path: 'add', component: AddPersonComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
