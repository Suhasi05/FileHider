package org.example.Views;

import org.example.DAO.DataDAO;
import org.example.Model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email) {
        this.email = email;
    }
    public void home() throws SQLException {
        do {
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());
            switch(choice) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for(Data data : files) {
                            System.out.println(data.getId() + "-" + data.getFileName());

                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    List<Data> files = DataDAO.getAllFiles(this.email);
                    System.out.println("ID - File Name");
                    for(Data data : files) {
                        System.out.println(data.getId() + "-" + data.getFileName());

                    }
                    System.out.print("Enter the id of file you wish to unhide : ");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for(Data data : files) {
                        if(data.getId() == id) {
                            isValidID = true;
                            break;
                        }
                    }
                    if(isValidID) {
                        try {
                            DataDAO.unHide(id);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Wrong ID");
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
