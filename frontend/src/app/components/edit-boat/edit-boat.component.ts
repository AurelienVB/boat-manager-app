import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { BoatService } from '@app/services/boat.service';

@Component({
  selector: 'app-edit-boat',
  templateUrl: './edit-boat.component.html',
  styleUrls: ['./edit-boat.component.scss']
})
export class EditBoatComponent implements OnInit {
  boatForm: any = {};

  @ViewChild("form")
  form!: NgForm;

  isSubmitted: boolean = false;

  constructor(
    private toastr: ToastrService, 
    private route: ActivatedRoute, 
    private router: Router,
    private boatService: BoatService) { }

  ngOnInit(): void {
    this.getBoatById(this.route.snapshot.params['id']);
  }

  getBoatById(id: number) {
    this.boatService.getBoatById(id).subscribe(boatResult => {
        this.boatForm.id = boatResult.id;
        this.boatForm.name = boatResult.name;
        this.boatForm.description = boatResult.description;
      },
      error => this.toastr.error(error)
    );
  }

  editBoat(isValid: boolean) {
    this.isSubmitted = true;
    if (isValid) {
      this.boatService.updateBoat(this.boatForm).subscribe(
        () => {
          this.toastr.success('Boat updated');
          this.router.navigate(['/']);
        },
        error => this.toastr.error(error)
      );
    }
  }
}
