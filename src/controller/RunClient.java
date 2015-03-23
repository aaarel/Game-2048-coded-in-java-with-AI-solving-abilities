//package controller;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//
//public class RunClient {
//
//	public static void main(String[] args) throws Exception{
//		System.out.println("client side");
//		Socket myServer = new Socket(InetAddress.getLocalHost(), 5001);
//		System.out.println("connected to server");
//		
//		BufferedReader inFromServer=new BufferedReader(new InputStreamReader(myServer.getInputStream()));
//		PrintWriter out2server=new PrintWriter(new OutputStreamWriter(myServer.getOutputStream()));
//		
//		BufferedReader inFromUser=new BufferedReader(new InputStreamReader(System.in));
//		String line;
//		while(!(line=inFromUser.readLine()).equals("exit")){
//			System.out.println("sebding to server: "+line);
//			out2server.println(line);
//			out2server.flush();
//		}
//		out2server.println(line);
//		out2server.flush();
//		
//		inFromServer.close();
//		out2server.close();
//		myServer.close();
//		
//		inFromUser.close();
//		
//	}
//}
