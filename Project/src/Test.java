import java.util.*;
import java.io.*;

public class Test {
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		int Method; // select method type
		int Exit = 1; // used to exit menu or not
		Method = Menu();// menu display function
		while (Exit != 0) {
			switch (Method) {
			case 1:
				IDS("Ramallah");// function of IDS
				Method = Menu();// return menu
				System.out.println(Method);// print menu again
				break;
			case 2:
				BFS("Ramallah");// function of BFS
				Method = Menu();
				System.out.println(Method);
				break;
			case 3:
				UC("Ramallah");// function of Uniform cost search
				Method = Menu();
				System.out.println(Method);
				break;
			case 4:
				AS("Ramallah");// function for A * search
				Method = Menu();
				System.out.println(Method);
				break;
			case 5:
				Exit = 0;
				break;
			default:
				System.out.println("Wrong input, Please Select Number From (1-4)");
				Method = Menu();
				System.out.println(Method);
				break;
			}
		}
		System.out.println("\nGoodBye!\n");
	}

	public static void Print(String fname) {
		String Fname = fname + ".txt";
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		try {
			ReadFile = new FileReader(Fname);
			ReadLine = new BufferedReader(ReadFile);
			String line;
			while ((line = ReadLine.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
				System.out.print(line);
				System.out.print(" | ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ReadFile != null) {
				try {
					ReadFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void PrintBranches(String fname) {
		String[] parts;
		String part1;
		String part2;
		String part3;
		String part4;
		String Name;
		int Arial;
		int Driving;
		int Walking;
		String Fname = fname + ".txt";
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		try {
			ReadFile = new FileReader(Fname);
			ReadLine = new BufferedReader(ReadFile);
			String line;
			while ((line = ReadLine.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
				parts = line.split("#");
				part1 = parts[0];
				part2 = parts[1];
				part3 = parts[2];
				part4 = parts[3];
				Name = part1;
				Arial = Integer.parseInt(part2.replaceAll("km", ""));
				Driving = Integer.parseInt(part3.replaceAll("km", ""));
				Walking = Integer.parseInt(part4.replaceAll("km", ""));
				System.out.println(Name + " " + Arial + " " + Driving + " " + Walking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ReadFile != null) {
				try {
					ReadFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int Menu() {
		int Method;
		Scanner input = new Scanner(System.in);
		System.out
				.print("Please Select a Search Method:\n1- IDS.\n2- BFS.\n3- Uniform Cost.\n4- A*\n5- Exit.\nOption:");
		Method = input.nextInt();
		return Method;
	}

	public static void IDS(String Initial) throws IOException {
		String Goal = Goal();
		if (Goal != null) {
			System.out.println("----------------");
			System.out.println("Source: " + Initial + "\nGoal: " + Goal);
			System.out.println("----------------");
			System.out.println("Starting Iterative Deeping Search Method... ");
			IDS_Tree(Initial, Goal, "Ramallah");
		}
	}

	public static void BFS(String Initial) throws IOException {
		String Goal = Goal();
		if (Goal != null) {
			System.out.println("----------------");
			System.out.println("Source: " + Initial + "\nGoal: " + Goal);
			System.out.println("----------------");
			System.out.println("Starting Breadth First search Method... ");
			BFS_Tree(Initial, Goal, "Ramallah", 0, "Ramallah");
		}
	}

	public static void UC(String Initial) throws IOException {
		String Goal = Goal();
		if (Goal != null) {
			System.out.println("----------------");
			System.out.println("Source: " + Initial + "\nGoal: " + Goal);
			System.out.println("----------------");
			UC_Tree(Initial, Goal, "Ramallah:0, ", "Ramallah", 0);
		}
	}

	public static void AS(String Initial) throws IOException {
		String Goal = "Gaza";
		System.out.println("----------------");
		System.out.println("Source: " + Initial + "\nGoal: " + Goal);
		System.out.println("----------------");
		AS_Tree(Initial, Goal, "Ramallah:0, ", "Ramallah", 0);
	}

	public static String Goal() {
		Scanner input = new Scanner(System.in);
		String Goal = null;
		System.out.println(
				"\nPlease Select Goal Destination From These Cities.\n-------------------------------------------------");
		Print("Names");
		System.out.print(
				"\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("\nDestination City: ");
		Goal = input.nextLine();
		int uniq = Search(Goal);
		if (uniq == 1) {
			return Goal;
		} else {
			System.out.println("This City Doesnt Exist in the list!");
		}
		return null;
	}

	public static int Search(String City) {
		int uniqe = 0;
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		try {
			ReadFile = new FileReader("Names.txt");
			ReadLine = new BufferedReader(ReadFile);
			String line;
			while ((line = ReadLine.readLine()) != null) {
				if (line.equals(City) && line.length() == City.length()) {
					uniqe = 1;
				}
				if (uniqe == 1) {

					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static void IDS_Tree(String S, String G, String Path) throws IOException {// Acre is Goal
		List<String> CityList = new ArrayList<>();
		int pathfound = 0;
		String PreventLoop = Path + " -> ";
		String[] parts;
		String part1 = null;
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		String filename = S + ".txt";
		ReadFile = new FileReader(filename);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		while ((line = ReadLine.readLine()) != null) {
			parts = line.split("#");
			part1 = parts[0];
			CityList.add(part1);
			if (part1.equals(G)) {
				pathfound = 1;
			}

			if (pathfound == 1) {
				System.out.println("IDS Path: " + PreventLoop + G + ".");
				break;
			} else {
			}
		}
		String[] array = CityList.toArray(new String[CityList.size()]);
		int length = CityList.size();
		//System.out.println(S + " Nodes: " + Arrays.toString(array));
		if (pathfound == 0) {
			while (PreventLooping(PreventLoop, part1) == 0) {
				length = length - 1;
				part1 = array[length];
			}
			System.out.println(part1 + " Selected.");
			PreventLoop = PreventLoop + part1;
			IDS_Tree(part1, G, PreventLoop);
		}
	}

	public static int PreventLooping(String Path, String Node) {
		if (Path.contains(Node)) {
			return 0;
		} else {
			return 1;
		}
	}

	public static void BFS_Tree(String S, String G, String VisitedCities, int pathfound, String skip)
			throws IOException {
		String PreventLoop = VisitedCities + " -> ";
		String[] parts;
		String part1 = null;
		String[] tempparts;
		String temppart1 = null;
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		String filename = S + ".txt";
		ReadFile = new FileReader(filename);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		String templine;
		BufferedWriter ReadTempLine = null;
		FileWriter WriteToTempFile = null;
		String tempfilename = "Temp.txt";
		WriteToTempFile = new FileWriter(tempfilename);
		ReadTempLine = new BufferedWriter(WriteToTempFile);
		while ((line = ReadLine.readLine()) != null) {
			parts = line.split("#");
			part1 = parts[0];
			if (PreventLooping(VisitedCities, part1) == 1) {
				VisitedCities = VisitedCities + " " + part1 + " ";
			}
			if (part1.equals(G)) {
				pathfound = 1;
			}

			if (pathfound == 1) {
				System.out.println("BFS Path: " + VisitedCities + ".");
				break;
			} else {
			}
		}
		WriteToTempFile.append(VisitedCities);
		WriteToTempFile.close();
		if (pathfound != 1) {
			BufferedReader in = new BufferedReader(new FileReader("Temp.txt"));
			while ((templine = in.readLine()) != null) {
				// System.out.println("Temp File Contents: " + templine);
				tempparts = templine.split(" ");
				for (int i = 0; i < tempparts.length; i++) {
					if (PreventLooping(skip, tempparts[i]) == 1) { //
						// System.out.println(tempparts[i]);
						skip = skip + tempparts[i];
						BFS_Tree(tempparts[i], G, VisitedCities, 0, skip);
					} else {

					}
				}

			}
		} else {
			System.out.println("Path Founded!");
			System.exit(0);
		}
	}

	public static void UC_Tree(String S, String G, String VisitedNodes, String ExecutedNodes, int Length)
			throws IOException {
		String[] Uniq = null;
		String NextMinNode;
		String Node = "";
		String[] parts;
		String part1;
		String part2;
		String part3;
		String part4;
		String Name;
		int Arial;
		int Driving;
		int Walking;
		String Fname = S + ".txt";
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		ReadFile = new FileReader(Fname);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		//
		BufferedWriter ReadTempLine = null;
		FileWriter WriteToTempFile = null;
		String tempfilename = "UCS.txt";
		WriteToTempFile = new FileWriter(tempfilename);
		ReadTempLine = new BufferedWriter(WriteToTempFile);
		//
		while ((line = ReadLine.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			parts = line.split("#");
			part1 = parts[0];
			part2 = parts[1];
			part3 = parts[2];
			part4 = parts[3];
			Name = part1;
			Arial = Integer.parseInt(part2.replaceAll("km", ""));
			Driving = Integer.parseInt(part3.replaceAll("km", ""));
			Walking = Integer.parseInt(part4.replaceAll("km", ""));

			if (PreventLooping(VisitedNodes, Name) == 1) {
				int L = Driving + Length;
				Node = Node + Name + ":" + L + " ,";
				/// System.out.println(Name + ":" + Driving + " , ");
			}
			// uniqueness
			// WriteToTempFile.append(Node);
		} // Create a File to write done node within it
		VisitedNodes = VisitedNodes + Node;// saving all visited nodes at this stage;
		WriteToTempFile.append(VisitedNodes);// append these nodes to the file
		WriteToTempFile.close();// close ucs file
		NextMinNode = FindNextNode("UCS.txt", ExecutedNodes, VisitedNodes);// open ucs file and search for
		if (NextMinNode != null) { // minimum // node in visited and not executed
			Uniq = NextMinNode.split(":");// giving Uniq the value of the next node to execute uniq here is an array of
											// //
			int Len = Integer.parseInt(Uniq[1].replaceAll("km", "")); // string
			ExecutedNodes = ExecutedNodes + " -> " + Uniq[0] + "[" + Len + "]";
			if (Uniq[0].contains(G)) {// if GoalFounded!
				System.out.println("UCS Path: " + ExecutedNodes + ".");
				System.exit(0);
			} else {// if goal not founded
				Length = Length + Len;
				UC_Tree(Uniq[0], G, VisitedNodes, ExecutedNodes, Length);
			}
		} else {
			System.out.println("Path Doesnt Exist!");
		}
	}

	public static String FindNextNode(String UCSFile, String ExecutedNodes, String VisitedNodes) throws IOException {
		int Min = 1000;
		String[] parts;
		String[] splits;
		String part1;
		String Name;
		int Driving;
		String RName = null;
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		ReadFile = new FileReader(UCSFile);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		if ((line = ReadLine.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			line = line.replaceAll(" ", "");
			parts = line.split(",");

			for (int i = 0; i < parts.length; i++) {
				splits = parts[i].split(":");
				Name = splits[0];
				part1 = splits[1];
				Driving = Integer.parseInt(part1.replaceAll(" ", ""));
				if (PreventLooping(VisitedNodes, Name) == 0 && PreventLooping(ExecutedNodes, Name) == 1) {
					if (Driving < Min) {
						Min = Driving;
						RName = Name + ":" + Min;
					}
				}
			}
			// System.out.println(Min);
		}
		// System.out.println(RName);
		return RName;
	}

	public static void AS_Tree(String S, String G, String VisitedNodes, String ExecutedNodes, int Length)
			throws IOException {
		String[] Uniq = null;
		String NextMinNode;
		String Node = "";
		String[] parts;
		String part1;
		String part2;
		String part3;
		String part4;
		String Name;
		int Arial;
		int Driving;
		int Walking;
		String Fname = S + ".txt";
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		ReadFile = new FileReader(Fname);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		//
		BufferedWriter ReadTempLine = null;
		FileWriter WriteToTempFile = null;
		String tempfilename = "AS.txt";
		WriteToTempFile = new FileWriter(tempfilename);
		ReadTempLine = new BufferedWriter(WriteToTempFile);
		//
		while ((line = ReadLine.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			parts = line.split("#");
			part1 = parts[0];
			part2 = parts[1];
			part3 = parts[2];
			part4 = parts[3];
			Name = part1;
			Arial = Integer.parseInt(part2.replaceAll("km", ""));
			Driving = Integer.parseInt(part3.replaceAll("km", ""));
			Walking = Integer.parseInt(part4.replaceAll("km", ""));

			if (PreventLooping(VisitedNodes, Name) == 1) {
				int L = Walking + Length;
				Node = Node + Name + ":" + L + " ,";
				/// System.out.println(Name + ":" + Driving + " , ");
			}
			// uniqueness
			// WriteToTempFile.append(Node);
		} // Create a File to write done node within it
		VisitedNodes = VisitedNodes + Node;// saving all visited nodes at this stage;
		WriteToTempFile.append(VisitedNodes);// append these nodes to the file
		WriteToTempFile.close();// close ucs file
		NextMinNode = FindNextNodeA("AS.txt", ExecutedNodes, VisitedNodes, Length);// open ucs file and search for
		if (NextMinNode != null) { // minimum // node in visited and not executed
			Uniq = NextMinNode.split(":");// giving Uniq the value of the next node to execute uniq here is an array of
											// //
			int Len = Integer.parseInt(Uniq[1].replaceAll("km", "")); // string
			ExecutedNodes = ExecutedNodes + " -> " + Uniq[0];
			if (Uniq[0].contains(G)) {// if GoalFounded!
				System.out.println("A* Path: " + ExecutedNodes + ".");
				System.out.println("h2 = " + Len);
				System.exit(0);
			} else {// if goal not founded
				Length = Length + Len;
				AS_Tree(Uniq[0], G, VisitedNodes, ExecutedNodes, Length);
			}
		} else {
			System.out.println("Path Doesnt Exist!");
		}
	}

	public static String FindNextNodeA(String ASFile, String ExecutedNodes, String VisitedNodes, int Length)
			throws IOException {
		int Min = 1000;
		String[] parts;
		String[] splits;
		String part1;
		String Name;
		int Driving;
		String RName = null;
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		ReadFile = new FileReader(ASFile);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		if ((line = ReadLine.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			line = line.replaceAll(" ", "");
			parts = line.split(",");

			for (int i = 0; i < parts.length; i++) {
				splits = parts[i].split(":");
				Name = splits[0];
				part1 = splits[1];
				Driving = Integer.parseInt(part1.replaceAll(" ", ""));
				int Heuristic1 = FindHeuristic(Name);
				if (PreventLooping(VisitedNodes, Name) == 0 && PreventLooping(ExecutedNodes, Name) == 1) {
					if (Heuristic1 < Min) {
						Min = Heuristic1;
						RName = Name + ":" + Driving;
					}
				}
			}
			// System.out.println(Min);
		}
		// System.out.println(RName);
		return RName;
	}

	public static int FindHeuristic(String CityName) throws NumberFormatException, IOException {
		String[] parts;
		String part1;
		String part2;
		String Name;
		int Arial;
		String Fname = "AStar.txt";
		BufferedReader ReadLine = null;
		FileReader ReadFile = null;
		StringBuffer sb = new StringBuffer();
		ReadFile = new FileReader(Fname);
		ReadLine = new BufferedReader(ReadFile);
		String line;
		while ((line = ReadLine.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
			parts = line.split("#");
			part1 = parts[0];
			part2 = parts[1];
			Name = part1;
			Arial = Integer.parseInt(part2.replaceAll("km", ""));
			if (Name.contains(CityName)) {
				return Arial;
			}
		}
		return 0;
	}
}