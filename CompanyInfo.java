
public class CompanyInfo {

	private String nasdaqCode;
	
	public CompanyInfo( String nasdaqCode){
		this.nasdaqCode = nasdaqCode; 
	}
	
	public String getPage(){
		return "http://quotes.nasdaq.com/asp/SummaryQuote.asp?symbol=" + this.nasdaqCode +  "&selected=" +	nasdaqCode;
	}
	
}
