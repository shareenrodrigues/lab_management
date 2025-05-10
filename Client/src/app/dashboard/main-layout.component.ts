import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { MenubarModule } from 'primeng/menubar';
import { MessageService } from 'primeng/api';
import { MenuItem } from 'primeng/api';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';

@Component({
    standalone: true,
    selector: 'app-main-layout',
    templateUrl: './main-layout.component.html',
    styleUrls: ['./dashboard.component.scss'],
    imports: [CommonModule, RouterOutlet, ToastModule, MenubarModule, ButtonModule],
    providers: [MessageService]
})
export class MainLayoutComponent {
    items: MenuItem[] = [];
    userRole: string = '';
    constructor(private router: Router) {

    }

    ngOnInit(): void {
        this.userRole = this.getLoggedInUserRole();
        if(this.userRole === 'Staff')
        {
            this.items = [
                { label: 'Home', icon: 'pi pi-home', routerLink: ['/dashboard',this.userRole] },
                { label: 'Manage Appointments', icon: 'pi pi-calendar-plus', routerLink: ['/appointments', this.userRole] },
               { label: 'View Results', icon: 'pi pi-file', routerLink: ['/results', this.userRole] },
            ];
        }
        else{
            this.items = [
                { label: 'Home', icon: 'pi pi-home', routerLink: ['/dashboard', this.userRole] },
                { label: 'View Tests', icon: 'pi pi-list', routerLink: ['/tests', this.userRole ]},
                { label: 'My Appointments', icon: 'pi pi-calendar-plus', routerLink: ['/appointments', this.userRole] },
               { label: 'View Results', icon: 'pi pi-file', routerLink: ['/results', this.userRole] },
            ];
        }
        
    }

    logout() {
        localStorage.removeItem('user');
        window.location.href = '/';
    }

    getLoggedInUserRole(): string {
        const userJson = localStorage.getItem('user');
        if (!userJson) return '';
        const user = JSON.parse(userJson);
        return user.roleName || '';
    }
}
