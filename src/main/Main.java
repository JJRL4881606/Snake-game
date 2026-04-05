package main;

import com.formdev.flatlaf.FlatDarkLaf;
//import views.GameFrame;
import views.HomeWindow;

public class Main {
    public static void main(String[] args) {
    	FlatDarkLaf.setup();
        new HomeWindow();
    }
}
