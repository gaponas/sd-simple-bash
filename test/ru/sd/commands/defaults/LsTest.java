package ru.sd.commands.defaults;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sd.commands.CommandManager;
import ru.sd.interpretator.Enviroment;
import ru.sd.interpretator.Interpretator;

import java.io.File;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class LsTest {
    Ls lsCommand = new Ls();
    Enviroment env = new Enviroment();

    @Test
    public void tooManyArgsTest(){
        String[] arg = {"..", "~/"};
        Assertions.assertThrows(LsException.class, ()->lsCommand.go_inner(arg, env));
        var expRes = "too many arguments";
        String actRes = "";
        try{
            lsCommand.go_inner(arg, env);
        }
        catch (LsException e){
            actRes = e.getMessage();
        }
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void zeroArgsTest() throws CdException, LsException {
        String[] arg = {};
        var currPath = System.getProperty("user.dir");
        File newDirPath1 = new File(currPath+"/testDir1");
        var isMakeNewDir1 = newDirPath1.mkdir();

        System.setProperty("user.dir", currPath+"/testDir1");
        env = new Enviroment();

        File newDirPath2 = new File(currPath+"/testDir1/testDir2");
        var isMakeNewDir2 = newDirPath2.mkdir();

        var actRes = lsCommand.go_inner(arg, env);
        var expRes = new HashSet<>();
        expRes.add("testDir2");
        Assertions.assertEquals(expRes, actRes);
        if(isMakeNewDir2){
            newDirPath2.delete();
        }
        if(isMakeNewDir1){
            newDirPath1.delete();
        }
    }

    @Test
    public void getWrongPwdTest(){
        String[] arg = {"/aa/a/asfgby_a"};
        Assertions.assertThrows(LsException.class, ()->lsCommand.go_inner(arg, env));
        var expRes = "no such file or directory";
        var actRes = "";
        try {
            lsCommand.go_inner(arg, env);
        }
        catch (LsException e){
            actRes = e.getMessage();
        }
        Assertions.assertEquals(expRes, actRes);
    }

    @Test
    public void simpleTest() throws LsException {
        String[] arg = {"testDir1"};
        var currPath = System.getProperty("user.dir");
        File newDirPath1 = new File(currPath+"/testDir1");
        var isMakeNewDir1 = newDirPath1.mkdir();

        File newDirPath2 = new File(currPath+"/testDir1/testDir2");
        var isMakeNewDir2 = newDirPath2.mkdir();

        var actRes = lsCommand.go_inner(arg, env);
        var expRes = new HashSet<>();
        expRes.add("testDir2");
        Assertions.assertEquals(expRes, actRes);
        if(isMakeNewDir2){
            newDirPath2.delete();
        }
        if(isMakeNewDir1){
            newDirPath1.delete();
        }
    }
}