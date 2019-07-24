/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.pico.pico_maven;

/**
 *
 * @author Admin
 */
 import picocli.CommandLine.Option;
import picocli.CommandLine.*;
import java.io.BufferedReader;
import picocli.CommandLine;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



@Command(
name = "App", header = "%n@|green Hello world demo|@",
  subcommands = {
      msg.class,
      show.class,
      runfile.class,
      databaseConfig.class,
      showarg.class
  }
)
public class App implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new App(), args);
    }

    @Override
    public void run() {

      System.out.println("$$$$$$$$$$$$$$$$$$$###################### CLI EMBER using PicoCLI ######################$$$$$$$$$$$$$$$$$$$");
      System.out.println("Commands :\n[1]->runfile :Enter name of any java file(without .java) and run the file .Arguments can also be passed");
      System.out.println("[2]->databaseConfig :Enter parameters and store them in a database");
      System.out.println("[3]->showarg :Show all the parameters entered previously");
      // System.out.println("##########################Ember CLI using picoCLI##########################");
      // System.out.println("");
      // System.out.println("[1]->DATABASE OPTIONS: --database");
      // System.out.println("     1.MongoDB");
      // System.out.println("     2.MapR");
      // System.out.println("     3.MySql");
      // System.out.println("     4.Postgresql");
      // System.out.println("     5.MSSQL");
      // System.out.println("     6.Oracle");
      // System.out.println("");
      // System.out.println("You can also provide:");
      // System.out.println("[2]->HOST NAME: --host_url");
      // System.out.println("[3]->USERNAME: -u,--user");
      // System.out.println("[4]->PASSWORD: -p,--password");
      // System.out.println("[5]->DATABASE NAME: -dn,--dbname");
      // System.out.println("[6]->TABLE NAME: -t,--tablename");
      // System.out.println("[7]->FILE NAME: -f,--filename");
      // System.out.println("");
      //System.out.println("");
      // System.out.print("ember~#");
      // Scanner myObj = new Scanner(System.in);  // Create a Scanner object
      // String command = myObj.nextLine();

        // try{
        // runProcess("javac -cp .;"+"\"C:\\javalib\\*\"" + " pico2.java");
        // runProcess("java -cp .;"+"\"C:\\javalib\\*\"" + " pico2 "+command);
        // }
        // catch(Exception e){}
    }

    private static void printLines(String cmd, InputStream ins) throws Exception {
      String line = null;
      BufferedReader in = new BufferedReader(
          new InputStreamReader(ins));
      while ((line = in.readLine()) != null) {
          System.out.println(line);
      }
    }

    private static void runProcess(String command) throws Exception {
      Process pro = Runtime.getRuntime().exec(command);
      printLines("", pro.getInputStream());
      printLines(command + " stderr:", pro.getErrorStream());
      pro.waitFor();
      // System.out.println(command + " exitValue() " + pro.exitValue());
    }
}

@Command(
  name = "msg"
)
class msg implements Runnable {

  @Option(names ="-m",description = "Database from a wide range of different databases.")
 String message;
      @Override
      public void run()
      {
          try{

           Class.forName("com.mysql.jdbc.Driver");
           Connection con=DriverManager.getConnection(
           "jdbc:mysql://localhost:3306/picocli","picoCLI","");
           //here sonoo is database name, root is username and password
           Statement stmt=con.createStatement();

           String sql = "INSERT INTO pico VALUES ("+"\'"+message+"\'"+")";

           stmt.executeUpdate(sql);
           con.close();
         }
         catch(Exception err){
           System.out.println("database connection Exception:"+err);
         }
      }
}

@Command(
  name = "show"
)
class show implements Runnable {
    @Override
    public void run() {

      try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/picocli","picoCLI","");
        //here sonoo is database name, root is username and password
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select * from pico");
        while(rs.next())
        System.out.println(rs.getString(1));
        con.close();
      }
      catch(Exception err){}
    }
}

@Command(
  name = "runfile"
)
class runfile implements Runnable {

  @Option(names = {"-f", "--file"},description = "Name of input file without.java")
  String file;

  @Option(names = {"-arg", "--arguments"},split = " ")
  String[] input;

    @Override
    public void run() {
        try {
            //runProcess("dir");
           System.out.println("**********");
           runProcess("javac "+file+".java");
           System.out.println("**********");
           String exe ="java "+file;
           for (String message : input) {
            exe=exe+" "+message;
        }
           //System.out.println(exe);
           runProcess(exe);
        } catch (Exception e) {
            e.printStackTrace();
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

      private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines("", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        // System.out.println(command + " exitValue() " + pro.exitValue());
      }
}

@Command(
  name = "databaseConfig"
)
class databaseConfig implements Runnable {

  // @Option(names = {"-f", "--file"},description = "Name of input file without.java")
  // String input;

  @Option(names ="--database",description = "Database from a wide range of different databases.")
  String dboption;

  @Option(names ="--host_url",description = "URL of the server.")
  InetAddress host_url;

  @Option(names ="--port",description = "Port Number")
  int port;

  @Option(names = {"-u", "--user"}, description = "User name")
  String username;

  @Option(names = {"-p", "--password"}, description = "Passphrase")
  String password;

  @Option(names = {"-dn,--dbname"},description = "Stores name of the database.")
  String dbname;

  @Option(names = {"-f","--filename"},description = "Name of JSON file")
  String filename;

  @Option(names = {"-t","--tablename"},description = "Name of the table in the selected database")
  String tablename;

   @Override
   public void run() {
       if(dboption ==null)
       {
           dboption ="MySQL";
       }
       if(host_url ==null)
       {
           try {
               host_url =InetAddress.getLocalHost();
           } catch (UnknownHostException ex) {
               Logger.getLogger(databaseConfig.class.getName()).log(Level.SEVERE, null, ex);
           }
       }

       if(port ==0)
       {
           port = 8080;
       }

       if(username ==null)
       {
           username ="root";
       }

       if(password ==null)
       {

           password ="toor";
       }

       if(dbname ==null)
       {
           dbname ="test";
       }

       if(tablename ==null)
       {
           tablename ="default";
       }

       if(filename ==null)
       {
           filename ="default.json";
       }

       try{

                try{
                  //create database if not present
                  Class.forName("com.mysql.jdbc.Driver");
                   Connection Conn = DriverManager.getConnection
                  ("jdbc:mysql://localhost/?user=root&password=");
                  Statement s=Conn.createStatement();
                  int Result=s.executeUpdate("CREATE DATABASE ember");




                   Connection con = DriverManager.getConnection(
                           "jdbc:mysql://localhost:3306/ember","picoCLI","");
                  Statement stmt2=con.createStatement();
                   String sql = "CREATE TABLE ember " +
                    "(dboption VARCHAR(30), " +
                    " host_url VARCHAR(30), " +
                    " username VARCHAR(30), " +
                    " password VARCHAR(30), " +
                    " dbname VARCHAR(30), " +
                    " filename JSON, " +
                    " tablename VARCHAR(30), " +
                    " port INT(11))" ;
                    stmt2.executeUpdate(sql);

                    con.close();
                    Conn.close();
                    //enter values in the table after creating the table
                       try (Connection con2 = DriverManager.getConnection(
                               "jdbc:mysql://localhost:3306/ember","picoCLI","")) {
                           //here sonoo is database name, root is username and password
                           Statement stmt=con2.createStatement();

                          // String sql ="DELETE FROM `ember`";
                          // stmt.executeUpdate(sql);

                            // String sql;
                           sql = "INSERT INTO `ember`(`dboption`, `host_url`, `username`, `password`, `dbname`, `filename`, `tablename`, `port`) VALUES ("+"'"+dboption+"','"+host_url.getHostName()+"','"+username+"','"+password+"','"+dbname+"','"+filename+"','"+tablename+"','"+port+"'"+")";

                           int i =stmt.executeUpdate(sql);
                           if(i==0)
                               System.out.println("query failed");
                       }
                       catch(Exception e){}
                }
                catch(Exception e)
                {
                  //execute if database already exists
                  Class.forName("com.mysql.jdbc.Driver");
                    try (Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ember","picoCLI","")) {
                        //here sonoo is database name, root is username and password
                        Statement stmt=con.createStatement();

                       String sql ="DELETE FROM `ember`";
                       stmt.executeUpdate(sql);

                         // String sql;
                        sql = "INSERT INTO `ember`(`dboption`, `host_url`, `username`, `password`, `dbname`, `filename`, `tablename`, `port`) VALUES ("+"'"+dboption+"','"+host_url.getHostName()+"','"+username+"','"+password+"','"+dbname+"','"+filename+"','"+tablename+"','"+port+"'"+")";

                        int i =stmt.executeUpdate(sql);
                        if(i==0)
                            System.out.println("query failed");
                    }
                }
                 //mysql connection
                 // Class.forName("com.mysql.jdbc.Driver");
                 // //here sonoo is database name, root is username and password
                 // try (Connection con = DriverManager.getConnection(
                 //         "jdbc:mysql://localhost:3306/ember","picoCLI","")) {
                 //     //here sonoo is database name, root is username and password
                 //     Statement stmt=con.createStatement();
                 //
                 //    String sql ="DELETE FROM `ember`";
                 //    stmt.executeUpdate(sql);
                 //
                 //      // String sql;
                 //     sql = "INSERT INTO `ember`(`dboption`, `host_url`, `username`, `password`, `dbname`, `filename`, `tablename`, `port`) VALUES ("+"'"+dboption+"','"+host_url.getHostName()+"','"+username+"','"+password+"','"+dbname+"','"+filename+"','"+tablename+"','"+port+"'"+")";
                 //
                 //     int i =stmt.executeUpdate(sql);
                 //     if(i==0)
                 //         System.out.println("query failed");
                 // }

          }
         catch(Exception err){
           System.out.println("database connection Exception:"+err);
         }
   }


}

@Command(
  name = "showarg"
)
class showarg implements Runnable {
    @Override
    public void run() {

      try {
          try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ember","picoCLI","");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from ember");
            while(rs.next())
            {
              System.out.print(rs.getString(1)+" ");
              System.out.print(rs.getString(2)+" ");
              System.out.print(rs.getString(3)+" ");
              System.out.print(rs.getString(4)+" ");
              System.out.print(rs.getString(5)+" ");
              System.out.print(rs.getString(6)+" ");
              System.out.print(rs.getString(7)+" ");
              System.out.print(rs.getInt(8)+" ");
            }
            con.close();
          }
          catch(Exception err){
            System.out.println("Database does not exist yet. Execute command databaseConfig first.");
          }
      }
      catch (Exception e) {
          e.printStackTrace();
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

    private static void runProcess(String command) throws Exception {
      Process pro = Runtime.getRuntime().exec(command);
      printLines("", pro.getInputStream());
      printLines(command + " stderr:", pro.getErrorStream());
      pro.waitFor();
      // System.out.println(command + " exitValue() " + pro.exitValue());
    }
}
