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


