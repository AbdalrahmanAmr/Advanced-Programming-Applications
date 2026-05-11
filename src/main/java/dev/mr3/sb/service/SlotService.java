package dev.mr3.sb.service;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Slot;
import dev.mr3.sb.model.Weekday;
import dev.mr3.sb.repository.DoctorRepository;
import dev.mr3.sb.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;

    public SlotService(SlotRepository slotRepository, DoctorRepository doctorRepository) {
        this.slotRepository = slotRepository;
        this.doctorRepository = doctorRepository;
    }

    public Slot createSlot(Slot slot, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            slot.setDoctor(doctor);
            return slotRepository.save(slot);
        }
        return null;
    }

    public List<Slot> getSlotsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            return slotRepository.findByDoctor(doctor);
        }
        return List.of();
    }

    public List<Slot> getSlotsByDoctorAndWeekday(Long doctorId, Weekday weekday) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            return slotRepository.findByDoctorAndWeekday(doctor, weekday);
        }
        return List.of();
    }

    public void deleteSlot(Long slotId) {
        slotRepository.deleteById(slotId);
    }
}
