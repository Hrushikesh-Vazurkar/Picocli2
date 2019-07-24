package cli1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
/**
 *
 * @author Admin
 */
public class Cli {

    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("##########################Ember CLI using picoCLI##########################");
      System.out.println("");
      System.out.println("Command :runfile");
      System.out.println("Enter the full path of the file(remove .java from the end) and run the file from CLI.");
      System.out.println("[1]->FILENAME: -f,--filename");
      System.out.println("[2]->ARGUMENTS -arg");
      System.out.println("");
      System.out.println("Command :databaseConfig");
      System.out.println("[1]->DATABASE OPTIONS: --database");
      System.out.println("     1.MongoDB");
      System.out.println("     2.MapR");
      System.out.println("     3.MySql");
      System.out.println("     4.Postgresql");
      System.out.println("     5.MSSQL");
      System.out.println("     6.Oracle");
      System.out.println("");
      System.out.println("You can also provide:");
      System.out.println("[2]->HOST NAME: --host_url");
      System.out.println("[3]->USERNAME: -u,--user");
      System.out.println("[4]->PASSWORD: -p,--password");
      System.out.println("[5]->DATABASE NAME: -dn,--dbname");
      System.out.println("[6]->TABLE NAME: -t,--tablename");
      System.out.println("[7]->FILE NAME: -f,--filename");
      System.out.println("");
      System.out.println("Command :showarg");
      System.out.println("Displays all the parameters entered in command \"databaseConfig\"");
      System.out.println("");
      System.out.println("");
      int flag =0;
      String cmd = null;
      while(true)
      {
        //A CLI that takes the command as an input and runs the programs ember.java which contains the
        //source code for these commands
        System.out.print("\nember~# ");
        try{
          Scanner myObj = new Scanner(System.in);  // Create a Scanner object
          cmd = myObj.nextLine();

          if("exit".equals(cmd))
          break;
          //runProcess is a function which runs the Command passed to it using java function Runtime.getRuntime().exec();
          // runProcess("javac -cp C:\\Users\\Admin\\Desktop\\ember\\src\\main\\java\\com\\picocli\\ember;"+"\"C:\\javalib\\*\"" + " C:\\Users\\Admin\\Desktop\\ember\\src\\main\\java\\com\\picocli\\ember\\App.java");
          // runProcess("java -cp C:\\Users\\Admin\\Desktop\\ember\\src\\main\\java\\com\\picocli\\ember;"+"\"C:\\javalib\\*\"" + " App "+cmd);
          // System.out.println("mvn exec:java@ember -Dexec.args="+"\""+cmd+"\"");
          // runProcess("mvn exec:java@ember -Dexec.args="+"\""+cmd+"\"");
                try
                {
                    // Run and get the output.
                    String outlist[] = runCommand("mvn.cmd exec:java@ember -Dexec.args="+"\""+cmd+"\"");
                    // Print the output to screen character by character.
                    for (int i = 0; i < outlist.length; i++)
                        System.out.println(outlist[i]);
                }
                catch (Exception e) {
                    System.err.println(e);
                }

        }
        catch(Exception e){}

          //if command "exit" is typed , exit from the CLI

      }
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
      String line = null;
      BufferedReader in = new BufferedReader(
          new InputStreamReader(ins));
      while ((line = in.readLine()) != null) {
          System.out.println(line);
      }
    }

    //this function runs the particular command which is passed as a parameter
    private static void runProcess(String command) throws Exception {
      Process pro = Runtime.getRuntime().exec(command);
      printLines("", pro.getInputStream());
      printLines(command + " stderr:", pro.getErrorStream());
      pro.waitFor();
    }

    static public String[] runCommand(String cmd)throws IOException
    {

                    // The actual procedure for process execution:
                    //runCommand(String cmd);
                    // Create a list for storing output.
                    ArrayList list = new ArrayList();
                    // Execute a command and get its process handle
                    Process proc = Runtime.getRuntime().exec(cmd);
                    // Get the handle for the processes InputStream
                    InputStream istr = proc.getInputStream();
                    // Create a BufferedReader and specify it reads
                    // from an input stream.

                    BufferedReader br = new BufferedReader(new InputStreamReader(istr));
                    String str; // Temporary String variable
                    // Read to Temp Variable, Check for null then
                    // add to (ArrayList)list
                    while ((str = br.readLine()) != null)
                        list.add(str);
                        // Wait for process to terminate and catch any Exceptions.
                            try {
                                proc.waitFor();
                                }
                            catch (InterruptedException e) {
                                System.err.println("Process was interrupted");
                                }
                            // Note: proc.exitValue() returns the exit value.
                            // (Use if required)
                            br.close(); // Done.
                            // Convert the list to a string and return
                            return (String[])list.toArray(new String[0]);
    }
}
