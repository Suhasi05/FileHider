package org.example.Views;

import org.example.DAO.UserDAO;
import org.example.Model.User;
import org.example.Service.GenerateOTP;
import org.example.Service.SendOTPService;
import org.example.Service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the app");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Signup");
        System.out.println("Press 0 to Exit");
        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);

        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the email : ");
        String email = sc.nextLine();
        try {
            if(UserDAO.isExist(email)) {
                String genOTP = GenerateOTP.generateOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if(otp.equals(genOTP)) {
//                    System.out.println("Welcome to the app");
                    new UserView(email).home();

                } else {
                    System.out.println("Invalid OTP");
                }
            } else {
                System.out.println("User does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name : ");
        String name = sc.nextLine();
        System.out.println("Enter email : ");
        String email = sc.nextLine();

    try {
        if(UserDAO.isExist(email)) {
            System.out.println("User already exists");
        } else {
            String genOTP = GenerateOTP.generateOTP();
            SendOTPService.sendOTP(email, genOTP);
            System.out.println("Enter the OTP");
            String otp = sc.nextLine();
            if(otp.equals(genOTP)) {
                User user = new User(name, email);
                int res = UserService.saveUser(user);
                switch (res) {
                    case 0 -> System.out.println("User already exists");
                    case 1 -> System.out.println("User registered successfully");
                }
            } else {
                System.out.println("Invalid OTP");
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
}
