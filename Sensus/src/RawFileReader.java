import java.io.*;
import java.util.Locale;

public class RawFileReader {
	
	// File of data
	private File file;
	private String currentdir = "";
	
	// CreatedFlag
	private boolean Created;
	
	// The constructor. Name = "rawdata.dat"
	public RawFileReader(String name) throws IOException {
		currentdir = System.getProperty("user.dir");
		try {
			file = new File(currentdir, name);
			if (file.createNewFile()) {
				System.out.println("Data file created");
				Created = true;
			} else {
				System.out.println("Data file already present");
				Created = true;
			}
		} catch (IOException e) {
			System.err.println("Error in connecting to file: " + e.getMessage());
			Created = false;
		}
	}
	
	// Clearing the data file
	public boolean ClearDataFile() throws FileNotFoundException, IOException {
		if (Created) {
			try {
				// Opening file for rewrite
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
				// Clearing the file
				out.writeChars("");
				// Closing the file
				out.close();
				return (boolean) true;
			} catch (FileNotFoundException fn) {
				System.err.println("Error in connecting to file: " + fn.getMessage());
				return (boolean) false;
			} catch (IOException e) {
				System.err.println("Error in writing to file: " + e.getMessage());
				return (boolean) false;
			} 
		} else {
			return (boolean) false;
		}
	}	
	
	// Filing the destination file by data
	public boolean processRawFile(int recordCount) throws IOException {
		if (Created) {
			String  curLine = null, s = null;
			String FIPS = "", FIPSpr = "";
			int totalPop = 0, totalChildrenpop = 0, totalChildrenPovPop = 0;
			double povPercentage;
			try {
				// Opening file for rewrite
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
				BufferedReader br = new BufferedReader(new FileReader(currentdir + "\\USSD13.txt"));
				//printing table header to file
				out.writeChars(String.format("%5s%14s%18s%26s%17s%n", "State", "Population", "Child Population", "Child Poverty Population", "% Child Poverty"));
				out.writeChars(String.format("%5s%14s%18s%26s%17s%n", "-----", "----------", "----------------", "------------------------", "---------------"));
				//Iterating through raw table 
				boolean notFirstTime = false;
				int recordCounter = 0;
				while (((curLine = br.readLine()) != null) && (recordCounter <= recordCount)) {
					s = curLine.substring(0, 2);
		            FIPS = s.trim(); 
		            if (!FIPS.equals(FIPSpr)) {	//New FIPS Code?
		            	//printing values to file
		            	if (notFirstTime) {
		            		povPercentage = ((double)totalChildrenPovPop/(double)totalChildrenpop)*100;
		            		out.writeChars(String.format(Locale.ENGLISH, "%5s%,14d%,18d%,26d%,17.2f%n", FIPSpr, totalPop, totalChildrenpop, totalChildrenPovPop, povPercentage));
		            		totalPop = 0; totalChildrenpop = 0; totalChildrenPovPop = 0;
		            	}
		            	notFirstTime = true;
		            }
		            //summarize quantity
		            s = curLine.substring(82, 90);
		            totalPop = totalPop + Integer.valueOf(s.trim());
		            s = curLine.substring(91, 99);
		            totalChildrenpop = totalChildrenpop + Integer.valueOf(s.trim());
		            s = curLine.substring(100, 108);
		            totalChildrenPovPop = totalChildrenPovPop + Integer.valueOf(s.trim());  
		            FIPSpr = FIPS;
		            recordCounter ++;
		        }
				//printing last record
            	if (curLine == null) {
            		povPercentage = ((double)totalChildrenPovPop/(double)totalChildrenpop)*100;
            		out.writeChars(String.format(Locale.ENGLISH, "%5s%,14d%,18d%,26d%,17.2f%n", FIPSpr, totalPop, totalChildrenpop, totalChildrenPovPop, povPercentage));
            		totalPop = 0; totalChildrenpop = 0; totalChildrenPovPop = 0;
            	}
				out.close();
				br.close();
				return (boolean) true;
			} catch (IOException e) {
				System.err.println("Error working with file: " + e.getMessage());
				return (boolean) false;
			}
		} else {
			return (boolean) false;
		}
	}
	
	public static void main(String[] args)  throws IOException {
		RawFileReader rf = new RawFileReader("data.txt");
		if (rf.ClearDataFile()) { System.out.println("File of data succesfully cleared! "); }
		if (rf.processRawFile(13486)) { System.out.println("File of data succesfully filled! "); }
	}
}
