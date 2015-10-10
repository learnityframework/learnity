//function to build the string of correct answers and the user answers(Ramu)
var ansArray=new Array()//Global Array for Storing the User Answers
var quesArray=new Array()//Global Array for Storing the Question Number User Answered
var markArray=new Array()
var FinalString
var timeleft
var timeleftr
var resStat
var test
resStat=0
FinalString=""
function buildAnsString(quesno,ans,ele)
	{
	//alert(quesno + "   " + ans)
		var arrCnt
		arrCnt=quesno.substr(1)
		ansArray[ele-1]=ans
		quesArray[ele-1]=quesno.substr(1)
	}

function setArrayLength(startq,endq,arrLen)	
	{
	//alert(arrLen)
		ansArray.length=arrLen+1
		quesArray.length=arrLen+1
		var q_start
		q_start=startq
		for(var i=0;i<arrLen+1;i++)
			{
				ansArray[i]="O"
				quesArray[i]=q_start
				q_start=parseInt(q_start)+1
			}
	}

function buildSectionString()
	{
				FinalString=""
				loopCnt=document.frmsec.noQues.value
				
				
  				for(var Cnt=0;Cnt<loopCnt;Cnt++)
	 				{
	 					if(FinalString=="")
	 						{
								FinalString=quesArray[Cnt] + "$" + ansArray[Cnt]
	 						}
	 					else
	 						{
	 							FinalString= FinalString + "#" + quesArray[Cnt] + "$" + ansArray[Cnt]
							}	
	 				}
	 			//	alert(FinalString)
	 			document.frmsec.fString.value=FinalString
	}
	
/*function submitPage()
	{
		window.document.frmsec.hddTimeLeft.value=timeleft
		buildSectionString()
		window.document.frmsec.action="../online_exam_performance_eam.asp"
		window.document.frmsec.submit()
	}
*/
/*function gotoResult()
	{
		buildSectionString()
		window.document.frmsec.hddTimeLeft.value=timeleft
		loopCnt=document.frmsec.noQues.value
		
	}*/
function gotoNextSection()
	{
		buildSectionString()
		window.document.frmsec.hddTimeLeft.value=timeleft
		loopCnt=document.frmsec.noQues.value
		window.document.frmsec.action="/includes/navpgbr.asp"
		window.document.frmsec.submit()
	
	}
function nextpage(nextpage)
	{

		buildSectionString()
//		window.document.forms["frmsec"].hddTimeLeft.value=timeleft
		window.document.frmsec.hddTimeLeft.value=timeleft
		//alert(timeleft)
		loopCnt=document.frmsec.noQues.value
		window.document.frmsec.action=nextpage + ".asp"
		window.document.frmsec.submit()
	}
function setTime(time_Left)
	{
		timeleftr=time_Left
		countdown()
	}

function counter()
	{
		  timeleftr=timeleftr - 0.25;
		setTimeout("counter()",15000);
                                         
	}

function sb()
	{
		if (timeleftr>0)
			{                   timeleft =Math.round(timeleftr)
				defaultStatus="TIME LEFT : "+timeleft+" MINUTES";
				setTimeout("sb()",30000);
			}
		else
			{
				alert("The Time Alloted for this Section has Elapsed. The section is Over now")
				submitPage()	
			}
	}
function countdown()
	{
		sb();
		counter();
	}

/*function topframefill(current_pageName,NoQues)
{
	//parent.frames[0].location.href="testTopPage.asp?currentPageName=" + current_pageName
	setArrayLength(NoQues);
}*/


var message="No right click please....."; 


function click(e) 
{
	if(document.all)
		{	
			if (event.button == 2 || event.button==3) 
			   {
					alert(message);
					return false;
			   }
		}

	if (document.layers) 
		{
			if (e.which == 3)			
				{	
					alert(message);
					return false;
				}
		}
}

if (document.layers) 
{
	document.captureEvents(Event.MOUSEDOWN);
}
document.onmousedown=click;

function trapKey() 
{
	var browsername=navigator.appName
	if(browsername=="Microsoft Internet Explorer")
		{
			if (document.all) 
				{
					if(event.keyCode==8||event.keyCode==116||event.keyCode==17||event.keyCode==18||event.keyCode==93)
						{
							alert("Operation not allowed")
							event.returnValue=false;
							event.cancelBubble=true;
						}
				}
		}
	if(browsername=="Netscape")
		{
			if (document.layers) 
				{
					if(event.which==8||e.which==116||e.which==17||e.which==18||e.which==93)
						{
							e.returnValue=false;
						}
				}
		}
}