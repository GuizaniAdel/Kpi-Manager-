package com.Springboot.example.web;

import com.Springboot.example.model.*;
import com.Springboot.example.repository.DbRepository;
import com.Springboot.example.repository.KpiRepository;
import com.Springboot.example.repository.RequeteRepository;
import com.Springboot.example.service.DbService;
import com.Springboot.example.service.KpiService;
import com.Springboot.example.service.RequeteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class KpiController {

    @Autowired
    KpiService servicekpi;
    @Autowired
    DbService dbservice;
    @Autowired
    RequeteService requeteService;
    @Autowired
    KpiRepository kpirepository ;
    @Autowired
    DbRepository dbrepository;
    @Autowired
    RequeteRepository requeteRepository;

    @GetMapping("/manage_kpi")
    public String manage_kpi(Model model) {
        model.addAttribute("oracleList", dbservice.getOracleDb());
        model.addAttribute("postgresList", dbservice.getPostgresDb());
        model.addAttribute("SqlServerList", dbservice.getSqlServerDb());
        model.addAttribute("kpi", new Kpi());
        Requete rq = new Requete();
        Requete rq1 = new Requete();
        model.addAttribute("requete", rq);
        model.addAttribute("a", rq1);
        return "/Kpi/manage_kpi";
    }

    @PostMapping("/Kpi/manage_kpi")
    public String Insert_rslt(Model model,@RequestParam("ajout") String[ ] valdim ,@RequestParam("ajoutt") String[ ] valdimalias
            ,@RequestParam("ajoute") String[ ] valdim1 ,@RequestParam("ajoutte") String[ ] valdimalias1
            ,@RequestParam("Control")Long id ,@RequestParam("Controll") Long id2
            ,@ModelAttribute("kpi")Kpi kpi,@ModelAttribute("requete")Requete r,
             @RequestParam("valKPI2")String val_kpi,@RequestParam("date2")String datee
            ,@RequestParam("valKPIAs2")String aliasvalkpi2
            ,@RequestParam("copr2")String copr) {

        Requete req = new Requete();
        Database db=dbrepository.findById(id).get();
        Database db2 = dbrepository.findById(id2).get();

        r.setId_databasee(db);
        req.setId_databasee(db2);
        req.setDate(datee);
        req.setVal_kpi(val_kpi);
        req.setVal_kpi_alias(aliasvalkpi2);
        req.setCopr(copr);
        List<Dimension> dims = new ArrayList<Dimension>();
        List<Dimension> dims2 = new ArrayList<Dimension>();
        for(int i = 0; i < valdim.length; i++) {
            Requete rr = new Requete();
            Requete r1 = new Requete();
            rr.setId_databasee(db2);
            rr.setDate(datee);
            rr.setVal_kpi(val_kpi);
            rr.setVal_kpi_alias(aliasvalkpi2);
            rr.setCopr(copr);

            r1.setId_databasee(db);
            r1.setDate(r.getDate());
            r1.setVal_kpi(r.getVal_kpi());
            r1.setVal_kpi_alias(r.getVal_kpi_alias());
            r1.setCopr(r.getCopr());

            Dimension d = new Dimension();
            Dimension d1 = new Dimension();
            Kpi k = new Kpi();
            d.setVal_dim(valdim[i]);
            d.setVal_dim_alias(valdimalias[i]);
            d1.setVal_dim(valdim1[i]);
            d1.setVal_dim_alias(valdimalias1[i]);

            k.setId_kpi((long)  Integer.valueOf(String.valueOf(kpi.getId_kpi()) + String.valueOf(i)));
            k.setName_kpi(kpi.getName_kpi() +" par "+ valdimalias[i] );
            k.setSeuil(kpi.getSeuil());
            k.setSubject_area(kpi.getSubject_area());
            k.setType("Child");
            k.setSeuil_gap(kpi.getSeuil_gap());
            k.setSeuil_dataQuality(kpi.getSeuil_dataQuality());
            rr.setKpi(k);
            r1.setKpi(k);
            r1.setCode_requete(k.getId_kpi().intValue());
            rr.setCode_requete(k.getId_kpi().intValue());

            dims.add(d);
            dims2.add(d1);
            r1.getDims().add(d);
            rr.getDims().add(d1);
            k.getRequetess().add(rr);
            k.getRequetess().add(r1);
            kpirepository.save(k);
            System.out.println(rr);

        }
        r.setCode_requete(kpi.id_kpi.intValue());
        r.setKpi(kpi);
        r.setDims(dims);
        req.setKpi(kpi);
        req.setCode_requete(kpi.id_kpi.intValue());
        req.setDims(dims2);
        kpi.getRequetess().add(r);
        kpi.getRequetess().add(req);


        kpirepository.save(kpi);
        return "redirect:/listKpis";
    }


    @RequestMapping(value="/editkpi/{kpiId}",method = RequestMethod.GET)
    public String editKpi(@PathVariable("kpiId") Long idkpi , Model model ) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        model.addAttribute("kpi",kpi);
        return "/Kpi/editkpi";

    }
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String update(Kpi kpi ,Model model ) {
        kpirepository.save(kpi);
        return "redirect:/listKpis";

    }
    @RequestMapping(value="/addRequete/{kpiId}",method = RequestMethod.GET)
    public String affecterRequeteKpi(@PathVariable("kpiId") Long idkpi , Model model ) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        model.addAttribute("databaseList",dbrepository.findAll());
        model.addAttribute("kpi",kpi);
        model.addAttribute("idkpi",kpi.getId_kpi());
        model.addAttribute("requete",new Requete());
        return "/Kpi/addRequeteKpi";

    }
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public String affecterRequete(@RequestParam("id_kpi")Long idkpi , Model model
            ,@RequestParam("ajout") String[ ] valdim ,@RequestParam("ajoutt") String[ ] valdimalias
            ,@RequestParam("Control")Long idDatabase,@RequestParam("valKPI")String val_kpi,@RequestParam("date")String datee
            ,@RequestParam("valKPIAs")String aliasvalkpi
            ,@RequestParam("copr")String copr ,@ModelAttribute("requete")Requete requete) {
        Kpi kpi = kpirepository.findById(idkpi).get();
        Database db=dbrepository.findById(idDatabase).get();

        Requete r = new Requete();
        requete.setCode_requete(kpi.getId_kpi().intValue());
        r.setDate(datee);
        r.setVal_kpi(val_kpi);
        r.setVal_kpi_alias(aliasvalkpi);
        r.setCopr(copr);
        requete.setId_databasee(db);
        List<Dimension> dims = new ArrayList<Dimension>();
        for(int j = 0; j < valdim.length; j++) {
            Dimension d = new Dimension();

            d.setVal_dim(valdim[j]);
            d.setVal_dim_alias(valdimalias[j]);
            dims.add(d);
        }
        requete.setDims(dims);
        requete.setKpi(kpi);
        kpi.getRequetess().add(requete);
        System.out.println(kpi);
        kpirepository.save(kpi);

        return "redirect:/listKpis";

    }

    @GetMapping("/listKpis")
    public String getAllKpis(Model model){
            model.addAttribute("listKpis", kpirepository.findAll());


        return "/Kpi/listkpis";
    }
    @PostMapping("/testerRequete")
    public String TesterRequete(Model model, @ModelAttribute("requete")Requete r,
      @ModelAttribute("Control")Long id, @ModelAttribute("kpi")Kpi k
       , @ModelAttribute("dateDeb")String dateDeb, @ModelAttribute("dateFin")String dateFin
    , WebRequest request,@RequestParam("ajout") String[ ] valdim,@RequestParam("ajoutt") String[ ] valdimalias) throws ClassNotFoundException {
        System.out.println(id);

        Database dbb=dbrepository.findById(id).get();
        r.setId_databasee(dbb);
        System.out.println(r);
        List<Dimension> dims = new ArrayList<Dimension>();
        for(int i = 0; i < valdim.length; i++) {
            Dimension d = new Dimension();
            d.setVal_dim_alias(valdim[i]);
            d.setVal_dim_alias(valdimalias[i]);
            dims.add(d);
        }
        r.setDims(dims);
       String message = requeteService.TesterRequete(dbb,r,k,dateDeb,dateFin,valdim,valdimalias);
               model.addAttribute("message",message);
                request.setAttribute("connected", true, WebRequest.SCOPE_SESSION);

                model.addAttribute("oracleList", dbservice.getOracleDb());
                model.addAttribute("postgresList", dbservice.getPostgresDb());
                model.addAttribute("SqlServerList", dbservice.getSqlServerDb());
                return "/Kpi/manage_kpi";


    }

    @PostMapping("/testerRq")
    public String TesterRq(@RequestParam("id_kpi")Long idkpi , Model model
            ,@ModelAttribute("ajout") String[] valdim ,@ModelAttribute("ajoutt") String[] valdimalias
            ,@ModelAttribute("Control")Long idDatabase, @ModelAttribute("dateDeb")String dateDeb, @ModelAttribute("dateFin")String dateFin
    ,@ModelAttribute("requete") Requete requete) throws ClassNotFoundException {
        Kpi kpi = kpirepository.findById(idkpi).get();
        Database db=dbrepository.findById(idDatabase).get();
        requete.setCode_requete(kpi.getId_kpi().intValue());

        requete.setId_databasee(db);
                List<Dimension> dims = new ArrayList<Dimension>();
        for(int j = 0; j < valdim.length; j++) {
            Dimension d = new Dimension();

            d.setVal_dim(valdim[j]);
            d.setVal_dim_alias(valdimalias[j]);
            dims.add(d);
        }
        requete.setDims(dims);
        requete.setKpi(kpi);
        kpi.getRequetess().add(requete);
        System.out.println(requete);

        String message = requeteService.TesterRequete(db,requete,kpi,dateDeb,dateFin,valdim,valdimalias);
        model.addAttribute("message",message);
        model.addAttribute("databaseList",dbrepository.findAll());
        model.addAttribute("kpi",kpi);
        model.addAttribute("idkpi",kpi.getId_kpi());
        return "/Kpi/addRequeteKpi";


    }
}
