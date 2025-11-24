import { Component, Input, OnInit, Type } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

import { BoatService } from '@app/services/boat.service';
import { Boat } from '@app/models/boat';

@Component({
  selector: 'ng-modal-confirm',
  template: `
  <div class="modal-header">
    <h5 class="modal-title" id="modal-title">Delete Confirmation</h5>
    <button type="button" class="btn close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p>Are you sure you want to delete?</p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('cancel click')">CANCEL</button>
    <button type="button" ngbAutofocus class="btn btn-success" (click)="modal.close('Ok click')">OK</button>
  </div>
  `,
})
export class NgModalConfirm {
  constructor(public modal: NgbActiveModal) { }
}

const MODALS: { [name: string]: Type<any> } = {
  deleteModal: NgModalConfirm,
};

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  boats: any = [];

  constructor(
    private router: Router,
    private modalService: NgbModal,
    private toastr: ToastrService,
    private boatService: BoatService) { }

  ngOnInit(): void {
    this.getAllBoats();
  }

  getAllBoats() {
    this.boatService.getAllBoats().subscribe(
      boatsResult => this.boats = boatsResult,
      error => this.toastr.error(error)
    );
  }

  addBoat() {
    this.router.navigate(['addBoat']);
  }

  deleteBoatConfirmation(boat: Boat) {
    this.modalService.open(
      MODALS['deleteModal'],
      { ariaLabelledBy: 'modal-basic-title' })
    .result.then(
      (result) => this.deleteBoat(boat),
      (reason) => {});
  }

  deleteBoat(boat: Boat) {
    this.boatService.deleteBoatById(boat.id).subscribe(
      () => this.getAllBoats(),
      error => this.toastr.error(error)
    );
  }
}