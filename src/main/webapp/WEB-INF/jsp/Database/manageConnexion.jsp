<%@ page import ="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
      <link href="<c:url value="/resources/fonts/font-awesome-4.7.0/css/pgFont.css"/>" rel="stylesheet">

      <!--===============================================================================================-->
      <link href="<c:url value="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>" rel="stylesheet">



      <!--===============================================================================================-->
      <link href="<c:url value="/resources/vendor/select2/select2.min.css"/>" rel="stylesheet">
      <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">
      <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
      <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- bootstrap-progressbar -->
    <link href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- bootstrap-daterangepicker -->
    <link href="../vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
        <!-- iCheck -->
    <link href="<c:url value="/resources/vendors/iCheck/skins/flat/green.css"/>" rel="stylesheet">
    
        <!-- Datatables -->
    
    <link href="../vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">

  </head>
  <style>
  span.ingredient {
  color: #f00;
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
               <img src="<c:url value="/resources/images/a.png"/>"alt="..." class="img-circle profile_img">
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
                       <li class="current-page"><a href="listRequetes">List Requetes</a>
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

                 <div class="col-md-12">





                         <form:form class="login100-form validate-form p-b-33 p-t-5" method="POST"  modelAttribute="database"  name="database">

                             <span class="ingredient"><h4>${message}</h4></span>
                      <div class="form-group  ${status.error ? 'has-error' : ''}">

                          <label class="input100 col-md-3 col-sm-3 ">Name Database </label>

                            <div class="wrap-input100 validate-input" data-validate = "Enter Database Name">
                          <form:input type="text" class="input100" placeholder="Name" path="name" id="name" required="required"/>
                                <span class="focus-input100" data-placeholder="&#xe80e;"></span>
                            </div>

                      </div>
                      <div class="form-group  ${status.error ? 'has-error' : ''}">
                          <label class="input100 col-md-3 col-sm-3 ">Port </label>

                          <div class="wrap-input100 validate-input" data-validate = "Enter port">
                          <form:input type="text" class="input100"  placeholder="Port" path="port" id="port" required="required"/>
                              <span class="focus-input100" data-placeholder="&#xe87d;"></span>
                          </div>

                      </div>
                       <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">UserName </label>
                           <div class="wrap-input100 validate-input" data-validate = "Enter Username">
                          <form:input type="text" class="input100"  placeholder="user" path="username" id="username" required="required"/>
                            <span class="focus-input100" data-placeholder="&#xe82a;"></span>
                        </div>

                       </div>
                             <div class="form-group  ${status.error ? 'has-error' : ''}">
                                 <label class="input100 col-md-3 col-sm-3 ">URL </label>
                                 <div class="wrap-input100 validate-input" data-validate = "Enter URL">
                                     <form:input type="text" class="input100"  placeholder="url" path="url" id="url"/>
                                     <span class="focus-input100" data-placeholder="&#xe82a;"></span>
                                 </div>

                             </div>
                       <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">Password </label>
                           <div class="wrap-input100 validate-input" data-validate = "Enter password">
                          <form:input type="password" class="input100"  placeholder="*****" path="password" id="password" required="required"/>
                               <span class="focus-input100" data-placeholder="&#xe80f;"></span>
                           </div>

                       </div>
                        <div class="form-group  ${status.error ? 'has-error' : ''}">
                        <label class="input100 col-md-3 col-sm-3 ">Source_System </label>
                            <div class="wrap-input100 validate-input" data-validate = "Enter System Source">
                          <form:select class="browser-default custom-select"  path="system" id="system" >
                           <form:option value="SqlServer">SqlServer</form:option>
                                <form:option value="Postgres"><i class="icon-pg-logo icon-4x"></i> Postgres</form:option>
                                <form:option value="Oracle">Oracle</form:option>
                          </form:select>
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group ">
                        <div class="col-md-9 col-sm-9  offset-md-3">

                          <button type="submit" class="login100-form-btn">add database</button>
                        </div>
                      </div>

                    </form:form>
                        </div>


                  </div>
        <!-- /page content -->
</div>
        <!-- footer content -->
        <footer>
          <div class="pull-right">
          
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->

<style>
	.bs-example{
    	margin: 20px;
    }
</style>

    
    <!-- jQuery -->

    <script src="<c:url value="/resources/vendors/jquery/dist/jquery.min.js"/>"></script>
    <!-- Bootstrap -->

   <script src="<c:url value="/resources/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
    <!-- FastClick -->
    <script src="../vendors/fastclick/lib/fastclick.js"></script>
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
    <script src="../vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="../vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="../vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="../vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="../vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="../vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="../vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="../vendors/jszip/dist/jszip.min.js"></script>
    <script src="../vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="../vendors/pdfmake/build/vfs_fonts.js"></script>
    <!-- jQuery Smart Wizard -->
    <script src="<c:url value="/resources/vendor/select2/select2.min.js"/>"></script>
     <script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
 
    <!-- jQuery Tags Input -->
    <script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
  
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>


  
<script>
    $(document).ready(function(){ 
        $("#myTab a").click(function(e){
            e.preventDefault();
            $(this).tab('show');
        });
    });
</script>
  </body>
</html>
