package com.pccw.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DBH2Loader {
    public static void main(String[] args) {

            String url = "jdbc:h2:file:./h2/test";
            String user = "sa";
            String passwd = "s$cret";

            String query = "CREATE TABLE SRCCODE_TABLE (id bigint auto_increment,artifact varchar(2000), fileName varchar(3000), package varchar(3000), content clob)";
            Connection con=null;
            try {
              con = DriverManager.getConnection(url, user, passwd);
                 Statement st = con.createStatement();
                 st.execute(query);

            } catch (SQLException ex) {
                ex.printStackTrace();

                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




