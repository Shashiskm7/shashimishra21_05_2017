package com.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class ShashiKumarMishra20_05_2017 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static Scanner in = new Scanner(System.in);
	private final int MAXLIMIT = 5;
	private final transient TreeMap<Long,String> insertedTree = new TreeMap<Long,String>();
	public static final String LOCATION = "C:\\Users\\SHASHI MISHRA\\Desktop\\Zycus\\";
	public static final String FILENAME = "Zycus.txt";
	public static final String FILENAME2 = "object.txt";
	static BufferedReader br = null;
	static FileReader fr = null;
	static BufferedWriter bw = null;
	static FileWriter fw = null;
	
	public static void main(String args[])
	{
		String exit = "Not Exit";
		ShashiKumarMishra20_05_2017 obj = new ShashiKumarMishra20_05_2017();
		while(!exit.equalsIgnoreCase("EXIT"))
		{
		System.out.println("Do you want to Insert or search enter");
		System.out.println("1. To Insert");
		System.out.println("2. To Search");
		System.out.println("3. EXIT");
		String input = in.nextLine();
		
		switch(input)
		{
		case "1"://To Insert
			obj.insertIntoMap(Integer.parseInt(input));
			break;
		case "2"://To Search
			System.out.println(obj.searchMap());
			System.out.println("End Of Searching.....");
			break;
		case "3"://Exit
			exit = "exit";
			System.gc();
			Runtime.getRuntime().gc();
			break;
		default:
			System.out.println("invalid ");
		}
		}
		
		try {
			FileOutputStream fout = new FileOutputStream(LOCATION+FILENAME2);
			ObjectOutputStream objout = new ObjectOutputStream(fout);
			objout.writeObject(obj);
			objout.flush();
		} catch (IOException e) {
			System.out.println("File cannot be found Dated : "+new java.util.Date());
			e.printStackTrace();
		}
	}
	//Value will be inserted into Treemap
	public void insertIntoMap(int input)
	{
		System.out.println("Ohk now you have to insert into map.So go on..!!");
		System.out.println("Put required String input...!!!");
		String value = in.nextLine();
		System.out.println("Value to be inserted is "+value);
		if(putValue(value))
		{
			System.out.println("Successfully inserted value");
		}
		else{
			System.out.println("Problem in inserting value");
		}
	}
	
	
	
	//Inserting value into TreeMap
	public synchronized boolean putValue(String value)
	{
	boolean isInserted = false;
	long Key  = value.charAt(0)+value.charAt(value.length()/2)+value.charAt(value.length()-1);
	System.out.println(insertedTree.size());
		if(insertedTree.size()>MAXLIMIT )
		{ 
			System.out.println("Reached to limit.Writing to file begins...."); 
			if(writeToFile())
			{
				System.out.println("SuccessFully Written To file....");
				System.out.println("Map is being cleared...");
				insertedTree.clear();
				System.out.println("Map has been cleared...!!!");
				insertedTree.put(Key,value);
				isInserted = true;
			}
			else{
				System.out.println("Cannot be written to file...!!");
			}
		}
		else{
			System.out.println("Within limit...inserting to Map begin..");
			insertedTree.put(Key,value);
			isInserted = true;
		}
		return isInserted;
	}
	///If TreeMap reaches to limit it will be written to file.
	public synchronized boolean writeToFile()
	{
		File file = new File(LOCATION+FILENAME);
	boolean	isWritten = false;
	try{
	if(file.createNewFile())
	{
		System.out.println("File Has Been Created");
	}
	else{
		System.out.println("File with same name already exists..!!");
	}
	
	fw = new FileWriter(LOCATION+FILENAME);
	bw = new BufferedWriter(fw);
	System.out.println("Writting Process Has Been Started...!!!!!");
	for(Map.Entry<Long,String> entry:insertedTree.entrySet())
	{
		System.out.println("Key of Value : "+entry.getKey());
		System.out.println("Value is : "+entry.getValue());
		bw.write(entry.getValue()+" \n");
		bw.newLine();
	}
	isWritten = true;
	System.out.println("Written Process has been completed");
	}
	catch(IOException ioe)
	{
		System.out.println("Exception in writting File....Dated : "+new java.util.Date());
	}finally{
			try{
				if(bw!=null)
					bw.close();
				
				if(fw!=null)
					fw.close();
		}
		catch(IOException ioe)
		{
			System.out.println("Exception in Closing fw or bw Dated : "+new java.util.Date());
		}	
	}
		return isWritten;
	}
	
	//Value will be searched in tree map
	public synchronized String searchMap()
	{
		System.out.println("Enter the String to be searched...");
		String value = in.nextLine();
		if(insertedTree.containsValue(value))
		{
			System.out.println("The Required String has been found...");
			return value;
		}
		else{
			System.out.println("The Required String has not been found in Map...");
			System.out.println("Searching in file..");
			return searchStringInFile(value);
		}
		
		
	}
	//Value will be searched in File
	public synchronized String searchStringInFile(String value)
	{
		try{
		fr = new FileReader(LOCATION+FILENAME);
		if(fr.ready())
		{
			System.out.println(fr.ready()+"....Reading begins");
		br = new BufferedReader(fr);
		String filevalue;
		while((filevalue=br.readLine())!=null)
		{
			System.out.println(filevalue);
			if(filevalue.trim().equals(value.trim()))
			{
				System.out.println("The Required String has been found in file..."+filevalue);
				return filevalue;
			}
			else{
				System.out.println("The Required String has not been found in file..Check for correct String name");
			}
		}
		}
		}catch(IOException ioe)
		{
			System.out.println("Cannot read String from file..Check for errors Dated:"+new java.util.Date());
		}
		finally{
			try{
				if(br!=null)
					br.close();
				
				if(fr!=null)
					fr.close();
			}catch(IOException ioe)
			{
				System.out.println("Exception in closing fr and br dated : "+new java.util.Date());
			}
		}
		
		return null;
	}
	
}
