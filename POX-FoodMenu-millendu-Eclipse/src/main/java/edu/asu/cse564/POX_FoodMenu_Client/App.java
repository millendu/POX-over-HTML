package edu.asu.cse564.POX_FoodMenu_Client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {
	public static void main(String[] args) {
		MenuClient client = new MenuClient();
		String path = "/home/manideep/Desktop/SelectedFoodItems.json";
		String requestMsg = "";
		String out = "";
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while((out = bufferedReader.readLine()) != null) {
			    requestMsg = requestMsg + out + "\n";
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String responseMsg = client.postJSONRequest(requestMsg);
        System.out.println(responseMsg);
	}
}
