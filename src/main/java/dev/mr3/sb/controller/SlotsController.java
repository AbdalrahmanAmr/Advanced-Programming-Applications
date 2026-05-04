package dev.mr3.sb.controller;

import dev.mr3.sb.dto.SlotsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SlotsController {

    private final dev.mr3.sb.service.SlotService service;

    public SlotsController(dev.mr3.sb.service.SlotService service) {
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