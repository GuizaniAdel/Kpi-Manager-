package com.Springboot.example.web;

import com.Springboot.example.model.Database;
import com.Springboot.example.model.Dimension;
import com.Springboot.example.model.Kpi;
import com.Springboot.example.model.Requete;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.repository.DimensionRepositroy;
import com.Springboot.example.repository.KpiRepository;
import com.Springboot.example.repository.RequeteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RequeteController {
    @Autowired
    private RequeteRepository requeteRepository;
    @Autowired
    private DbRepository dbRepository;
    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private DimensionRepositroy dimensionRepositroy;
    @GetMapping("/tester_requete")
    public String Tester_Requete(Model model, @RequestParam("ajoute") String[ ] countries ){


        return "/Requete/tester_requete";
    }

    @GetMapping("listRequetes")
    public String GetReq(Model model) {
        model.addAttribute("ReqList" ,requeteRepository.findAll());
        return "/Requete/listRequetes";

    }

    @GetMapping("/editrequete/{id}")
    public String editRequete(@PathVariable("id") Long idrequete , Model model ) {
        Requete requete = requeteRepository.findById(idrequete).get();
        model.addAttribute("requete",requete);
        return "/Requete/editrequete";

    }
    @RequestMapping(value="/updatee",method = RequestMethod.POST)
    public String updateRequete(Requete requete ,Model model
            ,@RequestParam("ajout") String[ ] valdim ,@RequestParam("ajoutt") String[ ] valdimalias,
    @RequestParam(value = "ids" , defaultValue = "") long[] ids) {

        System.out.println(requete);

        Requete r = requeteRepository.findById(requete.getId()).get();
        int j = 0 ;
        for(int i = 0; i < ids.length; i++) {
            Dimension d = dimensionRepositroy.findById(ids[i]).get();
            d.setVal_dim(valdim[i]);
            d.setVal_dim_alias(valdimalias[i]);
            dimensionRepositroy.save(d);
            j++;
            requete.getDims().add(d);
        }
        for (int i = j; i < valdim.length ; i++) {
            Dimension d = new Dimension();
            d.setVal_dim(valdim[i]);
            d.setVal_dim_alias(valdimalias[i]);
            dimensionRepositroy.save(d);
            requete.getDims().add(d);
        }
        r=requete;
        System.out.println(r);
        requeteRepository.save(r);
        return "redirect:/listRequetes";

    }
}
