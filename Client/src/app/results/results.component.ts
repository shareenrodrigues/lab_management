import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../services/appointment.service';
import { AccordionModule } from 'primeng/accordion';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ActivatedRoute } from '@angular/router';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
    selector: 'view-results',
    standalone: true,
    templateUrl: './results.component.html',
    imports: [
        AccordionModule, CommonModule, ButtonModule, ProgressSpinnerModule,
        TableModule],
    styleUrls: ['./results.component.scss']
})
export class ResultComponent implements OnInit {
    appointmentDetails: any[] = [];
    userRole: string | undefined;
    showClientRole: boolean = false;
    loading: boolean = false;

    constructor(private appointmentService: AppointmentService,private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.loading = true;
        this.route.paramMap.subscribe(params => {
            this.userRole = params.get('role')?.toString();
        });
        if (this.userRole === 'Client') {
            this.showClientRole = true;
        }
        if (this.showClientRole) {
            const userId = JSON.parse(localStorage.getItem('user') || '{}').userId || 0;
            this.appointmentService.getAppointmentsWithTestsAndResults(userId).subscribe({
                next: (data) => {
                    this.appointmentDetails = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.error('Error fetching results:', err);
                    this.loading = false;
                }
            });
        }
        else {
            this.appointmentService.getAllAppointmentsWithTestsAndResults().subscribe({
                next: (data) => {
                    this.appointmentDetails = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.error('Error fetching results:', err);
                    this.loading = false;
                }
            });
        }
        
    }
}
