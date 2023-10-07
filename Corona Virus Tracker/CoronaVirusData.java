/**
 *  CoronaVirusData.java
 *  This program creates a canvas where an animation
 *  of a bar graph containing Corona Virus data from
 *  the beginning of March to now is printed. Using
 *  data from a text file, data3.txt, and OpenFile.java
 *  the amount of cases for 5 states with some of the
 *  most corona virus cases in the US is displayed.
 *  The total amount of cases in the US for the date
 *  the data is from is also printed out. Each run a
 *  different date is shown and the progression of
 *  cases in those states and the US is demonstrated
 *  over the months.
 * @author   Sneha Agarwal
 * @version  1.0
 * @since    9/11/2020
 */

import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class CoronaVirusData
{
	/**  The number of cases for each state. */
	private int californiaCases;
	private int floridaCases;
	private int texasCases;
	private int nyCases;
	private int illinoisCases;

	/**  The current date for the data. */
	private String date;

	/**  The number of cases for the total US */
	private int totalUSCases;

	/**  The state with the most cases' info. */
	private int maxCases;
	private String maxCaseState;

	/**  The canvas dimensions */
	private int canvasWidth;
	private int canvasHight;

	/**  Creates a CoronaVirusData object, setting
	 * the variables to default values. Also setting
	 * the canvas sizes.
	 */
	public CoronaVirusData ( )
	{
		californiaCases = 0;
		floridaCases = 0;
		texasCases = 0;
		nyCases = 0;
		illinoisCases = 0;
		totalUSCases = 0;
		maxCaseState = null;
		maxCases = 0;
		canvasHight = 775;
		canvasWidth = 1400;
		date = "0";

	}

	/**
	 *  The main method, to create and run the instance of CoronaVirusData,
	 *  named "run" here. The methods used to pull data and set up the canvas
	 *  are also called here.
	 *  @param args          The array of String that can be used to
	 *                       pass in String arguments from the command line.
	 */
	public static void main(String [] args)
	{
		CoronaVirusData run = new CoronaVirusData();
		StdDraw.enableDoubleBuffering();
		run.setUpCanvas();
		run.saveData();
	}

	/**
	 *  Sets up the canvas of this JFrame, using the appropriate size. The
	 *  x and y axes are also scaled, ready for components like rectangles,
	 *  lines, and text to be placed.  The background is set to black.
	 */
	public void setUpCanvas ()
	{
		StdDraw.setCanvasSize(canvasWidth, canvasHight);
		StdDraw.setXscale(0, canvasWidth);
		StdDraw.setYscale(0, canvasHight);
	}

	/** This method pulls information from the data3.txt
	 * file using OpenFile.java of the number of cases
	 * for specific states. The cases are then stored
	 * into variables and the state with the largest
	 * amount of cases is identified and stored into
	 * another variable. Each run the printToCanvas
	 * method is called to update the data on the screen.
	 */
	public void saveData () {
		OpenFile readData = new OpenFile();
		Scanner scan = readData.openToRead("data3.txt");
		String line = null;
		int previousTotal = 0;

		//reads line by line data3.txt
		while(scan.hasNext())
		{
			line = scan.nextLine();
			String data = line.substring(line.indexOf(",") + 1);
			String runningDate = line.substring(0, line.indexOf(","));;
			String stateName = data.substring(0,data.indexOf(","));
			String casesAndDeaths = data.substring(data.indexOf(",") + 4);
			String strCases = casesAndDeaths.substring(0, casesAndDeaths.indexOf(","));

			//if the line is not the header
			if(!stateName.equals("state")){
				int cases = Integer.parseInt(strCases);

				//find which state has the most cases
				if(cases > maxCases) {
					maxCases = cases;
					maxCaseState = stateName;
				}

				//store the number of cases
				if(stateName.equals("California"))
					californiaCases = cases;
				if(stateName.equals("Florida"))
					floridaCases = cases;
				if(stateName.equals("Texas"))
					texasCases = cases;
				if(stateName.equals("New York"))
					nyCases = cases;
				if(stateName.equals("Illinois"))
					illinoisCases = cases;

				//checks whether the date has changed
				if(!date.equals(runningDate))
				{
					date = runningDate;
					printToCanvas();
					totalUSCases = 0;
				}
				totalUSCases += cases;
			}
		}
		printToCanvas();
		scan.close();
	}

	/**
	 *  This method clears out the canvas after
	 *  every run of the while loop and calls other
	 *  functions to print out the updated information
	 *  then shows it for 100 ms.
	 */
	public void printToCanvas ()
	{
		StdDraw.clear(StdDraw.BLACK);

		displayInfo ();
		drawBarGraphs();

		StdDraw.show();
		StdDraw.pause(100);
	}

	/**
	 *  This method prints out a bar graph for each
	 *  of the states and their name. Each bar graph
	 *  has a different color and is labeled. Using the
	 *  maximum cases all of the bar graphs are scaled
	 *  accordingly into the canvas.
	 */
	public void drawBarGraphs()
	{
		double scaleWidth = 0;
		Font font = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(font);

		//California
		scaleWidth = (canvasWidth * californiaCases) / maxCases - .1*(canvasWidth * californiaCases) / maxCases;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(scaleWidth/2 + 17, canvasHight - 77, scaleWidth/2, 25);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(scaleWidth/2 + 15, canvasHight - 75, scaleWidth/2, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(scaleWidth - 60,canvasHight - 75,String.format("%,d", californiaCases));
		StdDraw.textLeft(scaleWidth + 25,canvasHight - 75,"California");

		//Texas
		scaleWidth = (canvasWidth * texasCases) / maxCases - .1*(canvasWidth * texasCases) / maxCases;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(scaleWidth / 2 + 17, canvasHight - 152, scaleWidth / 2, 25);
		StdDraw.setPenColor(StdDraw.ORANGE);
		StdDraw.filledRectangle(scaleWidth / 2 + 15, canvasHight - 150, scaleWidth / 2, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(scaleWidth - 60, canvasHight - 150, String.format("%,d", texasCases));
		StdDraw.textLeft(scaleWidth + 25, canvasHight - 150, "Texas");

		//Florida
		scaleWidth = (canvasWidth*floridaCases)/ maxCases - .1*(canvasWidth * floridaCases) / maxCases;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(scaleWidth/2 + 17, canvasHight - 227, scaleWidth/2, 25);
		StdDraw.setPenColor(new Color(41, 230, 179));
		StdDraw.filledRectangle(scaleWidth/2 + 15, canvasHight - 225, scaleWidth/2, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(scaleWidth - 60,canvasHight - 225,String.format("%,d", floridaCases));
		StdDraw.textLeft(scaleWidth + 25,canvasHight - 225,"Florida");

		//New York
		scaleWidth = (canvasWidth*nyCases)/ maxCases - .1*(canvasWidth * nyCases) / maxCases;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(scaleWidth/2 + 17, canvasHight - 302, scaleWidth/2, 25);
		StdDraw.setPenColor(new Color(237, 75, 57));
		StdDraw.filledRectangle(scaleWidth/2 + 15, canvasHight - 300, scaleWidth/2, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(scaleWidth - 60,canvasHight - 300,String.format("%,d", nyCases));
		StdDraw.textLeft(scaleWidth + 25,canvasHight - 300,"New York");

		//Illinois
		scaleWidth = (canvasWidth*illinoisCases)/ maxCases - .1*(canvasWidth * illinoisCases) / maxCases;
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(scaleWidth/2 + 17, canvasHight - 377, scaleWidth/2, 25);
		StdDraw.setPenColor(new Color(24, 164, 219));
		StdDraw.filledRectangle(scaleWidth/2 + 15, canvasHight - 375, scaleWidth/2, 25);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(scaleWidth - 60,canvasHight - 375,String.format("%,d", illinoisCases));
		StdDraw.textLeft(scaleWidth + 25,canvasHight - 375,"Illinois");

	}

	/**
	 *  This method prints out the scale in the
	 *  background and the total US cases along
	 *  with the date and title in the corner.
	 *  The scale in the background is also scaled
	 *  by the maximum value and is shifted based
	 *  on it.
	 */
	public void displayInfo ()
	{
		Font font = new Font("Arial", Font.BOLD, 16);
		StdDraw.setFont(font);
		String currentDate = fixDate(date);

		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.line(15, canvasHight - 40, 15, 20);
		StdDraw.textLeft(10,canvasHight - 30,"0k");
		double buffer = 0;

		//prints out the scale in the background
		for (int i = 1; i <= 9; i++){
			if (maxCases < 10){
				String value = i +  "";
				buffer = .1 * (canvasWidth * 1 * i) / maxCases - 15;
				double x0 = (canvasWidth * 1 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 1 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 1 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else if (maxCases < 100){
				String value = i * 10 +  "";
				buffer = .1 * (canvasWidth * 10 * i) / maxCases - 15;
				double x0 = (canvasWidth * 10 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 10 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 10 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else if (maxCases < 1000){
				String value = i * 100 +  "";
				buffer = .1 * (canvasWidth * 100 * i) / maxCases - 15;
				double x0 = (canvasWidth * 100 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 100 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 100 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else if (maxCases < 10000){
				String value = i * 1 + "k";
				buffer = .1 * (canvasWidth * 1000 * i) / maxCases - 15;
				double x0 = (canvasWidth * 1000 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 1000 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 1000 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else if (maxCases < 100000){
				String value = i * 10 + "k";
				buffer = .1 * (canvasWidth * 10000 * i) / maxCases - 15;
				double x0 = (canvasWidth * 10000 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 10000 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 10000 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else if(maxCases < 200000){
				String value = i * 50 + "k";
				buffer = .1 * (canvasWidth * 50000 * i) / maxCases - 15;
				double x0 = (canvasWidth * 50000 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 50000 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 50000 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
			else {
				String value = i * 100 + "k";
				buffer = .1 * (canvasWidth * 100000 * i) / maxCases - 15;
				double x0 = (canvasWidth * 100000 * i) / maxCases - buffer;
				double y0 = canvasHight - 40;
				double x1 = (canvasWidth * 100000 * i) / maxCases - buffer;
				double y1 = 20;
				StdDraw.line(x0, y0, x1, y1);
				StdDraw.textLeft((canvasWidth * 100000 * i) / maxCases - buffer - 15, canvasHight - 30, value);
			}
		}

		//prints out the additional information in the right hand bottom corner
		font = new Font("Arial", Font.BOLD, 35);
		StdDraw.setFont(font);
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.textLeft(canvasWidth - 375,220,currentDate);
		StdDraw.textLeft(canvasWidth - 375,170,"US Total: " + String.format("%,d", totalUSCases));
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.textLeft(canvasWidth - 375,120,"CORONAVIRUS");
		StdDraw.textLeft(canvasWidth - 375,70,"Cases by State");
	}

	/**
	 *  Converts the date in numerical form by
	 *  finding the corresponding month to the number
	 *  and returning it as a String.
	 *  @param date        	The date in numeral form
	 *  @return completeDate			The date written out.
	 */
	public String fixDate (String date)
	{
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8);
		switch (month) {
			case "01":
				month = "January";
				break;
			case "02":
				month = "Febuary";
				break;
			case "03":
				month = "March";
				break;
			case "04":
				month = "April";
				break;
			case "05":
				month = "May";
				break;
			case "06":
				month = "June";
				break;
			case "07":
				month = "July";
				break;
			case "08":
				month = "August";
				break;
			case "09":
				month = "September";
				break;
			case "10":
				month = "October";
				break;
			case "11":
				month = "November";
				break;
			case "12":
				month = "December";
				break;
		}
		String completeDate = month + " " + day + ", " + year;
		return completeDate;

	}

}