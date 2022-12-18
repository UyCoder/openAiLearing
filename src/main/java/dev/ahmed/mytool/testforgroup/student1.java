/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.ahmed.mytool.testforgroup;

/**
 * @author HP
 */
public class student1 {
    public static void main(String[] args) {
        student s1 = new student("ali", "538749");
        student s2 = new student("mardan", "538759");
        student s3 = new student("salim", "538769");
        student s4 = new student("tursun", "538779");

        System.out.println(s1.toString());
        System.out.println(s1.veri(1, "538749"));
        System.out.println(s2.toString());
        System.out.println(s2.veri(1, "538749"));
        System.out.println(s3.toString());
        System.out.println(s4.toString());

    }

}
