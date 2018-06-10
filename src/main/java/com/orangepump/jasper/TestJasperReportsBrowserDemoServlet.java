package offering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class TestJasperReportsBrowserDemoServlet extends HttpServlet {

  @Override
  protected void doGet(
      HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
  
    ServletOutputStream servletOutputStream = response.getOutputStream();
    File reportFile = new File(getServletConfig()
    .getServletContext().getRealPath("/reports/report2.jasper"));
    
    
    List<Map<String, ?>> result = new ArrayList<Map<String,?>>();
    for( int i = 0; i < 10; i++) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      map.put("col1", "값_" + i + "_1");
      map.put("col2", "값_" + i + "_2");
      map.put("col3", "값_" + i + "_3");
      map.put("col4", "값_" + i + "_4");
      result.add(map);
    }

    JRDataSource datasource = new JRBeanCollectionDataSource(result);
    
    
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("param1", "parameter1");
    parameters.put("param2", "parameter2");

    byte[] bytes = null;
    
    try {
      JasperReport reporte = (JasperReport) JRLoader.loadObject(reportFile);
      JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parameters, datasource);  

      JRExporter exporter = new JRPdfExporter();  
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream()); 
      exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, 
          "this.print({bUI: true, bSilent: false, bShrinkToFit: true});");
      
      response.setContentType("application/pdf");
//      response.setHeader("Content-Disposition", "attachment; filename=\test.pdf\"");
      
      exporter.exportReport(); 
      
      
    } catch (JRException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
//    super.doGet(request, response);
  }
  
}
