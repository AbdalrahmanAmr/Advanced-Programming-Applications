package dev.mr3.sb.repository;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Slot;
import dev.mr3.sb.model.Weekday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    // Get all slots belonging to a specific doctor
    List<Slot> findByDoctor(Doctor doctor);

    // Get slots for a doctor on a specific day
    List<Slot> findByDoctorAndWeekday(Doctor doctor, Weekday weekday);

    List<Slot> findByDoctorPersonId(Long doctorId);

    List<Slot> findByDoctorPersonIdAndWeekday(Long doctorId, Weekday weekday);

    boolean existsByDoctorPersonIdAndWeekdayAndStartTimeAndEndTime(Long doctorId, Weekday weekday, String startTime, String endTime);
}
