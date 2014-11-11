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

  function soloNumerosDecimales(e, tipo, id,entero,decimal){
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
		var sub4 = /}[.]\d{1,/
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