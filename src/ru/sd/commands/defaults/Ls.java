package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ls extends Command {

    Enviroment envir;

    @Override
    public Command clone() {
        return this;
    }

    @Override
    protected void go(String[] args, Enviroment env) {
        jout.println("env curr:"+env.getCurrDirPath());
        try {
            var res = go_inner(args, env);
            printAllFiles(res);
        }
        catch (LsException e){
            jout.println(e.getMessage());
        }
    }

    protected HashSet<String> go_inner(String[] args, Enviroment env) throws LsException {
        envir = env;
        if(args.length > 1){
            throw new LsException("too many arguments");
        }
        String dirToView;
        if(args.length == 0){
            dirToView = getCurrDir();
        }
        else{
            var helpPath = args[0];
            dirToView = getCurrDir();

            if(helpPath.startsWith("~") && (helpPath.length()==1 || helpPath.charAt(1)=='/')){
                dirToView = getHomePath();
                helpPath = helpPath.length()>2 ? helpPath.substring(2): "";
            }
            while(helpPath.startsWith("..") &&(helpPath.length()==2 || helpPath.charAt(2)=='/')){
                dirToView = getParentPath();
                helpPath = helpPath.length()> 3 ? helpPath.substring(3) : "";
            }
            if(!helpPath.equals("")) {
                helpPath = dirToView + "/" + helpPath;
                dirToView = getPath(helpPath);
            }
        }
        return getAllFilesInDir(dirToView);
    }

    private String getHomePath(){
        return System.getProperty("user.home");
    }

    private String getParentPath(){
        var currPath = getCurrDir();
        var parentPath = Paths.get(currPath).getParent();
        if(parentPath == null){
            return currPath;
        }
        return parentPath.toString();
    }

    private String getPath(String dir) throws LsException {
        var path = Paths.get(dir);
        if(Files.isDirectory(path)){
            return dir;
        }
        else {
            throw new LsException("no such file or directory");
        }
    }

    private String getCurrDir(){
        return envir.getCurrDirPath();
    }

    private void setCurrDir(String path){
        envir.setCurrDirPath(path);
    }

    private HashSet<String> getAllFilesInDir(String currDir) throws LsException {
        var currPath = Paths.get(currDir);
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(currPath)) {
            HashSet<String> resSet = new HashSet<>();
            for(Path p : paths){
                var fileName = p.getFileName().toString();
                resSet.add(fileName);
            }
            return resSet;
        } catch (IOException e) {
            throw new LsException("no such file or directory");
        }
    }

    private void printAllFiles(HashSet<String> resSet){
        var strBuffer = new StringBuffer();
        resSet.stream()
                .filter(p->!p.startsWith("."))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .forEach(p-> strBuffer.append(p).append("\n"));
        jout.println(strBuffer);
    }
}
