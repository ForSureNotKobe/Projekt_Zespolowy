package com.example.barcode_beta;

public class Product {
    //private int id;
    private String Code;
    private String Price;
    private int NumberOf;
    private String ProductName;
   // private String Date;

    public Product( String Code, String Price, int NumberOf, String ProductName) {
      //  this.id = id;
        this.Code = Code;
        this.Price = Price;
        this.NumberOf = NumberOf;
        this.ProductName = ProductName;
       // this.Date = Date;
    }

    //public int getId() { return id; }

    public String getCode() {
        return Code;
    }

    public String getPrice() {
        return Price;
    }

    public int getNumberOf() {
        return NumberOf;
    }

    public String getProductName() {
        return ProductName;
    }

    //public String getDate() { return Date;  }
}