package ru.sd.interpretator;

public class GlobalEnviroment {
    private String currDirPath;

    public GlobalEnviroment(){
        currDirPath = System.getProperty("user.dir");
    }
    public String getCurrDirPath(){
        return currDirPath;
    }

    public void setCurrDirPath(String path){
        currDirPath = path;
    }
}
