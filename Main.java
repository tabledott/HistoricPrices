import java.io.*;

import javax.vecmath.*;


import com.gregdennis.drej.GaussianKernel;
import com.gregdennis.drej.LinearKernel;
import com.gregdennis.drej.MultiquadricKernel;
import com.gregdennis.drej.Regression;
import com.gregdennis.drej.Representer;

public class Main {
	
	@SuppressWarnings("restriction")
	public static void main(String args[]) throws IOException
	{			
		String[] codes = {"Open","High","Low","Close","Volume"};
		Company[] companies = 
		{new Company("MSFT"), new Company("GOOG"), new Company("AAPL"), 
			new Company("IMAX"), new Company("CSCO"), new Company("IBM"), 
			new Company("ORCL"), new Company("MCD")};
		
		for(int i = 0; i < companies.length; i++){
			String fileName = "D:\\FMI\\Magistratura\\Izvli4ane_na_informaciq\\GoogleGetDataProject\\HistoricPrices\\"
					+ companies[i].getNasdaqCode()+ ".csv";
			companies[i].loadfromCSV(fileName);
		}
		
	   FileWriter fstream = new FileWriter("statistics_results.txt");
	   BufferedWriter out = new BufferedWriter(fstream);
		
	   Statistics statistics = new Statistics();
		for( int i = 0; i < companies.length; i++)
			for( int j = 0; j < companies.length;j++){
				double averagePercentage = 0.0;
				
				//first statistics
				for (int daysBefore = 1; daysBefore <= 1000; daysBefore++)
				{					
					double[] regres = statistics.determineRegressionBetweenTwoVariables(companies[i].getValue("Close"), companies[j].getValue("Open"), daysBefore);
					double predicted_close = regres[0] * companies[i].getPriceNdayAgo("Open", daysBefore - 1) + regres[1];
					out.write(regres[0] + " " + regres[1]);
					out.write(" Nasdaq codes: " + companies[i].getNasdaqCode() + " " + companies[j].getNasdaqCode()+ " " + daysBefore);
					out.write("\n");
					double close = companies[j].getPriceNdayAgo("Close", daysBefore - 1);
					averagePercentage +=  100*(close-predicted_close)/close;
					out.write("Predicted: " + predicted_close + " Real: " + close + "Percentage: " + 100*(close-predicted_close)/close);
					out.write("\n");
				}
				
				
				Object[] opens = companies[i].getValue("Open").toArray();
				Object[] closes = companies[j].getValue("Close").toArray();
				
				double averagePercentageGaussian = 0.0;
				double averagePercentageLinear = 0.0;
				double averagePercentageQuadrattic = 0.0;
				
				for(int size = 1; size < 300; size++ ){
					double[] tmp_data_open = new double[size];
					double[] tmp_data_close = new double[size];
	
					for( int k = 0; k < size; k++){
						/*ArrayList<Double> a = new ArrayList<Double>();
						Double[] b = new Double[2];
						a.toArray(b);*/
						tmp_data_open[k] = Double.parseDouble(opens[size-k].toString());
						tmp_data_close[k] = Double.parseDouble(closes[size-k].toString());
					}
					
					
					//second statistics

					GMatrix data = new GMatrix(1, size, tmp_data_open);
					GVector values = new GVector(tmp_data_close);
					 // construct the kernel you want to use:
					GaussianKernel kernel = new GaussianKernel(0.5);
					
					double lambda = 0.5;
					 // do the regression, which returns a function fit to the data
					Representer representer = Regression.solve(data, values, kernel, lambda);
					
					double close = companies[j].getPriceNdayAgo("Close", 0);
					double[] tmp = {companies[i].getPriceNdayAgo("Open", 0)};					
					//given open for one day for first company should return close for second company for the same day
					double predicted_close_gaussian = representer.eval(new GVector(tmp));	 
					averagePercentageGaussian += 100*(close-predicted_close_gaussian)/close;	
					
					//third statistics
					LinearKernel kernel_linear = LinearKernel.KERNEL;					
					Representer representer_linear = Regression.solve(data, values, kernel_linear, lambda);									
					double predicted_close_linear = representer_linear.eval(new GVector(tmp));	 
					averagePercentageLinear += 100*(close-predicted_close_linear)/close;	
					
					//fourth statistics
					MultiquadricKernel quadratic_kernel = new MultiquadricKernel(0.5);
					Representer quadratic_representer =  Regression.solve(data, values, quadratic_kernel, lambda);
					double predicted_close_quadrattic = quadratic_representer.eval(new GVector(tmp));	 
					averagePercentageQuadrattic += 100*(close-predicted_close_quadrattic)/close;						
				}
				
				//fifth statistics - correlation 
				double res = statistics.Correlation(companies[i].getValue("Open"), companies[j].getValue("Close"));
				System.out.println("Correlation: " + companies[i].getNasdaqCode()+ " " + companies[j].getNasdaqCode()+ " " + res );
				System.out.println("Average error Ours "+ companies[i].getNasdaqCode() + " " + companies[j].getNasdaqCode()+ " " + averagePercentage/1000);
				System.out.println("Average error Gaussian: "+ companies[i].getNasdaqCode() + " " + companies[j].getNasdaqCode()+ " " + averagePercentageGaussian/100);
				System.out.println("Average error Linear: "+ companies[i].getNasdaqCode() + " " + companies[j].getNasdaqCode()+ " " + averagePercentageLinear/100);
				System.out.println("Average error Quadrattic: " + companies[i].getNasdaqCode() + " " + companies[j].getNasdaqCode()+ " " + averagePercentageQuadrattic/100);
			}
	}
}


