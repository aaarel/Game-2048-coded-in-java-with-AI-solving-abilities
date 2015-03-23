package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.datumbox.opensource.ai.AIsolver;
import com.datumbox.opensource.dataobjects.Direction;
import com.datumbox.opensource.game.Board;

@SuppressWarnings("unused")
public class MyServer extends Thread{
	
	int port;
	ServerSocket server;
	boolean stop;
	//num of clients for max connection limit
	int noc; 
	ClientHandler ch;
	int [][] array;
	
	public MyServer(int port,int noc,ClientHandler ch) {
		this.port=port;
		this.stop=false;
		this.noc=noc; 
		this.ch=ch;
	}
	
	public void run(){
		try {
			server=new ServerSocket(port);
			System.out.println("server started");
			server.setSoTimeout(5000);
			
			//using a thread pool to handle the threads in  a way that doesn't allocate all the resources of the machine
			ExecutorService tp=Executors.newFixedThreadPool(noc);
			
			
			while(!stop){
				try{
					System.out.println("waiting for client");
					final Socket someClient =  server.accept();
					System.out.println("cliet connected");
					
					final ObjectInputStream inFromClient=new ObjectInputStream(someClient.getInputStream());
					final ObjectOutputStream out2client=new ObjectOutputStream(someClient.getOutputStream());
										
					//using thread pool for thread MGMT, not to overload system with thread
					tp.execute(new Runnable() {
						
						@Override
						public void run() {
							ch.handleClient(inFromClient, out2client);
							try{
								inFromClient.close();
								out2client.close();
								someClient.close();
							} catch(Exception e){							
							}
						}
					});
				}catch(SocketTimeoutException e){					
				}
			}	// end loop
			
			server.close();
			System.out.println("server closed");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		this.stop=true;
	}

}
