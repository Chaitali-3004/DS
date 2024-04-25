
//DS Assignment 1: Implement multi-threaded client/server Process communication using RMI.

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		try {
			// Locate the registry
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 4000);

			// Get the references of exported objects from the registry
			Circle circle = (Circle) registry.lookup("rmi://localhost:4000/circle");
			
			int radius;
			try (Scanner in = new Scanner(System.in)) {
				System.out.print("Enter the radius of the circle: ");
				radius=in.nextInt();
			}
			System.out.println("\nThe Area of the circle is "+ circle.getArea(radius)); 
			System.out.println("The Perimeter of the circle is "+ circle.getPerimeter(radius));
		} catch (Exception e) 
		{
			System.out.println("Client Error: " + e);
		}
	}
}
/*Output:
Enter the radius of the circle: 18

The Area of the circle is 1018.2857142857142
The Perimeter of the circle is 113.14285714285714
*/


/*
Steps to run this program
(Make sure you are in the folder containing the java files)

Step 1 - On Terminal 1
javac *.java

Step 2 - On Terminal 2
java Server

Step 3 - On Terminal 3
java Client
 */
