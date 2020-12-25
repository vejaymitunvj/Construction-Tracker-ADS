
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.FileReader;

//##################### defining class rising city Main class where all functions takes place ###################
public class risingCity {
	int bNo;
	int eTOB;
	int tTOB;
	int nob = 0;
	int globalCounter = 0;
	int buildtimeAllocator = 0;
	int totalExecDays = 0;
	int totdays = 0;
	PrintStream printFile;
	String Ofile="output_file.txt";
	private static String IFILE;
	//##################### getters and setter for building properties number,execution time and total time ###################
	public int getBuildingNum() {
		return bNo;
	}

	public void setBuildingNum(int bNo) {
		this.bNo = bNo;
	}

	public int getExecutedTime() {
		return eTOB;
	}

	public void setExecutedTime(int eTOB) {
		this.eTOB = eTOB;
	}

	public int getTotalTime() {
		return tTOB;
	}

	public void setTotalTime(int tTOB) {
		this.tTOB = tTOB;
	}
	//##################### controller function where all the operations takes place ###################
	public void inputData(MinHeapRisingCity heapObj, RedBlackTreeRisingCity rbObj) throws IOException, URISyntaxException {
		//##################### change to write the file in output ###################
		printFile = new PrintStream(new File(Ofile));
		System.setOut(printFile);
		String input = null, inputType;
		int execCycle = 0, timer;
		
		//##################### Scanning input files for input ###################
		File file = new File(IFILE);
		Scanner inpt = new Scanner(file);

		//##################### retrieving out put from the files extraction functions to be performed ###################
		//##################### finding the total number of days the global counter has to run in order to execute the buildings ###################
		while (inpt.hasNextLine()) {
			input = (inpt.nextLine()).replaceAll("\\s", "");

			inputType = input.substring(input.indexOf(':') + 1, input.indexOf('('));

			if (inputType.equals("Insert")) {
				timer = Integer.parseInt(input.substring(input.indexOf(',') + 1, input.indexOf(')')));
				totalExecDays = totalExecDays + timer;
			}

		}
		
		//##################### Main operations where the global counter is checked everyday on finishing buildings ###################
		File file2 = new File(IFILE);
		Scanner inpt2 = new Scanner(file2);
		//##################### running the global counter until all building finish their execution ###################
		while (globalCounter < totalExecDays) {

			if (inpt2.hasNextLine()) {
				input = (inpt2.nextLine()).replaceAll("\\s", "");
				execCycle = Integer.parseInt(input.substring(0, input.indexOf(':')));
				inputType = input.substring(input.indexOf(':') + 1, input.indexOf('('));

				while (totdays < execCycle) {
					//##################### execution of buildings ###################
					executeBld(heapObj, rbObj);

				}

				if (totdays == execCycle) {
					//##################### performing operations at the time of input then executing one day ###################
					execOperations(input, heapObj, rbObj);
					executeBld(heapObj, rbObj);

				}

			} else {
				//##################### this is to execute finish the execution of wayne city ###################
				executeBld(heapObj, rbObj);
			}

		}
		inpt2.close();
		inpt.close();
	}

	//##################### insert and print operation from input ###################
	public void execOperations(String input, MinHeapRisingCity heapObj, RedBlackTreeRisingCity rbObj) {

		String totalTimeTaken, buildingNO, inputType;
		int execCycle;

		execCycle = Integer.parseInt(input.substring(0, input.indexOf(':')));
		inputType = input.substring(input.indexOf(':') + 1, input.indexOf('('));

		switch (inputType) {

		case "Insert":

			risingCity obj1 = new risingCity();
			buildingNO = input.substring(input.indexOf('(') + 1, input.indexOf(','));
			totalTimeTaken = input.substring(input.indexOf(',') + 1, input.indexOf(')'));
			obj1.setBuildingNum(Integer.parseInt(buildingNO));
			obj1.setTotalTime(Integer.parseInt(totalTimeTaken));
			obj1.setExecutedTime(0);

			risingCity nil = new risingCity();
			nil.setBuildingNum(-1);
			nil.setTotalTime(-1);
			nil.setExecutedTime(-1);
			rbObj.callInsert(obj1, nil);
			heapObj.construct(obj1);

			break;

		case "PrintBuilding":
			//##################### print range of buildings ###################
			if (input.contains(",")) {

				int rangeStart = Integer.parseInt(input.substring(input.indexOf('(') + 1, input.indexOf(',')));
				int rangeEnd = Integer.parseInt(input.substring(input.indexOf(',') + 1, input.indexOf(')')));
				List<risingCity> printRangeList = new LinkedList<risingCity>();

				printRangeList = rbObj.searchInRange(rangeStart, rangeEnd);

				if (printRangeList.size() != 0) {
					for (int i = 0; i < printRangeList.size(); i++) {
						System.out.print("(" + printRangeList.get(i).bNo + ","
								+ printRangeList.get(i).eTOB + "," + printRangeList.get(i).tTOB + ")");

						if (i < (printRangeList.size() - 1)) {
							System.out.print(",");
						}
					}
					System.out.print("\n");
				} else {
					System.out.println("(0,0,0)");
				}

			} else {
				//##################### print single buildings ###################
				int printBldNo = Integer.parseInt(input.substring(input.indexOf('(') + 1, input.indexOf(')')));
				RedBlackTreeRisingCity.RBNode print;
				print = rbObj.singlePrint(printBldNo);

				if (print != null) {
					System.out.println("(" + print.nodeVal.bNo + "," + print.nodeVal.eTOB + ","
							+ print.nodeVal.tTOB + ")");
				} else {
					System.out.println("(0,0,0)");
				}

			}
			break;

		default:
			System.out.println("Invalid Input, You can only insert or print please try again !!");

		}

	}

	//##################### perform min-heapify when the global counter reaches multiple of 5 days to switch to other buildings ###################
	public void reAllocateMinHeapify(MinHeapRisingCity heapObj) {

		if (buildtimeAllocator % 5 == 0) {
			heapObj.minHeapify(MinHeapRisingCity.root);
			buildtimeAllocator = 0;
		}

	}

	//##################### construction of building every day as per increament of global counter ###################
	public void executeBld(MinHeapRisingCity heapObj, RedBlackTreeRisingCity rbObj) {
		int addedVal;
		totdays++;
		
		if (heapObj.size > 0) {

			reAllocateMinHeapify(heapObj);
			//##################### execution of buildings on a daily basis ###################
			addedVal = heapObj.riseHeap[MinHeapRisingCity.root].eTOB + 1;
			int no=heapObj.riseHeap[MinHeapRisingCity.root].bNo;
			if (heapObj.riseHeap[MinHeapRisingCity.root].tTOB > addedVal) {
				heapObj.riseHeap[MinHeapRisingCity.root].eTOB = addedVal;

			}

			else {
				//##################### building is completed its printed out ###################
				buildtimeAllocator = -1;
				heapObj.riseHeap[MinHeapRisingCity.root].eTOB = heapObj.riseHeap[MinHeapRisingCity.root].tTOB;

				risingCity completedbuilding = heapObj.removeMinRisingCiti();

				rbObj.calldelete(completedbuilding);

				System.out.println("(" + completedbuilding.bNo + "," + totdays + ")");
			}

			globalCounter = globalCounter + 1;
			buildtimeAllocator = buildtimeAllocator + 1;
		}

	}
	
	public static void main(String Args[]) throws IOException, URISyntaxException {
		//##################### main function where the rising-city begins ###################
		
		IFILE=Args[0];
		
		MinHeapRisingCity heapObj = new MinHeapRisingCity(2000);

		RedBlackTreeRisingCity rbObj = new RedBlackTreeRisingCity();

		risingCity start = new risingCity();

		start.inputData(heapObj, rbObj);

	}

}
