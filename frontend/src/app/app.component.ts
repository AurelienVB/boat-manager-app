import { Component } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthenticationService } from './services/authentication.service';

@Component({ selector: 'app-root', templateUrl: 'app.component.html' })
export class AppComponent {
    user?: Observable<any | null>;

    constructor(private authenticationService: AuthenticationService) {
        this.user = this.authenticationService.user;
    }

    logout() {
        this.authenticationService.logout();
    }
}