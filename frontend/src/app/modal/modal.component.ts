import { Component, Input, ElementRef, ViewChild, Output, EventEmitter} from '@angular/core';

declare var $: any;

@Component({
  selector: 'modal-component',
  templateUrl: './modal.component.html',
  styles: []
})
export class ModalComponent {
    @Input() title: String;
    @Input() saveButtonText: String;

    @Output() onsave = new EventEmitter<Boolean>();
    @ViewChild('modal') modal: ElementRef;

    show(){
        $(this.modal.nativeElement).modal('show');
    }

    hide(){
        $(this.modal.nativeElement).modal('hide');
    }

    saveChanges(){
        this.onsave.emit(true);
    }
}