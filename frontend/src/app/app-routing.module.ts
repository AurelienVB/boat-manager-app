import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from '@app/components/home/home.component';
import { LoginComponent } from '@app/components/login/login.component';
import { ViewBoatComponent } from '@app/components/view-boat/view-boat.component';
import { AddBoatComponent } from '@app/components/add-boat/add-boat.component';
import { EditBoatComponent } from '@app/components/edit-boat/edit-boat.component';
import { AuthGuard } from '@app/helpers/auth.guard';

const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'viewBoat/:id', component: ViewBoatComponent, canActivate: [AuthGuard] },
    { path: 'addBoat', component: AddBoatComponent, canActivate: [AuthGuard] },
    { path: 'editBoat/:id', component: EditBoatComponent, canActivate: [AuthGuard] }, 
    { path: '**', redirectTo: '' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }