import java.io.*;

public class Vigenere {

	public static char encryptChar (char c, char p)
	{
		char x = Character.toUpperCase(c);
		return  (char) ((((x-'A')+(p-'A'))%26)+'A');
	}
	
	public static char decryptChar (char c, char p)
	{
		int i = (c-'A') - (p-'A');
		if (i<0)
			return (char) (26+i+'A');
		else
				return (char) (i+'A');
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// get inputs
		// while not eof
		//   read line into s
		//   encrypt s into s
		//   write line from s
		// close files
		
		try
		{
			String inputFileName = "C:/Users/Phil/Desktop/test.txt"; 
			String outputFileName = "C:/Users/Phil/Desktop/test.out"; 
			String phrase = "LXFMNZQBA";
			VigenereCipher v = new VigenereCipher();
			v.setPhrase(phrase);
			VigenereCipher v2 = new VigenereCipher();
			v2.setPhrase(phrase);
			
			String line;
			String outline = null;
			String testline = null;
			
			File infile = new File(inputFileName);
			FileReader inFileReader = new FileReader(infile);
			BufferedReader bufferedReader = new BufferedReader(inFileReader);

			File outfile = new File(outputFileName);
			FileWriter outFileWriter = new FileWriter(outfile);
			BufferedWriter bufferedWriter = new BufferedWriter(outFileWriter);
			
			while ((line = bufferedReader.readLine()) != null) {
				outline = v.encrypt(line);
				bufferedWriter.write(outline);
				bufferedWriter.newLine();
				// echoing decrypted line to check decryption
				testline = v2.decrypt(outline);
				System.out.println(testline);
			}
			inFileReader.close();
			bufferedWriter.close();
		
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
    	//String s = "Hello World!";
    	//String phrase = "LXMABDRZ";
    	//System.out.println(encrypt(s.charAt(0),phrase.charAt(0)));
    	//System.out.println(  decrypt( encrypt(s.charAt(0),phrase.charAt(0)) ,phrase.charAt(0))  );
	}

}
