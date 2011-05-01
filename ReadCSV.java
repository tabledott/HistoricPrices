import java.util.*;
import java.io.*;

public class ReadCSV {
	
	private String fileName;
	
	public ReadCSV(String file){
		fileName = file;
	}
	
	ArrayList<ArrayList<Double>> returnData(){		
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String strLine = "";
			StringTokenizer st = null;
			int tokenNumber = 0;
			strLine = br.readLine();
			st = new StringTokenizer(strLine, ",");
			while(st.hasMoreTokens()) { 	
				result.add(new ArrayList<Double>());
				st.nextToken();
			}
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null)
			{
				st = new StringTokenizer(strLine, ",");
				st.nextToken();
				while(st.hasMoreTokens())
				{
					result.get(tokenNumber++).add(Double.parseDouble(st.nextToken()));
				}
					
				tokenNumber = 0;
			}
		}
	
		catch(Exception e)
		{
			System.out.println("Exception while reading csv file: " + e);
		}
			
		return result;
	}

}
