<%@ page import ="java.sql.*" %>
<%@ page import="com.Springboot.example.service.DbServiceImpl" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>


<!DOCTYPE html>
<html lang="en">

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

      <title>DWH Test Automatisation</title>

      <!-- Bootstrap -->
     <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/resources/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
       <!-- bootstrap-daterangepicker -->
   <link href="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.css"/>" rel="stylesheet">
        <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">
    
        <!-- Datatables -->
    <link href="<c:url value="/resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"/>" rel="stylesheet">
 
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
  </head>
<style>
.select-box {
  cursor: pointer;
  position : relative;
  max-width:  20em;
  margin: 5em auto;
  width: 100%;
}

.select,
.label {
  color: #414141;
  display: block;
  font: 400 17px/2em 'Source Sans Pro', sans-serif;
}

.select {
  width: 100%;
  position: absolute;
  top: 0;
  padding: 5px 0;
  height: 40px;
  opacity: 0;
  -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
  background: none transparent;
  border: 0 none;
}
.select-box1 {
  background: #ececec;
}
</style>
  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
           

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="<c:url value="/resources/images/a.png"/>" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>${pageContext.request.userPrincipal.name}</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>General</h3>
                 <ul class="nav side-menu " class="toggled">
                  
                    <li>
                    <a><i class="fa fa-line-chart"></i>Manage KPIs/Requetes
                    <span class="fa fa-chevron-down"></span></a>
                     <ul class="nav child_menu" style="display: block;">
                       <li ><a href="listRequetes">List Requetes</a>
                       </li>
                       <li  ><a href="listKpis">List Kpis</a>
                        <li  ><a href="manage_kpi ">Add Kpis & Requetes</a>
                       </li>
                    </ul>
                    </li>
                      <li>
                      <a ><i class="fa fa-database"></i>Manage Connection 
                      <span class="fa fa-chevron-down"></span></a>
                      <ul class="nav child_menu" style="display: block;">
                       <li ><a href="list">List Databases</a>
                       </li>
                       <li  ><a href="manage_connexion">add Database</a>
                       </li>
                    </ul>
                      </li>
                      <li>
                      <a><i class="fa fa-bolt"></i>Resultat
                       <span class="fa fa-chevron-down"></span></a>
                       <ul class="nav child_menu" style="display: block;">
                       <li  ><a href="resultat">Lanch Script</a>
                       
                       </ul>
                      </li>
                </ul>
              </div>
              
              </div>

            </div>
            <!-- /sidebar menu -->

            
          </div>
        </div>
    
        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <div class="nav toggle">
                  <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                </div>
                <nav class="nav navbar-nav">
                <ul class=" navbar-right">
                  <li class="nav-item dropdown open" style="padding-left: 15px;">
                    <a href="javascript:;" class="user-profile dropdown-toggle" aria-haspopup="true" id="navbarDropdown" data-toggle="dropdown" aria-expanded="false">
                     <img src="<c:url value="/resources/images/img.jpg"/>" alt="">${pageContext.request.userPrincipal.name}
                    </a>
                    <div class="dropdown-menu dropdown-usermenu pull-right" aria-labelledby="navbarDropdown">
                      <a class="dropdown-item"  href="javascript:;"> Profile</a>
                      
                    
                      <a class="dropdown-item"  href="/logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                    </div>
                  </li>
  
                
                </ul>
              </nav>
            </div>
          </div>
        <!-- /top navigation -->

        <!-- page content -->
         <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
            
                    <div id="a" class="alert alert-success alert-dismissible " hidden="true" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">×</span>
                    </button>
                  </div>
              <div class="title_left">
                <h3>Manage Scripts</h3>
              </div>

              <div class="title_right">
                <div class="col-md-5 col-sm-5   form-group pull-right top_search">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button">Search</button>
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
            
              <div class="col-md-12 col-sm-12 ">
                <div class="x_panel">
                  <div class="x_title">
                    <h2><small></small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form id="demo-form2" data-parsley-validate class="form-horizontal form-label-left" method="get" action ="resultat">
                        <p style="color:red;">${message}</p>
                      <div class="form-group row">
                        <label class="control-label col-md-2 col-sm-2 label-align" >Database 1 <span class="required">*</span>
                        </label>
                        <div class="col-md-3 col-sm-3 ">
                         
                          <select  class="browser-default custom-select" id="select" name="db1" >
                             <% if( (request.getParameter("db1")!=null) && (request.getParameter("db2")!=null)) {
                            	   %>
                            	   <option><%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getId()%></option> <%
                        } 
                        %>
                          <option>--- select Database ----</option>
                          <c:forEach items="${databaseList}"  var="emp">
                             <option value="${emp.id}">${emp.name} </option>
                             </c:forEach>
                             </select>
                        </div>
                     
                        <label class="col-form-label col-md-2 col-sm-2 label-align" >Database 2 <span class="required">*</span>
                        </label>
                        <div class="col-md-3 col-sm-3 ">
                         
                         <select  class="browser-default custom-select" id="select" name="db2">
                         <% if( (request.getParameter("db1")!=null) && (request.getParameter("db2")!=null)) {
                            	   %>
                            	   <option><%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getId()%></option> <% 
                        } 
                        %>
                         <option>--- Select Database ----</option>
                          <c:forEach items="${databaseList}"  var="emp">
                             <option value="${emp.id}">${emp.name} </option>
                             </c:forEach>
                             </select>
                        </div>
                        <button class="btn btn-primary" type="submit"> Load Kpi</button>
                      </div>
         
                               
                               <% if( (request.getParameter("db1")!=null) && (request.getParameter("db2")!=null)) {
                            	   %>
                               
                       <h5>database 1 : <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db1"))).getName()%></h5>
                        <h5>database 2 : <%= DbServiceImpl.getRecordById(Long.parseLong(request.getParameter("db2"))).getName()%></h5>
                        <% 
                        } 
                        %>

                         <div class="item form-group">

                        <label class="col-form-label col-md-2 col-sm-2 label-align" >Date Deb <span class="required">*</span>
                        </label>
                      <div class="col-md-3 col-sm-3 ">
                          <input class="form-control" type="date" name="DateDeb" >
                      </div>
                     
                        <label class="col-form-label col-md-2 col-sm-2 label-align"> Date Fin <span class="required">*</span>
                        </label>
                      <div class="col-md-3 col-sm-3 ">
                          <input class="form-control" type="date" name="DateFin" >
                      </div>
                      </div>
                      <div class="item form-group">
                      
                        <div class="col-md-12 col-sm-12 ">
                         <table id="resultat" class="table table-striped table-bordered" style="width:100%">
                             <thead>
                        <tr>
                        <th></th>
                          <th>Code Requete</th>
                          <th>Name_kpi</th>
                          <th>Subject_area</th>
                          <th>Seuil_KPI</th>
                            <th>Seuil_GAP</th>
                            <th>Seuil_DQ</th>
                         
                          
                           
                           
                        </tr>
                      </thead>
                      <%
                      try{
                    	  Class.forName("org.postgresql.Driver");
                         Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Test4"
                                  ,"postgres","root");
                    	  Statement stm = conn.createStatement();
                    	  System.out.print(request.getParameter("db1"));
                    	  ResultSet rslt = stm.executeQuery("select * from kpi k , requete r , requete rr  WHERE k.id_kpi = r.id_kpi and rr.id_kpi =k.id_kpi  and r.id_database ="+request.getParameter("db1")+" and rr.id_database= "+request.getParameter("db2"));
                      while(rslt.next())
                      {  %> 
                    	  <tbody>
                      
                      <tr>
                       <td class="a-center ">
           <input type="checkbox" value="<%= rslt.getInt("id_kpi") %>" name="kpiii" class="flat" >
           </td>
                      <td><%= rslt.getInt("id_kpi") %></td>
                      <td><%= rslt.getString("name_kpi") %></td>
                      <td><%= rslt.getString("subject_area") %></td>
                      <td><%= rslt.getInt("seuil") %></td>
                          <td><%= rslt.getDouble("seuil_gap") %></td>
                          <td><%= rslt.getDouble("seuil_data_quality") %></td>
                   
             
          
          
          
       </tr>
       
                       </tbody>
                       <%
                      }
                      rslt.close();
                      conn.close();
                      }
                       catch(Exception e){
                    	   
                       }
                       %>
                       </table>
                        </div>
                      </div>
             
                      <div class="ln_solid"></div>
                      <div class="item form-group">
                        <div class="col-md-6 col-sm-6 offset-md-3">
                        <button class="btn btn-primary" type="button" onclick="generateReportC();" >See Resultat Only</button>
                          <button class="btn btn-primary" type="button" onclick="generateReportB();" >Load DB 1</button>
						  <button class="btn btn-primary" type="reset"  onclick="generateReportA();" >Load DB 2</button>
                         <button id="load12" class="btn btn-primary" type="button" name="load12"  onclick="generateReportD();"> Load data from two databases</button>
          
                        </div>
                      </div>
                      
                     	 <label class="col-form-label col-md-2 col-sm-2 label-align" ><H2> Plan Task </H2> <span class="required"></span>
                        </label>
                      <div class="col-md-3 col-sm-3 ">
                          <input class="form-control" type="date" name="DateDeb" >
                      </div>
    <div class="modal fade" id="exampleModalLong3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle2" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLongTitle">Veuillez Maintenant cocher les Kpis à tester </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div   class="modal-body" id="bodyModal3"  >
       
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-secondary"  >Retour</button>
             
           
          </div>
            </div>
    
      </div>
      </div>
                    </form>
                   
      
 
                  </div>
                </div>
              </div>
            </div>
     </div>
     </div>
     
                    </div>
                 
   
        <!-- /page content -->
   
        <!-- footer content -->
        <footer>
          <div class="pull-right">
          
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->


      <!-- jQuery -->

    <script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
    <!-- Bootstrap -->

   <script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
    <!-- FastClick -->
    <script src="<c:url value="/resources/vendors/fastclick/lib/fastclick.js"/>"></script>
   
    <!-- NProgress -->
    <script src="../vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->
    <script src="../vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- jQuery Sparklines -->
    <script src="../vendors/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
    <!-- morris.js -->
    <script src="../vendors/raphael/raphael.min.js"></script>
    <script src="../vendors/morris.js/morris.min.js"></script>
    <!-- gauge.js -->
    <script src="../vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="../vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- Skycons -->
    <script src="../vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="../vendors/Flot/jquery.flot.js"></script>
    <script src="../vendors/Flot/jquery.flot.pie.js"></script>
    <script src="../vendors/Flot/jquery.flot.time.js"></script>
    <script src="../vendors/Flot/jquery.flot.stack.js"></script>
    <script src="../vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="../vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="../vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="../vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="../vendors/DateJS/build/date.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="../vendors/moment/min/moment.min.js"></script>
    <script src="../vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

     <!-- iCheck -->
     <script src="<c:url value="/resources/vendors/iCheck/icheck.min.js"/>"></script>
       <!-- Datatables -->
    <script src="<c:url value="/resources/vendors/datatables.net/js/jquery.dataTables.min.js"/>"></script>
    <script src="<c:url value="/resources/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"/>"></script>
     <script src="<c:url value="/resources/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"/>"></script>
     <script src="<c:url value="/resources/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.flash.min.js"/>"></script>
        <script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.html5.min.js"/>"></script>
      <script src="<c:url value="/resources/vendors/datatables.net-buttons/js/buttons.print.min.js"/>"></script>
      <script src="<c:url value="/resources/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"/>"></script>
     <script src="<c:url value="/resources/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"/>"></script>
     <script src="<c:url value="/resources/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"/>"></script>
     <script src="<c:url value="/resources/vendors/jszip/dist/jszip.min.js"/>"></script>

 <script src="<c:url value="/resources/vendors/pdfmake/build/pdfmake.min.js"/>"></script>
 <script src="<c:url value="/resources/vendors/pdfmake/build/vfs_fonts.js"/>"></script>


     <script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
     <script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
    <!-- jQuery Smart Wizard -->

     <script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
 
    <!-- jQuery Tags Input -->
    <script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
  
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
      <!-- bootstrap-daterangepicker -->
              <script src="<c:url value="/resources/vendors/moment/min/moment.min.js"/>"></script>
              <script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>
    <!-- iCheck 1.0.1 -->
<script src="https://almsaeedstudio.com/themes/AdminLTE/plugins/iCheck/icheck.min.js"></script>
  <script type="text/javascript">
  function disableUsedOptions($table) {
	  $selects = $table.find("select");
	  $selects.on("change", function() {
	    $selects = $table.find("select");

	    console.log("In table:");
	    console.log($table);
	    console.log("there are " + $selects.length + " selects");
	    if ($selects.length <= 1) return;
	    let selected = [];
	    
	    $selects.each(function(index, select) {
	      if (select.value !== "") {
	        selected.push(select.value);
	      }
	    });

	    console.log("option values, that are being deactivated: " + selected);

	    $table.find("option").prop("disabled", false);
	    for (var index in selected) {
	     $table
	                .find('option[value="' + selected[index] + '"]:not(:selected)')
	                .prop("disabled", true);
	    }
	  });
	  $selects.trigger("change");
	}

	$tables = $("body");
	$tables.each(function(index) {
	  $table = $(this);
	  disableUsedOptions($table);
	});
	function afficher_pop(){
		document.getElementById('a').hidden = false;
		
	}
	function generateReportD(){
		   document.forms['demo-form2'].action = 'resultat2';
		   document.forms['demo-form2'].submit();
		  
		}
	function generateReportA(){
		   document.forms['demo-form2'].action = ' resultat3';
		   document.forms['demo-form2'].submit();
		  
		}
	function generateReportB(){
		   document.forms['demo-form2'].action = ' resultat4';
		   document.forms['demo-form2'].submit();
		  
		}
	function generateReportC(){
		   document.forms['demo-form2'].action = ' resultat5';
		   document.forms['demo-form2'].submit();
		  
		}
  </script>
<script src="<c:url value="/resources/js/tablefilter.js"/>"></script>


<script data-config="">

             var filtersConfig = {
        base_path: 'tablefilter/',
        col_1:'select',
        col_2: 'select',
        col_3: 'select',
        col_4: 'select',
        col_0: 'none',
        popup_filters: true,
        auto_filter: {
            delay: 1100 //milliseconds
        },
        alternate_rows: true,
        rows_counter: true,
        btn_reset: true,
        status_bar: true,
        msg_filter: 'Filtering...',
        col_widths:[
            '90px', '90px', '90px',
            '90px', '90px', '90px',
            '90px', '90px', '90px'
        ]
    };
    var tf = new TableFilter('resultat', filtersConfig);
    tf.init();

             var date_deb = $.datepicker.parseDate('dd/mm/yy', $('#DateDebut').val());
             var date_fin = $.datepicker.parseDate('dd/mm/yy', $('#DateFin').val());
             if (date_deb > date_fin) {
                 alert("Erreur: la date de fin est inférieur à la date de début");
             }else{
                 alert("OK");
             }
    </script>
  </body>
</html>
