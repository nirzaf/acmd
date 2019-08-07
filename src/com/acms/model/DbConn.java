package com.acms.model;

import java.sql.Connection;  
import java.sql.DriverManager; 

public class DbConn
{
   /**
 * @param args
 */
public static void main (String[] args)
   {
       Connection conn = null;

       try
       {
           String url = "jdbc:sqlite:C:/sqlite/ams.db";
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection(url);
           //System.out.println ("Database connection established");
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       finally
       {
           if (conn != null)
           {
               try
               {
                   conn.close ();
                   //System.out.println ("Database connection terminated");
               }
               catch (Exception e) { /* ignore close errors */ }
           }
       }
   }
}