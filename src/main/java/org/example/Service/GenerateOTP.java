package org.example.Service;

import java.util.Random;

public class GenerateOTP {
    public static String generateOTP(){
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}