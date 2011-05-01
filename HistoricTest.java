import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class HistoricTest {
	
	public static void getHistoricPricesInFile(String nasdaqCode) throws Exception {
		String fileName = "D:\\FMI\\Magistratura\\Izvli4ane_na_informaciq\\GoogleGetDataProject\\HistoricPrices\\"+nasdaqCode+".csv";
		
		String startMonth = "JAN", startDay = "1", startYear = "2001";
		String endMonth = "APR", endDay = "12", endYear = "2011";
	    
		String link = "http://www.google.com/finance/historical?q=" + nasdaqCode +
	    		"&startdate=" + startMonth +
	    		"+" + startDay +
	    		"%2C+" + startYear +
	    		"&enddate=" + endMonth +
	    		"+" + endDay +
	    		"%2C+" + endYear +
	    		"&output=csv";
	    System.out.println(link);
	    
	    InputStream in = new URL(link).openStream();
	    FileOutputStream fos = new FileOutputStream(fileName);
	    BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
	    byte data[] = new byte[1024];
	    int count;
	    while((count = in.read(data)) >= 0) {
    		bout.write(data,0,count);
	    }
	    bout.close();
	    fos.close();
	    in.close();
	}
	
	public static void main(String[] args) throws Exception {
		String[] nasdaqCodes = {"MSFT", "GOOG", "AAPL", "IMAX", "CSCO", "IBM", "ORCL", "MCD"};
		for (String code : nasdaqCodes) {
			getHistoricPricesInFile(code);
		}
	}
}
