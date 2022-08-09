<%@ page import ="java.sql.*" %>

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
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
        <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">
    
        <!-- Datatables -->
    <link href="<c:url value="/resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"/>" rel="stylesheet">
     <link href="<c:url value="/resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"/>" rel="stylesheet">
 
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
  </head>

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
                       <li class="current-page" ><a href="listKpis">List Kpis</a>
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
              <div class="title_left">
                <h3> KPI</h3>
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
       <div class="col-md-12 col-sm-12  ">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>List Kpis </h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><i class="fa fa-wrench"></i></a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                           
                          </div>
                      </li>
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>

                  <div class="x_content">

                   
                         <div class="row">
                          <div class="col-sm-12">
                            <div class="card-box table-responsive">
                        		
          <table id="tableKpi" class="table table-striped table-bordered" style="width:100%">                          
                        
                         <thead>
                        <tr>
                          <th>Code Requete</th>
                          <th>Name KPI</th>
                          <th>Subject Area</th>
                          <th>Type</th>
                          <th>Seuil</th>
                          <th> Update</th>
                           <th> Add requete</th>
                        </tr>
                      </thead>
                       <tbody>
                  <c:forEach items="${listKpis}"  var="emp">
           <tr>
           <td>${emp.id_kpi}</td>
           <td>${emp.name_kpi}</td>
           <td>${emp.subject_area}</td>
           <td>${emp.type}</td>
           <td> ${emp.seuil}</td>
          <td>

              <a type="submit" href ="<c:url value='/editkpi/${emp.id_kpi}' />" class="btn btn-dark btn-xs">
                  <i class="fa fa-pencil"></i> </a>
          <input type="text" value="${emp.id_kpi}" name="kpiId" class="flat" hidden="true" ></td>
         <td style="align-items: center">
             <a type="submit"  href ="<c:url value='/addRequete/${emp.id_kpi}' />" class="btn btn-success btn-xs">

             <i class="fa fa-plus-square"></i> </a> </td>
            
          
       </tr>
       </c:forEach>
                       </tbody>
                       </table>
                       
                       </div>
                       </div>
                       </div>
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
         <!-- bootstrap-daterangepicker -->
              <script src="<c:url value="/resources/vendors/moment/min/moment.min.js"/>"></script>
              <script src="<c:url value="/resources/vendors/bootstrap-daterangepicker/daterangepicker.js"/>"></script>




     <script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
     <script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
    <!-- jQuery Smart Wizard -->

     <script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
 
    <!-- jQuery Tags Input -->
    <script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
  
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
    <script src="<c:url value="/resources/js/tablefilter.js"/>"></script>
    <!-- iCheck 1.0.1 -->
<script src="https://almsaeedstudio.com/themes/AdminLTE/plugins/iCheck/icheck.min.js"></script>
  <script type="text/javascript">
  var filtersConfig = {
	        base_path: 'tablefilter/',
	        col_0:'none',
	        col_1:'none',
	        col_2:'none',
	        col_3: 'none',
	        col_4: 'none',
	        col_5: 'none',
	        col_6: 'none',
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
	            '125px', '90px', '100px',
	            '90px', '90px', '90px',
	            '90px', '90px', '90px'
	        ]
	    };
	    var tf = new TableFilter('tableKpi', filtersConfig);
	    tf.init();
   $(document).ready(function() {
        $('#tableKpi').DataTable( {
            dom: 'Bfrtip',
            buttons: [
                
                'excelHtml5',
                
               
            ]
        } );
    } );
   var data =   $('#tableKpi').DataTable.buttons.exportData( {
       format: {
           header: function ( data, columnIdx ) {
               return columnIdx +': '+ data;
           }
       }
   } );
   </script>
  </body>
</html>
