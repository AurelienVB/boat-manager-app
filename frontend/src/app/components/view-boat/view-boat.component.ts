import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { BoatService } from '@app/services/boat.service';

@Component({
  selector: 'app-view-boat',
  templateUrl: './view-boat.component.html',
  styleUrls: ['./view-boat.component.scss']
})
export class ViewBoatComponent implements OnInit {

  boat: any = {};
   
  constructor(
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private boatService : BoatService) {}
  
  ngOnInit(): void {  
    this.getBoatById(this.route.snapshot.params['id']);
  }

  getBoatById(boatId: number) {       
    this.boatService.getBoatById(boatId).subscribe(
      (boatResult: any) => this.boat = boatResult,
      (error: any) => this.toastr.error(error)
    ); 
  }
}
