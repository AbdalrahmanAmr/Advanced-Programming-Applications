package dev.mr3.sb.service;

import dev.mr3.sb.dto.SlotsDto;
import dev.mr3.sb.model.Slot;
import dev.mr3.sb.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

    @Autowired
    private SlotRepository repo;

    public void addSlot(SlotsDto dto) {
        Slot s = new Slot();
        s.setWeekday(dto.getWeekday());
        s.setAvailableCount(dto.getAvailableCount());

        repo.save(s);
    }
}