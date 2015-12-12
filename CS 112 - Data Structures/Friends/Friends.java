import java.io.FileNotFoundException;
import java.util.Scanner;


/**
*@author Jared Patriarca and Russell Jew
*/

public class Friends {

	Scanner in=new Scanner(System.in);
	FriendshipGraph<Friend> fg;
	
	public static void main(String[] args) {new Friends();}
	
	public Friends(){
		System.out.println("Input the name of the file that contains Friends information:");
				
		try {
			fg = new FriendshipGraph<Friend>(in.next());
		} catch (FileNotFoundException e) {System.out.println("File not found.");System.exit(0);}
		
		System.out.println("File Loaded without problems");
		
		Menu();
	}
	
	public void Menu(){
		while (true){
		System.out.println("Choose action:\n(1) Intro Chain\n(2) Cliques\n(3) Connectors\n(4) Quit");
		int response = Integer.parseInt(in.next());
		while (response != 1 && response != 2 && response != 3 && response != 4 && response != 5 && response != 6) {
			System.out.print("\tYou must enter a valid selection: 1, 2, 3, 4, 5, or 6. => ");
			response = Integer.parseInt(in.next());
		}
		if (response==1){
			System.out.println("Enter the first person's name: ");
			String firstName = in.next();
			System.out.println("Enter the first person's school: ");
			String firstSchool = in.next();
			System.out.println("Enter the second person's name: ");
			String secondName = in.next();
			System.out.println("Enter the second person's school : ");
			String secondSchool = in.next();
			System.out.println(fg.getIntroductionChain(firstName, firstSchool, secondName, secondSchool));
			}
		else if (response==2){
			System.out.println("Enter the school of the cliques you want to see: ");
			System.out.print(fg.getCliques(in.next()));
			}
		else if (response==3)System.out.println(fg.getConnectors());
		else if (response==4)System.exit(0);
		}
	}
}
