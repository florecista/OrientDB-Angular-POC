import { Component, Input, ViewChild, OnInit, Output, EventEmitter  } from '@angular/core';
import { ModalComponent } from '../../../modal/modal.component';

import { AddCorrespondenceComponent } from './add-correspondence.component';
import { EditCorrespondenceComponent } from './edit-correspondence.component';

import { CorrespondenceService } from './correspondence.service';

@Component({
    selector: 'person-details-correspondences',
    templateUrl: './person-correspondences.component.html',
    providers: [ CorrespondenceService ]
})
export class PersonCorrespondencesComponent implements OnInit{

    @Input() person : any;
    @ViewChild('add_new') addNewModal: ModalComponent;
    @ViewChild('edit') editModal: ModalComponent;

    @ViewChild(AddCorrespondenceComponent) addCorrespondenceComponent : AddCorrespondenceComponent;
    @ViewChild(EditCorrespondenceComponent) editCorrespondenceComponent : EditCorrespondenceComponent;

    @Output() success = new EventEmitter();

    page: number = 1;
    correspondence: any = null;
    correspondences : any[]

    constructor(private correspondenceService: CorrespondenceService){}

    ngOnInit(){
        if(this.person && this.person.correspondences)
            this.correspondences = this.person.correspondences;
    }

    addNewCorrespondence(){
        this.addNewModal.show();
    }

    saveCorrespondence(){
        this.addCorrespondenceComponent.createCorrespondence(this.person.id);
    }

    editCorrespondence(correspondence){
        this.editModal.show();
        this.correspondence = correspondence;
    }

    updateCorrespondence(){
        this.editCorrespondenceComponent.updateCorrespondence();
    }
    
    onSaveSuccess(){
        this.addNewModal.hide();
        this.success.emit();
        alert("Corresspondence successfully created");
    }
    onUpdateSuccess(){
        this.editModal.hide();
        this.success.emit();
        alert("Corresspondence successfully updated");
    }

    deleteCorrespondence(correspondence){
        if( window.confirm("Do you really want to delete?") ){
            this.correspondenceService.deleteCorrespondence(correspondence)
                .subscribe( res => {
                    this.success.emit();
                } )

        }
    }
}