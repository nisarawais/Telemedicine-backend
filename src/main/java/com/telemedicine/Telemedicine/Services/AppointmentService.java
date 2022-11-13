package com.Telemedicine.Telemedicine.Services;

import com.Telemedicine.Telemedicine.Models.Appointment;
import com.Telemedicine.Telemedicine.Models.User;
import com.Telemedicine.Telemedicine.Repositories.AppointmentRepository;
import com.Telemedicine.Telemedicine.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAppointments(long patient_id) {
        return appointmentRepository.findAllByPatientId(patient_id);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalStateException(
                        "appointment with id " + appointmentId + "does not exist"
                ));
    }

    public void addNewAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        boolean exists = appointmentRepository.existsById(appointmentId);
        if (!exists) {
            throw new IllegalStateException("appointment with id " + appointmentId + "does not exist");
        }
        appointmentRepository.deleteById(appointmentId);
    }

    @Transactional
    public Appointment updateAppointment(Long appointmentId, Appointment newAppointment) {
        return appointmentRepository.findById(appointmentId)
                .map(appointment -> {
                    appointment.setName(newAppointment.getName());
                    appointment.setDate(newAppointment.getDate());
                    appointment.setTime(newAppointment.getTime());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new IllegalStateException(
                        "appointment with id " + appointmentId + "does not exist"
                ));
    }
}
