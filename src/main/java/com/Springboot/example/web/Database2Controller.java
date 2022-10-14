package com.Springboot.example.web;

import com.Springboot.example.model.*;
import com.Springboot.example.repository.*;
import com.Springboot.example.service.Database1Service;

import com.Springboot.example.service.Database1ServiceImp;
import com.Springboot.example.service.Database2Service;
import com.Springboot.example.service.VueDetailSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@EnableScheduling
public class Database2Controller {

//	@Autowired
//	private RslRepository database2repository;
	@Autowired
	private Database2Repository database2repository;
	@Autowired
	private KpiRepository kpirepository;
//	@Autowired
//	Database1Service database2Service;
	@Autowired
	Database2Service database2Service;
	@Autowired
	public VueDetailSevice vueDetailSevice;

	@Autowired
	private DbRepository dbrepository;
	@Autowired
	private RequeteRepository requeteRepository;
//	@Autowired
//	private RslRepository rslrepository;
//	@Autowired
//	private Database1Repository database2repo;
	@Autowired
	private Database2Repository database2repo;

	@GetMapping("resultat100")
	public String GetScript100(Model model) throws Exception {

		// System.out.println(database2Service.getCompKpi());
		model.addAttribute("ReqList", requeteRepository.findAll());
		model.addAttribute("databaseList", dbrepository.findAll());
		model.addAttribute("KpisList", kpirepository.findAll());
		return "resultat100";

	}

	@PostMapping("resultat100")
	public String GetScript200(Model model) throws Exception {

		// System.out.println(database2Service.getCompKpi());
		model.addAttribute("ReqList", requeteRepository.findAll());
		model.addAttribute("databaseList", dbrepository.findAll());
		model.addAttribute("KpisList", kpirepository.findAll());
		return "resultat100";

	}

	@RequestMapping("deleteAll100")
	public String GetScript300(Model model) throws Exception {
		database2repo.deleteAll();
		model.addAttribute("msg", "All records are deleted successfully...");
		model.addAttribute("ReqList", requeteRepository.findAll());
		model.addAttribute("databaseList", dbrepository.findAll());
		model.addAttribute("KpisList", kpirepository.findAll());
		return "resultat100";

	}

	@RequestMapping(value = "/resultat200", method = RequestMethod.GET)
	public String GetRes10(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
			@RequestParam("DateFin") String dateFin // , @RequestParam("PlanTask") String PlanTask //here
			, @RequestParam("db1") long db1, @RequestParam("db2") Long db2) throws Exception {
		// LocalDate localDate = LocalDate.now();
		// while (new SimpleDateFormat("yyy-MM-dd").parse(PlanTask).equals(localDate)) ;
		if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
			model.addAttribute("message", "date Deb supérieur à date Fin");
			return "resultat100";
		}
		Database d1 = dbrepository.findDatabaseById(db1);
		Database d2 = dbrepository.findDatabaseById(db2);
		List<Database2> rslt = new ArrayList<Database2>();
		for (String s : kpis) {
			long kpi = Long.parseLong(s);
			Kpi k = kpirepository.findById(kpi).get();

			Requete r1 = new Requete();
			for (Requete r : k.getRequetess()) {
				if (r1 != r) {
					r1 = r;

					if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())
							) {

						if (r.getId_databasee().getSystem().equals("SqlServer")) {

							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

							Connection con = DriverManager.getConnection(
									"jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
											+ r.getId_databasee().getName(),
									r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

							String date = r.getDate();
							String copr = r.getCopr();
							String val_kpi = r.val_kpi;
							String alias_val_kpi = r.val_kpi_alias;

							List<Dimension> dimss = r.getDims();
							String dimension = " ";
							for (Dimension d : dimss) {
								if (d == dimss.get(dimss.size() - 1)) {
									dimension = dimension + d.getVal_dim();
								} else {
									dimension = dimension + d.getVal_dim() + " +'|'+ ";
								}
							}
							System.out.println(dimension);

							System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ,"
									+ dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr
									+ " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by "
									+ date + " ," + dimension);
							System.out.println("no1");
							PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
									+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
									+ alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
									+ dateFin + "' group by " + date + " ," + dimension);
							System.out.println("no2");
							ResultSet rs = ps.executeQuery();

							while (rs.next()) {
								Database2 blog = new Database2();
								blog.setDate(rs.getString("date"));
								blog.setIdKpi(rs.getFloat("Code_requete"));
								blog.setVal_dim(rs.getString("val_dim"));
								blog.setVal_kpi(rs.getFloat(alias_val_kpi));
								blog.setSysteme("SqlServer");
								rslt.add(blog);
							}

							con.close();
						} else if (r.getId_databasee().getSystem().equals("Postgres")) {

							try {
								Connection conn = null;
								Class.forName("org.postgresql.Driver");
								conn = DriverManager.getConnection(
										"jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
												+ r.getId_databasee().getName(),
										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

								String date = r.getDate();
								String copr = r.getCopr();
								String val_kpi = r.val_kpi;
								String alias_val_kpi = r.val_kpi_alias;
								String name_db = r.getId_databasee().getName();
								List<Dimension> dimss = r.getDims();
								String dimension = " ";
								for (Dimension d : dimss) {
									if (d == dimss.get(dimss.size() - 1)) {
										dimension = dimension + d.getVal_dim();
									} else {
										dimension = dimension + d.getVal_dim() + " ||'|'|| ";
									}
								}
								System.out.println("no4");
								System.out.println(dimension);
								System.out.println("Select " + date + " as date, " + k.getId_kpi()
										+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
										+ alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb
										+ "' and " + dateFin + " group by " + date + " ," + dimension);

								PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, "
										+ k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi
										+ " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb
										+ "' and '" + dateFin + "' group by " + date + " ," + dimension);
								ResultSet rs = ps.executeQuery();
								System.out.println("no3");
								while (rs.next()) {
									Database2 blog = new Database2();
									blog.setDate(rs.getString("date"));
									blog.setIdKpi(rs.getFloat("Code_requete"));
									blog.setVal_dim(rs.getString("val_dim"));
									blog.setVal_kpi(rs.getFloat(alias_val_kpi));
									System.out.println("dddddddd" + name_db);
									blog.setDatabase_name(name_db);
									blog.setSysteme(name_db);
							
									blog.setDbname(name_db);
									rslt.add(blog);
								
					
								}
								conn.close();
							} catch (Exception e) {
								System.out.println(
										"Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
							}
						} else if (r.getId_databasee().getSystem().equals("Oracle"))
							try {

								Class.forName("oracle.jdbc.OracleDriver");
								String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
										+ r.getId_databasee().getName() + r.getId_databasee().getUsername()
										+ r.getId_databasee().getPassword();
								System.out.println();
								Connection con = DriverManager.getConnection(
										"jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
												+ r.getId_databasee().getName(),
										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

							} catch (Exception e) {
								System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
							}
						else {

							System.out.println("no connexions");
						}

					}
				}
			}
		}
		System.out.println(rslt.size());
		for (Database2 rs : rslt) {
		
			Database2 r = new Database2();
			
			r.setDate(rs.getDate());
			r.setIdKpi(rs.getIdKpi());
			r.setVal_dim(rs.getVal_dim());
			r.setVal_kpi(rs.getVal_kpi());
			r.setSysteme(rs.getSysteme());
			r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
			r.setDatabase_name(rs.getDatabase_name());
		
		
//			database2Service.InsertOrUpdate(r);

		}
		
		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
		for (String s : kpis) {
			long kpi = Long.parseLong(s);
			Kpi k = kpirepository.findById(kpi).get();

			System.out.println("voilaaa");
			Connection conn = null;
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
//			PreparedStatement ps00 = conn.prepareStatement("delete from database2 where id_kpi= " + k.id_kpi + " and date between'"  + dateDeb +"' and '" + dateFin + "'");
//			int rs0 = ps00.executeUpdate();	

			PreparedStatement ps4 = conn.prepareStatement("select T2.date,sum(T2.nbre) as load2 from\n"
					+ "(select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d2.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim\n" + "except all\n"
					+ "select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d1.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim) as T2 \n"
					+ "group by T2.date \n" + "order by T2.date ");

	

			PreparedStatement ps = conn.prepareStatement(
					"select DISTINCT b.idkpi Code_requete ,b.valeur_dim as dim, b.date as Date,b.val_kpi as val4 , \r\n"
					+"  k.name_kpi as name from database2 b ,kpi k \r\n"
					+"  where a.idkpi = k.id_kpi \r\n"
							

			);

			ResultSet rs = ps.executeQuery();

			PreparedStatement ps1 = conn.prepareStatement(
					"select  valeur_dim as mix from database2 where valeur_dim = (select max(valeur_dim) from database2)");
			ResultSet rs1 = ps1.executeQuery();

			long startTime = System.currentTimeMillis();



			System.out.println("bf");

			while (rs.next()) {
				System.out.println("pap");

				Vue_Globale blog = new Vue_Globale();
				blog.setDate(rs.getString("Date"));
				blog.setCode_requete(rs.getLong("Code_requete"));

				blog.setVal_kpi2(rs.getLong("val4"));
				blog.setName_kpi(rs.getString("name"));
 
				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				blog.dateExec = (dfff.format(dateobj));
				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);



				rslt2.add(blog);

			}

		}
		for (Database2 rs : rslt) {
			
			Database2 r = new Database2();
			
			r.setDate(rs.getDate());
			r.setIdKpi(rs.getIdKpi());
			r.setVal_dim(rs.getVal_dim());
			r.setVal_kpi(rs.getVal_kpi());
			r.setSysteme(rs.getSysteme());
			r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
			r.setDatabase_name(rs.getDatabase_name());
		
		
//			database2Service.InsertOrUpdate(r);

		}
		
		List<Vue_Globale> rslt21 = new ArrayList<Vue_Globale>();
		for (String s : kpis) {
			long kpi = Long.parseLong(s);
			Kpi k = kpirepository.findById(kpi).get();

			System.out.println("voilaaa");
			Connection conn = null;
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
		

			PreparedStatement ps4 = conn.prepareStatement("select T2.date,sum(T2.nbre) as load2 from\n"
					+ "(select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d2.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim\n" + "except all\n"
					+ "select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d1.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim) as T2 \n"
					+ "group by T2.date \n" + "order by T2.date ");

	

			PreparedStatement ps = conn.prepareStatement(
					"select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as val4 , \r\n"
					+"  k.name_kpi as name, b.system as system from database2 a ,database2 b ,kpi k \r\n"
					+"  where a.idkpi = k.id_kpi \r\n"
							

			);

			ResultSet rs = ps.executeQuery();

			PreparedStatement ps1 = conn.prepareStatement(
					"select  valeur_dim as mix from database2 where valeur_dim = (select max(valeur_dim) from database2)");
			ResultSet rs1 = ps1.executeQuery();

			long startTime = System.currentTimeMillis();



			System.out.println("bf");

			while (rs.next()) {
				System.out.println("pap");

				Vue_Globale blog = new Vue_Globale();
				blog.setDate(rs.getString("Date"));
				blog.setCode_requete(rs.getLong("Code_requete"));

				blog.setVal_kpi2(rs.getLong("val4"));
				blog.setName_kpi(rs.getString("name"));
				blog.setLoad2(rs.getString("system"));
				System.out.println(rs.getString("system"));
				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				blog.dateExec = (dfff.format(dateobj));
				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);



				rslt21.add(blog);

			}

		}
//		for (Vue_Globale r : rslt21) {
//			database2repository.save(r);
//		}
		model.addAttribute("rslt", rslt21);

		return "resultat200";

	}





}
