
//DS Assignment 1: Implement multi-threaded client/server Process communication using RMI.
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Circle extends Remote {

	public double getArea(int radius) throws RemoteException;

	public double getPerimeter(int radius) throws RemoteException;
}
