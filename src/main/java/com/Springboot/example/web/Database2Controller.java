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
		
		
			database2Service.InsertOrUpdate(r);

		}
		
		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
		for (String s : kpis) {
			long kpi = Long.parseLong(s);
			Kpi k = kpirepository.findById(kpi).get();

			System.out.println("voilaaa");
			Connection conn = null;
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
			// requete pour afficher idkpi valkpi1,valkpi2,date,NbreRecord 1 , Nbre Record 2
//            PreparedStatement ps = conn.prepareStatement("select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3 ,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n" +
//                    "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n" +
//                    " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from \n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    " select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T3\n" +
//                    "  FULL OUTER JOIN\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from\n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    
//                    " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T4\n" +
//                    " ON   T3.date = T4.date) as RR1 \n" +
//                    "right OUTER JOIN\n" +
//                    " (select sum((	T1.val_kpi)/2) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n" + // Here
//                    ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n" +
//                    ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" +  //here  (- to +)
//                    ",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n" +
//                    " from " +
//                    " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "'  and  a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
//                    " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "' and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
//                    " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n" +
//                    " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n" +
//                    " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n" +
//                    "order by COALESCE(T1.date, B2.date)) as RR2\n" +
//                    " \n" +
//                    " on RR1.date=RR2.date\n" +
//                    " Left outer join\n" +
//                    " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n" +
//                    "(select  a.idkpi,a.date,  COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n" +
//                    "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R1\n" +
//                    "full OUTER join  \n" +
//                    " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n" +
//                    "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R2 \n" +
//                    " on R1.date=R2.date\n" +
//                    " order by R1.date) as RR3 \n" +
//                    " on RR2.date = RR3.date	" 
//                   );

			// requete pour afficher Nbre d'enregistrement existe dans db2 et NON dans db1

			PreparedStatement ps4 = conn.prepareStatement("select T2.date,sum(T2.nbre) as load2 from\n"
					+ "(select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d2.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim\n" + "except all\n"
					+ "select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n" + "where a.idkpi="
					+ k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system='"
					+ d1.getSystem() + "' \n" + "group by a.idkpi,a.date,a.system,a.valeur_dim) as T2 \n"
					+ "group by T2.date \n" + "order by T2.date ");

			/*
			 * PreparedStatement ps5 = conn.
			 * prepareStatement("select k.name_kpi as name , a.idkpi as Code_Requete ,a.date as Date,count(distinct(a.val_kpi)) as val2,sum(distinct(a.val_kpi)) as val3\r\n"
			 * + " from database2 as a ,kpi k where k.id_kpi=a.idkpi\r\n " +
			 * "and a.date IN(\r\n " + "select a.date\r\n " + "from database2 as a \r\n "
			 * + "where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
			 * +dateFin+"' \r\n " + "and a.system='"+d1.getSystem()+"'\r\n " +
			 * 
			 * "EXCEPT\r\n" + "select a.date\r\n " + "from database2 as a \r\n " +
			 * "where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
			 * +dateFin+"' \r\n " + "and a.system='"+d2.getSystem()+"')  " +
			 * "and a.idkpi="+k.id_kpi+"\r\n " + "group by a.date,k.name_kpi , a.idkpi");
			 * System.out.println("pfff"); PreparedStatement ps6 = conn.
			 * prepareStatement("select k.name_kpi as name , a.idkpi as Code_Requete ,a.date as Date,count(distinct(a.val_kpi)) as val2,sum(distinct(a.val_kpi)) as val3\r\n"
			 * + " from database2 as a ,kpi k where k.id_kpi=a.idkpi\r\n " +
			 * " and a.date IN(\r\n" + " select a.date\r\n" + " from database2 as a \r\n"
			 * + " where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
			 * +dateFin+"' \r\n" + " and a.system='"+d2.getSystem()+"'\r\n" +
			 * 
			 * " EXCEPT\r\n" + " select a.date\r\n" + " from database2 as a \r\n" +
			 * " where a.idkpi="+k.id_kpi+" and a.date between '"+dateDeb+"' and '"
			 * +dateFin+"' \r\n" + " and a.system='"+d1.getSystem()+"')\r\n" +
			 * " and a.idkpi="+k.id_kpi+"\r\n" + " group by a.date,k.name_kpi , a.idkpi");
			 */

			PreparedStatement ps = conn.prepareStatement(
					"select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as val4 , \r\n"
					+"  k.name_kpi as name from database2 a ,database2 b ,kpi k \r\n"
					+"  where a.idkpi = k.id_kpi \r\n"
							

			);

			ResultSet rs = ps.executeQuery();

			PreparedStatement ps1 = conn.prepareStatement(
					"select  valeur_dim as mix from database2 where valeur_dim = (select max(valeur_dim) from database2)");
			ResultSet rs1 = ps1.executeQuery();

			long startTime = System.currentTimeMillis();

			// esultSet rs1 = ps1.executeQuery();

			// ResultSet rs2 = ps2.executeQuery();

			// ResultSet rs3 = ps3.executeQuery();

			ResultSet rs4 = ps4.executeQuery();
			long startTime2 = System.currentTimeMillis();

			// ResultSet rs5 = ps5.executeQuery();
			long startTime3 = System.currentTimeMillis();

			// ResultSet rs6 = ps6.executeQuery();
			System.out.println("bf");

			while (rs.next()) {
				System.out.println("pap");

				Vue_Globale blog = new Vue_Globale();
				blog.setDate(rs.getString("Date"));
				blog.setCode_requete(rs.getLong("Code_requete"));
			//	blog.setVal_kpi1(rs.getLong("val3"));
				blog.setVal_kpi2(rs.getLong("val4"));
				blog.setName_kpi(rs.getString("name"));
//                blog.setLoad1(rs.getString("val1"));
//                blog.setLoad2(rs.getString("val2"));

				// probleme au niveau de nbreRecordOk si on a un seul enregistrement //
				// Heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere

				// blog.setNbreRecordOk(rs.getInt("nbreRecordOK"));
				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				blog.dateExec = (dfff.format(dateobj));
				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);

//                
//                int  j = rs1.getInt("mix");
////                   blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk")+ j);
//				int i = 0;
//				int j = 0;
//
////                if (rs.getInt("gap") > 0) 
////                	i = i+1 ;
////                while (i < j)
//
//				if (rs.getInt("gap") > 1500) {
//					i = i + 1;
//					blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk") + i);
//				} else if (rs.getInt("gap") < 1500)
//					j = j + 1;
//				{
//					blog.setNbreRecordOk(rs.getInt("nbreRecordOk") + j);
//				}
//
//				// blog.setFoundDB1(rs.getInt("load1"));
////                blog.setFoundDB2(rs.getInt("load2"));
//				blog.setGap((long) rs.getInt("gap"));
//				java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
//				BigDecimal bigD = new BigDecimal(
//						(rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//				blog.setGAP_par_100(dff.format(bigD));
//				if (rs.getInt("gap") < 1400) {// (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))) == 0) &&
//												// (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"),
//												// rs.getDouble("val2"))) * 100F == 100.0) {
//					blog.setAcceptation("OK");
//				} else if (rs.getInt("gap") > 1400 && rs.getInt("gap") < 2400) {
//					blog.setAcceptation("OK partiel");
//				}
//
////                } else if (k.getSeuil_dataQuality() < (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F
////                        && k.getSeuil_gap() > (rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))) {
////                    blog.setAcceptation("OK partiel");
//				else {
//					blog.setAcceptation("NotOk"); // This One !
//				}

				rslt2.add(blog);

			}

		}
		/*
		 * while(rs5.next()) { Vue_Globale blog = new Vue_Globale();
		 * blog.setDate(rs5.getString("Date"));
		 * blog.setCode_requete(rs5.getLong("Code_requete")); DateFormat dfff = new
		 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
		 * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
		 * java.text.DecimalFormat("###.###");
		 * 
		 * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime2) /1000F);
		 * blog.setVal_kpi1( rs5.getLong("val3")); blog.setVal_kpi2((long) 0);
		 * blog.setName_kpi(rs5.getString("name"));
		 * blog.setLoad1(rs5.getString("val2")); blog.setLoad2("0");
		 * blog.setNbreRecordOk(0); blog.setNbreRecordNotOk(0);
		 * blog.setDataQualite("0"); blog.setFoundDB2(0);
		 * blog.setGap(rs5.getLong("val3")); java.text.DecimalFormat dff = new
		 * java.text.DecimalFormat("###"); System.out.println(); BigDecimal bigD = new
		 * BigDecimal((Math.abs ( rs5.getDouble("val3")) *100)/ rs5.getDouble("val3"));
		 * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
		 * 
		 * 
		 * } while(rs6.next()) { Vue_Globale blog = new Vue_Globale();
		 * blog.setDate(rs6.getString("Date"));
		 * blog.setCode_requete(rs6.getLong("Code_requete")); DateFormat dfff = new
		 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
		 * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
		 * java.text.DecimalFormat("###.###");
		 * 
		 * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime3) /1000F);
		 * blog.setVal_kpi1( rs6.getLong("val3")); blog.setVal_kpi2((long) 0);
		 * blog.setName_kpi(rs6.getString("name"));
		 * blog.setLoad1(rs6.getString("val2")); blog.setLoad2("0");
		 * blog.setDataQualite("0"); blog.setNbreRecordOk(0);
		 * blog.setNbreRecordNotOk(0); blog.setFoundDB1( rs6.getInt("val2"));
		 * blog.setFoundDB2(0); blog.setGap(rs6.getLong("val3"));
		 * java.text.DecimalFormat dff = new java.text.DecimalFormat("###");
		 * System.out.println(); BigDecimal bigD = new BigDecimal((Math.abs (
		 * rs6.getDouble("val3")) *100)/ rs6.getDouble("val3"));
		 * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
		 * 
		 * 
		 * }
		 */

		for (Vue_Globale r : rslt2) {
			database2repository.save(r);
		}
		model.addAttribute("rslt", rslt2);

		return "resultat200";

	}

// FROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM HERE !!!

//	@RequestMapping(value = "/resultat30", method = RequestMethod.GET)
//	public String GetScript30(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
//			@RequestParam("DateFin") String dateFin, @RequestParam("db1") long db1, @RequestParam("db2") Long db2)
//			throws Exception {
//
//		if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
//				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0) {
//			model.addAttribute("message", "date Deb supérieur à date Fin");
//			return "resultat10";
//		}
//		Database d1 = dbrepository.findDatabaseById(db1);
//		Database d2 = dbrepository.findDatabaseById(db2);
//
//		List<Database1> rslt = new ArrayList<Database1>();
//		for (String s : kpis) {
//			long kpi = Long.parseLong(s);
//			Kpi k = kpirepository.findById(kpi).get();
//
//			Requete r1 = new Requete();
//			for (Requete r : k.getRequetess()) {
//				if (r1 != r) {
//					r1 = r;
//
//					if (dbrepository.findDatabaseById(db2).equals(r.getId_databasee())) { // HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERE
//																							// !
//
//						if (r.getId_databasee().getSystem().equals("SqlServer")) {
//
//							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//							Connection con = DriverManager.getConnection(
//									"jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() + ";databaseName="
//											+ r.getId_databasee().getName(),
//									r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							String date = r.getDate();
//							String copr = r.getCopr();
//							String val_kpi = r.val_kpi;
//							String alias_val_kpi = r.val_kpi_alias;
//
//							List<Dimension> dimss = r.getDims();
//							String dimension = " ";
//							for (Dimension d : dimss) {
//								if (d == dimss.get(dimss.size() - 1)) {
//									dimension = dimension + d.getVal_dim();
//								} else {
//									dimension = dimension + d.getVal_dim() + " +'|'+ ";
//								}
//							}
//							System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ,"
//									+ dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr
//									+ " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by "
//									+ date + " ," + dimension);
//
//							PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi()
//									+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//									+ alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '"
//									+ dateFin + "' group by " + date + " ," + dimension);
//							ResultSet rs = ps.executeQuery();
//
//							while (rs.next()) {
//								Database1 blog = new Database1();
//								blog.setDate(rs.getString("date"));
//								blog.setIdKpi(rs.getFloat("Code_requete"));
//								blog.setVal_dim(rs.getString("val_dim"));
//								blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//								blog.setSysteme("SqlServer");
//								rslt.add(blog);
//							}
//
//							con.close();
//						} else if (r.getId_databasee().getSystem().equals("Postgres")) {
//
//							try {
//								Connection conn = null;
//								Class.forName("org.postgresql.Driver");
//								conn = DriverManager.getConnection(
//										"jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/"
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//								String date = r.getDate();
//								String copr = r.getCopr();
//								String val_kpi = r.val_kpi;
//								String alias_val_kpi = r.val_kpi_alias;
//
//								List<Dimension> dimss = r.getDims();
//								String dimension = " ";
//								for (Dimension d : dimss) {
//									if (d == dimss.get(dimss.size() - 1)) {
//										dimension = dimension + d.getVal_dim();
//									} else {
//										dimension = dimension + d.getVal_dim() + " ||'|'|| ";
//									}
//								}
//								System.out.println("Select " + date + " as date, " + k.getId_kpi()
//										+ " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as "
//										+ alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb
//										+ "' and " + dateFin + " group by " + date + " ," + dimension);
//
//								PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, "
//										+ k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi
//										+ " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb
//										+ "' and '" + dateFin + "' group by " + date + " ," + dimension);
//								ResultSet rs = ps.executeQuery();
//
//								while (rs.next()) {
//									Database1 blog = new Database1();
//									blog.setDate(rs.getString("date"));
//									blog.setIdKpi(rs.getFloat("Code_requete"));
//									blog.setVal_dim(rs.getString("val_dim"));
//									blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//									blog.setSysteme("Postgres");
//									rslt.add(blog);
//								}
//
//								conn.close();
//							} catch (Exception e) {
//								System.out.println(
//										"Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
//							}
//						} else if (r.getId_databasee().getSystem().equals("Oracle"))
//							try {
//
//								Class.forName("oracle.jdbc.OracleDriver");
//								String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//										+ r.getId_databasee().getName() + r.getId_databasee().getUsername()
//										+ r.getId_databasee().getPassword();
//								System.out.println();
//								Connection con = DriverManager.getConnection(
//										"jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/"
//												+ r.getId_databasee().getName(),
//										r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//							} catch (Exception e) {
//								System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
//							}
//						else {
//
//							System.out.println("no connexions");
//						}
//
//					}
//				}
//			}
//		}
//		System.out.println(rslt.size());
//		for (Database1 rs : rslt) {
//
//			Database1 r = new Database1();
//			r.setDate(rs.getDate());
//			r.setIdKpi(rs.getIdKpi());
//			r.setVal_dim(rs.getVal_dim());
//			r.setVal_kpi(rs.getVal_kpi());
//			r.setSysteme(rs.getSysteme());
//			r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
//			database2Service.InsertOrUpdate(r);
//		}
//
//		List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
//		for (String s : kpis) {
//			long kpi = Long.parseLong(s);
//			Kpi k = kpirepository.findById(kpi).get();
//			Connection conn = null;
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
//			PreparedStatement ps = conn.prepareStatement(
//					"select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n"
//							+ "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n"
//							+ " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n"
//							+ " (select T2.date,sum(T2.nbre) as nbre from \n"
//							+ " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n"
//							+ " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "'  and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n"
//							+ " select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n"
//							+ " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '"
//							+ dateFin + "'  and a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" + " group by T2.date\n"
//							+ " order by T2.date) as T3\n" + "  FULL OUTER JOIN\n"
//							+ " (select T2.date,sum(T2.nbre) as nbre from\n"
//							+ " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n"
//							+ " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "'  and a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n"
//							+ " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from database2 a \n"
//							+ " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '"
//							+ dateFin + "'  and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" + " group by T2.date\n"
//							+ " order by T2.date) as T4\n" + " ON   T3.date = T4.date) as RR1 \n" + "right OUTER JOIN\n"
//							+ " (select sum(T1.val_kpi) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n"
//							+ ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n"
//							+ ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" + // heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere
//							",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n" + " from "
//							+ " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n"
//							+ " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin
//							+ "'  and  a.system='" + d2.getSystem() + "'\n"
//							+ " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n"
//							+ " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n"
//							+ " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system='" + d1.getSystem() + "'\n"
//							+ " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n"
//							+ " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n"
//							+ " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n"
//							+ " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n"
//							+ "order by COALESCE(T1.date, B2.date)) as RR2\n" + " \n" + " on RR1.date=RR2.date\n"
//							+ " Left outer join\n"
//							+ " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n"
//							+ "(select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n"
//							+ "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n"
//							+ "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//							+ "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" + " group by a.date , a.idkpi  \n"
//							+ " order by a.date) as R1\n" + "full OUTER join  \n"
//							+ " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n"
//							+ "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n"
//							+ "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
//							+ "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb + "' and '" + dateFin
//							+ "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" + " group by a.date , a.idkpi  \n"
//							+ " order by a.date) as R2 \n" + " on R1.date=R2.date\n" + " order by R1.date) as RR3 \n"
//							+ " on RR2.date = RR3.date	");
//
//			long startTime = System.currentTimeMillis();
//
//			ResultSet rs = ps.executeQuery();
//
//			PreparedStatement ps3 = conn.prepareStatement(
//					"select DISTINCT a.idkpi Code_requete ,a.valeur_dim as dim, a.date as Date,a.val_kpi as val3 , abs(a.val_kpi - b.val_kpi) as gap  , 0 as nbreRecordOK , 0 as nbreRecordNotOk "
//							+ " , b.val_kpi as val4 , k.name_kpi as name from database2 a ,database2 b ,kpi k \r\n"
//							+ "where b.valeur_dim = a.valeur_dim \r\n" + "and b.idkpi = a.idkpi \r\n"
//							+ "and a.idkpi = k.id_kpi and abs(b.val_kpi - a.val_kpi) > k.seuil "
//
//			);
////            PreparedStatement ps50 = conn.prepareStatement("select k.id_kpi as Code_requete, coalesce (sum(T1.val_kpi),0) as val1\n" +
////                    ",coalesce (sum(B2.val_kpi),0) as val2\n" +
////                    ",ABS(coalesce (sum(T1.val_kpi),0)- coalesce (sum(B2.val_kpi),0)) as gap\n" +   //heeeeeeeeeeeere
////                    ",k.name_kpi as name,coalesce(B2.date,T1.date) as Date ,coalesce(T1.valeur_dim,B2.valeur_dim) as dim\n" +
////                    "from\n" +
////                    "(select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
////                    "where idkpi="+k.getCode_requete()+" and a.date like '"+k.getDate()+"' and  a.system='SqlServer'\n" +
////                    "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
////                    "FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
////                    "where idkpi="+k.getCode_requete()+" and a.date like '"+k.getDate()+"'  and a.system='Postgres'\n" +
////                    "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
////                    " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n" +
////                    "left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi " +
////                    "where ABS(coalesce ((T1.val_kpi),0)- coalesce ((B2.val_kpi),0))  > "+kpi.getSeuil()+" \n" +
////                    "group by k.name_kpi, B2.date,B2.valeur_dim,T1.date,k.id_kpi,T1.valeur_dim,B2.valeur_dim\n" +
////                    "\n" +
////                    "order by B2.date");
////            ResultSet rs50 = ps50.executeQuery();
////            while(rs.next()) {
////                Vue_Detaillé blog = new  Vue_Detaillé();
////                blog.setDate(rs.getString("Date"));
////                blog.setCode_requete(rs.getLong("Code_requete"));
////                blog.setGroupement(rs.getString("dim"));
////                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
////                blog.setVal_kpi1(rs.getDouble("val1"));
////                blog.setVal_kpi2(rs.getDouble("val2"));
////                blog.setName_kpi(rs.getString("name"));
////
////                blog.setGap(rs.getDouble("gap"));
////                rslt2.add(blog);
////            }
//
//			ResultSet rs3 = ps3.executeQuery();
//
//			while (rs.next() && rs3.next()) {
//				System.out.println("pap");
//
//				Vue_Globale blog = new Vue_Globale();
//				blog.setDate(rs.getString("Date"));
//				blog.setCode_requete(rs.getLong("Code_requete"));
//				blog.setVal_kpi1(rs.getLong("val1"));
//				blog.setVal_kpi2(rs.getLong("val2"));
//				blog.setName_kpi(rs.getString("name"));
//				blog.setLoad1(rs.getString("val3"));
//				blog.setLoad2(rs.getString("val4"));
//				// probleme au niveau de nbreRecordOk si on a un seul enregistrement
//				blog.setNbreRecordOk(rs.getInt("nbreRecordOK"));
//				DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//				Date dateobj = new Date();
//				blog.dateExec = (dfff.format(dateobj));
//				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//				blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//
//				blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk"));
//
//				System.out.println(
//						(rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
//				blog.setDataQualite(ddf.format(
//						(rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//
//				blog.setFoundDB1(rs.getInt("load1"));
//				blog.setFoundDB2(rs.getInt("load2"));
//				blog.setGap((long) rs.getInt("gap"));
//				java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
//
//				BigDecimal bigD = new BigDecimal(
//						(rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//				blog.setGAP_par_100(dff.format(bigD));
//				System.out.println(blog.getGap());
//				if (((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))) == 0)
//						&& (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2")))
//								* 100F == 100.0) {
//					blog.setAcceptation("OK");
//				} else if (k
//						.getSeuil_dataQuality() < (rs.getDouble("nbreRecordOk")
//								/ Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F
//						&& k.getSeuil_gap() > (rs.getInt("gap") * 100)
//								/ (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))) {
//					blog.setAcceptation("OK partiel");
//				} else {
//					blog.setAcceptation("NotOk");
//				}
//
//				rslt2.add(blog);
//			}
//
//			model.addAttribute("rslt", rslt2);
//
//		}
//
//		for (Vue_Globale r : rslt2) {
//			database2repository.save(r);
//		}
//		return "resultat30";
//	}

	// }
	// TOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO HERE
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/resultat400", method = RequestMethod.GET)
	public String GetScript4(Model model, @RequestParam("kpiii") String[] kpis, @RequestParam("DateDeb") String dateDeb,
			@RequestParam("DateFin") String dateFin, @RequestParam("db1") long db1, @RequestParam("db2") Long db2)
			throws Exception {

		if (new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb)
				.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(dateFin)) > 0)
			model.addAttribute("message", "date Deb supérieur à date Fin");
		return "resultat400";
	}
//        Database d1 =dbrepository.findDatabaseById(db1);
//        Database d2 = dbrepository.findDatabaseById(db2);
//
//        List<Database1> rslt = new ArrayList<Database1>();
//        for (String s : kpis) {
//            long kpi = Long.parseLong(s);
//            Kpi k = kpirepository.findById(kpi).get();
//
//            Requete r1 = new Requete();
//            for (Requete r : k.getRequetess()) {
//                if (r1 != r) {
//                    r1 = r;
//
//                    if (dbrepository.findDatabaseById(db1).equals(r.getId_databasee())){  //HEEEEEEEEEEEEEEEEEEEEERE !
//
//                        if (r.getId_databasee().getSystem().equals("SqlServer")) {
//
//
//                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//                        Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() +
//                                        ";databaseName=" + r.getId_databasee().getName()
//                                , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//                        String date = r.getDate();
//                        String copr = r.getCopr();
//                        String val_kpi = r.val_kpi;
//                        String alias_val_kpi = r.val_kpi_alias;
//
//
//
//                        List<Dimension> dimss = r.getDims();
//                        String dimension = " ";
//                        for (Dimension d : dimss) {
//                            if (d == dimss.get(dimss.size() - 1)) {
//                                dimension = dimension + d.getVal_dim();
//                            } else {
//                                dimension = dimension + d.getVal_dim() + " +'|'+ ";
//                            }
//                        }
//                        System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by " + date + " ," + dimension);
//
//                        PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
//                        ResultSet rs = ps.executeQuery();
//
//                        while (rs.next()) {
//                            Database1 blog = new Database1();
//                            blog.setDate(rs.getString("date"));
//                            blog.setIdKpi(rs.getFloat("Code_requete"));
//                            blog.setVal_dim(rs.getString("val_dim"));
//                            blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//                            blog.setSysteme("SqlServer");
//                            System.out.println("hetha  tee sqlserver"+blog);
//                            rslt.add(blog);
//                        }
//
//                      con.close();
//                    } else if (r.getId_databasee().getSystem().equals("Postgres")) {
//
//
//                        try {
//                            Connection conn = null;
//                            Class.forName("org.postgresql.Driver");
//                            conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
//                                    , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//                            String date = r.getDate();
//                            String copr = r.getCopr();
//                            String val_kpi = r.val_kpi;
//                            String alias_val_kpi = r.val_kpi_alias;
//
//
//
//
//
//                            List<Dimension> dimss = r.getDims();
//                            String dimension = " ";
//                            for (Dimension d : dimss) {
//                                if (d == dimss.get(dimss.size() - 1)) {
//                                    dimension = dimension + d.getVal_dim();
//                                } else {
//                                    dimension = dimension + d.getVal_dim() + " ||'|'|| ";
//                                }
//                            }
//                            System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by " + date + " ," + dimension);
//
//                            PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
//                            ResultSet rs = ps.executeQuery();
//
//                            while (rs.next()) {
//                                Database1 blog = new Database1();
//                                blog.setDate(rs.getString("date"));
//                                blog.setIdKpi(rs.getFloat("Code_requete"));
//                                blog.setVal_dim(rs.getString("val_dim"));
//                                blog.setVal_kpi(rs.getFloat(alias_val_kpi));
//                                blog.setSysteme("Postgres");
//                                System.out.println("hetha  tee post"+blog);
//                                rslt.add(blog);
//                            }
//
//                           conn.close();
//                        } catch (Exception e) {
//                            System.out.println("Failed to create JDBC dateDeb connection " + e.toString() + e.getMessage());
//                        }
//                    } else if (r.getId_databasee().getSystem().equals("Oracle"))
//                        try {
//
//                            Class.forName("oracle.jdbc.OracleDriver");
//                            String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
//                                    + r.getId_databasee().getUsername() + r.getId_databasee().getPassword();
//                            System.out.println();
//                            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
//                                    , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
//
//
//                        } catch (Exception e) {
//                            System.out.println("Failed to create JDBC dateDeb connection " + e.getMessage());
//                        }
//                    else {
//
//                        System.out.println("no connexions");
//                    }
//
//                }}
//            }
//        }
//        System.out.println(rslt.size());
//        for (Database1 rs : rslt){
//
//            Database1 r= new Database1();
//            r.setDate(rs.getDate());
//            r.setIdKpi(rs.getIdKpi());
//            r.setVal_dim(rs.getVal_dim());
//            r.setVal_kpi(rs.getVal_kpi());
//            r.setSysteme(rs.getSysteme());
//            r.setKpi(kpirepository.findById((long) rs.getIdKpi()).get());
//            database2Service.InsertOrUpdate(r);
//        }
	// List<Vue_Globale> rslt2 = new ArrayList<Vue_Globale>();
//        for (String s : kpis) {
//            long kpi = Long.parseLong(s);
//            Kpi k = kpirepository.findById(kpi).get();
//            Connection conn = null;
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
//                    , "postgres", "root");
//            PreparedStatement ps = conn.prepareStatement("select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n" +
//                    "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n" +
//                    " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from \n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    " select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T3\n" +
//                    "  FULL OUTER JOIN\n" +
//                    " (select T2.date,sum(T2.nbre) as nbre from\n" +
//                    " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim\n" +
//                    " except all\n" +
//                    " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from database2 a \n" +
//                    " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb + "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
//                    " group by T2.date\n" +
//                    " order by T2.date) as T4\n" +
//                    " ON   T3.date = T4.date) as RR1 \n" +
//                    "right OUTER JOIN\n" +
//                    " (select sum(T1.val_kpi) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n" +
//                    ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n" +
//                    ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" + //heeeeeeeeeeeeeeeeeeeeeeeeeeeeeere 
//                    ",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n" +
//                    " from " +
//                    " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "'  and  a.system='" + d2.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
//                    " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
//                    " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" + dateFin + "' and a.system='" + d1.getSystem() + "'\n" +
//                    " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
//                    " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n" +
//                    " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n" +
//                    " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n" +
//                    "order by COALESCE(T1.date, B2.date)) as RR2\n" +
//                    " \n" +
//                    " on RR1.date=RR2.date\n" +
//                    " Left outer join\n" +
//                    " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n" +
//                    "(select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n" +
//                    "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R1\n" +
//                    "full OUTER join  \n" +
//                    " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n" +
//                    "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n" +
//                    "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n" +
//                    "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb + "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" +
//                    " group by a.date , a.idkpi  \n" +
//                    " order by a.date) as R2 \n" +
//                    " on R1.date=R2.date\n" +
//                    " order by R1.date) as RR3 \n" +
//                    " on RR2.date = RR3.date	");

//            long startTime = System.currentTimeMillis();
//
//           // ResultSet rs = ps.executeQuery();
//
//
//            while (rs.next()) {
//                System.out.println("pap");
//
//
//                Vue_Globale blog = new Vue_Globale();
//                blog.setDate(rs.getString("Date"));
//                blog.setCode_requete(rs.getLong("Code_requete"));
//                blog.setVal_kpi1(rs.getLong("val3"));
//                blog.setVal_kpi2(rs.getLong("val4"));
//                blog.setName_kpi(rs.getString("name"));
//                blog.setLoad1(rs.getString("val1"));
//                blog.setLoad2(rs.getString("val2"));
//                //probleme au niveau de nbreRecordOk si on a un seul enregistrement
//                blog.setNbreRecordOk(rs.getInt("nbreRecordOK"));
//                DateFormat dfff = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//                Date dateobj = new Date();
//                blog.dateExec = (dfff.format(dateobj));
//                java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//                blog.tempsExec = ddf.format((System.currentTimeMillis() - startTime) / 1000F);
//
//                blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk"));
//
//                System.out.println((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
//                blog.setDataQualite(
//                        ddf.format((rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
//
//                blog.setFoundDB1(rs.getInt("load1"));
//                blog.setFoundDB2(rs.getInt("load2"));
//                blog.setGap((long) rs.getInt("gap"));
//                java.text.DecimalFormat dff = new java.text.DecimalFormat("###.##");
//
//                BigDecimal bigD = new BigDecimal((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
//                blog.setGAP_par_100(dff.format(bigD));
//                System.out.println(blog.getGap());
//                if(((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))==0) && (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F==100.0){
//                    blog.setAcceptation("OK");
//                }
//                else if(k.getSeuil_dataQuality()< (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F
//                        && k.getSeuil_gap()>(rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))){
//                    blog.setAcceptation("OK partiel");
//                }else{
//                    blog.setAcceptation("NotOk");
//                }
//
//                rslt2.add(blog);
//            }
//
//            model.addAttribute("rslt", rslt2);
//
//        }

//        for (Vue_Globale r : rslt2){
//            database2repository.save(r);
//        }

	// return "resultat2";

	// }
	/*
	 * @RequestMapping(value="/resultat5",method=RequestMethod.GET) public String
	 * GetScript5(Model model,@RequestParam("kpiii") String[ ] kpis
	 * ,@RequestParam("DateDeb") String dateDeb,@RequestParam("DateFin") String
	 * dateFin ,@RequestParam("db1")long db1 ,@RequestParam("db2")Long db2) throws
	 * Exception {
	 * 
	 * 
	 * if(new SimpleDateFormat("yyyy-MM-dd").parse(dateDeb).compareTo(new
	 * SimpleDateFormat("yyyy-MM-dd").parse(dateFin))>0){
	 * model.addAttribute("message","date Deb supérieur à date Fin"); return
	 * "resultat"; } Database d1 =dbrepository.findDatabaseById(db1); Database d2 =
	 * dbrepository.findDatabaseById(db2); List<Vue_Globale> rslt2 = new
	 * ArrayList<Vue_Globale>(); for (String s : kpis) { long kpi =
	 * Long.parseLong(s); Kpi k = kpirepository.findById(kpi).get();
	 * 
	 * SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * 
	 * 
	 * 
	 * Connection conn = null; Class.forName("org.postgresql.Driver"); conn =
	 * DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4" ,
	 * "postgres", "root"); //requete pour afficher idkpi
	 * valkpi1,valkpi2,date,NbreRecord 1 , Nbre Record 2 PreparedStatement ps =
	 * conn.
	 * prepareStatement("select COALESCE(RR1.date,RR2.date) as Date,RR2.idkpi as Code_requete,RR2.val_kpi1 as val3,RR2.val_kpi2 as val4,RR2.valeur_dim1 as val1,RR2.valeur_dim2 as val2,RR2.gap as gap ,\n"
	 * +
	 * "RR2.name_kpi as name ,COALESCE(RR1.load1,0) as load1 ,COALESCE(RR1.load2,0) as load2 ,coalesce(RR3.nbreRecordOK,0) as nbreRecordOK,coalesce(RR3.nbreRecordNOTOK,0) as nbreRecordNotOk from \n"
	 * +
	 * " (select COALESCE(T3.date,T4.date) as date,COALESCE(T3.nbre,0) as load1,COALESCE(t4.nbre,0) as load2 from\n"
	 * + " (select T2.date,sum(T2.nbre) as nbre from \n" +
	 * " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n"
	 * + " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
	 * " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n" +
	 * " select a.idkpi, count(a.valeur_dim) as nbre,a.date from database2 a \n"
	 * + " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
	 * " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
	 * " group by T2.date\n" + " order by T2.date) as T3\n" + "  FULL OUTER JOIN\n"
	 * + " (select T2.date,sum(T2.nbre) as nbre from\n" +
	 * " (select a.idkpi, count(a.valeur_dim) as Nbre,a.date from database2 a \n"
	 * + " where a.idkpi=" + k.id_kpi + "    and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "'  and a.system='" + d2.getSystem() + "'\n" +
	 * " group by a.idkpi,a.date,a.system,a.valeur_dim\n" + " except all\n" +
	 * " select a.idkpi, count(a.valeur_dim)  as nbre,a.date from database2 a \n"
	 * + " where a.idkpi=" + k.id_kpi + "     and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "'  and a.system='" + d1.getSystem() + "'\n" +
	 * " group by a.idkpi,a.date,a.system,a.valeur_dim) as T2\n" +
	 * " group by T2.date\n" + " order by T2.date) as T4\n" +
	 * " ON   T3.date = T4.date) as RR1 \n" + "right OUTER JOIN\n" +
	 * " (select sum(T1.val_kpi) as val_kpi1, COALESCE(count(distinct(T1.valeur_dim)),0) as valeur_dim1\n"
	 * +
	 * ",sum(B2.val_kpi) as val_kpi2 ,COALESCE(count(distinct(B2.valeur_dim)),0) as valeur_dim2\n"
	 * + ",ABS(sum(T1.val_kpi)- sum(B2.val_kpi)) as gap\n" +
	 * ",k.name_kpi as name_kpi,COALESCE(T1.date, B2.date) as date,k.id_kpi as idkpi\n"
	 * + " from " +
	 * " (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n" +
	 * " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '" +
	 * dateFin + "'  and  a.system='" + d2.getSystem() + "'\n" +
	 * " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n" +
	 * " FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n"
	 * + " where idkpi=" + k.id_kpi + "  and a.date between '" + dateDeb + "' and '"
	 * + dateFin + "' and a.system='" + d1.getSystem() + "'\n" +
	 * " group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n" +
	 * " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n"
	 * + " left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi\n" +
	 * " group by k.name_kpi,k.seuil,COALESCE(T1.date, B2.date),k.id_kpi\n" +
	 * "order by COALESCE(T1.date, B2.date)) as RR2\n" + " \n" +
	 * " on RR1.date=RR2.date\n" + " Left outer join\n" +
	 * " (select COALESCE(R1.date,R2.date) as date,COALESCE(R1.nbreRecordOK,0) as nbreRecordOK , COALESCE(R2.nbreRecordNOTOK,0) as nbreRecordNOTOK from\n"
	 * +
	 * "(select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordOK     \n"
	 * +
	 * "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n"
	 * +
	 * "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
	 * + "where a.idkpi=" + k.id_kpi + "  and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi=b.val_kpi\n" +
	 * " group by a.date , a.idkpi  \n" + " order by a.date) as R1\n" +
	 * "full OUTER join  \n" +
	 * " (select  a.idkpi,a.date, COALESCE(count(distinct(a.valeur_dim)),0) as nbreRecordNOTOK      \n"
	 * +
	 * "from database2 as a FULL OUTER JOIN database2 as b ON  a.idkpi=b.idkpi \n"
	 * +
	 * "and a.date=b.date and a.valeur_dim=b.valeur_dim  INNER JOIN kpi as k on a.idkpi=k.id_kpi      \n"
	 * + "where a.idkpi=" + k.id_kpi + "   and a.date  between '" + dateDeb +
	 * "' and '" + dateFin + "' and a.system<>b.system and a.val_kpi<>b.val_kpi\n" +
	 * " group by a.date , a.idkpi  \n" + " order by a.date) as R2 \n" +
	 * " on R1.date=R2.date\n" + " order by R1.date) as RR3 \n" +
	 * " on RR2.date = RR3.date	");
	 * 
	 * long startTime = System.currentTimeMillis();
	 * 
	 * ResultSet rs = ps.executeQuery();
	 * 
	 * 
	 * while (rs.next()) { System.out.println("pap");
	 * 
	 * 
	 * Vue_Globale blog = new Vue_Globale(); blog.setDate(rs.getString("Date"));
	 * blog.setCode_requete(rs.getLong("Code_requete"));
	 * blog.setVal_kpi1(rs.getLong("val3")); blog.setVal_kpi2(rs.getLong("val4"));
	 * blog.setName_kpi(rs.getString("name")); blog.setLoad1(rs.getString("val1"));
	 * blog.setLoad2(rs.getString("val2"));
	 * blog.setNbreRecordOk(rs.getInt("nbreRecordOK")); DateFormat dfff = new
	 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
	 * blog.dateExec = (dfff.format(dateobj)); java.text.DecimalFormat ddf = new
	 * java.text.DecimalFormat("###.##"); blog.tempsExec =
	 * ddf.format((System.currentTimeMillis() - startTime) / 1000F);
	 * 
	 * blog.setNbreRecordNotOk(rs.getInt("nbreRecordNotOk"));
	 * 
	 * System.out.println((rs.getDouble("nbreRecordOk") /
	 * Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F);
	 * blog.setDataQualite( ddf.format((rs.getDouble("nbreRecordOk") /
	 * Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F));
	 * 
	 * blog.setFoundDB1(rs.getInt("load1")); blog.setFoundDB2(rs.getInt("load2"));
	 * blog.setGap((long) rs.getInt("gap")); java.text.DecimalFormat dff = new
	 * java.text.DecimalFormat("###.##");
	 * 
	 * BigDecimal bigD = new BigDecimal((rs.getInt("gap") * 100) /
	 * (Math.max(rs.getDouble("val4"), rs.getDouble("val3"))));
	 * blog.setGAP_par_100(dff.format(bigD)); System.out.println(blog.getGap());
	 * if(((rs.getInt("gap") * 100) / (Math.max(rs.getDouble("val4"),
	 * rs.getDouble("val3")))==0) && (rs.getDouble("nbreRecordOk") /
	 * Math.max(rs.getDouble("val1"), rs.getDouble("val2"))) * 100F==100.0){
	 * blog.setAcceptation("OK"); } else if(k.getSeuil_dataQuality()<
	 * (rs.getDouble("nbreRecordOk") / Math.max(rs.getDouble("val1"),
	 * rs.getDouble("val2"))) * 100F && k.getSeuil_gap()>(rs.getInt("gap") * 100) /
	 * (Math.max(rs.getDouble("val4"), rs.getDouble("val3")))){
	 * blog.setAcceptation("OK partiel"); }else{ blog.setAcceptation("NotOk"); //NOT
	 * THIS ONE }
	 * 
	 * rslt2.add(blog); }
	 * 
	 * 
	 * } /* while(rs5.next()) { Vue_Globale blog = new Vue_Globale();
	 * blog.setDate(rs5.getString("Date"));
	 * blog.setCode_requete(rs5.getLong("Code_requete")); DateFormat dfff = new
	 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
	 * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
	 * java.text.DecimalFormat("###.###");
	 * 
	 * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime2) /1000F);
	 * blog.setVal_kpi1( rs5.getLong("val3")); blog.setVal_kpi2((long) 0);
	 * blog.setName_kpi(rs5.getString("name"));
	 * blog.setLoad1(rs5.getString("val2")); blog.setLoad2("0");
	 * blog.setNbreRecordOk(0); blog.setNbreRecordNotOk(0);
	 * blog.setDataQualite("0"); blog.setFoundDB2(0);
	 * blog.setGap(rs5.getLong("val3")); java.text.DecimalFormat dff = new
	 * java.text.DecimalFormat("###"); System.out.println(); BigDecimal bigD = new
	 * BigDecimal((Math.abs ( rs5.getDouble("val3")) *100)/ rs5.getDouble("val3"));
	 * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
	 * 
	 * 
	 * } while(rs6.next()) { Vue_Globale blog = new Vue_Globale();
	 * blog.setDate(rs6.getString("Date"));
	 * blog.setCode_requete(rs6.getLong("Code_requete")); DateFormat dfff = new
	 * SimpleDateFormat("dd/MM/yy HH:mm:ss"); Date dateobj = new Date();
	 * blog.dateExec=(dfff.format(dateobj)); java.text.DecimalFormat ddf = new
	 * java.text.DecimalFormat("###.###");
	 * 
	 * blog.tempsExec=ddf.format((System.currentTimeMillis()-startTime3) /1000F);
	 * blog.setVal_kpi1( rs6.getLong("val3")); blog.setVal_kpi2((long) 0);
	 * blog.setName_kpi(rs6.getString("name"));
	 * blog.setLoad1(rs6.getString("val2")); blog.setLoad2("0");
	 * blog.setDataQualite("0"); blog.setNbreRecordOk(0);
	 * blog.setNbreRecordNotOk(0); blog.setFoundDB1( rs6.getInt("val2"));
	 * blog.setFoundDB2(0); blog.setGap(rs6.getLong("val3"));
	 * java.text.DecimalFormat dff = new java.text.DecimalFormat("###");
	 * System.out.println(); BigDecimal bigD = new BigDecimal((Math.abs (
	 * rs6.getDouble("val3")) *100)/ rs6.getDouble("val3"));
	 * blog.setGAP_par_100(dff.format(bigD)); rslt2.add(blog);
	 * 
	 * 
	 * }
	 */

	// model.addAttribute("rslt", rslt2);

//        for (Vue_Globale r : rslt2){
//            database2repository.save(r);
//        }
//
//
//
//        return "resultat2";*/
//
//    }
	@GetMapping("/Vue_Global100")
	public String getAllRslt100(Model model) {
		try {
			model.addAttribute("rslt", database2Service.getCompKpi());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Vue_Global100";
	}

//	@GetMapping("/Vue_detaille")
//	public String getAllRsltDetaille(Model model, @RequestParam("kpiii") String[] kpis) throws Exception {
//
//		List<Vue_Detaillé> rslt2 = new ArrayList<>();
//
//		for (int i = 0; i < kpis.length; i++) {
//			long vue_globale = Long.parseLong(kpis[i]);
//
//			Vue_Globale k = rslrepository.findById((int) vue_globale).get();
//			Kpi kpi = kpirepository.findById(k.Code_requete).get();
//			Connection conn = null;
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4", "postgres", "root");
//			// requete pour afficher vue detaillé
//			PreparedStatement ps = conn
//					.prepareStatement("select k.id_kpi as Code_requete, coalesce (sum(T1.val_kpi),0) as val1\n"
//							+ ",coalesce (sum(B2.val_kpi),0) as val2\n"
//							+ ",ABS(coalesce (sum(T1.val_kpi),0)- coalesce (sum(B2.val_kpi),0)) as gap\n" + // heeeeeeeeeeeere
//							",k.name_kpi as name,coalesce(B2.date,T1.date) as Date ,coalesce(T1.valeur_dim,B2.valeur_dim) as dim\n"
//							+ "from\n" + "(select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n"
//							+ "where idkpi=" + k.getCode_requete() + " and a.date like '" + k.getDate()
//							+ "' and  a.system='SqlServer'\n"
//							+ "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as T1\n"
//							+ "FULL OUTER JOIN  (select a.val_kpi,a.valeur_dim,a.idkpi,a.date from database2 as a\n"
//							+ "where idkpi=" + k.getCode_requete() + " and a.date like '" + k.getDate()
//							+ "'  and a.system='Postgres'\n" + "group by a.val_kpi,a.valeur_dim,a.idkpi,a.date) as B2\n"
//							+ " ON  B2.idkpi=T1.idkpi and T1.date = B2.date and B2.valeur_dim=T1.valeur_dim\n"
//							+ "left outer JOIN kpi k on T1.idkpi = k.id_kpi or B2.idkpi=k.id_kpi "
//							+ "where ABS(coalesce ((T1.val_kpi),0)- coalesce ((B2.val_kpi),0))  > " + kpi.getSeuil()
//							+ " \n"
//							+ "group by k.name_kpi, B2.date,B2.valeur_dim,T1.date,k.id_kpi,T1.valeur_dim,B2.valeur_dim\n"
//							+ "\n" + "order by B2.date");
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				Vue_Detaillé blog = new Vue_Detaillé();
//				blog.setDate(rs.getString("Date"));
//				blog.setCode_requete(rs.getLong("Code_requete"));
//				blog.setGroupement(rs.getString("dim"));
//				java.text.DecimalFormat ddf = new java.text.DecimalFormat("###.##");
//				blog.setVal_kpi1(rs.getDouble("val1"));
//				blog.setVal_kpi2(rs.getDouble("val2"));
//				blog.setName_kpi(rs.getString("name"));
//
//				blog.setGap(rs.getDouble("gap"));
//				rslt2.add(blog);
//			}
//			for (Vue_Detaillé r : rslt2) {
//				vueDetailSevice.InsertOrUpdate(r);
//			}
//
//			model.addAttribute("rslt", rslt2);
//		}
//
//		return "Vue_detaille";
//	}

}
