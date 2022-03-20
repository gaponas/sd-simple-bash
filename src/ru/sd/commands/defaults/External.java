package ru.sd.commands.defaults;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import ru.sd.interpretator.Enviroment;

public class External extends Command{

    private class TakeInStream extends Thread{
        protected InputStream sourceStream;
        protected PrintStream targetStream;
        public TakeInStream(InputStream source, PrintStream target) {
            sourceStream = source;
            targetStream = target; 
        }
    }

    private class TakeOutStream extends Thread {
        protected OutputStream sourceStream;
        protected Scanner targetStream;
        protected Process process;
        public TakeOutStream(OutputStream source, Scanner target, Process proc) {
            sourceStream = source;
            targetStream = target;
            process = proc;
        }

    }

    protected void go(String[] args, Enviroment en) {
        try {
            Process proc = Runtime.getRuntime().exec(args[0]);

            TakeOutStream tos = new TakeOutStream(proc.getOutputStream(), jin, proc) {
                @Override
                public void run() {
                    PrintStream ps = new PrintStream(sourceStream);
                    while(proc.isAlive() && targetStream.hasNext()) {
                        ps.println(targetStream.next());
                    }
                    ps.close();
                }
            };
            TakeInStream tis = new TakeInStream(proc.getInputStream(), jout) {
                @Override
                public void run() {
                    Scanner sc = new Scanner(sourceStream);
                    while(sc.hasNext()) {
                        targetStream.println(sc.next());
                    }
                    sc.close();
                }
            };

            tos.start();
            tis.start();

            proc.waitFor();

            tos.join();
            tis.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Command clone() {
        return new External();
    }
}
