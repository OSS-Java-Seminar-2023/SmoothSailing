package com.SmoothSailing.controllers;

import com.SmoothSailing.dto.CrewDto;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.models.CrewModel;
import com.SmoothSailing.services.CompanyService;
import com.SmoothSailing.services.CrewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/crew")
public class CrewController {
    @Autowired
    private CrewService crewService;
    @Autowired
    private CompanyService companyService;

    @GetMapping(path="/list/{position}")
    public String members(@PathVariable("position") String position, Model model){
        List<CrewModel> members = crewService.getMembers(position);
        model.addAttribute("roleRequest", members);
        return "crew/crew_list";
    }

    @GetMapping(path = "/list")
    public String all(Model model){
        List<CrewModel> members = crewService.getAll();
        model.addAttribute("roleRequest", members);
        return "crew/crew_list";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerRequest", new CrewModel());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "crew/register_crew";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute CrewDto crewDto){
        CrewModel crewModel = crewService.register(crewDto);
        return crewModel == null ? "error_page" : "redirect:/crew/list/" + crewModel.getPosition().toLowerCase();
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable("id") String id, Model model){
        Optional<CrewModel> member = crewService.byId(id);
        member.ifPresent(crewModel -> model.addAttribute("editRequest", crewModel));
        model.addAttribute("companies", companyService.getAllCompanies());
        return "crew/edit_crew";
    }

    @PostMapping("/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute CrewDto crewDto){
        crewService.edit(id, crewDto);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        crewService.deleteById(id);
        return "redirect:/";
    }

}