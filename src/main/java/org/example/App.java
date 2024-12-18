package org.example;

import org.example.Views.Welcome;

public class App 
{
    public static void main( String[] args )
    {
        Welcome w = new Welcome();
        do {
            w.welcomeScreen();
        } while( true );
    }
}
