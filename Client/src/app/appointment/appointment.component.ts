import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../services/appointment.service';
import { AccordionModule } from 'primeng/accordion';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ActivatedRoute } from '@angular/router';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { FormsModule } from '@angular/forms';
import { ProgressSpinnerModule } from 'primeng/progressspinner';


@Component({
    selector: 'view-appointments',
    standalone: true,
    templateUrl: './appointment.component.html',
    imports: [
        AccordionModule, CommonModule, ButtonModule, ProgressSpinnerModule,
        TableModule, CalendarModule, DialogModule, ToastModule, FormsModule],
    styleUrls: ['./appointment.component.scss']
})
export class AppointmentsComponent implements OnInit {

    appointments: any[] = [];
    userRole: string | undefined;
    showClientRole: boolean = false;
    rescheduleDialogVisible = false;
    loading: boolean = false;

    rescheduleForm = {
        appointmentId: 0,
        appointmentDate: null as Date | null,
        appointmentTime: null as Date | null
    };
    minDate: Date = new Date();

    constructor(private appointmentService: AppointmentService, private messageService: MessageService, 
        private route: ActivatedRoute) {

    }

    ngOnInit(): void {
        this.loading = true;
        this.route.paramMap.subscribe(params => {
            this.userRole = params.get('role')?.toString();
        });
        if (this.userRole === 'Client') {
            this.showClientRole = true;
        }

        if (this.showClientRole) {
            const userId = this.getLoggedInUserId();
            this.appointmentService.getAppointmentsByUser(userId).subscribe({
                next: (data) => {
                    this.appointments = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.error('Error fetching appointments:', err);
                    this.loading = false;
                }
            });
        }
        else {
            this.appointmentService.getAllAppointments().subscribe({
                next: (data) => {
                    this.appointments = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.error('Error fetching appointments:', err);
                    this.loading = false;
                }
            });
        }

    }

    getLoggedInUserId(): number {
        const userJson = localStorage.getItem('user');
        if (!userJson) return 0;
        const user = JSON.parse(userJson);
        return user.userId || user.id || 0;
    }

    cancelAppointment(appointmentId: number): void {
        if (confirm('Are you sure you want to cancel this appointment?')) {
            this.appointmentService.cancelAppointment(appointmentId).subscribe({
                next: () => {
                    // update UI
                    const appt = this.appointments.find(a => a.appointmentId === appointmentId);
                    if (appt) {
                        appt.appointmentStatus = 'CANCELLED';
                        appt.testMappings.forEach((tm: { testStatus: string; }) => tm.testStatus = 'CANCELLED');
                    }

                    // show success
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Cancelled',
                        detail: 'Appointment cancelled successfully'
                    });
                },
                error: (err) => {
                    console.error('Error cancelling appointment:', err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Could not cancel appointment'
                    });
                }
            });
        }
    }

    rejectAppointment(appointmentId: number): void {
        if (confirm('Are you sure you want to reject this appointment?')) {
            this.appointmentService.cancelAppointment(appointmentId).subscribe({
                next: () => {
                    // update UI
                    const appt = this.appointments.find(a => a.appointmentId === appointmentId);
                    if (appt) {
                        appt.appointmentStatus = 'REJECTED';
                        appt.testMappings.forEach((tm: { testStatus: string; }) => tm.testStatus = 'REJECTED');
                    }

                    // show success
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Rejected',
                        detail: 'Appointment rejected successfully'
                    });
                },
                error: (err) => {
                    console.error('Error rejecting appointment:', err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Could not reject appointment'
                    });
                }
            });
        }
    }

    deleteTestFromAppointment(appointmentId: number, testMapping: any): void {
        if (confirm(`Delete test "${testMapping.testName}" from this appointment?`)) {
            this.appointmentService.deleteTestFromAppointment(appointmentId, testMapping.testId).subscribe({
                next: () => {
                    const appt = this.appointments.find(a => a.appointmentId === appointmentId);
                    if (appt) {
                        appt.testMappings = appt.testMappings.filter((t: { testId: any; }) => t.testId !== testMapping.testId);
                    }

                    this.messageService.add({
                        severity: 'success',
                        summary: 'Test deleted',
                        detail: `"${testMapping.testName}" was removed.`
                    });
                },
                error: (err) => {
                    console.error('Failed to delete test:', err);
                    this.messageService.add({
                        severity: 'error',
                        summary: 'Error',
                        detail: 'Could not delete the test'
                    });
                }
            });
        }
    }

    openRescheduleDialog(appt: any): void {
        this.rescheduleForm = {
            appointmentId: appt.appointmentId,
            appointmentDate: new Date(appt.appointmentDate),
            appointmentTime: new Date(`1970-01-01T${appt.appointmentTime}`)
        };
        this.rescheduleDialogVisible = true;
    }

    submitReschedule(): void {
        const payload = {
            appointmentDate: this.rescheduleForm.appointmentDate
            ? this.rescheduleForm.appointmentDate.toISOString().split('T')[0]
            : null,
            appointmentTime:this.rescheduleForm.appointmentTime
            ? this.rescheduleForm.appointmentTime.toTimeString().split(' ')[0]
            : null
        };

        if (!payload.appointmentDate || !payload.appointmentTime) {
            this.messageService.add({ severity: 'warn', summary: 'Missing fields', detail: 'Select both date and time.' });
            return;
          }

        this.appointmentService.rescheduleAppointment(this.rescheduleForm.appointmentId, payload).subscribe({
            next: () => {
                const updated = this.appointments.find(a => a.appointmentId === this.rescheduleForm.appointmentId);
                if (updated) {
                    updated.appointmentDate = payload.appointmentDate;
                    updated.appointmentTime = payload.appointmentTime;
                    updated.appointmentStatus = 'RESCHEDULED';
                }

                this.messageService.add({ severity: 'success', summary: 'Rescheduled', detail: 'Appointment updated' });
                this.rescheduleDialogVisible = false;
            },
            error: (err) => {
                this.messageService.add({ severity: 'error', summary: 'Failed', detail: 'Could not reschedule' });
            }
        });
    }
}
