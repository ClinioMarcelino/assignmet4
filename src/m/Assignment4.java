package m;

import java.util.Scanner;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Clinio Marcelino<br>
 * 
 * @version 1.0
 * @date Feb 29 2024
 * 
 * This class was made to read files that have numbers separated by " "(spaces) and create a two dimensional array.
 * Also some methods to find some things in these or other integers two dimensional arrays.
 * 
 */
public class Assignment4 {

	/**
	 *
	 * @param filename
	 * @return number of lines in a text file
	 * @throws Exception
	 */
	public static int getNoLines(String filename) throws Exception{
		try (Stream<String> fileStream = Files.lines(Paths.get(filename))) {
			return (int) fileStream.count();
		}
	}

	/**
	 * 
	 * @param filename - source file
	 * @return two dim array (jagged array), where each row in the array contains the values in one line of the file,
	 * the length of each row depends on the number of values in each line of the file.
	 * @throws Exception
	 */
	public static int[][] create2DArray(String filename) throws Exception {
		Scanner fsc;
		try {
			fsc = new Scanner(new File(filename));
		}catch(FileNotFoundException e) {
			throw new Exception("File not Found...");
		}
		int[][] arr = new int[getNoLines(filename)][];
		int row = 0;
		
		while (fsc.hasNextLine()) {
			String[] line = fsc.nextLine().trim().split(" ");
			arr[row] = new int[line.length];
			for(int i=0;i<line.length;i++) {
				arr[row][i] = Integer.parseInt(line[i]);
			}
			row++;
		}
		fsc.close();
		
        return arr;
	}
	
	/**
	 * 
	 * @param arr - multi dimensional array
	 * @return row - the number of the longest row, first appearance of the max size.
	 * @throws Exception if array is null or empty
	 */
	public static int findLongestRow(int[][] arr) throws Exception {
		if(!( arr.length >= 0 )|| arr == null)
			throw new Exception("The array im empty");
		int longest = arr[0].length;
		int row = 0;
		
		for(int i=1;i<arr.length;i++) {
			if(arr[i].length > longest) {
				row = i;
				longest = arr[i].length;
			}
		}
		return row;
	}
	
	/**
	 * 
	 * @param arr - multi dimension array
	 * @param row - row in the array for desired maximum value
	 * @return max - int max value in the desired row
	 * @throws Exception if row parameter is not in the array range
	 */
	public static int findMaxInRow(int[][] arr, int row) throws Exception {
		if(!( arr.length >= row )|| row < 0 )
			throw new Exception("Parameter row must be inside the array length");
		return findMax(arr[row]);
	}
	
	/**
	 * 
	 * @param arr - multi dimension array
	 * @return max - int max value of array
	 * @throws Exception if array is null or empty
	 */
	public static int findMax(int[][] arr) throws Exception {
		if(!(arr.length >= 0) || arr == null)
			throw new Exception("The array is empty");
		
		int max = findMax(arr[0]);
		for(int i=1; i<arr.length;i++)
			if(findMax(arr[i]) > max)
				max = findMax(arr[i]);
		
		return max;
	}
	
	/**
	 * 
	 * @param arr - single dimension array
	 * @throws Exception if array is null or empty
	 * @return int - maximum value of the array
	 */
	private static int findMax(int[] arr) throws Exception {
		if(!(arr.length >= 0) || arr == null)
			throw new Exception("The array is empty");
		
		int max = arr[0];
		for(int i=1;i<arr.length;i++) 
			if(arr[i]>=max)
				max=arr[i];

		return max;
	}
	
	public static void main(String[] args) {
		String filename = null;
		if (args.length <1) {
			System.out.println("usage: Assignment4 inputFilename ");
			System.exit(0);
			
		}
		filename = args[0];
		int arr[][] = null;
		try {
			System.out.println("Number of lines in the file = "+ getNoLines(filename));
			arr = create2DArray(filename);
			int longestRow = findLongestRow(arr);
			System.out.println("Longest row in the file is: "+ (longestRow+1 )+" ,with length of: "+arr[longestRow].length);
			System.out.println("Max value in first row = "+ findMaxInRow(arr, 0));
			System.out.println("Max value in file = "+ findMax(arr));
		} catch (Exception e) {
			System.out.print(e);
		}
	}

}
