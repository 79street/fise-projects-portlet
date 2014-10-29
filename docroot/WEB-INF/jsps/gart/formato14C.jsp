
<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

<portlet:actionURL var="guardarFormato14CURL">  
	<portlet:param name="action" value="guardar" />
</portlet:actionURL>

<link href="/fise-projects-portlet/css/tablas.css" rel="stylesheet" type="text/css">

<script type="text/javascript"
		src="/fise-projects-portlet/js/formato14c.js"></script>

<script type="text/javascript">
</script>

<style>

</style>


<form:form modelAttribute="formato14CBean" method="POST" action="${guardarFormato14CURL}">
	
	
	
	
	
	
	
	
	<!-- NUEVO REGISTRO -->
	<table>
	  <tr>
	   <td> <label style="font-size: 12px; font-weight:bold">Distribuidora Eléctrica:</label></td>
	   <td><form:input path="codEmpresa"/></td>
	   <td> <label style="font-size: 12px; font-weight:bold">EDELNOR</label></td>
	  </tr>	
	  <tr>
	   <td> <label style="font-size: 12px; font-weight:bold">Año/Mes</label></td>
	   <td><form:input path="anioPres"/></td>
	   <td><form:input path="mesPres"/></td>
	  </tr>	
	   <tr>
	   <td> <label style="font-size: 12px; font-weight:bold">Rubro</label></td>
	   <td colspan="2"><form:input path="etapa"/></td> 	  
	  </tr>	
	   <tr>
	   <td> <label style="font-size: 12px; font-weight:bold">Frecuencia</label></td>
	   <td colspan="2"><form:input path="etapa"/></td>	   
	  </tr>	
	</table>
	
	<table>
	    <tr>
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Actividades</label>
			</td>
			<td colspan="5" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Zona Rural</label>
			</td>
			<td colspan="5" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Zona Urbano Provincias</label>
			</td>	
			<td colspan="5" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Zona Urbano Lima</label>
			</td>	
							
		</tr>
		<tr>
			<td colspan="2" style="text-align:center"> 			
			</td>
			<!-- RURAL -->
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
			</td>
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
			</td>	
			<td style="text-align:center"> 			 
			</td>	
			<!-- URBANO PROVINCIAS -->
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
			</td>
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
			</td>	
			<td style="text-align:center"> 			 
			</td>
			<!-- URBANO LIMA -->
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Directo</label>
			</td>
			<td colspan="2" style="text-align:center"> 
			 <label style="font-size: 12px; font-weight:bold">Costo Indirecto</label>
			</td>	
			<td style="text-align:center"> 			 
			</td>		
							
		</tr>
		<tr>
			<td colspan="2"> 			
			</td>
			<!-- RURAL -->
		    <td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Directo</label>
			</td>
			<td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>	
			<td style="text-align:center"> 
			 <label>Costo Indirecto</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Total</label>
			</td>
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Directo</label>
			</td>	
			<td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Indirecto</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Total</label>
			</td>
			<!-- URBANA LIMA -->
			<td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Directo</label>
			</td>	
			<td style="text-align:center"> 
			 <label>Cantidad</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Indirecto</label>
			</td>
			<td style="text-align:center"> 
			 <label>Costo Total</label>
			</td>												
		</tr>
		<tr>
			<td colspan="2"> 
			 <label style="font-size: 12px; font-weight:bold">1  Datos Generales</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center;display:none" colspan="5"></td> 
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center;display:none" colspan="5"></td>
			<!-- URBANA LIMA -->
			<td style="text-align:center;display:none" colspan="5"></td>				 		
		</tr>
		<tr>
			<td> 
			 <label style="margin-left:0.5cm;">1.1 Nombre de la Sede: </label>
			</td>	
			<td style="text-align:left"><form:input path="nombreSede"/></td>
			<!-- RURAL --> 
			<td style="text-align:center;display:none" colspan="5"></td>
			<!-- URBANA PROVINCIAS --> 	
			<td style="text-align:center;display:none" colspan="5"></td>
			<!-- URBANA LIMA --> 	
			<td style="text-align:center;display:none" colspan="5"></td>		
		</tr>
		<tr>
			<td colspan="2">
			 <label style="margin-left: 0.5cm;">1.2 Número Total Beneficiarios Empadronados a Diciembre del periodo anterior</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text8" path="numRural" size="5"/></td>
			<td style="text-align:center" colspan="4"></td> 
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center"><form:input id="text9" path="numUrbProv" size="5"/></td>
			<td style="text-align:center" colspan="4"></td> 
			<!-- URBANA LIMA -->
			<td style="text-align:center"><form:input id="text10" path="numUrbLima" size="5"/></td>
			<td style="text-align:center" colspan="4"></td> 						
		</tr>
		<tr>
			<td colspan="2">
			 <label style="font-size: 12px; font-weight:bold">2 Costo por Gestion del Personal</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center;display:none" colspan="5"></td> 
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center;display:none" colspan="5"></td> 
			<!-- URBANA LIMA -->
			<td style="text-align:center;display:none" colspan="5"></td> 			
		</tr>
		<tr>
			<td colspan="2">
			 <label  style="margin-left: 0.5cm;">2.1  Costo de Coordinador</label> 
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text11" path="canDRCoord" size="5" /></td> 
			<td style="text-align:center"><form:input id="text12" path="costDRCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text13" path="canIRCoord" size="5" /></td> 
			<td style="text-align:center"><form:input id="text14" path="costIRCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text15" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center"><form:input id="text16" path="canDPCoord" size="5" /></td> 
			<td style="text-align:center"><form:input id="text17" path="costDPCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text18" path="canIPCoord" size="5" /></td>
			<td style="text-align:center"><form:input id="text19" path="costIPCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text20" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA LIMA -->
			<td style="text-align:center"><form:input id="text21" path="canDLCoord" size="5" /></td> 
			<td style="text-align:center"><form:input id="text22" path="costDLCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text23" path="canILCoord" size="5" /></td>
			<td style="text-align:center"><form:input id="text24" path="costILCoord" size="5"/></td> 
			<td style="text-align:center"><form:input id="text25" path="nombreSede" size="5" disabled="true"/></td>		
		</tr>
		<tr>
			<td colspan="2">
			 <label style="margin-left: 0.5cm;">2.2  Costo de Supervisor</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text31" path="canDRSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text32" path="costDRSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text33" path="canIRSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text34" path="costIRSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text35" path="nombreSede" size="5" disabled="true"/></td>	
			<!-- URBANA PROVINCIAS -->	
			<td style="text-align:center"><form:input id="text36" path="canDPSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text37" path="costDPSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text38" path="canIPSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text39" path="costIPSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text40" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA LIMA -->	
			<td style="text-align:center"><form:input id="text41" path="canDLSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text42" path="costDLSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text43" path="canILSupe" size="5" /></td> 
			<td style="text-align:center"><form:input id="text44" path="costILSupe" size="5"/></td> 
			<td style="text-align:center"><form:input id="text45" path="nombreSede" size="5" disabled="true"/></td>		
		</tr>
		<tr>
			<td colspan="2">
			 <label style="margin-left: 0.5cm;">2.3  Costo de Gestor</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text46" path="canDRGest" size="5" /></td> 
			<td style="text-align:center"><form:input id="text47" path="costDRGest" size="5"/></td>
			<td style="text-align:center"><form:input id="text48" path="canIRGest" size="5" /></td>  
			<td style="text-align:center"><form:input id="text49" path="costIRGest" size="5"/></td> 
			<td style="text-align:center"><form:input id="text50" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center"><form:input id="text51" path="canDPGest" size="5" /></td> 
			<td style="text-align:center"><form:input id="text52" path="costDPGest" size="5"/></td> 
			<td style="text-align:center"><form:input id="text53" path="canIPGest" size="5" /></td>
			<td style="text-align:center"><form:input id="text54" path="costIPGest" size="5"/></td> 
			<td style="text-align:center"><form:input id="text55" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA LIMA -->
			<td style="text-align:center"><form:input id="text56" path="canDLGest" size="5" /></td> 
			<td style="text-align:center"><form:input id="text57" path="costDLGest" size="5"/></td> 
			<td style="text-align:center"><form:input id="text58" path="canILGest" size="5" /></td>
			<td style="text-align:center"><form:input id="text59" path="costILGest" size="5"/></td> 
			<td style="text-align:center"><form:input id="text60" path="nombreSede" size="5" disabled="true"/></td>		
		</tr>
		<tr>
			<td colspan="2">
			 <label style="margin-left: 0.5cm;">2.4  Costo de Asistente y/o Auxiliar</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text61" path="canDRAsist" size="5" /></td> 
			<td style="text-align:center"><form:input id="text62" path="costDRAsist" size="5"/></td>
			<td style="text-align:center"><form:input id="text63" path="canIRAsist" size="5" /></td>  
			<td style="text-align:center"><form:input id="text64" path="costIRAsist" size="5"/></td> 
			<td style="text-align:center"><form:input id="text65" path="nombreSede" size="5" disabled="true"/></td>	
			<!-- URBANA PROVINCIAS -->	
			<td style="text-align:center"><form:input id="text66" path="canDPAsist" size="5" /></td> 
			<td style="text-align:center"><form:input id="text67" path="costDPAsist" size="5"/></td>
			<td style="text-align:center"><form:input id="text68" path="canIPAsist" size="5" /></td>  
			<td style="text-align:center"><form:input id="text69" path="costIPAsist" size="5"/></td> 
			<td style="text-align:center"><form:input id="text70" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA LIMA -->	
			<td style="text-align:center"><form:input id="text71" path="canDLAsist" size="5" /></td> 
			<td style="text-align:center"><form:input id="text72" path="costDLAsist" size="5"/></td>
			<td style="text-align:center"><form:input id="text73" path="canILAsist" size="5" /></td>  
			<td style="text-align:center"><form:input id="text74" path="costILAsist" size="5"/></td> 
			<td style="text-align:center"><form:input id="text75" path="nombreSede" size="5" disabled="true"/></td>			
		</tr>
		<tr>
			<td colspan="2">
			 <label style="margin-left: 0.5cm;font-weight:bold">2.5  Costo Total por Gestion de Personal (2.1)+(2.2)+(2.3)+(2.4)</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center"><form:input id="text76" path="numRural" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text77" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text78" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text79" path="nombreSede" size="5" disabled="true"/></td>
			<td style="text-align:center"><form:input id="text80" path="nombreSede" size="5" disabled="true"/></td>
			<!-- URBANA PROVINCIAS -->
			<td style="text-align:center"><form:input id="text81" path="numRural" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text82" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text83" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text84" path="nombreSede" size="5" disabled="true"/></td>
			<td style="text-align:center"><form:input id="text85" path="nombreSede" size="5" disabled="true"/></td>	
			<!-- URBANA LIMA -->
			<td style="text-align:center"><form:input id="text86" path="numRural" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text87" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text88" path="nombreSede" size="5" disabled="true"/></td> 
			<td style="text-align:center"><form:input id="text89" path="nombreSede" size="5" disabled="true"/></td>
			<td style="text-align:center"><form:input id="text90" path="nombreSede" size="5" disabled="true"/></td>				
		</tr>
		<tr>
			<td colspan="2"> 
			 <label style="font-size: 12px; font-weight:bold">3  Costo promedio mensual por utiles y materiales de oficina</label>
			</td>
			<!-- RURAL -->
			<td style="text-align:center" colspan="5"><form:input id="text91" path="costoPromRural" size="5"/></td>
			<!-- URBANA PROVINCIAS --> 
			<td style="text-align:center" colspan="5"><form:input id="text92" path="costoPromUrbProv" size="5"/></td>
			<!-- URBANA LIMA --> 
			<td style="text-align:center" colspan="5"><form:input id="text93" path="costoPromUrbLima" size="5"/></td>								
		</tr>
	</table> 
	<form:button id="btnGrabar">Grabar</form:button> 
	
   <!-- FIN DEL NUEVO REGISTRO -->	
	

</form:form> 


