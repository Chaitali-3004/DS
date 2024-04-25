
//DS Assignment 1: Implement multi-threaded client/server Process communication using RMI.
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server 
{

    public static void main(String[] args) {
        try {
            System.out.println("Server started...");

            // Set hostname for the server using javaProperty
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");

            // Register the exported class in RMI registry with some name,
            // Client will use that name to get the reference of those exported object

            // Get the registry to register the object.
            Registry registry = LocateRegistry.createRegistry(4000);

            // create product objects
            Circle stub = new CircleImpl();
            registry.rebind("rmi://localhost:4000/circle", stub);
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }
}