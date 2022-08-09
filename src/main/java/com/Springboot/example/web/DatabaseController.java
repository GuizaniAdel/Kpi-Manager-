package com.Springboot.example.web;

import com.Springboot.example.model.Database;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@Controller
public class DatabaseController {
    @Autowired
    DbService dbService;
    @Autowired
    DbRepository dbRepository;

    @GetMapping("/manage_connexion")
    public String reg(Model model) {
        model.addAttribute("database", new Database());
        return "/Database/manageConnexion";
    }
    @PostMapping("/manage_connexion")
    public String createOrUpdateDb(Model model,@ModelAttribute("database") Database empDTO)  {
       if (dbRepository.findDatabaseByName(empDTO.getName())!=null){
            model.addAttribute("message", "Database Exist");
            return "Database/manageConnexion";
        }
        else {
       String verify= dbService.createOrUpdateDb(empDTO);
        if( verify.contains("Failed to create JDBC db connection :")){
            model.addAttribute("message", verify);
            return "Database/manageConnexion";
        }
        else {
            return "redirect:/list";
    }
    }}
    @GetMapping("/list")
    public String getAllDb(Model model){
        model.addAttribute("empList", dbService.getAllDb());


        return "/Database/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteDatabase(@PathVariable("id") String id) {
        dbService.deleteDatabase(Long.parseLong(id));
        return "redirect:/list";
    }

    @GetMapping("/verifyConnexion/{id}")
    public String VerifyExistenceDatabase(@PathVariable("id") String id,Model model) {
       Database db = dbRepository.findDatabaseById(Long.parseLong(id));
       model.addAttribute("message",dbService.VerifyConnexion(db));
        model.addAttribute("empList", dbService.getAllDb());
        return "/Database/list";
    }
}
