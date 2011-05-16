import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.*;

public class JTextFieldDemo2 extends JFrame implements ActionListener {
	JTextField jtfInput;

	JTextArea jtAreaOutput;

	String newline = "\n";

	public JTextFieldDemo2() {
		createGui();
	}

	public void createGui() {
		jtfInput = new JTextField(20);
		jtfInput.addActionListener(this);

		jtAreaOutput = new JTextArea(5, 20);
		jtAreaOutput.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(jtAreaOutput,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		GridBagLayout gridBag = new GridBagLayout();
		Container contentPane = getContentPane();
		contentPane.setLayout(gridBag);
		
		GridBagConstraints gridBagConstraintsx1 = new GridBagConstraints();
        gridBagConstraintsx1.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraintsx1.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(jtfInput, gridBagConstraintsx1);
        
        GridBagConstraints gridBagConstraintsx2 = new GridBagConstraints();
        gridBagConstraintsx2.weightx = 1.0;
        gridBagConstraintsx2.weighty = 1.0;
        contentPane.add(scrollPane, gridBagConstraintsx2);

	}

	private String parseCompanyInfo(String nasdaqCode){
		String link = "http://quotes.nasdaq.com/asp/SummaryQuote.asp?symbol=" + nasdaqCode +
		"&selected=" + nasdaqCode;

		StringBuffer result = new StringBuffer();
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(link).openStream()));
			String line; 
			while((line = in.readLine()) != null) {
				result.append(line + "\n");
			}
			in.close();

		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		String tmp = "Company Description  (as filed with the SEC)";
		String tmp1 = result.toString(); 
		String res = tmp1.substring(tmp1.indexOf(tmp) + tmp.length());
		res = res.substring(res.indexOf(">") + 1);
		res = res.substring(res.indexOf(">") + 1);
		res = res.substring(0, res.indexOf("&nbsp"));
		return res;
	}
	
	public void actionPerformed(ActionEvent evt) {
		String nasdaqCode = jtfInput.getText();
		//TODO: check if the file is in list

		jtAreaOutput.append(parseCompanyInfo(nasdaqCode));
		jtfInput.selectAll();
	}
	
	   public static void main(String[] args) {
		   JTextFieldDemo2 jtfTfDemo = new JTextFieldDemo2();		
	        jtfTfDemo.pack();
	        jtfTfDemo.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                System.exit(0);
	            }
	        });
	        jtfTfDemo.setVisible(true);
	    }
}
