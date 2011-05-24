import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class GetWebSite {
	
	static String errors = "";
	static List<String> codes = new ArrayList<String>();

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		
		List<String> allCompanyCodes = getAllCompanyCodes();
		for (String nasdaqCode : allCompanyCodes) {
			getCompanyInfo(nasdaqCode);
		}
		
		
		
		System.out.println("Done");
		long time = System.currentTimeMillis() - start;
		
		String result = String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(time),
			    TimeUnit.MILLISECONDS.toSeconds(time) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
			);
		
		
		System.out.println(result);
		
		System.out.println(errors);
		
		// second try
		for (String code : codes) {
			getCompanyInfo(code);
		}
		
	}

	private static void getCompanyInfo(String nasdaqCode) {
		String fileName = "C:\\Users\\Duxmaster\\Desktop\\lucene\\index\\"
				+ nasdaqCode + ".html";

		String link = "http://quotes.nasdaq.com/asp/SummaryQuote.asp?symbol="
				+ nasdaqCode + "&selected=" + nasdaqCode;

		File f = new File(fileName);
		if(f.exists()) {
			return;
		}
		try {
			InputStream in = new URL(link).openStream();
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data)) >= 0) {
				bout.write(data, 0, count);
			}
			bout.close();
			fos.close();
			in.close();
		} catch (Exception e) {
			errors += e.getMessage() + "\n";
			codes.add(nasdaqCode);
		}
	}

	private static List<String> getAllCompanyCodes() {
		String fileName = "C:\\Users\\Duxmaster\\Desktop\\lucene\\companylist.csv";

		List<String> result = new ArrayList<String>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String strLine = "";
			StringTokenizer st = null;
			strLine = br.readLine();
			// read comma separated file line by line
			while ((strLine = br.readLine()) != null) {
				st = new StringTokenizer(strLine, ",");
				String code = st.nextToken().replaceAll("\"", "");
				code = code.trim();
				result.add(code);
			}
		} catch (Exception e) {
			System.out.println("Exception while reading csv file: " + e);
		}

		return result;
	}

}
