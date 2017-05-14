package com.pmse;

public class PMSEServer {

	public static String serverIp = "10.0.2.2";
	public static String serverPort = "8084";
	public static String project = "PMSE";
	public static String keyword = "";
	
	public static String un = "guest";
	public static String pw = "guest";
	
	

	public static String httpServerUrl = "http://" + serverIp + ":"
			+ serverPort + "/"+project+"/";
	
}
