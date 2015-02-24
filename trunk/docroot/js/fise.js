////////////
function redondeo(numero, decimales){
	var flotante = parseFloat(numero);
	var resultado = Math.round(flotante*Math.pow(10,decimales))/Math.pow(10,decimales);
	return resultado;
}
//////////
function Rpad(number, length,character) {   
		var str = '' + number;
		while (str.length < length) {
			str = str + character;
		} 
		return str;
	}
function completarBlanco(id){
	var elementa = document.getElementById(id); 
	var numstr = trim(elementa.value);
	if(numstr==''){
		numstr='0';
	}
	var conversion = Number(numstr);
	elementa.value = conversion;
}
function completarDecimal(id,decimal){
		
		var elementa = document.getElementById(id); 
		var numstr = trim(elementa.value);
		var posicionPunto = numstr.indexOf('.');
		if(posicionPunto >= 0){
			var parteEntera = numstr.substring(0,posicionPunto);
			var parteDecimal = numstr.substring(posicionPunto+1);
			if(parteEntera==''){
				parteEntera='0';
			}
			var conversion = Number(parteEntera);
			parteDecimal = Rpad(parteDecimal,decimal,'0');
			elementa.value = conversion+'.'+parteDecimal;
		}	
		else{
			var conversion = Number(elementa.value);
			if( isNaN(conversion)){
				elementa.value = '0.'+Rpad('',decimal,'0');
			}
			else{
				elementa.value = Number(elementa.value)+'.'+Rpad('',decimal,'0');
			}
		}
	}

  function soloNumerosDecimales_original(e, tipo, id,entero,decimal){
	var TIPO_NUMERO_ENTERO  = 1;
	var TIPO_NUMERO_DECIMAL = 2;
	var charCode;
	if (navigator.appName == "Netscape"){
	   charCode = e.which;
	}
	else{
	   charCode = e.keyCode;
	}
	if( tipo==TIPO_NUMERO_ENTERO ){
		var valoresOK = new Array(
						'8' ,/*backspace*/
						'32',/*space*/ 
						''
						);
	}

	if( tipo==TIPO_NUMERO_DECIMAL ){
		var valoresOK = new Array(
						'8' ,/*backspace*/
						//'32',/*space*/ 
						'46',/*punto*/
						'0',
						''
						);
	}
	var elem = document.getElementById(id); 
	var numstr = trim(elem.value);
	var punto = ".";
	var longitudEntera=0;
	var incremento=1; 
	var cantidadPuntos=0;
	for (var i = 0 ; i < numstr.length ; i++){
		if (punto.indexOf(numstr.substr(i,1)) == 0){
			if(cantidadPuntos>0){
				incremento=0;
			}
			cantidadPuntos = cantidadPuntos+1;
		}
		longitudEntera = longitudEntera+incremento;
	} 
	if(longitudEntera>entero && cantidadPuntos==0 && charCode != '46' && charCode != '8'){
		return false;
	}
	if( (cantidadPuntos > 1) || (cantidadPuntos==1 && charCode =='46') ){
		return false;
	}
	else if(numstr != "" && charCode != '8'){
		var sub1 = /^\d{1,/;
		var sub2 = /}$/;
		var sub3 = /}[.]$/;
		var sub4 = /}[.]\d{1,/;
		var sub5 = /^[.]$/;
		var sub6 = /^[.]\d{1,/;
		if( charCode == '46' || numstr.match(new RegExp(sub1.source+(entero)+sub2.source)) ) {
		}
		else if(numstr.match(new RegExp(sub1.source+entero+sub3.source))){
				if(charCode == '46'){
				return false;
		   }
		}
		else if(numstr.match(new RegExp(sub1.source+entero+sub4.source+(decimal)+sub2.source))){
		}
		else if(numstr.match(new RegExp(sub5.source))){
		}
		else if(numstr.match(new RegExp(sub6.source+(decimal)+sub2.source))){
		}
		else{
			return false;
		}
	}
	for (i = 0; i < valoresOK.length; i++) {      
		if( valoresOK[i]==charCode){
			return true;
		}
	}

	if (charCode < 48 || charCode > 57){
		return false;
	}
	else{
		return true;
	}              
}
  
  //
  function soloNumerosEntero(e, tipo, id,entero,decimal){
		var TIPO_NUMERO_ENTERO  = 1;
		var TIPO_NUMERO_DECIMAL = 2;
		var charCode;
		if (navigator.appName == "Netscape"){
		   charCode = e.which;
		}
		else{
		   charCode = e.keyCode;
		}
		if( tipo==TIPO_NUMERO_ENTERO ){
			var valoresOK = new Array(
							'8' ,/*backspace*/
							'32',/*space*/ 
							''
							);
		}

		if( tipo==TIPO_NUMERO_DECIMAL ){
			var valoresOK = new Array(
							'8' ,/*backspace*/
							//'32',/*space*/ 
							'46',/*punto*/
							'0',
							''
							);
		}
		var elem = document.getElementById(id); 
		var numstr = trim(elem.value);
		var punto = ".";
		var longitudEntera=0;
		var incremento=1; 
		var cantidadPuntos=0;
		for (var i = 0 ; i < numstr.length ; i++){
			if (punto.indexOf(numstr.substr(i,1)) == 0){
				if(cantidadPuntos>0){
					incremento=0;
				}
				cantidadPuntos = cantidadPuntos+1;
			}
			longitudEntera = longitudEntera+incremento;
		} 
		if(longitudEntera>entero && cantidadPuntos==0 && charCode != '46' && charCode != '8'){
			return false;
		}
		if( (cantidadPuntos > 1) || (cantidadPuntos==1 && charCode =='46') ){
			return false;
		}
		else if(numstr != "" && charCode != '8'){
			var sub1 = /^\d{1,/;
			var sub2 = /}$/;
			var sub3 = /}[.]$/;
			var sub4 = /}[.]\d{1,/;
			var sub5 = /^[.]$/;
			var sub6 = /^[.]\d{1,/;
			if( charCode == '46' || numstr.match(new RegExp(sub1.source+(entero)+sub2.source)) ) {
			}
			else if(numstr.match(new RegExp(sub1.source+entero+sub3.source))){
					if(charCode == '46'){
					return false;
			   }
			}
			else if(numstr.match(new RegExp(sub1.source+entero+sub4.source+(decimal)+sub2.source))){
			}
			else if(numstr.match(new RegExp(sub5.source))){
			}
			else if(numstr.match(new RegExp(sub6.source+(decimal)+sub2.source))){
			}
			else{
				return false;
			}
		}
		for (i = 0; i < valoresOK.length; i++) {      
			if( valoresOK[i]==charCode){
				return true;
			}
		}

		if (charCode < 48 || charCode > 57){
			return false;
		}
		else{
			return true;
		}              
	}
  //
  
  function soloNumerosDecimalescopia(e, tipo, id,entero,decimal) {
	   console.debug('ingresando a decimales formateo');
	    var field = document.getElementById(id);
	    key = e.keyCode ? e.keyCode : e.which;	   
	    // backspace
	    if (key == 8) {
	    	 console.debug('hola 1:  ');
	    	return true;
	    }	  
	    // 0-9 a partir del .decimal  
	    if (field.value != "") {
	        if ((field.value.indexOf(".")) > 0) {
	            //si tiene un punto valida dos digitos en la parte decimal
	            if (key > 47 && key < 58 ) {
	                if (field.value == ""){
	                	 console.debug('hola 1');
	                	return true;
	                }              
	                regexp = /[0-9]{2}$/;
	                return !(regexp.test(field.value));
	            }         
	        }        
	    }
	    // 0-9 
	    if (key > 47 && key < 58) {
	        if (field.value == "") return true;
	        regexp = /[0-9]{7}/;      
	        return !(regexp.test(field.value));
	    } 
	    // .
	    if (key == 46) {
	        if (field.value == "") return false;
	        regexp = /^[0-9]+$/;
	        return regexp.test(field.value);
	    }
	    if(key==9){
	    	//tab cambia focus
	    	return true;
	    }else{
	    	 // other key
		    return false;	
	    }	   
	}  
  
  function soloNumerosDecimales2(e, tipo, id,entero,decimal) {
	   console.debug('ingresando a decimales formateo');
	    var field = document.getElementById(id);
	    var TIPO_NUMERO_ENTERO  = 1;		
	    key = e.keyCode ? e.keyCode : e.which;
	    console.debug('KEY : '+key);
	    // backspace
	    if (key == 8) {	    	 
	    	return true;
	    }	   
	    //teclas flecha izquierza y flecha derecha
	    if (key == 37 || key == 39) {	    	 
	    	return true;
	    }	
	    
		if( tipo==TIPO_NUMERO_ENTERO ){
			// 0-9 
		    if (key > 47 && key < 58) {
		        if (field.value == "") return true;
		        regexp = /[0-9]{7}/;      
		        return !(regexp.test(field.value));
		    } 
		    // .
		    if (key == 46) {
		         return false;	       
		    }
		    if(key==9){
		    	//tab cambia focus
		    	return true;
		    }else{
		    	 // other key
			    return false;	
		    }	   
		}else{
			// 0-9 a partir del .decimal  
		    if (field.value != "") {
		    	console.debug('ingrensado a field !=');
		        if ((field.value.indexOf(".")) > 0) {
		            //si tiene un punto valida dos digitos en la parte decimal
		            if (key > 47 && key < 58 ) {		            	
		                if (field.value == ""){		                	
		                	return true;
		                }              
		                regexp = /[0-9]{2}$/;
		                if(!(regexp.test(field.value))){		                	
		                	return true;
		                }else{
		                	var elem = field.value.split('.');	
		                	var suma = parseInt(elem[1].length);
		                	console.debug('ingrensado value: '+ suma);
	                	    if(suma ==2 && field.value=='0.00'){	              		  
	                	 	  return true;
	                	    }	                	   
		                	return false;	
		                }             
		            }         
		        }        
		    }
		    // 0-9 
		    if (key > 47 && key < 58) {
		    	console.debug('ingrensado a bloque numeros 1');
		        if (field.value == "") return true;
		        regexp = /[0-9]{7}/;      
		        return !(regexp.test(field.value));
		    } 
		    // .
		    if (key == 46) {
		        if (field.value == "") return false;
		        regexp = /^[0-9]+$/;
		        return regexp.test(field.value);
		    }
		    if(key==9){
		    	//tab cambia focus
		    	console.debug('ingrensado a cambio de focus');
		    	return true;
		    }else if (key ==50){
		    	console.debug('ingrensado a bloque de numeros');
		    	return true;
		    }else{
		    	 // other key
			    return false;	
		    }	   	
		}    
	}  
  
  
  //cambios 02/02/2015 euclides
  //VARIABLE GLOBAL
  var textoAnterior = '';
  //ESTA FUNCIÓN DEFINE LAS REGLAS DEL JUEGO
  function cumpleReglas(simpleTexto)
      {
          //la pasamos por una poderosa expresión regular
          var expresion = new RegExp("^(|([0-9]{1,7}(\\.([0-9]{1,2})?)?))$");

          //si pasa la prueba, es válida
          if(expresion.test(simpleTexto))
              return true;
          return false;
      }//end function checaReglas

  //ESTA FUNCIÓN REVISA QUE TODO LO QUE SE ESCRIBA ESTÉ EN ORDEN  
  //function revisaCadena(textItem)
  function soloNumerosDecimales(e, tipo, id,entero,decimal)
      {
	    var textItem = document.getElementById(id);
	    var TIPO_NUMERO_ENTERO  = 1;
	    key = e.keyCode ? e.keyCode : e.which;
	    if( tipo==TIPO_NUMERO_ENTERO ){
	    	 // backspace
		    if (key == 8) {	    	 
		    	return true;
		    }	   
		    //teclas flecha izquierza y flecha derecha
		    if (key == 37 || key == 39) {	    	 
		    	return true;
		    }	
			// 0-9 
		    if (key > 47 && key < 58) {
		        if (textItem.value == "") return true;
		        regexp = /[0-9]{7}/;      
		        
		        var sub1 = /[0-9]{/;
				var sub2 = /}/;
				
		        return !((sub1.source+entero+sub2.source).test(textItem.value));
		    } 
		    // .
		    if (key == 46) {
		         return false;	       
		    }
		    if(key==9){
		    	//tab cambia focus
		    	return true;
		    }else{
		    	 // other key
			    return false;	
		    }	   
		}else{
			  //si comienza con un punto, le agregamos un cero
	          if(textItem.value.substring(0,1) == '.') 
	              textItem.value = '0' + textItem.value;

	          //si no cumples las reglas, no te dejo escribir
	          if(!cumpleReglas(textItem.value))
	              textItem.value = textoAnterior;
	          else //todo en orden
	              textoAnterior = textItem.value;
		}       
      }//end function revisaCadena  
   
  
function trim( cadena ){
	return cadena.replace(/^\s*|\s*$/g, "");
}
function validaAnio(id){
	var elem = document.getElementById(id); 
	var numstr = trim(elem.value);
	if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){  
   		alert('Ingrese un año válido');
		return false;  
    }  
}
function rellenarEspacios(cadena,tamanio){ 
	var i; 
	//var m=cadena.length;
	for( i=0; i<tamanio-cadena.length; i++) 
		cadena=cadena+" "; 
	return cadena;
}
function completarCerosIzq(cadena,longitud) {
	cadena = cadena.toString();
    while( cadena.length < longitud )
    	cadena = "0"+cadena;
    return cadena;
}

function validaFechaDDMMAAAA(fecha){
	var dtCh= "/";
	var minYear=1900;
	var maxYear=2100;
	function isInteger(s){
		var i;
		for (i = 0; i < s.length; i++){
			var c = s.charAt(i);
			if (((c < "0") || (c > "9"))) return false;
		}
		return true;
	}
	function stripCharsInBag(s, bag){
		var i;
		var returnString = "";
		for (i = 0; i < s.length; i++){
			var c = s.charAt(i);
			if (bag.indexOf(c) == -1) returnString += c;
		}
		return returnString;
	}
	function daysInFebruary (year){
		return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
	}
	function DaysArray(n) {
		for (var i = 1; i <= n; i++) {
			this[i] = 31;
			if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;}
			if (i==2) {this[i] = 29;}
		}
		return this;
	}
	function isDate(dtStr){
		var daysInMonth = DaysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strDay=dtStr.substring(0,pos1);
		var strMonth=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
		if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
		for (var i = 1; i <= 3; i++) {
			if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
		}
		month=parseInt(strMonth);
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){
			return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
			return false;
		}
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			return false;
		}
		if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
			return false;
		}
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
			return false;
		}
		return true;
	}
	if(isDate(fecha)){
		return true;
	}else{
		return false;
	}
}

function completarNumeroBlanco(id){	
	var elementa = document.getElementById(id);		
	var conversion = Number(elementa.value);
	if( isNaN(conversion)){
			elementa.value = '0';
	}else{
		elementa.value = Number(elementa.value);
	}	
}

function validarAnioInicioVig(anioInicio){
  var numstr = trim($('#anoIniVigencia').val());
   if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){			
	   return false;
   }else{
	   return true;
   }	
}

function validarAnioFinVig(anioFin){	
	var numstr = trim($('#anoFinVigencia').val());
	if (isNaN(numstr) || numstr.length<4 || parseFloat(numstr)<1900){	
		return false;
	}else{
		return true;
	}
}


function isNumeric_copia(input) {
	$(input).blur(function() {
		var number = $(this).val();
		if ($.isNumeric(number)) {
			$(this).val(number);
		} else {
			$(this).val("");
		}
	});

}

function isNumeric(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {       
        return false;
    }    
    return true;
}

function letras(input){
	$(input).blur(function(ev) {
		var number = $(this).val();
			number = number.replace(/[0-9]/g, '');
			$(this).val(number);
		
	});
}

	

function soloLetras(e){
   key = e.keyCode || e.which;
   tecla = String.fromCharCode(key).toLowerCase();
   letras = " áéíU+00F3úabcdefghijklmnñopqrstuvwxyz1234567890";
   especiales = "8-37-39-46";
   console.debug("tecla  :  "+key);
   tecla_especial = false;
   for(var i in especiales){
        if(key == especiales[i]){
            tecla_especial = true;
            break;
        }
    }

    if(letras.indexOf(tecla)==-1 && !tecla_especial){
        return false;
    }
}
//funcion para validar alfanumeicos
//\u00f1\u00d1 ñÑ
//\u00e1-\u00fa\u00c1-\u00da á-úÁ-Ú --  \u0022="  \u000A= salto de linea
function validarLetra(campo) {	
	cadena = "^[A-z0-9:,;. \u000A \u00dc \u00DC \u00FC \u00fc \u0027 \u0022 \u00f1\u00d1 \u00e1-\u00fa\u00c1-\u00da-]{1,600}$";	
	var re = new RegExp(cadena);
	if (campo.match(re))
		return true;
	else
		return false;
}

function validarCargoFijoNumero(valor){
	if(valor!=null && parseInt(valor)+0==0){
		return true;
	}else{
		return false;
	}
}

function validarCargoFijoFloat(valor){	
	if(valor!=null && parseFloat(valor)+ 0.0 == 0.0){
		return true;
	}else{
		return false;
	}
}



