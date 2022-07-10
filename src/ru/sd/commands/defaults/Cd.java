package ru.sd.commands.defaults;

import ru.sd.interpretator.Enviroment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cd extends Command {

    String oldPath = null;

    @Override
    public Command clone() {
        return this;
    }

    @Override
    protected void go(String[] args, Enviroment env) {
        try {
            go_inner(args);
        } catch (CdException e) {
            jout.println(e.getMessage());
        }
    }

    protected String go_inner(String[] args) throws CdException {

        if(args.length > 1){
            throw new CdException("too many arguments");
        }

        String resDir;
        var startPath = System.getProperty("user.dir");

        if(args.length == 0){
            resDir = getHomePath();
        }
        else if(args[0].equals("-")){
            resDir = getOldPath();
        }
        else{
            var helpPath = args[0];
            resDir = getCurrDir();

            if(helpPath.startsWith("~") && (helpPath.length()==1 || helpPath.charAt(1)=='/')){
                resDir = getHomePath();
                helpPath = helpPath.length()>2 ? helpPath.substring(2): "";
            }
            while(helpPath.startsWith("..") &&(helpPath.length()==2 || helpPath.charAt(2)=='/')){
                resDir = getParentPath();
                helpPath = helpPath.length()> 3 ? helpPath.substring(3) : "";
            }
            if(!helpPath.equals("")) {
                helpPath = resDir + "/" + helpPath;
                resDir = getPath(helpPath);
            }
        }
        setCurrDir(resDir);
        oldPath = startPath;
        return resDir;
    }

    private String getHomePath(){
        return System.getProperty("user.home");
    }

    private String getOldPath() throws CdException {
        if(oldPath == null){
            throw new CdException("OLDPWD not set");
        }
        else{
            //jout.println(oldPath);
            return oldPath;
        }
    }

    private String getParentPath(){
        var currPath = getCurrDir();
        var parentPath = Paths.get(currPath).getParent();
        if(parentPath == null){
            return currPath;
        }
        return parentPath.toString();
    }

    private String getPath(String dir) throws CdException {
        var path = Paths.get(dir);
        if(Files.isDirectory(path)){
            return dir;
        }
        else {
            throw new CdException("no such file or directory");
        }
    }

    private String getCurrDir(){
        return System.getProperty("user.dir");
    }

    private void setCurrDir(String path){
        System.getProperty("user.dir", path);
    }
}
