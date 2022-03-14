package ru.sd.main;
import java.util.Scanner;

import ru.sd.commands.CommandManager;
import ru.sd.interpretator.*;

public class Program {
    public static void main(String[] args) {


        CommandManager cmnds = new CommandManager();
        Interpretator i = new Interpretator(cmnds);
        if(args.length > 0) {
            i.go(args[0]);
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("$ ");
        while(sc.hasNextLine()) {
            String cmnd = sc.nextLine();
            i.go(cmnd);
            System.out.print("$ ");
        }
        sc.close();         
    }
}
