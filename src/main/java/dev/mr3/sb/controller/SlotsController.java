package com.example.Project.controller;

import com.example.Project.dto.SlotsDto;
import com.example.Project.service.SlotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SlotsController {

    private final SlotService service;

    public SlotsController(SlotService service) {
        this.service = service;
    }

    @GetMapping("/slots")
    public String openPage(Model model) {
        model.addAttribute("slot", new SlotsDto());
        return "ManageSlots";
    }

    @PostMapping("/saveSlot")
    public String saveSlot(@ModelAttribute("slot") SlotsDto dto) {
        service.addSlot(dto);
        return "redirect:/slots";
    }
}