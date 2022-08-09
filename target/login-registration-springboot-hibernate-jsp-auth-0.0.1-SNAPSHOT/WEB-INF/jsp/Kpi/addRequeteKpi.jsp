<%@page import="com.Springboot.example.service.KpiServiceImp"%>
<%@page import="com.Springboot.example.repository.KpiRepository"%>
<%@page import="com.Springboot.example.model.Database"%>
<%@page import="com.Springboot.example.service.DbServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>    
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en">

  <head>
      <title>DWH Test Automatisation</title>

      <style>
 h1 {
	color: red;
}
  .hide {
  display: none;
}
</style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>GreenPlumDWH </title>
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
                       <li ><a href="/listRequetes">List Requetes</a>
                       </li>
                       <li  ><a href="/listKpis">List Kpis</a>
                        <li  ><a href="/manage_kpi">Add Kpis & Requetes</a>
                       </li>
                    </ul>
                    </li>
                      <li>
                      <a ><i class="fa fa-database"></i>Manage Connection 
                      <span class="fa fa-chevron-down"></span></a>
                      <ul class="nav child_menu" style="display: block;">
                       <li ><a href="/list">List Databases</a>
                       </li>
                       <li  ><a href="/manage_connexion">add Database</a>
                       </li>
                    </ul>
                      </li>
                      <li>
                      <a><i class="fa fa-bolt"></i>Resultat
                       <span class="fa fa-chevron-down"></span></a>
                       <ul class="nav child_menu" style="display: block;">
                       <li  ><a href="/resultat">Lanch Script</a>
                        
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
                      
                    
                      <a class="dropdown-item"  href="logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a>
                    </div>
                  </li>
  
                
                </ul>
              </nav>
            </div>
          </div>
        <!-- /top navigation -->
         <div class="right_col" role="main" >
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Manage KPIs</h3>
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
                    <h2>Add Requete</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                    
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                      <% if (request.getAttribute("message")!=null)
                      {
                      %>
                      <div class="alert alert-warning alert-dismissible fade show">


                          <strong>${error}${message}</strong>
                          <button type="button" class="close" data-dismiss="alert">&times;</button>
                      </div>
                      <% } %>
        <c:url var ="action" value ="/add"> </c:url>
          <form id="formkpi"  class="form-horizontal form-label-left" method="post"  action="${action}" >
                     
 

                    
                     
                          <div class="form-group row">
                            
                            <div class="col-md-6 col-sm-6 ">
                           
                              <input  value="<%= request.getAttribute("idkpi") %>" type="number" id="id_kpi" name="id_kpi" required="required" class="form-control" hidden="true"/>
                            
                            </div>
                         </div>
                          <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">Database <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 ">
                             <select name="Control"  id="select">
                             <option>--- select Database ----</option>
                          <c:forEach items="${databaseList}"  var="emp">
                             <option value="${emp.id}">${emp.name} </option>
                             </c:forEach>
                             </select>
                             
                            </div>
                          </div>
                         <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">Date <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 ">

                              <spring:bind path="requete.date">
                              <input id="date" class="form-control" value="${requete.date}" name="date"  required="required" type="text"/>
                              </spring:bind>
                            </div>
                          </div>
                          
                          
                            
                          
                       
                        
                          <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">Val KPI <span class="required">*</span>
                            </label>
                            <div class="col-md-3 col-sm-12">
                                <spring:bind path="requete.val_kpi">
                              <input id="valKPI" class="form-control" value="${requete.val_kpi}"  name="val_kpi" required="required" type="text"/>
                                </spring:bind>
                            </div>As
                            <div class="col-md-3 col-sm-12">
                                <spring:bind path="requete.val_kpi_alias">
                              <input id="valKPIAs" class="form-control" value="${requete.val_kpi_alias}" name="val_kpi_alias"  required="required" type="text"/>
                                </spring:bind>
                            </div>
                          </div>
                          <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">COPR <span class="required">*</span>
                            </label>
                            <div class="col-md-6 col-sm-6 ">
                                <spring:bind path="requete.copr">
                              <textarea id="copr" name="copr"   required="required" >

                                  ${requete.copr}
                              </textarea>
                                </spring:bind>
                            </div>
                          </div>
                          
                           <div class="field_wrapper">
                           <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">Dimensions <span class="required">*</span>
                            </label>

                          
                             <div class="col-md-3 col-sm-12">
                                 <c:forEach items="${requete.dims}" var="d">
                          <input class="form-control" value="${d.val_dim}" type="text" id="ajout" name="ajout" size="15"/> as
                          <input class="form-control" type="text" value="${d.val_dim_alias}" id="ajoutt" name="ajoutt" size="15"/>
                                 </c:forEach>
                            <div  id="champs"></div>

                            <input type="button" class="bouton" name="add_button" id="add_button" value="Ajouter"/>
                          
                                </div> 
                             </div>
                          </div>
                             
                          <div class="form-group row">
                            <label class="col-form-label col-md-3 col-sm-3 label-align">Date_Image 
                            </label>
                            <div class="col-md-6 col-sm-6 ">
                            
                              <input id="DateImage"  class="form-control" type="text"/>
                         
                            </div>
                          </div> 
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong" onClick="submitText()"> <!-- here was an syntax error. you were calling method by uts name without () sign -->
                         See Requete
                        </button>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong2" onClick="submitText1()"> <!-- here was an syntax error. you were calling method by uts name without () sign -->
                         Launch Script
                        </button>
                         
                    <button type="submit" class="btn btn-primary"> 
                       add
                        </button>
                           
      <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLongTitle">Requete </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div   class="modal-body" id="bodyModal"  >

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Retour</button>
             
           
          </div>
            </div>
    
      </div>
      </div>
      <div class="modal fade" id="exampleModalLong2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLong2" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" >Requete </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align">Requete <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
          <textarea cols="20" rows="30" hidden="true" class="modal-body" id="bodyModal2" name="requetee" >
       
          </textarea>
          
          </div>
          </div>
          <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="dateDeb">Date Deb <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <input type="date" name="dateDeb" id="dateDeb"  class="form-control ">
                        </div>
                      </div>
                      <div class="item form-group">
                        <label class="col-form-label col-md-3 col-sm-3 label-align" for="dateFin">Date Fin <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 ">
                          <input type="date" id="dateFin" name="dateFin"  class="form-control">
                        </div>
                        </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" onclick="tester();" >Tester</button>



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


     <script src="<c:url value="/resources/vendors/validator/multifield.js"/>"></script>
     <script src="<c:url value="/resources/vendors/validator/validator.js"/>"></script>
    <!-- jQuery Smart Wizard -->

     <script src="<c:url value="/resources/vendors/jQuery-Smart-Wizard/js/jquery.smartWizard.js"/>"></script>
 
    <!-- jQuery Tags Input -->
    <script src="../vendors/jquery.tagsinput/src/jquery.tagsinput.js"></script>
  
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
    <!-- iCheck 1.0.1 -->
<script src="https://almsaeedstudio.com/themes/AdminLTE/plugins/iCheck/icheck.min.js"></script>
 <script type="text/javascript">
 var $Increment=0;
  var $saisi= new Array();
  $("#add_button").on("click", function() {

      var div = $("#champs");

      $Increment = $Increment + 1;
      div.append( '<div><br><input class="form-control" type="text"  id="ajout['+$Increment+']" name="ajout" size="15" />'+'AS&nbsp;<input class="form-control" type="text" id="ajoutt['+$Increment+']" name="ajoutt" size="15"/>'+'&nbsp;<a href="#" class="remove_field">Remove</a></div>');

      $saisi[$Increment] = document.getElementById('ajout['+$Increment+']').value;

});
  
  var div = $("#champs");

  $(div).on("click",".remove_field", function(e){ //user click on remove text
		e.preventDefault(); $(this).parent('div').remove();  x--;
	})

  //iCheck for checkbox and radio inputs
  $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
    checkboxClass: 'icheckbox_minimal-blue',
    radioClass: 'iradio_minimal-blue'
  });
  $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
      checkboxClass: 'icheckbox_flat-green',
      radioClass: 'iradio_flat-green'
    });
    
  function submitText(){
	  var i =1;
	  var $d ="";
		while(i<$saisi.length){
	    	var	$s = "<font color='#FF0000'>||'|'|| </font> " +document.getElementById('ajout['+i+']').value+" as "+  document.getElementById('ajoutt['+i+']').value+" ,<br> ";
	    	var	$d = $d + $s +' ' ;
	    		i++;
	    											
	    	};
            var html="<font color='#FF0000'>Select</font> "+$("#date").val()+" as Date <br>"+$("#id_kpi").val()+" As code_requete,<br>   "+document.getElementById('ajout').value+" as "+document.getElementById('ajoutt').value+",<br>"+$d +$("#val_kpi").val()+" as "+$("#val_kpi_alias").val()+"<br>   "+$("#copr").val()+"<br> And "+$("#date").val()+"<font color='#FF0000'> between</font><p class='bleu'> <font color='#008000'>DateDeb</font></p><font color='#FF0000'> and</font><p class='bleuu'> <font color='#008000'>DateFin</font> </p><br> <font color='#FF0000'>group by</font> "+$("#date").val()+",<br> "+$("#ajout").val()+" as "+$("#ajoutt").val()+",<br>"+$d+"";
            $("#bodyModal").html(html);
        }
 function submitText1(){
	  
	  var i =1;
	  var $d ="";
		while(i<$saisi.length){
	    	var	$s = "+'|'+ " +document.getElementById('ajout['+i+']').value+" /* as "+  document.getElementById('ajoutt['+i+']').value+" */ ";
	    	var	$d = $d + $s +' ' ;
	    		i++;
	    											
	    	};
            var html="Select "+$("#date").val()+" as Date,  "+ $("#id_kpi").val()+" as Code_requete, "+$("#ajout").val()+" /*  as "+$("#ajoutt").val()+" */ "+$d +" as val_dim ,"+$("#val_kpi").val()+" as "+$("#val_kpi_alias").val()+" "+$("#copr").val()+" and " +$("#date").val()+ " between DateDeb and DateFin group by "+$("#date").val()+","+$("#ajout").val()+" /* as "+$("#ajoutt").val()+" */ "+$d+"";
            $("#bodyModal2").html(html);
        }

 function tester(){
     document.forms['formkpi'].action = '/testerRq';
     document.forms['formkpi'].submit();

 }
 
 </script>
</body>
</html>