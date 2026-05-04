package com.example.Project.service;

import com.example.Project.dto.SlotsDto;
import com.example.Project.model.Slots;
import com.example.Project.repository.SlotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

    @Autowired
    private SlotsRepository repo;

    public void addSlot(SlotsDto dto) {
        Slots s = new Slots();
        s.setWeekday(dto.getWeekday());
        s.setAvailableCount(dto.getAvailableCount());

        repo.save(s);
    }
}