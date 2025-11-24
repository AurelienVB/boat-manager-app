import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private userSubject: BehaviorSubject<any | null>;
    public user: Observable<any | null>;

    constructor(
        private router: Router,
        private http: HttpClient
    ) {
        this.userSubject = new BehaviorSubject(JSON.parse(sessionStorage.getItem('user')!));
        this.user = this.userSubject.asObservable();
    }

    public get userValue() {
        return this.userSubject.value;
    }

    login(username: string, password: string) {
        const user = {
            authdata: window.btoa(username + ':' + password)
        };
        return this.http.get(`${environment.apiUrl}/login`,
            { headers: new HttpHeaders({Authorization: `Basic ${user.authdata}`})})
            .pipe(
                tap(() => {
                    sessionStorage.setItem('user', JSON.stringify(user));
                    this.userSubject.next(user);
                })
            );
    }

    logout() {
        sessionStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/login']);
    }
}