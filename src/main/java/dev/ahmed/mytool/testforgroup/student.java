/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.ahmed.mytool.testforgroup;

/**
 * @author HP
 */
public class student {

    private String name;
    private int no;
    private boolean verification;
    private String password;
    private static String college = "IT";
    private static int count = 1;
    private static int passwordRules = 6;

    public student(String name, String password) {
        this.name = name;
        this.password = password;
        this.no = count;
        this.verification = veri(this.no, this.password);// I added this line
        count++;
    }

    public boolean veri(int enterNo, String enterPassword) {
        if (enterNo == no && enterPassword.equals(password)) {
            verification = true;
            return true; // I also added this line
        }
        return false;
    }

    public static boolean logen(String password) {
        if (password.length() >= passwordRules) {
            return true;
        } else {
            return false;
        }
    }

    @Override  // tostring metodni qayta yazdim.
    public String
    toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", no=" + no +
                ", verification=" + verification +
                ", password='" + password + '\'' +
                '}';
    }
}
