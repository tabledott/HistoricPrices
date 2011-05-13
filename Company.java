import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Company {
	
	private String nasdaqCode;
	//keys can be Open,High,Low,Close,Volume
	private Map<String, ArrayList<Double>> financial_data;
	private ArrayList<String> dates;
	
	public String getNasdaqCode(){
		return nasdaqCode;
	}
	
	public Company(String _nasdaqCode){
		nasdaqCode = _nasdaqCode;
		financial_data = new HashMap<String, ArrayList<Double> >();
		dates = new ArrayList<String>();
	}
	
	//Can be Open,High,Low,Close,Volume
	public ArrayList<Double> getValue(String info){
        if( financial_data.containsKey(info) ){
        	return (ArrayList<Double>)financial_data.get(info);
        }else{
        	return null;
        }
	}
	
	public double getPriceNdayAgo(String info, int daysAgo)
	{
		return financial_data.get(info).get(daysAgo);
	}
	public boolean loadfromCSV(String fileName){
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String strLine = "";
			StringTokenizer st = null;
			int i = 0;
			String[] keys = new String[10];
			
			strLine = br.readLine();
			st = new StringTokenizer(strLine, ",");
			while(st.hasMoreTokens()) { 	
				String str = st.nextToken();
				keys[i++] = str;
				if( i>0)
				financial_data.put(str, new ArrayList<Double>());
			}
			
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null)
			{
				StringTokenizer st1 = new StringTokenizer(strLine, ",");
				if (st1.hasMoreTokens()){
					dates.add(st1.nextToken());
				}
				i = 1;
				while(st1.hasMoreTokens()){
					financial_data.get(keys[i]).add(Double.parseDouble(st1.nextToken()));
					i++;
				}
			}
			return true;
		}
	
		catch(Exception e)
		{
			System.out.println("Exception while reading csv file: " + e);
			return false;
		}
	}
}
