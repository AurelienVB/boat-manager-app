import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from '@app/app.component';
import { AppRoutingModule } from '@app/app-routing.module';
import { ViewBoatComponent } from '@app/components/view-boat/view-boat.component';
import { AddBoatComponent } from '@app/components/add-boat/add-boat.component';
import { EditBoatComponent } from '@app/components/edit-boat/edit-boat.component';
import { BasicAuthInterceptor } from '@app/helpers/basic-auth.interceptor';
import { ErrorInterceptor } from '@app/helpers/error.interceptor';
import { HomeComponent } from '@app/components/home/home.component';
import { LoginComponent } from '@app/components/login/login.component';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule,
        NgbModule,
        ToastrModule.forRoot(),
        BrowserAnimationsModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        LoginComponent,
        ViewBoatComponent,
        AddBoatComponent,
        EditBoatComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }