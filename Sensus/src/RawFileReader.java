import java.io.*;

public class RawFileReader {
	
	// File of data
	private File file;
	
	// CreatedFlag
	public boolean Created;
	
	// The constructor. Name = "rawdata.dat"
	public RawFileReader(String name) throws IOException {
		String currentdir = System.getProperty("user.dir");
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
	public boolean processRawFile() throws IOException {
		if (Created) {
			String  curLine = null;
			String[] curRawRecord, curProcessedRecord;
			String prevState, curState; prevState = "";
			
			try {
				// Opening file for rewrite
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
				BufferedReader br = new BufferedReader(new FileReader("url"));
				while ((curLine = br.readLine()) != null) {
		            curRawRecord = curLine.split(" ");
		            curState = curRawRecord[0];
		            if (curState == prevState) {
		            	//summarize quantyties
		            }
		        } 
				return (boolean) true;
			} catch (IOException e) {
				System.err.println("Error in writing to file: " + e.getMessage());
				return (boolean) false;
			}
		} else {
			return (boolean) false;
		}
	}
	
	
}
