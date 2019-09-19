import { Component, Input, ViewChild, Output, EventEmitter  } from '@angular/core';
import { Person } from '../../models/person.model';
import { ModalComponent } from '../../modal/modal.component';
import { EditPersonComponent } from '../../person/edit-person.component';

@Component({
    selector: 'person-details',
    templateUrl: './person-details.component.html'
})
export class PersonDetailsComponent{

    @Input() person: Person;
    @ViewChild('edit_person') editPersonModal: ModalComponent;
    @ViewChild(EditPersonComponent) editPersonComponent: EditPersonComponent;

    @Output() success = new EventEmitter();

    constructor(){}

    editPerson(){
        this.editPersonModal.show();
    }
    updatePerson(): void{
        this.editPersonComponent.savePerson(this.person);
    }
    onUpdateSuccess(event): void{
        alert("Person successfully updated");
        this.editPersonModal.hide();
    }

    onProfileUpdated(){
        this.success.emit();
    }

}