 
 package interfaceenginev2;

import javax.servlet.http.HttpServletRequest;

import comv2.aunwesha.lfutil.Pair;


/**
 * 
 * @author Partha Pratim Dutta
 */
 
 
 public interface ValidatorFunction
{  
	

	public Pair<Boolean,String> validateadd(HttpServletRequest request);
	
	public Pair<Boolean,String> validateedit(HttpServletRequest request);
	
	public Pair<Boolean,String> validatedelete(HttpServletRequest request);



}
 





