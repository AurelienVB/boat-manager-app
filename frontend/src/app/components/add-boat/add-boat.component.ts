import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { BoatService } from '@app/services/boat.service';

@Component({
  selector: 'app-add-boat',
  templateUrl: './add-boat.component.html',
  styleUrls: ['./add-boat.component.scss']
})
export class AddBoatComponent implements OnInit {
  boatForm: any = {};

  @ViewChild("form")
  form!: NgForm;

  isSubmitted: boolean = false;

  constructor(
    private router: Router, 
    private boatService: BoatService, 
    private toastr: ToastrService) {}

  ngOnInit(): void {
  }

  addBoat(isValid: boolean) {
    this.isSubmitted = true;
    if (isValid) {
      this.boatService.createBoat(this.boatForm).subscribe(
        boatResult => {
          this.toastr.success(`Boat created`);
          this.router.navigate(['/']);
        },
        error => this.toastr.error(error)
      )
    }
  }

}