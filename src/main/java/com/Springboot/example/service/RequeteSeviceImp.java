package com.Springboot.example.service;

import com.Springboot.example.model.Database;
import com.Springboot.example.model.Dimension;
import com.Springboot.example.model.Kpi;
import com.Springboot.example.model.Requete;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RequeteSeviceImp implements RequeteService {
    @Override
    public String TesterRequete(Database db, Requete r, Kpi k, String dateDeb, String dateFin, String[] valdim, String[] valdimalias) throws ClassNotFoundException {
        if (db.getSystem().equals("SqlServer")) {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:" + r.getId_databasee().getPort() +
                                ";databaseName=" + r.getId_databasee().getName()
                        , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

                String date = r.getDate();
                String copr = r.getCopr();
                String val_kpi = r.val_kpi;
                String alias_val_kpi = r.val_kpi_alias;


                System.out.println(valdim.length);
                String dimension = " ";


                for (int i = 0; i < valdim.length; i++) {
                    Dimension d = new Dimension();
                    d.setVal_dim(valdim[i]);
                    d.setVal_dim_alias(valdimalias[i]);
                    if (valdim.length - 1 == i) {
                        dimension = dimension + d.getVal_dim();
                    } else {
                        dimension = dimension + d.getVal_dim() + " +'|'+ ";
                    }
                    System.out.println(dimension);
                }

                System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);

                PreparedStatement ps = con.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
                ResultSet rs = ps.executeQuery();

                return "Requete Correcte";

            } catch (SQLException throwables) {
                return throwables.getMessage();
            }
        } else if (db.getSystem().equals("Postgres")) {


            try {
                Connection conn = null;
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                        , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());

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
                        dimension = dimension + d.getVal_dim() + " ||'|'|| ";
                    }
                }
                System.out.println("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and '" + date + "' between '" + dateDeb + "' and " + dateFin + " group by " + date + " ," + dimension);

                PreparedStatement ps = conn.prepareStatement("Select " + date + " as date, " + k.getId_kpi() + " as Code_requete ," + dimension + " as val_dim, " + val_kpi + " as " + alias_val_kpi + " " + copr + " and " + date + " between '" + dateDeb + "' and '" + dateFin + "' group by " + date + " ," + dimension);
                ResultSet rs = ps.executeQuery();

                return "Requete Correcte";
            } catch (SQLException throwables) {
                return throwables.getMessage();
            }
        } else if (db.getSystem().equals("Oracle"))
            try {

                Class.forName("oracle.jdbc.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                        + r.getId_databasee().getUsername() + r.getId_databasee().getPassword();
                System.out.println();
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:" + r.getId_databasee().getPort() + "/" + r.getId_databasee().getName()
                        , r.getId_databasee().getUsername(), r.getId_databasee().getPassword());
                return "Requete Correcte";

            } catch (Exception e) {
                return e.getMessage();

            }
        return "no connection";
    }
}
