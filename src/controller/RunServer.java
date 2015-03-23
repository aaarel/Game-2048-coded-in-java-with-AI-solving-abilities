package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;

import com.datumbox.opensource.ai.AIsolver;
import com.datumbox.opensource.dataobjects.Direction;
import com.datumbox.opensource.game.Board;

import model.ClientHandler;
import model.MyServer;
@SuppressWarnings("unused")

//Server sides Model (calculations etc..) not for "running void main"
public class RunServer { 

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		MyServer server=new MyServer(5001, 1, new ClientHandler() {
			
//			final BufferedReader inFromServer = new BufferedReader(new InputStreamReader(myServer.getInputStream() ));
//			final PrintWriter out2server = new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
//			ObjectOutputStream obout2server = new ObjectOutputStream(myServer.getOutputStream());
			
			@Override
			public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2client) {
				int [][] array = new int[4][4];
				Direction hint=null;
				
				try {
					
					System.out.println("Server got Array: "+array);
					array = (int [][]) inFromClient.readObject();
//					for(int i=0; i<4;i++)
//						for(int j=0; j<4;j++)
//							array[i][j]= (int) inFromClient.readInt();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					hint = AIsolver.findBestMove(new Board(array), 4);
				} 
				catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out2client.writeObject(hint.getCode());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		server.start();
		
		//3 minute wait period
		Thread.sleep(9*60*1000);
		
		server.close();
	}

}
