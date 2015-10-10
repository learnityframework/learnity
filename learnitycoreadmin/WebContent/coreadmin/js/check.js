//helppane.js Version 1.7
ie = document.all?1:0;
ns4 = document.layers;
var index = 0;
function CCA(){
	/*if (ie) {
	}
	else {
		for (var i=0;i<document.frm.elements.length;i++){
			var e = document.frm.elements[i];
			if (e.type=='radio'){
				e.checked=false;
			}
		}
	}*/
	for (var i=0;i<document.frm.elements.length;i++){
		var e = document.frm.elements[i];
		if (e.type=='radio'){
			if (e.checked)
				hL(e);
			else
				dL(e);
		}
	}
	
}

function hL(E){
	if (ie){
		while (E.tagName!="TR"){
			E=E.parentElement;
		}
	}
	else{
		while (E.tagName!="TR"){
			E=E.parentNode;
		}
	}
	E.className = "H";
}

function dL(E){
	if (ie){
		while (E.tagName!="TR"){
			E=E.parentElement;
		}
	}
	else{
		while (E.tagName!="TR"){
			E=E.parentNode;
		}
	}
	E.className = "";
}


function scrollit(seed) {
	var m1 = "Powered by LearnITy!";
	var msg=m1;
	var out = " ";
	var c = 1;
	if (seed > 100) {
		seed--;
		cmd="scrollit("+seed+")";
		timerTwo=window.setTimeout(cmd,100);
	}
	else if (seed <= 100 && seed > 0) {
		for (c=0 ; c < seed ; c++) {
			out+=" ";
		}
		out+=msg;
		seed--;
		window.status=out;
		cmd="scrollit("+seed+")";
		timerTwo=window.setTimeout(cmd,100);
	}
	else if (seed <= 0) {
		if (-seed < msg.length) {
			out+=msg.substring(-seed,msg.length);
			seed--;
			window.status=out;
			cmd="scrollit("+seed+")";
			timerTwo=window.setTimeout(cmd,100);
		}
		else {
			window.status=" ";
			timerTwo=window.setTimeout("scrollit(100)",75);
		}
	}

}

function setfocus(txt) {

	txt.focus();
}

function fnCheckNull(astrFieldValue,astrFieldName)
{
	
	if (fnStripSpaces(astrFieldValue) == "") 
	{
		alert("Please enter "+astrFieldName+".");
		return false;
	}
	else 
	{
		return true;
	}	
}

function fnStripSpaces (astrString)  
{		      
var astrOutString;
var astrTempChar;
		astrOutString="";
        for ( Count=0 ; Count < astrString.length ; Count++ )  
	{
            astrTempChar=astrString.substring (Count, Count+1);
            if ( astrTempChar != " " )
                 astrOutString=astrOutString+astrTempChar;
        }
        return (astrOutString);
}

function fnCheckName(astrFieldValue,astrFieldName)
{		      
	return fnCheckAlphabeticHypAposSpace(astrFieldValue,astrFieldName);
}
function fnCheckAlphabeticHypAposSpace(astrFieldValue,astrFieldName)
{
	var lstrRefString="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ '-.";
	var lstrInString = astrFieldValue;
	if (lstrInString.length != 0)	
	{
		for (Count=0; Count < lstrInString.length; Count++)
		{
			if (lstrRefString.indexOf(lstrInString.substring (Count, Count+1)) == -1)
			{
				alert("Please enter a valid "+astrFieldName+".");
				return(false);
			}
		}
		return(true);
	}
return(true);
}

function checkEmail(myForm) {
if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(myForm.Email_Id.value)){
return (true)
}
//alert("Invalid E-mail Address! Please re-enter.")
return (false)
}

function isInteger(value) {
  return (parseInt(value) == value);
}
function isFloat(value) {
  return (parseFloat(value) == value);
}

function fnValidate(doc)
{
   if(!fnCheckNull(document.frm.FirstName.value,"First Name"))
   {
     document.frm.FirstName.focus();
     return false;
   }
/*   if(!fnCheckName(document.frm.FirstName.value,"First Name"))
   {
     document.frm.FirstName.focus();
     return false;
   } */
 //  if((fnStripSpaces(document.frm.MiddleName.value) != "") && !fnCheckName(document.frm.MiddleName.value,"Middle Name")){
   /*if((fnStripSpaces(document.frm.MiddleName.value) != "")){
     document.frm.MiddleName.focus();
     return false;
}*/
  if(!fnCheckNull(document.frm.LastName.value,"Last Name"))
   {
     document.frm.LastName.focus();
     return false;
   }
/*   if(!fnCheckName(document.frm.LastName.value,"Last Name"))
   {
     document.frm.LastName.focus();
     return false;
   } */
  
  if((fnStripSpaces(document.frm.age.value) != "") && (!isInteger(document.frm.age.value))) {
	alert("Please enter a valid Age.");
	document.frm.age.focus();
	return false;
  }
  if((fnStripSpaces(document.frm.Experience.value) != "") && (!isFloat(document.frm.Experience.value))) {
	alert("Please enter a valid Experience.");
	document.frm.Experience.focus(2);
	return false;
  }
  if((fnStripSpaces(document.frm.Email_Id.value) != "") && !checkEmail(document.frm)) {
		alert("Please enter a valid E-mail address.");
		document.frm.Email_Id.focus();
		return false;
  } 
  if(!fnCheckNull(document.frm.Userid.value,"User Id"))
   {
     document.frm.Userid.focus();
     return false;
   }
   //if(!fnCheckName(document.frm.Userid.value,"User Id"))
   //{
   //  document.frm.Userid.focus();
   //  return false;
   //}
   if(!fnCheckNull(document.frm.Password.value,"Password"))
   {
     document.frm.Password.focus();
     return false;
   }
   //if(!fnCheckName(document.frm.Password.value,"Password"))
   //{
   //  document.frm.Password.focus();
   //  return false;
   //}
   if(document.frm.Password.value.length < 6)
   {
     alert("Your password must be at least six characters and may contain numbers, uppercase and lowercase letters, and standard symbols. Do not include spaces or international characters.");
     document.frm.Password.focus();
     return false;
   }
   if(!fnCheckNull(document.frm.ConfirmedPassword.value,"Confirmed Password"))
   {
     document.frm.ConfirmedPassword.focus();
     return false;
   }
   
   if(document.frm.ConfirmedPassword.value.length < 6)
   {
     alert("Your password must be at least six characters and may contain numbers, uppercase and lowercase letters, and standard symbols. Do not include spaces or international characters.");
     document.frm.ConfirmedPassword.focus();
     return false;
   }
   if(document.frm.Password.value != document.frm.ConfirmedPassword.value)
   {
     alert("Password and Confirmed Password mismatched");
     document.frm.Password.value = "";
     document.frm.ConfirmedPassword.value = "";
     document.frm.Password.focus();
     return false;
   }
   return true;
}

function fnClick()
{
if(fnValidate())
  {	
	document.frm.submit();
  }
 else
  return false;
  
}
