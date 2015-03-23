package model;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public interface ClientHandler {

	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2client);
}
