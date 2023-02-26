package com.jdbcproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class SearchBar {
    JFrame jf ;
    JTextField jt;
    JScrollPane js;
    JTable resultTable;
    JPanel main;
    DefaultTableModel tableModel ;
    SearchBar(){
        jf = new JFrame("Search Via Number");
        jt = new JTextField();
        js=new JScrollPane();
        jt.setBounds(0,10, 400,30);
        tableModel = new DefaultTableModel();

        jt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("perform Searching");

                try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","syssir","jpa");
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    tableModel = new DefaultTableModel();

                    while(tableModel.getRowCount() > 0)
                    {
                        tableModel.removeRow(0);
                    }
                    while(tableModel.getRowCount() > 0)
                    {
                        tableModel.removeRow(0);
                    }
                    String val = (jt.getText().toString().trim());
                    String query=null;
                    if(!val.isEmpty()) {
                         query = ("SELECT * FROM Emp420 WHERE phone LIKE '" + val + "%' OR city LIKE '" + val + "%'");
                        System.out.println(query);
                        ResultSet rs1 = st.executeQuery(query);

                        ResultSetMetaData rsmd=rs1.getMetaData();
                        int count=rsmd.getColumnCount();

                        for(int i=1;i<=count;i++){
//                        System.out.print(rsmd.getColumnName(i)+"              |");
                            tableModel.addColumn( rsmd.getColumnName(i));
                        }
                        System.out.println();

                        while(rs1.next()){
//                        System.out.println(rs1.getString(1)+"        | "+rs1.getString(2)+"              | "+rs1.getString(3)+"                | "+rs1.getString(4));
                            tableModel.addRow(new Object[]{rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4)});
                        }
                    }else{

                    }


                    resultTable = new JTable(tableModel);
                   js.setViewportView(resultTable);


                }catch(Exception ee){
                    ee.printStackTrace();
                }
            }
        });

        js.setBounds(0,40,400,300);
        jf.add(jt);
        jf.add(js);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,400);
        jf.setLayout(null);
        jf.setResizable(false);
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        new SearchBar();
    }


}
