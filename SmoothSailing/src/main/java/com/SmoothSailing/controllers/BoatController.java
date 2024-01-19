package com.SmoothSailing.controllers;

import com.SmoothSailing.models.BoatModel;
import com.SmoothSailing.models.CompanyModel;
import com.SmoothSailing.repositories.BoatRepo;
import com.SmoothSailing.services.BoatService;
import com.SmoothSailing.services.CompanyService;
import com.SmoothSailing.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
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

    @GetMapping(path="/boat/register")
    public String getRegisterBoat(Model model){
        model.addAttribute("registerBoatRequest", new BoatModel());
        model.addAttribute("companies", companyService.getAllCompanies());
        return "register_boat";
    }

    @PostMapping(path="/boat/register")
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
}