package ru.sd.commands.defaults;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sd.interpretator.Enviroment;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CdTest {

    Cd cdCommand = new Cd();
    Enviroment env = new Enviroment();


    @Test
    public void tooManyArgsTest(){
        String[] arg = {"..", "~/"};
        Assertions.assertThrows(CdException.class, ()->cdCommand.go_inner(arg, env));
        var expRes = "too many arguments";
        String actRes = "";
        try{
            cdCommand.go_inner(arg, env);
        }
        catch (CdException e){
            actRes = e.getMessage();
        }
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void zeroArgsTest() throws CdException {
        var expRes = System.getProperty("user.home");
        String[] arg ={};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void goToHomeDirTest() throws CdException {
        var expRes = System.getProperty("user.home");
        String[] arg ={"~"};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void setOldPwdExceptionTest(){
        String[] arg = {"-"};
        cdCommand = new Cd();
        Assertions.assertThrows(CdException.class, ()->cdCommand.go_inner(arg, env));
        String expRes = "OLDPWD not set";
        String actRes = "";
        try {
            cdCommand.go_inner(arg, env);
        }
        catch (CdException e){
            actRes = e.getMessage();
        }
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void setOldPwdTest() throws CdException {
        var expRes = System.getProperty("user.dir");
        zeroArgsTest();
        String[] arg = {"-"};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void setWrongPwdTest(){
        String[] arg = {"/aa/a/asfgby_a"};
        Assertions.assertThrows(CdException.class, ()->cdCommand.go_inner(arg, env));
        var expRes = "no such file or directory";
        var actRes = "";
        try {
            cdCommand.go_inner(arg, env);
        }
        catch (CdException e){
            actRes = e.getMessage();
        }
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void setNewWaySimpleTest() throws CdException {
        var currDir = System.getProperty("user.dir");
        var expRes = currDir+"/testDir";
        File newDirPath = new File(expRes);
        var isMakeNewDir = newDirPath.mkdir();
        String[] arg = {"testDir"};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
        if(isMakeNewDir){
            newDirPath.delete();
        }
    }

    @Test
    public void setNewWayComplicateTest() throws CdException{
        var currDir = System.getProperty("user.dir");
        var expRes = currDir+"/testDir1/testDir2";
        File newDirPath1 = new File(currDir+"/testDir1");
        var isMakeNewDir1 = newDirPath1.mkdir();
        File newDirPath2 = new File(currDir+"/testDir1/testDir2");
        var isMakeNewDir2 = newDirPath2.mkdir();
        String[] arg = {"testDir1/testDir2"};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
        if(isMakeNewDir2){
            newDirPath2.delete();
        }
        if(isMakeNewDir1) {
            newDirPath1.delete();
        }
    }

    @Test
    public void setNewWayStartsInHomeDirTest() throws CdException {
        var homeDir = System.getProperty("user.home");
        var expRes = homeDir+"/testDir";
        File newDirPath = new File(expRes);
        var isMakeNewDir = newDirPath.mkdir();
        String[] arg = {"~/testDir"};
        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);
        if(isMakeNewDir){
            newDirPath.delete();
        }
    }

    @Test
    public void setNewWayInParentDirTest()throws CdException {
        var currDir = System.getProperty("user.dir");
        var expRes = currDir+"/testDir1/testDir3";
        File newDirPath1 = new File(currDir+"/testDir1");
        var isMakeNewDir1 = newDirPath1.mkdir();
        File newDirPath2 = new File(currDir+"/testDir1/testDir2");
        var isMakeNewDir2 = newDirPath2.mkdir();
        File newDirPath3 = new File(currDir+"/testDir1/testDir3");
        var isMakeNewDir3 = newDirPath3.mkdir();
        String[] arg0 = {"testDir1/testDir2"};
        cdCommand.go_inner(arg0, env);

        String[] arg = {"../testDir3"};

        var actRes = cdCommand.go_inner(arg, env);
        Assertions.assertEquals(expRes, actRes);

        if(isMakeNewDir2){
            newDirPath2.delete();
        }
        if(isMakeNewDir3){
            newDirPath3.delete();
        }
        if(isMakeNewDir1) {
            newDirPath1.delete();
        }
    }
}