package interfaceenginev2.dashboard;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.ChartWithoutAxes;
import org.eclipse.birt.chart.model.DialChart;
import org.eclipse.birt.chart.model.attribute.Anchor;
import org.eclipse.birt.chart.model.attribute.AxisType;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.DataPoint;
import org.eclipse.birt.chart.model.attribute.DataPointComponent;
import org.eclipse.birt.chart.model.attribute.DataPointComponentType;
import org.eclipse.birt.chart.model.attribute.Fill;
import org.eclipse.birt.chart.model.attribute.IntersectionType;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.LineAttributes;
import org.eclipse.birt.chart.model.attribute.LineDecorator;
import org.eclipse.birt.chart.model.attribute.LineStyle;
import org.eclipse.birt.chart.model.attribute.MarkerType;
import org.eclipse.birt.chart.model.attribute.Position;
import org.eclipse.birt.chart.model.attribute.RiserType;
import org.eclipse.birt.chart.model.attribute.TickStyle;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointComponentImpl;
import org.eclipse.birt.chart.model.attribute.impl.DataPointImpl;
import org.eclipse.birt.chart.model.attribute.impl.GradientImpl;
import org.eclipse.birt.chart.model.attribute.impl.InsetsImpl;
import org.eclipse.birt.chart.model.attribute.impl.JavaNumberFormatSpecifierImpl;
import org.eclipse.birt.chart.model.attribute.impl.LineAttributesImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.DialRegion;
import org.eclipse.birt.chart.model.component.MarkerLine;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.DialRegionImpl;
import org.eclipse.birt.chart.model.component.impl.MarkerLineImpl;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataElementImpl;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.impl.ChartWithoutAxesImpl;
import org.eclipse.birt.chart.model.impl.DialChartImpl;
import org.eclipse.birt.chart.model.layout.Legend;
import org.eclipse.birt.chart.model.layout.Plot;
import org.eclipse.birt.chart.model.type.AreaSeries;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.DialSeries;
import org.eclipse.birt.chart.model.type.LineSeries;
import org.eclipse.birt.chart.model.type.PieSeries;
import org.eclipse.birt.chart.model.type.ScatterSeries;
import org.eclipse.birt.chart.model.type.impl.AreaSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.DialSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.LineSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.PieSeriesImpl;
import org.eclipse.birt.chart.model.type.impl.ScatterSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.ReportEngine;
import org.grlea.log.SimpleLogger;
//import org.eclipse.birt.chart.log.impl.*;
//import org.eclipse.birt.chart.device.pdf.PDFRendererImpl;
/*import com.ibm.icu.util.ULocale;*/

public class chart3 extends HttpServlet {
	private static final SimpleLogger log = new SimpleLogger(chart3.class);
	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);
	}
	
	static String chartdimen = "";
        static String student_id = "";
        
        static String course_id = "";
        static String argument1 = "";
        static String argument2 = "";
        static String argument3 = "";
        static String argument4 = "";
        static String argument5 = "";
        static String argument6 = "";
        private IDeviceRenderer idr = null;

    private Chart cm = null;   
       
  //static String course_id="";
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {   
    	//System.setProperty("-DSTANDALONE","true");
    	HttpSession mysession = request.getSession(false); //Obtaining the session variable
	student_id = (String)mysession.getAttribute("student_id"); //Obtaining student_id from session	
	course_id  = (String)mysession.getAttribute("course_id");  //Obtaining course_id from session
	argument1 = request.getParameter("argument1");
	argument2 = request.getParameter("argument2");
	argument3 = request.getParameter("argument3");
	argument4 = request.getParameter("argument4");
	argument5 = request.getParameter("argument5");
	argument6 = request.getParameter("argument6");	
	System.out.println("argument1,argument2,argument3,argument4,argument5,argument6---:"+argument1+" , "+argument2+" , "+argument3+" , "+argument4+" , "+argument5+" , "+argument6);
       // chartdimen = request.getParameter("dimen");	
        //System.out.println("======== dimension ====="+chartdimen);
	String dashboradType=request.getParameter("id");
	String interface_id=request.getParameter("IID");
	String part_id=request.getParameter("part_id");
	System.out.println("==============interface_id,part_id===================="+interface_id+" , "+part_id);
	

     String realpath = getServletContext().getRealPath("/");
     System.out.println("==============path===================="+realpath);
     
     Vector DashboardDetails =NewDataBaseLayer.getmetdetails(interface_id,part_id);
      int height=300;
      int width=300;
      Vector DashboardLayoutDetails =NewDataBaseLayer.getmetLayoutdetails(interface_id,part_id); 
        for(int i=0;i<DashboardLayoutDetails.size();i=i+2){
        int www=(Integer)DashboardLayoutDetails.elementAt(i);
        if(www<300)
        width=width+300;
        else
        width=width+www;
        
        int hhh=(Integer)DashboardLayoutDetails.elementAt(i+1);
        if(hhh<300)
        height=height+300;
        else
        height=height+hhh;	
        }
      
        BufferedImage bi = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

 
       EngineConfig config = new EngineConfig( );
	
		config.setEngineHome( realpath+"WEB-INF/platform" );
		
		ReportEngine engine = new ReportEngine( config );
        //IDeviceRenderer idr = new PDFRendererImpl();

        
        try{
        	
        	IDeviceRenderer idr = PluginSettings.instance( ) .getDevice( "dv.SWING" );
        	
        	
        	 
        	 Bounds ooo[]=new Bounds[DashboardDetails.size()];
        	//Bounds boi = BoundsImpl.create( 0, 0, 450, 300 );
        	 RunTimeContext rtc = new RunTimeContext( );
                 //rtc.setULocale( ULocale.getDefault( ) );
             	rtc.setLocale( Locale.getDefault( ) );
            
             	Generator gr = Generator.instance( );
             	//GeneratedChartState gcs = null;
             	//gcs = gr.build( idr.getDisplayServer( ), cm, boi, null, rtc, null );
             	//idr.setProperty( IDeviceRenderer.GRAPHICS_CONTEXT, bi.getGraphics() );
             	//gr.render( idr, gcs );
             	idr.setProperty( IDeviceRenderer.GRAPHICS_CONTEXT, bi.getGraphics() );
            	idr.setProperty( IDeviceRenderer.CACHED_IMAGE, bi );
        	 
        	  
        int l=0;
       		int m=0;
       		int j=0;
       		//int k=0;
       			 
       	int n=20;
       	int p=0;
       	int g=20;
       	int b=0;
       	int a=0;
       	
       if(DashboardDetails!=null){
       	for(int i=0;i<DashboardDetails.size();i++){
       		String DashValue[]=(String[])DashboardDetails.elementAt(i);
		System.out.println("DashboardDetails---i-------------"+i);
       		 l=Integer.parseInt(DashValue[10]);
       		 if(l==0)l=300;
       		 m=Integer.parseInt(DashValue[11]);
       		 if(m==0)m=300;	
       		
       		int k=0;
       		   	
       		if(i%2==0){
       		j=0;
       		//n=n+m+20;
       		if(i==0)
       		k=p;
       		else{
       		k=p+n;
       		n=n+20;
       		}
       		p=p+m;
       		b=l;
       			}
       		else{ 
       		if(i==1){
       		k=0;
       		//j=20+m;  
       		j=20+b;         				
       		}else {
       		//k=g+p-m; 
       		k=g+a;    		
       		//j=20+m;
       		j=20+b;    
       		g=g+20;   
       		System.out.println("k=="+k+"j=="+j+"m=="+m);  
       		
       		}
       		a=a+m;
       		}
       		
       		
       		Bounds bo = BoundsImpl.create(j,k,l,m);
       		bo.scale(72d / idr.getDisplayServer().getDpiResolution());
       		ooo[i]=bo;
       		
       		
       		
      
       	String strChartType=DashValue[0];
       	String sqltextdata=DashValue[1];
       	String sqlnumberdata=DashValue[2];
       	String sqlnumberdata2=DashValue[3];
       	String sqlnumberdata3=DashValue[4];
       	String sqllegenddata=DashValue[5];
       	String sqllegenddata2=DashValue[6];
       	String sqllegenddata3=DashValue[7];
       	String sqlsubtype=DashValue[8];
       	
       	String chartheading=DashValue[9];
       	String YaxisTitle=DashValue[12];
       	String XaxisTitle=DashValue[13];
       	int Bcolor=Integer.parseInt(DashValue[14]);
       	String transpose=DashValue[15];
       	String stacked=DashValue[16];
	String dimension=DashValue[17];
       System.out.println("6666666666666666666666666666666666666666666666666666666666666677777777777"+sqlsubtype);

       		if(strChartType.equals("barchart")){
       			        if(sqlsubtype.equals("sidebyside")) {
        
				Chart cm1 =null;
				
				
				if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createCFBarChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
				}
				
				else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createCFBarChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				}
				else
				{
				//cm1=createCFBarChart( );
				cm1=createCFBarChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				}
				
			
		// cm//1.setType("Bar Chart");
			//cm1.setSubType("STACKED_SUBTYPE_LITERAL");
			//
			//System.out.println("............................................................."+cm1.getType());
			if(dimension.equals("TwoDimensionalwithdepth"))
				{
					cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);
				//gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
				}
			else if(dimension.equals("TwoDimensional"))
				{}
			else
				{}
				//cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);
				gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
				
        	           } 
        	           
      				else if(sqlsubtype.equals("percentstacked")) {
        
				Chart cm1 =null;
				
				
				if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createPercentStackedChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
				}
				
				else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createPercentStackedChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				}
				else
				{
				//cm1=createCFBarChart( );
				cm1=createPercentStackedChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				}
				
			
		// cm//1.setType("Bar Chart");
			//cm1.setSubType("STACKED_SUBTYPE_LITERAL");
			//
			//System.out.println("............................................................."+cm1.getType());
				if(dimension.equals("TwoDimensionalwithdepth"))
				{
					cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
					gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
				}
				else if(dimension.equals("TwoDimensional"))
				{}
				else
				{}
				
				//cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
				//gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
        	           }                               	                                                    
        	
                     	else {
       			//Chart cm1=createStackedMultiBarChart();
       			Chart cm1 =null;
       			
       			
       			if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createStackedChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
				//cm1 =createMyChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
				}
       			
       			else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
				{
				cm1 =createStackedChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				//cm1 =createMyChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				}
       			else
				{
				cm1=createStackedChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				// cm1=createMyChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				
				}
        		
	          
	       // cm//1.setType("Bar Chart");
	             //cm1.setSubType("STACKED_SUBTYPE_LITERAL");
	            //
				
				if(dimension.equals("TwoDimensionalwithdepth"))
				{
					cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
					gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
				}
				else if(dimension.equals("TwoDimensional"))
				{}
				else
				{}
				
	             	System.out.println("............................................................."+cm1.getType());
        		//cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
        	 	//gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
        		}
        	}
        	if(strChartType.equals("line")){
        		if(sqlsubtype.equals("percentstacked")) {
                          Chart cm =null;
                     if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
       	                   {
       	                    cm =createPercentLineChart2(sqltextdata,sqlnumberdata,sqllegenddata,chartheading,YaxisTitle,XaxisTitle,Bcolor);	
                           }
       			
                     else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
       	                   {
       	                    cm =createPercentLineChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,chartheading,YaxisTitle,XaxisTitle,Bcolor);
       	                   }
                     else
                           {
                            cm =createPercentLineChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,chartheading,YaxisTitle,XaxisTitle,Bcolor);
       	                   }
        		
			   if(dimension.equals("TwoDimensionalwithdepth"))
			   {
				   cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
				   gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
			   }
			   else if(dimension.equals("TwoDimensional"))
			   {}
			   else
			   {}
			   
// 			   cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
        			
        		}
        		else {
        		Chart cm =null;
                     if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
       	                   {
       	                    cm =createLineChart2(sqltextdata,sqlnumberdata,sqllegenddata,chartheading,YaxisTitle,XaxisTitle,Bcolor);	
                           }
       			
                     else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
       	                   {
       	                    cm =createLineChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,chartheading,YaxisTitle,XaxisTitle,Bcolor);
       	                   }
                     else
                           {
                            cm =createLineChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,chartheading,YaxisTitle,XaxisTitle,Bcolor);
       	                   }
        		
			   if(dimension.equals("TwoDimensionalwithdepth"))
			   {
				   cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
         	 		   gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
			   }
			   else if(dimension.equals("TwoDimensional"))
			   {}
			   else
			   {}
			   
// 			   cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
        	 	
        	 }
        	}
        	 if(strChartType.equals("pie")){
        	 	Chart cm =createPieChart(sqltextdata,sqlnumberdata,chartheading,Bcolor);
        		
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
			cm.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
// 			cm.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
        	}
        	
        	if(strChartType.equals("meter")){
        	 	Chart cm =createSDialSRegionChart(sqltextdata,sqlnumberdata,chartheading,Bcolor);
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
			cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
// 			cm.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
        	}
        	
        	if(strChartType.equals("scatter")){
        	 	Chart cm =createScatterChart(sqltextdata,sqlnumberdata,chartheading,Bcolor);
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
				cm.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
				gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
			
//         		cm.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm, null, ooo[i], rtc ));
        	}

       		if(strChartType.equals("areachart")){

       			
       			
       			//Chart cm1 =createCFBarChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
       			Chart cm1 =createAreaChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
        		
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
				cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
				gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
			
// 			cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
//         	 	gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
        	}   
		     	
		
		
		////////////////////// multibar /////////////////////////////
		if(strChartType.equals("multibar")) {
        
			Chart cm1 =null;
				
				
			if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
			{
				cm1 =createMultiYAxisChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
			}
				
			else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
			{
				cm1 =createMultiYAxisChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
			}
			else
			{
				//cm1=createCFBarChart( );
				cm1=createMultiYAxisChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
			}
				
			
		// cm//1.setType("Bar Chart");
			//cm1.setSubType("STACKED_SUBTYPE_LITERAL");
			//
			//System.out.println("............................................................."+cm1.getType());
			
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
				cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
				gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
			
			
			
// 			cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
// 			gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
		} 
		///////////////////////end multibar////////////////////////////////
		if(strChartType.equals("stacked")) {
       			//Chart cm1=createStackedMultiBarChart();
			Chart cm1 =null;
       			
       			
			if(sqllegenddata2.equals("")&& sqlnumberdata2.equals("")&& sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
			{
				cm1 =createStackedChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
				//cm1 =createMyChart2(sqltextdata,sqlnumberdata,sqllegenddata,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);	
			}
       			
			else if(sqllegenddata3.equals("")&& sqlnumberdata3.equals(""))
			{
				cm1 =createStackedChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				//cm1 =createMyChart1(sqltextdata,sqlnumberdata,sqlnumberdata2,sqllegenddata,sqllegenddata2,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
			}
			else
			{
				cm1=createStackedChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				// cm1=createMyChart(sqltextdata,sqlnumberdata,sqlnumberdata2,sqlnumberdata3,sqllegenddata,sqllegenddata2,sqllegenddata3,sqlsubtype,chartheading,YaxisTitle,XaxisTitle,Bcolor,transpose,stacked);
				
			}
        		
	          
	       // cm//1.setType("Bar Chart");
	             //cm1.setSubType("STACKED_SUBTYPE_LITERAL");
			//
			System.out.println("............................................................."+cm1.getType());
			
			if(dimension.equals("TwoDimensionalwithdepth"))
			{
				cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
				gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
			}
			else if(dimension.equals("TwoDimensional"))
			{}
			else
			{}
			
			
			
// 			cm1.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL); 
// 			gr.render(idr,gr.build( idr.getDisplayServer( ), cm1, null, ooo[i], rtc ));
		}
		
        	
        	}
		}
        	
       
        
      }
      catch (ChartException x)
	        {
	       //     System.out.println("***************=="+x);
	            x.printStackTrace();
	        }
	     response.setContentType("image/PNG");
	       ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(bi,"png",sos);
		
		//response.setContentType("image/PNG");
	       // ServletOutputStream sos = response.getOutputStream();
		//PNGEncodeParam encParam = new PNGEncodeParam.Gray();
		//ImageEncoder encoder2=ImageCodec.createImageEncoder ("PNG", sos, encParam);
		//encoder2.encode(bi); 

      
}
      
    //public static Chart createMyChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor)
public static Chart createMyChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
{

	ChartWithAxes cwc = ChartWithAxesImpl.create();
	
	
	if(Transpose.equals("1"))
	{
	cwc.setTransposed(true);
         }
         else
         {
         	cwc.setTransposed(false);
        }
         
	//cwc.setOrientation( Orientation.HORIZONTAL_LITERAL);
      
	//System.out.println("************************************"+ss);
	switch(bcolor){
	case 1:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
	 //    cwc.getBlock().setBackground(
	//ColorDefinitionImpl.WHITE()
	//ColorDefinitionImpl.RED()
	//ColorDefinitionImpl.setBlue(999993);
	//);
	cwc.getBlock().getOutline().setVisible(false);
	
	
	//cwc.setDimension(
	//ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL
	//);
	
	     
	Plot p = cwc.getPlot();
	p.getOutline( ).setStyle( LineStyle.DASH_DOTTED_LITERAL );
	p.getClientArea().setBackground(GradientImpl.create(ColorDefinitionImpl.create(225, 225, 255),
						ColorDefinitionImpl.create(255, 255, 225), -35, false));
	p.getOutline().setVisible(false);
	cwc.getTitle().getLabel().getCaption().setValue(ChartHeading);
	//cwc.getTitle().getLabel().getCaption().setValue("Stacked Bar Chart");
	     
	     Legend lg = cwc.getLegend();
	     lg.getText().getFont().setSize(16);
	     lg.getInsets().set(6, 4, 0, 0);
	     lg.setAnchor(Anchor.NORTH_LITERAL);
	
	   
	     Axis xAxisPrimary = cwc.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwc.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);   
	
	
	     System.out.println("argument1,argument2,argument3,argument4,argument5,argument6---2-----:"+argument1+" , "+argument2+" , "+argument3+" , "+argument4+" , "+argument5+" , "+argument6);
	     
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	System.out.println("strSql ------------"+strSql);
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	System.out.println("strSql1 ------------"+strSql1);
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
	   
	//ArrayList usage =PortalMgmt.portal.NewDataBaseLayer.getUserscoInfo(student_id);
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");	
	
	
	
	String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
	String strSql41=strSql4.replace("argument6","'"+argument6+"'");
	String strSql42=strSql41.replace("argument1","'"+argument1+"'");
	String strSql43=strSql42.replace("argument2","'"+argument2+"'");
	String strSql44=strSql43.replace("argument3","'"+argument3+"'");
	String strSql45=strSql44.replace("argument4","'"+argument4+"'");	
	
	
	//System.out.println(strSql2);
	ArrayList usage =NewDataBaseLayer.getNumberdata(strSql25);  
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata1(strSql35);  
	ArrayList usage2 =NewDataBaseLayer.getNumberdata2(strSql45);
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues3 = NumberDataSetImpl.create(usage2);
	
	
	Series seCategory = SeriesImpl.create();
	seCategory.setDataSet(categoryValues);
	SeriesDefinition sdX = SeriesDefinitionImpl.create();
	//sdX.getSeriesPalette().update(0); 
	xAxisPrimary.getSeriesDefinitions().add(sdX);
	sdX.getSeries().add(seCategory);
	
	BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
	//bs1.setSeriesIdentifier("Unit Times");
	bs2.setSeriesIdentifier(sqlLegendData);
	bs2.setDataSet(orthoValues1);
	bs2.setRiserOutline(null);
	bs2.getLabel().setVisible(true);
	bs2.setLabelPosition(Position.INSIDE_LITERAL);
	
	
	BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
	bs1.setSeriesIdentifier(sqlLegendData2);
	
	bs1.setDataSet(orthoValues2);
	bs1.setRiserOutline(null);
	
	bs1.getLabel().setVisible(true);
	bs1.setLabelPosition(Position.INSIDE_LITERAL);
	
	BarSeries bs3 = (BarSeries) BarSeriesImpl.create();
	bs3.setSeriesIdentifier(sqlLegendData3);
	bs3.setDataSet(orthoValues3);
	bs3.setRiserOutline(null);
	
	bs3.getLabel().setVisible(true);
	bs3.setLabelPosition(Position.INSIDE_LITERAL);
	
	if(Stacked.equals("1"))
	{
        bs1.setStacked(true);
        bs2.setStacked(true);
        bs3.setStacked(true);
        }
        
        else
        {
        	
        bs1.setStacked(false);
        bs2.setStacked(false);
        bs3.setStacked(false);	
        }
	
	
	
	
	
	SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(1); 	
		yAxisPrimary.getSeriesDefinitions().add(sdY);		
		sdY.getSeries().add(bs1);
		sdY.getSeries().add(bs2);
    sdY.getSeries().add(bs3);
    
    
    
  /*  DataPointComponent dpc = DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
				JavaNumberFormatSpecifierImpl.create( "$###,###.00" ) );//$NON-NLS-1$
		bs1.getDataPoint( ).getComponents( ).clear( );
		bs1.getDataPoint( ).getComponents( ).add( dpc );
		bs2.getDataPoint( ).getComponents( ).clear( );
		bs2.getDataPoint( ).getComponents( ).add( dpc );
		bs3.getDataPoint( ).getComponents( ).clear( );
		bs3.getDataPoint( ).getComponents( ).add( dpc );*/
	return cwc;

}

//////////////////////////////////////////LEGEND////////////////////////
public static Chart createMyChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
{

	ChartWithAxes cwc = ChartWithAxesImpl.create();
	
	if(Transpose.equals("1"))
	{
	cwc.setTransposed(true);
         }
         else
         {
         	cwc.setTransposed(false);
        }
	//cwc.setSubType("2");
	//System.out.println("Subtype checked............"+cwc.getSubType());
      
	//System.out.println("************************************"+ss);
	switch(bcolor){
	case 1:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
	 //    cwc.getBlock().setBackground(
	//ColorDefinitionImpl.WHITE()
	//ColorDefinitionImpl.RED()
	//ColorDefinitionImpl.setBlue(999993);
	//);
	cwc.getBlock().getOutline().setVisible(false);
	
	
	//cwc.setDimension(
	//ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL
	//);
	
	     
	Plot p = cwc.getPlot();
	p.getClientArea().setBackground(ColorDefinitionImpl.create(255, 255, 225));
	p.getOutline().setVisible(false);
	cwc.getTitle().getLabel().getCaption().setValue(ChartHeading);
	
	     
	     Legend lg = cwc.getLegend();
	     lg.getText().getFont().setSize(8);
	     lg.getInsets().set(6, 4, 0, 0);
	     lg.setAnchor(Anchor.NORTH_LITERAL);
	
	   
	     Axis xAxisPrimary = cwc.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwc.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);   
	
	
	
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
	   
	//ArrayList usage =PortalMgmt.portal.NewDataBaseLayer.getUserscoInfo(student_id);
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");	
	
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
		
	ArrayList usage =NewDataBaseLayer.getNumberdata(strSql25);  
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata1(strSql35);  
	
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage1);
	
	
	Series seCategory = SeriesImpl.create();
	seCategory.setDataSet(categoryValues);
	
	
	BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
	//bs1.setSeriesIdentifier("Unit Times");
	bs2.setSeriesIdentifier(sqlLegendData);
	bs2.setDataSet(orthoValues1);
	bs2.setRiserOutline(null);
	bs2.getLabel().setVisible(true);
	bs2.setLabelPosition(Position.INSIDE_LITERAL);
	
	
	BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
	bs1.setSeriesIdentifier(sqlLegendData2);
	
	bs1.setDataSet(orthoValues2);
	bs1.setRiserOutline(null);
	
	bs1.getLabel().setVisible(true);
	bs1.setLabelPosition(Position.INSIDE_LITERAL);
	
	if(Stacked.equals("1"))
	{
        bs1.setStacked(true);
        bs2.setStacked(true);
       // bs3.setStacked(true);
        }
        
        else
        {
        	
        bs1.setStacked(false);
        bs2.setStacked(false);
       // bs3.setStacked(false);	
        }
	
     
	
	SeriesDefinition sdX = SeriesDefinitionImpl.create();
	//sdX.getSeriesPalette().update(0); 
	xAxisPrimary.getSeriesDefinitions().add(sdX);
	sdX.getSeries().add(seCategory);
	
	
	SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(1); 	
		yAxisPrimary.getSeriesDefinitions().add(sdY);		
		sdY.getSeries().add(bs1);
		sdY.getSeries().add(bs2);
   
	
	return cwc;

}




public static Chart createMyChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
{

	ChartWithAxes cwc = ChartWithAxesImpl.create();
	
	if(Transpose.equals("1"))
	{
	cwc.setTransposed(true);
         }
         else
         {
         	cwc.setTransposed(false);
        }
	//cwc.setSubType("2");
	//System.out.println("Subtype checked............"+cwc.getSubType());
      
	//System.out.println("************************************"+ss);
	switch(bcolor){
	case 1:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwc.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwc.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
	 //    cwc.getBlock().setBackground(
	//ColorDefinitionImpl.WHITE()
	//ColorDefinitionImpl.RED()
	//ColorDefinitionImpl.setBlue(999993);
	//);
	cwc.getBlock().getOutline().setVisible(false);
	
	
	//cwc.setDimension(
	//ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL
	//);
	
	     
	Plot p = cwc.getPlot();
	p.getClientArea().setBackground(ColorDefinitionImpl.create(255, 255, 225));
	p.getOutline().setVisible(false);
	cwc.getTitle().getLabel().getCaption().setValue(ChartHeading);
	
	     
	     Legend lg = cwc.getLegend();
	     lg.getText().getFont().setSize(8);
	     lg.getInsets().set(6, 4, 0, 0);
	     lg.setAnchor(Anchor.NORTH_LITERAL);
	
	   
	     Axis xAxisPrimary = cwc.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwc.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);   
	
	
	
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
	   
	//ArrayList usage =PortalMgmt.portal.NewDataBaseLayer.getUserscoInfo(student_id);
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
		
	//String strSql3=sqlForNumberData2.replace("?","'"+student_id+"'");
	
	ArrayList usage =NewDataBaseLayer.getNumberdata(strSql25);  
	
	//ArrayList usage1 =NewDataBaseLayer.getNumberdata1(strSql3);  
	
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage);
	//NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage1);
	
	
	Series seCategory = SeriesImpl.create();
	seCategory.setDataSet(categoryValues);
	
	
	BarSeries bs2 = (BarSeries) BarSeriesImpl.create();
	//bs1.setSeriesIdentifier("Unit Times");
	bs2.setSeriesIdentifier(sqlLegendData);
	bs2.setDataSet(orthoValues1);
	bs2.setRiserOutline(null);
	bs2.getLabel().setVisible(true);
	bs2.setLabelPosition(Position.INSIDE_LITERAL);
	
	
	//BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
	//bs1.setSeriesIdentifier(sqlLegendData2);
	
	//bs1.setDataSet(orthoValues2);
	//bs1.setRiserOutline(null);
	
	//bs1.getLabel().setVisible(true);
	//bs1.setLabelPosition(Position.INSIDE_LITERAL);
	
	if(Stacked.equals("1"))
	{
        //bs1.setStacked(true);
        bs2.setStacked(true);
       // bs3.setStacked(true);
        }
        
        else
        {
        	
       //bs1.setStacked(false);
        bs2.setStacked(false);
       // bs3.setStacked(false);	
        }
	
     
	
	SeriesDefinition sdX = SeriesDefinitionImpl.create();
	//sdX.getSeriesPalette().update(0); 
	xAxisPrimary.getSeriesDefinitions().add(sdX);
	sdX.getSeries().add(seCategory);
	
	
	SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(1); 	
		yAxisPrimary.getSeriesDefinitions().add(sdY);		
		//sdY.getSeries().add(bs1);
		sdY.getSeries().add(bs2);
   
	
	return cwc;

}




//////////////////////////////////////////endlegend////////////////////





	public static final Chart createSDialSRegionChart(String sqlForTextData,String sqlForNumberData,String ChartHeading,int bcolor ) {
		DialChart dChart = (DialChart) DialChartImpl.create();
		dChart.setDialSuperimposition(true);
		dChart.setGridColumnCount(2);
		dChart.setSeriesThickness(25);

		// Title/Plot
		//dChart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 dChart.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	dChart.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = dChart.getPlot();
		p.getClientArea().setBackground(ColorDefinitionImpl.CREAM());
		//p.getClientArea().setBackground(ColorDefinitionImpl.BLUE());
		p.getClientArea().getOutline().setVisible(false);
		p.getOutline().setVisible(false);

		dChart.getTitle().getLabel().getCaption().setValue(ChartHeading);
		dChart.getTitle().getOutline().setVisible(false);

		// Legend
		Legend lg = dChart.getLegend();
		LineAttributes lia = lg.getOutline();
		lg.getText().getFont().setSize(6);
		lia.setStyle(LineStyle.SOLID_LITERAL);
		lg.getInsets().setLeft(5);
		lg.getInsets().setRight(5);
		lg.setBackground(null);
		lg.getOutline().setVisible(false);
		lg.setShowValue(true);
		lg.getClientArea().setBackground(ColorDefinitionImpl.PINK());

		lg.getClientArea().getOutline().setVisible(false);
		lg.getTitle().getCaption().getFont().setSize(8);
		lg.getTitle().setInsets(InsetsImpl.create(10, 10, 10, 10));
		lg.setTitlePosition(Position.ABOVE_LITERAL);

		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");
			
		Vector sqlTextdata=NewDataBaseLayer.getdialTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl
				.create(sqlTextdata);
/////////////////////////////////////////////////////////
		SeriesDefinition sd = SeriesDefinitionImpl.create();
		dChart.getSeriesDefinitions().add(sd);
		Series seCategory = (Series) SeriesImpl.create();

		final Fill[] fiaBase = {
				ColorDefinitionImpl.ORANGE(),
				GradientImpl.create(ColorDefinitionImpl.create(225, 225, 255),
						ColorDefinitionImpl.create(255, 255, 225), -35, false),
				ColorDefinitionImpl.RED(), ColorDefinitionImpl.RED(),
				ColorDefinitionImpl.GREEN(),
				ColorDefinitionImpl.BLUE().brighter(),
				ColorDefinitionImpl.CYAN().darker(), };
		sd.getSeriesPalette().getEntries().clear();
		for (int i = 0; i < fiaBase.length; i++) {		
			sd.getSeriesPalette().getEntries().add(fiaBase[i]);
		}

		seCategory.setDataSet(categoryValues);
		sd.getSeries().add(seCategory);

		SeriesDefinition sdCity = SeriesDefinitionImpl.create();

		// Dial
		DialSeries seDial = (DialSeries) DialSeriesImpl.create();
		
//		Double totaltime=PortalMgmt.portal.NewDataBaseLayer.getTotalAccessTime(student_id);
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");		
		//String sqlTextdata[]=NewDataBaseLayer.getdialNumberdata(textsql);
		
		ArrayList totaltime=NewDataBaseLayer.getdialNumberdata(numbersql14);
		seDial.setDataSet(NumberDataSetImpl.create( totaltime ));
		seDial.getDial().setFill(
				GradientImpl.create(ColorDefinitionImpl.create(225, 225, 255),
						ColorDefinitionImpl.create(255, 255, 225), -35, false));
		seDial.getNeedle().setDecorator(LineDecorator.ARROW_LITERAL);
		seDial.getDial().getMinorGrid().getTickAttributes().setVisible(true);
		seDial.getDial().getMinorGrid().getTickAttributes().setColor(
				ColorDefinitionImpl.RED());
		seDial.getDial().getMinorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		seDial.getDial().getScale().setMin(NumberDataElementImpl.create(0));
		seDial.getDial().getScale().setMax(NumberDataElementImpl.create(180));
		seDial.getDial().getScale().setStep(30);
		seDial.getLabel().setOutline(
				LineAttributesImpl.create(ColorDefinitionImpl.GREY().darker(),
						LineStyle.SOLID_LITERAL, 1));
		seDial.getLabel().setBackground(ColorDefinitionImpl.GREY().brighter());

		DialRegion dregion21 = DialRegionImpl.create();
		dregion21.setFill(ColorDefinitionImpl.GREEN());
		dregion21.setStartValue(NumberDataElementImpl.create(0));
		dregion21.setEndValue(NumberDataElementImpl.create(100));
		seDial.getDial().getDialRegions().add(dregion21);

		sd.getSeriesDefinitions().add(sdCity);
		sdCity.getSeries().add(seDial);

		return dChart;
	}
	
	
	
   public static final Chart createPieChart(String sqlForTextData,String sqlForNumberData,String ChartHeading,int bcolor) {
		ChartWithoutAxes cwoaPie = ChartWithoutAxesImpl.create();

		// Plot
		cwoaPie.setSeriesThickness(25);
		//cwoaPie.getBlock().setBackground(ColorDefinitionImpl.WHITE());
		switch(bcolor){
	case 1:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwoaPie.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwoaPie.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		Plot p = cwoaPie.getPlot();
		p.getClientArea().setBackground(null);
		p.getClientArea().getOutline().setVisible(false);
		p.getOutline().setVisible(false);

		// Legend
		/*Legend lg = cwoaPie.getLegend();
		lg.getText().getFont().setSize(12);
		lg.setBackground(null);
		lg.getOutline().setVisible(false);*/
		
		
		Legend lg = cwoaPie.getLegend( );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );
		lg.getClientArea( ).getOutline( ).setVisible( true );
		lg.getTitle( ).setVisible( false );
                lg.getText().getFont().setSize(12);

		// Title
		cwoaPie.getTitle().getLabel().getCaption().setValue(ChartHeading);
		cwoaPie.getTitle().getOutline().setVisible(false);

		// Data Set   for x value that is for x axis
		System.out.println("-----argument5,argument6,argument1,argument2,argument3,argument4-----"+argument5+","+argument6+","+argument1+","+argument2+","+argument3+","+argument4);
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		System.out.println("textsql-------"+textsql);
		
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
		System.out.println("textsql1-------"+textsql1);
		
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
		System.out.println("textsql11-------"+textsql11);
		
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
		System.out.println("textsql12-------"+textsql12);
		
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
		System.out.println("textsql13-------"+textsql13);
		
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");
		System.out.println("textsql14-------"+textsql14);
			
		//System.out.println("===================sqlForTextData value ===================="+textsql1);
		Vector sqlTextdata=NewDataBaseLayer.getpieTextData(textsql14);
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata );
		
		// Data Set   for 1st y value that is for 1st y axis
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	
			
		ArrayList totaltime=NewDataBaseLayer.getpieNumberdata(numbersql14);
		NumberDataSet seriesOneValues = NumberDataSetImpl.create(totaltime);
		// Base Series
		Series seCategory = (Series) SeriesImpl.create();
		seCategory.setDataSet(categoryValues);

		SeriesDefinition sd = SeriesDefinitionImpl.create();
		cwoaPie.getSeriesDefinitions().add(sd);
		sd.getSeriesPalette().update(0);
		sd.getSeries().add(seCategory);

		// Orthogonal Series
		PieSeries sePie = (PieSeries) PieSeriesImpl.create();
		sePie.setDataSet(seriesOneValues);
		sePie.setSeriesIdentifier("");

		SeriesDefinition sdCity = SeriesDefinitionImpl.create();
		//sdCity.getQuery().setDefinition("Unit.Status");
		sd.getSeriesDefinitions().add(sdCity);
		sdCity.getSeries().add(sePie);
		
	DataPointComponent dpc = DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
				JavaNumberFormatSpecifierImpl.create( "0" ) );//$NON-NLS-1$
		sePie.getDataPoint( ).getComponents( ).clear( );
		sePie.getDataPoint( ).getComponents( ).add( dpc );

		return cwoaPie;
	}
	
	
	
	public static final Chart createLineChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");	
	
	
	
	String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
	String strSql41=strSql4.replace("argument6","'"+argument6+"'");
	String strSql42=strSql41.replace("argument1","'"+argument1+"'");
	String strSql43=strSql42.replace("argument2","'"+argument2+"'");
	String strSql44=strSql43.replace("argument3","'"+argument3+"'");
	String strSql45=strSql44.replace("argument4","'"+argument4+"'");
	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		ArrayList totaltime1=NewDataBaseLayer.getlineNumberdata(strSql35);
		ArrayList totaltime2=NewDataBaseLayer.getlineNumberdata(strSql45);
		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(totaltime1);
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(totaltime2);

		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
		ls1.setSeriesIdentifier(sqlLegendData2);
		ls1.setDataSet(orthoValues1);
		ls1.getLineAttributes().setColor(ColorDefinitionImpl.GREEN());
		ls1.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls1.getLabel().setVisible(true);
		
		LineSeries ls2 = (LineSeries) LineSeriesImpl.create();
		ls2.setSeriesIdentifier(sqlLegendData3);
		ls2.setDataSet(orthoValues2);
		
		ls2.getLineAttributes().setColor(ColorDefinitionImpl.RED());
		ls2.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls2.getLabel().setVisible(true);				

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );		
		sdY.getSeries().add(ls);
		sdY2.getSeries().add(ls1);
		sdY3.getSeries().add(ls2);

		return cwaLine;
	}
	
	
	public static final Chart createLineChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");	
	
	
	

	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		ArrayList totaltime1=NewDataBaseLayer.getlineNumberdata(strSql35);

		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(totaltime1);


		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
		ls1.setSeriesIdentifier(sqlLegendData2);
		ls1.setDataSet(orthoValues1);
		ls1.getLineAttributes().setColor(ColorDefinitionImpl.GREEN());
		ls1.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls1.getLabel().setVisible(true);
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		sdY.getSeries().add(ls);
		sdY2.getSeries().add(ls1);


		return cwaLine;
	}
	
	
	public static final Chart createLineChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        

	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		

		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		


		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		sdY.getSeries().add(ls);

		return cwaLine;
	}		
	
	
	public static final Chart createScatterChart(String sqlForTextData,String sqlForNumberData,String ChartHeading,int bcolor) {
		ChartWithAxes cwaScatter = ChartWithAxesImpl.create();
		
		switch(bcolor){
	case 1:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaScatter.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaScatter.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		//Plot
		Plot p = cwaScatter.getPlot();
		
		p.getOutline().setStyle(LineStyle.DASH_DOTTED_LITERAL);
		p.getOutline().setColor(ColorDefinitionImpl.create(214, 100, 12));
		p.getOutline().setVisible(true);
		
		p.setBackground(ColorDefinitionImpl.CREAM());
		p.setAnchor(Anchor.NORTH_LITERAL);
        
        p.getClientArea().setBackground(GradientImpl.create(ColorDefinitionImpl.create(225, 0, 255),
				ColorDefinitionImpl.create(255, 253, 200), -35, false));
		p.getClientArea().getOutline().setVisible(true);
		
		//Title
        cwaScatter.getBlock().setBackground(ColorDefinitionImpl.WHITE());
		cwaScatter.getTitle().getLabel().getCaption().setValue(ChartHeading);//$NON-NLS-1$

		//X-Axis
		Axis xAxisPrimary = ((ChartWithAxesImpl) cwaScatter).getPrimaryBaseAxes()[0];
		
		xAxisPrimary.getTitle().getCaption().setValue("X Axis");//$NON-NLS-1$
		xAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		xAxisPrimary.getLabel().getCaption().setColor(
				ColorDefinitionImpl.GREEN().darker());
		xAxisPrimary.getTitle().setVisible(false);
		
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getMajorGrid().getLineAttributes().setStyle(
				LineStyle.DOTTED_LITERAL);
		xAxisPrimary.getMajorGrid().getLineAttributes().setColor(
				ColorDefinitionImpl.GREY());
		xAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
		
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);

		//Y-Axis
		Axis yAxisPrimary = ((ChartWithAxesImpl) cwaScatter).getPrimaryOrthogonalAxis(xAxisPrimary);
		
		yAxisPrimary.getLabel().getCaption().setValue("Price Axis");//$NON-NLS-1$
		yAxisPrimary.getLabel().getCaption().setColor(
				ColorDefinitionImpl.BLUE());
		yAxisPrimary.getTitle().setVisible(true);
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		yAxisPrimary.getMajorGrid().getLineAttributes().setStyle(
				LineStyle.DOTTED_LITERAL);
		yAxisPrimary.getMajorGrid().getLineAttributes().setColor(
				ColorDefinitionImpl.GREY());
		yAxisPrimary.getMajorGrid().getLineAttributes().setVisible(true);
		
		yAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);

		//Data Set
		/*NumberDataSet dsNumericValues1 = NumberDataSetImpl.create(new double[] {
				-46.55, 25.32, 84.46, 125.95, 38.65, -54.32, 30 });
		NumberDataSet dsNumericValues2 = NumberDataSetImpl.create(new double[] {
				125.99, 352.95, -201.95, 299.95, -95.95, 65.95, 58.95 });*/

          //String textsql=sqlForTextData.replace("?","'"+student_id+"'");
		//Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql);
		
		//TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);



		//X-Series
		Series seBase = SeriesImpl.create();
		seBase.setDataSet(orthoValues);
		
		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seBase);

		//Y-Series
		ScatterSeries ss = (ScatterSeries) ScatterSeriesImpl.create();
		ss.setSeriesIdentifier("Unit Price");//$NON-NLS-1$
		ss.getMarker().setType(MarkerType.CIRCLE_LITERAL);
		/*DataPoint dp = ss.getDataPoint();
		dp.getComponents().clear();
		dp.setPrefix("(");//$NON-NLS-1$
		dp.setSuffix(")");//$NON-NLS-1$
		dp.getComponents().add(
				DataPointComponentImpl.create(
						DataPointComponentType.BASE_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create("0.00")));//$NON-NLS-1$
		dp.getComponents().add(
				DataPointComponentImpl.create(
						DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create("0.00")));//$NON-NLS-1$*/
		ss.getLabel().getCaption().setColor(ColorDefinitionImpl.RED());
		ss.getLabel().setBackground(ColorDefinitionImpl.CYAN());
		ss.getLabel().setVisible(true);
		ss.setDataSet(orthoValues);

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		sdY.getSeriesPalette().update(ColorDefinitionImpl.BLACK());
		sdY.getSeries().add(ss);

		return cwaScatter;
	}
	

//////////////////////////////     Area chart /////////////////////////////////////////

	public static final Chart createAreaChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaArea = ChartWithAxesImpl.create( );

	if(Transpose.equals("1"))
	{
	cwaArea.setTransposed(true);
         }
         else
         {
         	cwaArea.setTransposed(false);
        }
	//cwc.setSubType("2");
	//System.out.println("Subtype checked............"+cwc.getSubType());
      
	//System.out.println("************************************"+ss);
	switch(bcolor){
	case 1:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaArea.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaArea.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}


		// Plot/Title
		cwaArea.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaArea.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 225,
				225,
				225 ) );
		cwaArea.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$
		cwaArea.getTitle( ).setVisible( true );

		// Legend
		Legend lg = cwaArea.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaArea.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLUE( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );//$NON-NLS-1$
		xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 0 );
		xAxisPrimary.getLabel( ).setVisible( true );

		// Y-Axis
		Axis yAxisPrimary = cwaArea.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.setLineAttributes( LineAttributesImpl.create( ColorDefinitionImpl.BLACK( ),
						LineStyle.SOLID_LITERAL,
						1 ) );
		yAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.setPercent( false );
		yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );//$NON-NLS-1$
		yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.getTitle( ).getCaption( ).getFont( ).setRotation( 90 );
		yAxisPrimary.getLabel( ).setVisible( true );

		MarkerLine ml = MarkerLineImpl.create( yAxisPrimary,
				NumberDataElementImpl.create( 2 ) );
		yAxisPrimary.getMarkerLines( ).add( ml );

		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Jan.", "Feb.", "Mar.", "Apr", "May"} ); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
				14.32, -19.5, 8.38, 0.34, 9.22
		} );
		NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
				4.2, -19.5, 0.0, 9.2, 7.6
		} );*/
		
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
	   
	//ArrayList usage =PortalMgmt.portal.NewDataBaseLayer.getUserscoInfo(student_id);
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");	
	
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
		
	ArrayList usage =NewDataBaseLayer.getNumberdata(strSql25);  
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata1(strSql35);  
	
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage1);		

		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		AreaSeries as1 = (AreaSeries) AreaSeriesImpl.create( );
		as1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		as1.setDataSet( orthoValues1 );
		as1.setTranslucent( true );
		as1.getLineAttributes( ).setColor( ColorDefinitionImpl.BLUE( ) );
		as1.getLabel( ).setVisible( true );

		AreaSeries as2 = (AreaSeries) AreaSeriesImpl.create( );
		as2.setSeriesIdentifier( sqlLegendData2 );//$NON-NLS-1$
		as2.setDataSet( orthoValues2 );
		as2.setTranslucent( true );
		as2.getLineAttributes( ).setColor( ColorDefinitionImpl.PINK( ) );
		as2.getLabel( ).setVisible( true );

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette( ).update( 1 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( as1 );
		sdY.getSeries( ).add( as2 );

		return cwaArea;

	}


///////////////////////////// end of area chart //////////////////////////////////////


//////////////////////////// CF Bar Chart ///////////////////////////////////


	public static final Chart createCFBarChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );


	switch(bcolor){
		case 1:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLACK());
		break;
		case 2:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLUE());
		break;
		case 3:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.CREAM());
		break;
		case 4:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.CYAN());
		break;
		case 5:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREEN());
		break;
		case 6:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREY());
		break;
		case 7:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
		break;
		case 8:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.PINK());
		break;
		case 9:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.RED());
		break;
		case 10:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
		break;
		case 11:
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
		break;
		
		default :
		cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
		break;
		
		}
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
	     Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50); 
		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );*/
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
		
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		//bs1.setTranslucent( true );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		//bs1.setCurveFitting( CurveFittingImpl.create( ) );
		
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );
		bs2.setDataSet( orthoValues2 );
		//bs2.setTranslucent( true );
		bs2.setRiserOutline( null );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );
		//bs2.setCurveFitting( CurveFittingImpl.create( ) );		
	if(Stacked.equals("1"))
	{
        bs1.setStacked(true);
        bs2.setStacked(true);
        
        }
        
        else
        {
        	
        bs1.setStacked(false);
        bs2.setStacked(false);
        
        }			

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );

		return cwaBar;
	}
	



	public static final Chart createCFBarChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );


	switch(bcolor){
	case 1:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
	     Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50); 
		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );*/
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	

	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	
		
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		//bs1.setTranslucent( true );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		//bs1.setCurveFitting( CurveFittingImpl.create( ) );
		
	if(Stacked.equals("1"))
	{
        bs1.setStacked(true);
       
        }
        
        else
        {
        	
        bs1.setStacked(false);

        }		

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		

		return cwaBar;
	}
	
	
	public static final Chart createCFBarChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{

		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );


	switch(bcolor){
	case 1:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaBar.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
				255,
				225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
	     Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
	     xAxisPrimary.setType(AxisType.TEXT_LITERAL);
	     xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
	     xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
	     xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
	     xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
	     xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
	     xAxisPrimary.getTitle( ).setVisible( true );
	     Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
	     yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
	     yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
	     yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	     yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
	     yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
	     xAxisPrimary.getLabel().getCaption().getFont().setRotation(50); 	
		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
				"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
				25, 35, 15
		} );*/
	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	// X-Series
	Series seCategory = SeriesImpl.create( );
	seCategory.setDataSet( categoryValues );

	SeriesDefinition sdX = SeriesDefinitionImpl.create( );
	sdX.getSeriesPalette( ).update( 0 );
	xAxisPrimary.getSeriesDefinitions( ).add( sdX );
	sdX.getSeries( ).add( seCategory );

	// Y-Series

	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
		
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);

		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
				
		String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
		String strSql31=strSql3.replace("argument6","'"+argument6+"'");
		String strSql32=strSql31.replace("argument1","'"+argument1+"'");
		String strSql33=strSql32.replace("argument2","'"+argument2+"'");
		String strSql34=strSql33.replace("argument3","'"+argument3+"'");
		String strSql35=strSql34.replace("argument4","'"+argument4+"'");
		
		if (strSql35!="")
		{
		ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
		bs2.setSeriesIdentifier( sqlLegendData2 );
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );
		}
		
		String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
		String strSql41=strSql4.replace("argument6","'"+argument6+"'");
		String strSql42=strSql41.replace("argument1","'"+argument1+"'");
		String strSql43=strSql42.replace("argument2","'"+argument2+"'");
		String strSql44=strSql43.replace("argument3","'"+argument3+"'");
		String strSql45=strSql44.replace("argument4","'"+argument4+"'");	
		
		if (strSql45!="")
		{
		ArrayList usage3 =NewDataBaseLayer.getNumberdata2(strSql45);		
		NumberDataSet orthoValues3 = NumberDataSetImpl.create(usage3);
		bs3.setSeriesIdentifier(sqlLegendData3);
		bs3.setDataSet(orthoValues3);
		bs3.setRiserOutline(null);
		bs3.getLabel().setVisible(true);
		bs3.setLabelPosition(Position.INSIDE_LITERAL);		
		}

		
		
	if(Stacked.equals("1"))
	{
        bs1.setStacked(true);
        bs2.setStacked(true);
        bs3.setStacked(true);
        }
        
        else
        {
        	
        bs1.setStacked(false);
        bs2.setStacked(false);
        bs3.setStacked(false);	
        }		
		

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1); 
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );
                sdY.getSeries( ).add( bs3 );
                
                
		return cwaBar;
	}	
	
	
///////////////////////////////// End of CF Bar Chart //////////////////////////////	


	
	public static final Chart createStackedChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		
		
		switch(bcolor){
			case 1:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.BLACK());
				break;
			case 2:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.BLUE());
				break;
			case 3:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.CREAM());
				break;
			case 4:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.CYAN());
				break;
			case 5:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.GREEN());
				break;
			case 6:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.GREY());
				break;
			case 7:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
				break;
			case 8:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.PINK());
				break;
			case 9:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.RED());
				break;
			case 10:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
			case 11:
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
				break;
	
			default :
				cwaCombination.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
	
		}
		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
                xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue(YaxisTitle );//$NON-NLS-1$ 
                yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
	
	String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
	String strSql41=strSql4.replace("argument6","'"+argument6+"'");
	String strSql42=strSql41.replace("argument1","'"+argument1+"'");
	String strSql43=strSql42.replace("argument2","'"+argument2+"'");
	String strSql44=strSql43.replace("argument3","'"+argument3+"'");
	String strSql45=strSql44.replace("argument4","'"+argument4+"'");	
	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
	ArrayList usage3 =NewDataBaseLayer.getNumberdata2(strSql45);		
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
	NumberDataSet orthoValues3 = NumberDataSetImpl.create(usage3);




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( sqlLegendData3 );//$NON-NLS-1$
		bs3.setDataSet( orthoValues3 );
		bs3.setRiserOutline( null );
		bs3.setRiser( RiserType.RECTANGLE_LITERAL );
		bs3.setStacked( true );
                dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs3.setDataPoint( dp );

		

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );


		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		sdY1.getSeries( ).add( bs3 );


		return cwaCombination;
	}	
	

	public static final Chart createStackedChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );
		yAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	

	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
			
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
	




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );




		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		


		return cwaCombination;
	}	
	
	public static final Chart createStackedChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );//$NON-NLS-1$ 
		xAxisPrimary.getTitle( ).setVisible( true );

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );

		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	System.out.println("====================strSql14==========="+strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	System.out.println("====================strSql25==========="+strSql25);

	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);

			
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	
	




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

	

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );






		sdY1.getSeries( ).add( bs1 );
		
		


		return cwaCombination;
	}		
	
	
	
////////////////////////////////////// Testing for Percent Stacked Chart //////////////////////////////


	public static final Chart createPercentStackedChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );
                xAxisPrimary.getTitle( ).setVisible( true );
		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue(YaxisTitle );//$NON-NLS-1$ 
                yAxisPrimary.getTitle( ).setVisible( true );
		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );
                yAxisPrimary.setPercent( true );
		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
	
	String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
	String strSql41=strSql4.replace("argument6","'"+argument6+"'");
	String strSql42=strSql41.replace("argument1","'"+argument1+"'");
	String strSql43=strSql42.replace("argument2","'"+argument2+"'");
	String strSql44=strSql43.replace("argument3","'"+argument3+"'");
	String strSql45=strSql44.replace("argument4","'"+argument4+"'");	
	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
	ArrayList usage3 =NewDataBaseLayer.getNumberdata2(strSql45);		
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
	NumberDataSet orthoValues3 = NumberDataSetImpl.create(usage3);




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		BarSeries bs3 = (BarSeries) BarSeriesImpl.create( );
		bs3.setSeriesIdentifier( sqlLegendData3 );//$NON-NLS-1$
		bs3.setDataSet( orthoValues3 );
		bs3.setRiserOutline( null );
		bs3.setRiser( RiserType.RECTANGLE_LITERAL );
		bs3.setStacked( true );
                dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs3.setDataPoint( dp );

		

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );


		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		sdY1.getSeries( ).add( bs3 );


		return cwaCombination;
	}	
	

	public static final Chart createPercentStackedChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );
		yAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );
                yAxisPrimary.setPercent( true );
		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	

	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
			
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
	




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );//$NON-NLS-1$
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.setRiser( RiserType.RECTANGLE_LITERAL );
		bs2.setStacked( true );
		dp = DataPointImpl.create( "[", "]", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		bs2.setDataPoint( dp );

		

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );

		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );




		sdY1.getSeries( ).add( bs1 );
		sdY1.getSeries( ).add( bs2 );
		


		return cwaCombination;
	}	
	
	public static final Chart createPercentStackedChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaCombination = ChartWithAxesImpl.create( );

		// Plot
		cwaCombination.setUnitSpacing( 25 );
		cwaCombination.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaCombination.getPlot( );
		p.getClientArea( )
				.setBackground( GradientImpl.create( ColorDefinitionImpl.create( 255,
						235,
						255 ),
						ColorDefinitionImpl.create( 255, 255, 225 ),
						-35,
						false ) );

		p.getClientArea( ).getInsets( ).set( 8, 8, 8, 8 );
		p.getOutline( ).setVisible( true );

		// Legend
		Legend lg = cwaCombination.getLegend( );
		lg.setBackground( ColorDefinitionImpl.YELLOW( ) );
		lg.getOutline( ).setVisible( true );

		// Title
		cwaCombination.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue( ChartHeading );//$NON-NLS-1$ 

		// X-Axis
		Axis xAxisPrimary = cwaCombination.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );

		// Y-Series
		Axis yAxisPrimary = cwaCombination.getPrimaryOrthogonalAxis( xAxisPrimary );

		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.setTitlePosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );//$NON-NLS-1$ 
		xAxisPrimary.getTitle( ).setVisible( true );

		yAxisPrimary.setType( AxisType.LINEAR_LITERAL );
		yAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 37 );

		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );
		yAxisPrimary.getMinorGrid( ).setTickStyle( TickStyle.ACROSS_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		yAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.GREEN( ) );
				
                yAxisPrimary.setPercent( true );				

		// Data Set
		/*String[] saTextValues = {
				"CPUs", "Keyboards", "Video Cards",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
				"Monitors", "Motherboards", "Memory", "Storage Devices",//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"Media", "Printers", "Scanners"};//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

		TextDataSet categoryValues = TextDataSetImpl.create( saTextValues );
		NumberDataSet seriesOneValues = NumberDataSetImpl.create( new double[]{
				56.99,
				352.95,
				201.95,
				299.95,
				95.95,
				25.45,
				129.33,
				26.5,
				43.5,
				122
		} );
		NumberDataSet seriesTwoValues = NumberDataSetImpl.create( new double[]{
				20, 35, 59, 105, 150, 37, 65, 99, 145, 185
		} );
		NumberDataSet seriesThreeValues = NumberDataSetImpl.create( new double[]{
				54.99, 21, 75.95, 39.95, 7.95, 91.22, 33.45, 25.63, 40, 13
		} );
		NumberDataSet seriesFourValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesFiveValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );
		NumberDataSet seriesSixValues = NumberDataSetImpl.create( new double[]{
				15, 45, 43, 5, 19, 25, 35, 94, 15, 55
		} );
		NumberDataSet seriesSevenValues = NumberDataSetImpl.create( new double[]{
				43, 65, 35, 41, 45, 55, 29, 15, 85, 65
		} );*/


	String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
	String strSql1=strSql.replace("argument6","'"+argument6+"'");
	String strSql11=strSql1.replace("argument1","'"+argument1+"'");
	String strSql12=strSql11.replace("argument2","'"+argument2+"'");
	String strSql13=strSql12.replace("argument3","'"+argument3+"'");
	String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
	Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
	TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
	String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
	String strSql21=strSql2.replace("argument6","'"+argument6+"'");
	String strSql22=strSql21.replace("argument1","'"+argument1+"'");
	String strSql23=strSql22.replace("argument2","'"+argument2+"'");
	String strSql24=strSql23.replace("argument3","'"+argument3+"'");
	String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	


	
	
	ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);

			
	
		
	 
	
	NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	
	




		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );//$NON-NLS-1$
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.setRiser( RiserType.RECTANGLE_LITERAL );
		bs1.setStacked( true );
		DataPoint dp = DataPointImpl.create( "(", ")", ", " );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		dp.getComponents( ).clear( );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.BASE_VALUE_LITERAL,
						null ) );
		dp.getComponents( )
				.add( DataPointComponentImpl.create( DataPointComponentType.ORTHOGONAL_VALUE_LITERAL,
						JavaNumberFormatSpecifierImpl.create( "0.00" ) ) );//$NON-NLS-1$
		bs1.setDataPoint( dp );

	

		SeriesDefinition sdY1 = SeriesDefinitionImpl.create( );
		sdY1.getSeriesPalette( ).update( 0 );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY1 );






		sdY1.getSeries( ).add( bs1 );
		
		


		return cwaCombination;
	}

//////////////////////////////// End of Percent Stacked Chart ////////////////////////////////////////

/////////////////////////////// Percent Line Chart /////////////////////////////////

	public static final Chart createPercentLineChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
	 	yAxisPrimary.setPercent( true );
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");	
	
	
	
	String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
	String strSql41=strSql4.replace("argument6","'"+argument6+"'");
	String strSql42=strSql41.replace("argument1","'"+argument1+"'");
	String strSql43=strSql42.replace("argument2","'"+argument2+"'");
	String strSql44=strSql43.replace("argument3","'"+argument3+"'");
	String strSql45=strSql44.replace("argument4","'"+argument4+"'");
	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		ArrayList totaltime1=NewDataBaseLayer.getlineNumberdata(strSql35);
		ArrayList totaltime2=NewDataBaseLayer.getlineNumberdata(strSql45);
		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(totaltime1);
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(totaltime2);

		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
		ls1.setSeriesIdentifier(sqlLegendData2);
		ls1.setDataSet(orthoValues1);
		ls1.getLineAttributes().setColor(ColorDefinitionImpl.GREEN());
		ls1.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls1.getLabel().setVisible(true);
		
		LineSeries ls2 = (LineSeries) LineSeriesImpl.create();
		ls2.setSeriesIdentifier(sqlLegendData3);
		ls2.setDataSet(orthoValues2);
		
		ls2.getLineAttributes().setColor(ColorDefinitionImpl.RED());
		ls2.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls2.getLabel().setVisible(true);				

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		SeriesDefinition sdY3 = SeriesDefinitionImpl.create( );
		sdY3.getSeriesPalette( ).update( ColorDefinitionImpl.RED( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY3 );		
		sdY.getSeries().add(ls);
		sdY2.getSeries().add(ls1);
		sdY3.getSeries().add(ls2);

		return cwaLine;
	}
	
	
	public static final Chart createPercentLineChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	yAxisPrimary.setPercent( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        
	String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
	String strSql31=strSql3.replace("argument6","'"+argument6+"'");
	String strSql32=strSql31.replace("argument1","'"+argument1+"'");
	String strSql33=strSql32.replace("argument2","'"+argument2+"'");
	String strSql34=strSql33.replace("argument3","'"+argument3+"'");
	String strSql35=strSql34.replace("argument4","'"+argument4+"'");	
	
	
	

	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		ArrayList totaltime1=NewDataBaseLayer.getlineNumberdata(strSql35);

		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(totaltime1);


		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		LineSeries ls1 = (LineSeries) LineSeriesImpl.create();
		ls1.setSeriesIdentifier(sqlLegendData2);
		ls1.setDataSet(orthoValues1);
		ls1.getLineAttributes().setColor(ColorDefinitionImpl.GREEN());
		ls1.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls1.getLabel().setVisible(true);
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		SeriesDefinition sdY2 = SeriesDefinitionImpl.create( );
		sdY2.getSeriesPalette( ).update( ColorDefinitionImpl.GREEN( ) );
		yAxisPrimary.getSeriesDefinitions( ).add( sdY2 );

		sdY.getSeries().add(ls);
		sdY2.getSeries().add(ls1);


		return cwaLine;
	}
	
	
	public static final Chart createPercentLineChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor) {
		ChartWithAxes cwaLine = ChartWithAxesImpl.create();

		// Plot
		//cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	switch(bcolor){
	case 1:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLACK());
	 break;
	case 2:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.BLUE());
	  break;
	case 3:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CREAM());
	  break;
	case 4:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.CYAN());
	  break;
	case 5:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREEN());
	  break;
	case 6:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.GREY());
	  break;
	case 7:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
	  break;
	case 8:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.PINK());
	  break;
	case 9:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.RED());
	  break;
	case 10:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	  break;
	case 11:
	 cwaLine.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
	  break;
	
	default :
	cwaLine.getBlock().setBackground(ColorDefinitionImpl.WHITE());
	break;
	
	}
		
		
		Plot p = cwaLine.getPlot();
		p.getClientArea().setBackground(
				ColorDefinitionImpl.create(255, 255, 225));

		// Title
		cwaLine.getTitle().getLabel().getCaption().setValue(ChartHeading);

		// Legend
		cwaLine.getLegend().setVisible(false);

		// X-Axis
		Axis xAxisPrimary = cwaLine.getPrimaryBaseAxes()[0];
		
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle ); 		
		xAxisPrimary.getTitle().setVisible(true);
		//xAxisPrimary.getTitle().setVisible(false);

		// Y-Axis
		Axis yAxisPrimary = cwaLine.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		//////////////////
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
	 	yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle ); 
	 	yAxisPrimary.getTitle( ).setVisible( true );
	 	yAxisPrimary.setPercent( true );
	 	//yAxisPrimary.getTitle().getCaption().setValue(" "); 
/////////////////////////////////
		// Data Set
		String textsql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String textsql1=textsql.replace("argument6","'"+argument6+"'");
	        String textsql11=textsql1.replace("argument1","'"+argument1+"'");
	        String textsql12=textsql11.replace("argument2","'"+argument2+"'");
	        String textsql13=textsql12.replace("argument3","'"+argument3+"'");
	        String textsql14=textsql13.replace("argument4","'"+argument4+"'");		
		Vector sqlTextdata=NewDataBaseLayer.getLineTextData(textsql14);
		
		TextDataSet categoryValues = TextDataSetImpl.create(sqlTextdata);
		
		String numbersql=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String numbersql1=numbersql.replace("argument6","'"+argument6+"'");
	        String numbersql11=numbersql1.replace("argument1","'"+argument1+"'");
	        String numbersql12=numbersql11.replace("argument2","'"+argument2+"'");
	        String numbersql13=numbersql12.replace("argument3","'"+argument3+"'");
	        String numbersql14=numbersql13.replace("argument4","'"+argument4+"'");
	        

	
		        		
		ArrayList totaltime=NewDataBaseLayer.getlineNumberdata(numbersql14);
		

		NumberDataSet orthoValues = NumberDataSetImpl.create(totaltime);
		


		// X-Series
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();

		xAxisPrimary.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		// Y-Sereis
		LineSeries ls = (LineSeries) LineSeriesImpl.create();
		ls.setSeriesIdentifier(sqlLegendData);
		ls.setDataSet(orthoValues);
		ls.getLineAttributes().setColor(ColorDefinitionImpl.BLUE());
		ls.getMarker().setType(MarkerType.TRIANGLE_LITERAL);
		ls.getLabel().setVisible(true);
		
		
		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		sdY.getSeriesPalette().update(0);
		yAxisPrimary.getSeriesDefinitions().add(sdY);
		
		sdY.getSeries().add(ls);

		return cwaLine;
	}		

/////////////////////////////// End of Percent Line Chart ////////////////////////	
	
	
	
	//////////////////////////////   multiple bar chart/////////////////////////
	
	public static final Chart  createMultiYAxisChart2(String sqlForTextData,String sqlForNumberData,String sqlLegendData,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );
		
		cwaBar.setDimension(ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL);

        // Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
		245,
		255 ) );

        // Title
		cwaBar.getTitle( )
				.getLabel( )
				.getCaption( )
				.setValue(ChartHeading );//$NON-NLS-1$
				//.setValue( "Line Chart with Multiple Y Axis" );//$NON-NLS-1$
		
		

        // Legend
		Legend lg = cwaBar.getLegend( );
		LineAttributes lia = lg.getOutline( );
		lg.getText( ).getFont( ).setSize( 16 );
		lia.setStyle( LineStyle.SOLID_LITERAL );
		lg.getInsets( ).set( 10, 5, 0, 0 );
		lg.getOutline( ).setVisible( false );
		lg.setAnchor( Anchor.NORTH_LITERAL );
		
		
		
		

        // X-Axis
		///////////////either this for  X-Axis
// 		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
// 		xAxisPrimary.setType( AxisType.TEXT_LITERAL );
// 		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
// 		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
// 		xAxisPrimary.getTitle( ).setVisible( false );
		
		/////////////or this  for  X-Axis
		
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes( )[0];
		xAxisPrimary.setType( AxisType.TEXT_LITERAL );

		xAxisPrimary.getLabel( )
				.setBackground( ColorDefinitionImpl.create( 255, 255, 235 ) );
		xAxisPrimary.getLabel( )
				.setShadowColor( ColorDefinitionImpl.create( 225, 225, 225 ) );
		xAxisPrimary.getLabel( ).getCaption( ).getFont( ).setRotation( 25 );

		xAxisPrimary.getOrigin( ).setType( IntersectionType.VALUE_LITERAL );
		xAxisPrimary.setTitlePosition( Position.BELOW_LITERAL );
		xAxisPrimary.setLabelPosition( Position.BELOW_LITERAL );

		xAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.BELOW_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setStyle( LineStyle.DOTTED_LITERAL );
		xAxisPrimary.getMajorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.create( 64, 64, 64 ) );
		xAxisPrimary.getMajorGrid( ).getLineAttributes( ).setVisible( true );

		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );//$NON-NLS-1$ 
		xAxisPrimary.getOrigin( ).setType( IntersectionType.MIN_LITERAL );

		xAxisPrimary.getMinorGrid( )
				.getLineAttributes( )
				.setColor( ColorDefinitionImpl.CYAN( ) );
		xAxisPrimary.getMinorGrid( ).getLineAttributes( ).setVisible( true );
		

        // Y-Axis
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		yAxisPrimary.getMajorGrid( ).setTickStyle( TickStyle.LEFT_LITERAL );
		yAxisPrimary.setLabelPosition( Position.LEFT_LITERAL );
		yAxisPrimary.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );//$NON-NLS-1$

        // Y-Axis (2)
		Axis yAxis = cwaBar.getPrimaryOrthogonalAxis( xAxisPrimary );
		//yAxis.setType( AxisType.LINEAR_LITERAL );
		yAxis.getMajorGrid( ).setTickStyle( TickStyle.RIGHT_LITERAL );
		yAxis.setLabelPosition( Position.RIGHT_LITERAL );
		//xAxisPrimary.getAssociatedAxes( ).add( yAxis );
		yAxis.getTitle( )
				.getCaption( )
				.setValue( YaxisTitle );
        // Data Set
//        TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
//                "March", "April", "May", "June", "July"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
//        NumberDataSet orthoValues1 = NumberDataSetImpl.create( new double[]{
//                12.5, 19.6, 18.3, 13.2, 26.5
//        } );
//        NumberDataSet orthoValues2 = NumberDataSetImpl.create( new double[]{
//                22.7, 23.6, 38.3, 43.2, 40.5
//        } );

		
		
		
		String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String strSql1=strSql.replace("argument6","'"+argument6+"'");
		String strSql11=strSql1.replace("argument1","'"+argument1+"'");
		String strSql12=strSql11.replace("argument2","'"+argument2+"'");
		String strSql13=strSql12.replace("argument3","'"+argument3+"'");
		String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
		Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
		TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
		String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String strSql21=strSql2.replace("argument6","'"+argument6+"'");
		String strSql22=strSql21.replace("argument1","'"+argument1+"'");
		String strSql23=strSql22.replace("argument2","'"+argument2+"'");
		String strSql24=strSql23.replace("argument3","'"+argument3+"'");
		String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	

	
	
		ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
	
		
	
		
	 
	
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
	
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		//bs1.setTranslucent( true );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		//bs1.setCurveFitting( CurveFittingImpl.create( ) );
		
		if(Stacked.equals("1"))
		{
			bs1.setStacked(true);
       
		}
        
		else
		{
        	
			bs1.setStacked(false);

		}		

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		

		return cwaBar;
	}
	
	
	
	public static final Chart createMultiYAxisChart1(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlLegendData,String sqlLegendData2,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );


		switch(bcolor){
			case 1:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLACK());
				break;
			case 2:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLUE());
				break;
			case 3:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.CREAM());
				break;
			case 4:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.CYAN());
				break;
			case 5:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREEN());
				break;
			case 6:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREY());
				break;
			case 7:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
				break;
			case 8:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.PINK());
				break;
			case 9:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.RED());
				break;
			case 10:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
			case 11:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
				break;
		
			default :
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
		
		}
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
		255,
		225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
		xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
		xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
		yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
		yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50); 
		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
		"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
		25, 35, 15
	} );*/
		String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String strSql1=strSql.replace("argument6","'"+argument6+"'");
		String strSql11=strSql1.replace("argument1","'"+argument1+"'");
		String strSql12=strSql11.replace("argument2","'"+argument2+"'");
		String strSql13=strSql12.replace("argument3","'"+argument3+"'");
		String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
		Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
		TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
		String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String strSql21=strSql2.replace("argument6","'"+argument6+"'");
		String strSql22=strSql21.replace("argument1","'"+argument1+"'");
		String strSql23=strSql22.replace("argument2","'"+argument2+"'");
		String strSql24=strSql23.replace("argument3","'"+argument3+"'");
		String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
		String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
		String strSql31=strSql3.replace("argument6","'"+argument6+"'");
		String strSql32=strSql31.replace("argument1","'"+argument1+"'");
		String strSql33=strSql32.replace("argument2","'"+argument2+"'");
		String strSql34=strSql33.replace("argument3","'"+argument3+"'");
		String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
	
		ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
		ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
		
	
		
	 
	
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		//bs1.setTranslucent( true );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
		//bs1.setCurveFitting( CurveFittingImpl.create( ) );
		
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );
		bs2.setDataSet( orthoValues2 );
		//bs2.setTranslucent( true );
		bs2.setRiserOutline( null );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );
		//bs2.setCurveFitting( CurveFittingImpl.create( ) );		
		if(Stacked.equals("1"))
		{
			bs1.setStacked(true);
			bs2.setStacked(true);
        
		}
        
		else
		{
        	
			bs1.setStacked(false);
			bs2.setStacked(false);
        
		}			

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1);
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );

		return cwaBar;
	}
	
	
	
	
	
	
	
	
	
	public static final Chart createMultiYAxisChart(String sqlForTextData,String sqlForNumberData,String sqlForNumberData2,String sqlForNumberData3,String sqlLegendData,String sqlLegendData2,String sqlLegendData3,String Subtype,String ChartHeading,String YaxisTitle,String XaxisTitle,int bcolor,String Transpose,String Stacked)
	{
		ChartWithAxes cwaBar = ChartWithAxesImpl.create( );


		switch(bcolor){
			case 1:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLACK());
				break;
			case 2:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.BLUE());
				break;
			case 3:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.CREAM());
				break;
			case 4:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.CYAN());
				break;
			case 5:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREEN());
				break;
			case 6:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.GREY());
				break;
			case 7:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.ORANGE());
				break;
			case 8:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.PINK());
				break;
			case 9:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.RED());
				break;
			case 10:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
			case 11:
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.YELLOW());
				break;
	
			default :
				cwaBar.getBlock().setBackground(ColorDefinitionImpl.WHITE());
				break;
	
		}
		// Plot
		cwaBar.getBlock( ).setBackground( ColorDefinitionImpl.WHITE( ) );
		cwaBar.getBlock( ).getOutline( ).setVisible( true );
		Plot p = cwaBar.getPlot( );
		p.getClientArea( ).setBackground( ColorDefinitionImpl.create( 255,
		255,
		225 ) );
		p.getOutline( ).setVisible( false );

		// Title
		cwaBar.getTitle( ).getLabel( ).getCaption( ).setValue( ChartHeading );//$NON-NLS-1$

		// Legend
		Legend lg = cwaBar.getLegend( );
		lg.getText( ).getFont( ).setSize( 16 );
		lg.setItemType( LegendItemType.CATEGORIES_LITERAL );

		// X-Axis
		Axis xAxisPrimary = cwaBar.getPrimaryBaseAxes()[0];
		xAxisPrimary.setType(AxisType.TEXT_LITERAL);
		xAxisPrimary.getMajorGrid().setTickStyle(TickStyle.BELOW_LITERAL);
		xAxisPrimary.getOrigin().setType(IntersectionType.VALUE_LITERAL);
	     //xAxisPrimary.getTitle().setVisible(false);
		xAxisPrimary.setLabelPosition(Position.BELOW_LITERAL);
		xAxisPrimary.setTitlePosition(Position.BELOW_LITERAL);
		xAxisPrimary.getTitle( ).getCaption( ).setValue( XaxisTitle );
		xAxisPrimary.getTitle( ).setVisible( true );
		Axis yAxisPrimary = cwaBar.getPrimaryOrthogonalAxis(xAxisPrimary);
		yAxisPrimary.getMajorGrid().setTickStyle(TickStyle.LEFT_LITERAL);
		yAxisPrimary.setType(AxisType.LINEAR_LITERAL);
		yAxisPrimary.getLabel().getCaption().getFont().setRotation(50);
	
		yAxisPrimary.getTitle( ).getCaption( ).setValue( YaxisTitle );
		yAxisPrimary.getTitle( ).setVisible( true );
	// yAxisPrimary.getTitle().getCaption().setValue(" "); 
		xAxisPrimary.getLabel().getCaption().getFont().setRotation(50); 	
		// Data Set
		/*TextDataSet categoryValues = TextDataSetImpl.create( new String[]{
		"Item 1", "Item 2", "Item 3"} );//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		NumberDataSet orthoValues = NumberDataSetImpl.create( new double[]{
		25, 35, 15
	} );*/
		String strSql=sqlForTextData.replace("argument5","'"+argument5+"'");
		String strSql1=strSql.replace("argument6","'"+argument6+"'");
		String strSql11=strSql1.replace("argument1","'"+argument1+"'");
		String strSql12=strSql11.replace("argument2","'"+argument2+"'");
		String strSql13=strSql12.replace("argument3","'"+argument3+"'");
		String strSql14=strSql13.replace("argument4","'"+argument4+"'");	
		Vector  vs =NewDataBaseLayer.getBarChartText(strSql14);
	
		TextDataSet categoryValues = TextDataSetImpl.create(vs);
	
			
		String strSql2=sqlForNumberData.replace("argument5","'"+argument5+"'");
		String strSql21=strSql2.replace("argument6","'"+argument6+"'");
		String strSql22=strSql21.replace("argument1","'"+argument1+"'");
		String strSql23=strSql22.replace("argument2","'"+argument2+"'");
		String strSql24=strSql23.replace("argument3","'"+argument3+"'");
		String strSql25=strSql24.replace("argument4","'"+argument4+"'");
	
	
		String strSql3=sqlForNumberData2.replace("argument5","'"+argument5+"'");
		String strSql31=strSql3.replace("argument6","'"+argument6+"'");
		String strSql32=strSql31.replace("argument1","'"+argument1+"'");
		String strSql33=strSql32.replace("argument2","'"+argument2+"'");
		String strSql34=strSql33.replace("argument3","'"+argument3+"'");
		String strSql35=strSql34.replace("argument4","'"+argument4+"'");
	
	
		String strSql4=sqlForNumberData3.replace("argument5","'"+argument5+"'");
		String strSql41=strSql4.replace("argument6","'"+argument6+"'");
		String strSql42=strSql41.replace("argument1","'"+argument1+"'");
		String strSql43=strSql42.replace("argument2","'"+argument2+"'");
		String strSql44=strSql43.replace("argument3","'"+argument3+"'");
		String strSql45=strSql44.replace("argument4","'"+argument4+"'");	
	
	
		ArrayList usage1 =NewDataBaseLayer.getNumberdata(strSql25);
		ArrayList usage2 =NewDataBaseLayer.getNumberdata1(strSql35);  
		ArrayList usage3 =NewDataBaseLayer.getNumberdata2(strSql45);		
	
		
	 
	
		NumberDataSet orthoValues1 = NumberDataSetImpl.create(usage1);
		NumberDataSet orthoValues2 = NumberDataSetImpl.create(usage2);
		NumberDataSet orthoValues3 = NumberDataSetImpl.create(usage3);
		// X-Series
		Series seCategory = SeriesImpl.create( );
		seCategory.setDataSet( categoryValues );

		SeriesDefinition sdX = SeriesDefinitionImpl.create( );
		sdX.getSeriesPalette( ).update( 0 );
		xAxisPrimary.getSeriesDefinitions( ).add( sdX );
		sdX.getSeries( ).add( seCategory );

		// Y-Series
		BarSeries bs1 = (BarSeries) BarSeriesImpl.create( );
		bs1.setSeriesIdentifier( sqlLegendData );
		bs1.setDataSet( orthoValues1 );
		bs1.setRiserOutline( null );
		bs1.getLabel( ).setVisible( true );
		bs1.setLabelPosition( Position.INSIDE_LITERAL );
				
		BarSeries bs2 = (BarSeries) BarSeriesImpl.create( );
		bs2.setSeriesIdentifier( sqlLegendData2 );
		bs2.setDataSet( orthoValues2 );
		bs2.setRiserOutline( null );
		bs2.getLabel( ).setVisible( true );
		bs2.setLabelPosition( Position.INSIDE_LITERAL );
			
		
		BarSeries bs3 = (BarSeries) BarSeriesImpl.create();
		bs3.setSeriesIdentifier(sqlLegendData3);
		bs3.setDataSet(orthoValues3);
		bs3.setRiserOutline(null);
	
		bs3.getLabel().setVisible(true);
		bs3.setLabelPosition(Position.INSIDE_LITERAL);		
		
		if(Stacked.equals("1"))
		{
			bs1.setStacked(true);
			bs2.setStacked(true);
			bs3.setStacked(true);
		}
        
		else
		{
        	
			bs1.setStacked(false);
			bs2.setStacked(false);
			bs3.setStacked(false);	
		}		
		

		SeriesDefinition sdY = SeriesDefinitionImpl.create( );
		sdY.getSeriesPalette().update(1); 
		yAxisPrimary.getSeriesDefinitions( ).add( sdY );
		sdY.getSeries( ).add( bs1 );
		sdY.getSeries( ).add( bs2 );
		sdY.getSeries( ).add( bs3 );
                
                
		return cwaBar;
	}	
	//////////////////////////////////end multiple bar chart ///////////////
	
	
	
	
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        	throws IOException, ServletException {
        doGet(request, response);
    }		
}

