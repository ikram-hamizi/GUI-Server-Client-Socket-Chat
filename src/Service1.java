/**************************************SERVICEBROKER**************************************
 * Filename: Service1.java
 * Name: Ikram Hamizi
 * Class: Software Engineering - VCU (SP18)
 * 
 * ***************************************************************************************
 * Description: Service1 class receives the adjectives from the service broker and contains 
 * the main method that outputs the synonym. This class searches for the word from a 2-D 
 * array that stores the pairs (adjective:synonym) found in the "trans.txt" text file.
 * ***************************************************************************************
 * 
 * in:  Adjective passed as an argument to the main method
 * out: Synonym of the adjective sent back to Server as outputStream
/*****************************************************************************************/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Service1 {

	public static void main(String[] args) throws IOException { //Exception propagated to the main program SB

		//	1. The Service1.jar file will read in this list and place the pairs into a 2-D array
		//	and match the parameter passed in to one of the synonym pairs.

		FileReader fr = new FileReader("trans.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;

		String[][] dict = new String[20][2];
		int i=0;

		for(;i<dict.length; i++) 
		{
			line=br.readLine(); //1- Read each line of the .txt file | 2- Split the synonym pair into two separate words
			dict[i][0] = line.split(":")[0]; //3- Save the 2 words in the 2-D array, adjacent to each other
			dict[i][1] = line.split(":")[1];
		}

		String adj = args[0];

		//  2. The Service1.jar file sends the result back to Server as an outputStream
		// --> If the parameter passed is not listed as one of the pairs �Word not found� is displayed.

		for(i=0; i<dict.length; i++)
			if(dict[i][0].equalsIgnoreCase(adj))
			{
				System.out.println("The synonym for \"" + adj + "\" is: " + dict[i][1]);
				break;
			}
			else if(dict[i][1].equalsIgnoreCase(adj))
			{
				System.out.println("The synonym for \"" + adj + "\" is: " + dict[i][0]);
				break;
			}

		//The array does not contain the searched adj
		if(i==dict.length)
			System.out.println("Word not found.");

		br.close();
		fr.close();
	}
}
