import { Component, Input, ViewChild, OnInit, Output, EventEmitter  } from '@angular/core';
import { ModalComponent } from '../../../modal/modal.component';

import { AddStudyWorkComponent } from './add-workstudy.component';
import { EditStudyWorkComponent } from './edit-workstudy.component';

import { WorkStudyService } from './workstudy.service';

@Component({
    selector: 'person-details-workstudy',
    templateUrl: './person-workstudy.component.html',
    providers: [ WorkStudyService ]
})
export class PersonWorkStudyComponent implements OnInit{

    //@Input() workStudies: any[];
    @Input() person : any;
    @ViewChild('add_new') addNewModal: ModalComponent;
    @ViewChild('edit') editModal: ModalComponent;

    @ViewChild(AddStudyWorkComponent) addStudyWorkComponent : AddStudyWorkComponent;
    @ViewChild(EditStudyWorkComponent) editStudyWorkComponent : EditStudyWorkComponent;

    @Output() success = new EventEmitter();

    page: number = 1;
    workstudy: any = null;
    workStudies : any[];

    constructor(private workstudyService: WorkStudyService){ }
    
    ngOnInit(){
        if(this.person && this.person.workStudies)
            this.workStudies = this.person.workStudies;
    }

    addNewStudyWork(){
        this.addNewModal.show();
    }
    saveWorkStudy(){
        this.addStudyWorkComponent.createWorkStudy(this.person.id);
    }

    editWorkStudy(workstudy){
        this.editModal.show();
        this.workstudy = workstudy;

        //console.log(workstudy);
    }
    updateWorkStudy(){
        this.editStudyWorkComponent.updateWorkStudy()
    }

    onSaveSuccess(){
        this.addNewModal.hide();
        this.success.emit();
        alert("Work/Study successfully created");
    }
    onUpdateSuccess(){
        this.editModal.hide();
        this.success.emit();
        alert("Work/Study successfully updated");
    }

    deleteworkStudy(workstudy){
        if( window.confirm("Do you really want to delete?") ){
            this.workstudyService.deleteWorkStudy(workstudy)
                .subscribe( res => {
                    this.success.emit();
                } )
        }
    }
}