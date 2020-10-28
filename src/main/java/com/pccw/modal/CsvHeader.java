/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pccw.modal;

/**
 *
 * @author PCCW
 */
public class CsvHeader {
    String columnName;
    int columnIndexNo;

    public CsvHeader(String columnName, int columnIndexNo) {
        this.columnName = columnName;
        this.columnIndexNo = columnIndexNo;
    }

    public CsvHeader() {
    }
    

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnIndexNo() {
        return columnIndexNo;
    }

    public void setColumnIndexNo(int columnIndexNo) {
        this.columnIndexNo = columnIndexNo;
    }

    @Override
    public String toString() {
        return  columnName;
    }
    
    
}
