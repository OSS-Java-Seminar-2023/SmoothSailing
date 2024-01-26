package com.SmoothSailing.controllers;

import com.SmoothSailing.dto.BoatRegisterDto;
import com.SmoothSailing.dto.UserRegisterDto;
import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.models.UserModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.services.BoatService;
import com.SmoothSailing.services.CompanyService;
import com.SmoothSailing.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/boat")
public class BoatController {
    @Autowired
    private final BoatRepo boatRepo;
    @Autowired
    private BoatService boatService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    public BoatController(BoatRepo boatRepo){
        this.boatRepo=boatRepo;
    }

    @GetMapping(path="/register")
    public String getRegisterBoat(@CookieValue(name = "company_id", required = false) String id, Model model){
        if(id == null || id.isEmpty()){
            return "company/login_page";
        }
        model.addAttribute("registerBoatRequest", new BoatModel());
        model.addAttribute("companies", companyService.getCompanyById(id));
        return "register_boat";
    }

    @PostMapping(path="/register")
    public String registerBoat(@ModelAttribute BoatModel boatModel, @RequestParam("image") MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        boatModel.setImg(fileName);

        BoatModel registeredBoat= boatService.registerBoat(boatModel);

        String uploadDir = "/images/";

        try {
            fileUploadService.saveFile(uploadDir, fileName, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return registeredBoat == null ? "error_page" : "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model){
        Optional<BoatModel> boat = boatService.getBoatById(id);
        boat.ifPresent(boatModel -> model.addAttribute("editBoat", boatModel));
        return "edit_boat";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, @ModelAttribute BoatRegisterDto boatRegisterDto){
        boatService.editBoat(id, boatRegisterDto);
            return "redirect:/company/boat-list?page=0";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        boatService.delete(id);
        return "redirect:/company/boat-list?page=0";
    }

    @GetMapping("/list")
    public String list(@RequestParam Map<String, String> allParams, Model model){
        model.addAttribute("boats", boatService.getAll(Integer.parseInt(allParams.get("page"))));
        model.addAttribute("admin", true);
        model.addAttribute("prev", Integer.parseInt(allParams.get("page")) - 1);
        model.addAttribute("next", Integer.parseInt(allParams.get("page")) + 1);
        return "boat_list";
    }
}