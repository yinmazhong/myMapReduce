package dfs;

import scala.None;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;

/**
 * Created by xxh on 18-8-14.
 */
public class DataNode {

    public DataNode(String dataDir){

    }

    public DataNode(String machineName, File dataDir, InetSocketAddress nameNodeAddr){
    }

    private static Vector subThreadList = null;
    //DataNodeProtocal namenode;
    //FSDataset data;

    static DataNode makeInstanceForDir(String dataDir){
        DataNode dn = null;
        File data = new File(dataDir);
        data.mkdirs();
        if (!data.isDirectory()){
            return null;
        }else {
            dn = new DataNode(dataDir);
        }
        return  dn;
    }

    public static void run(){
        String[] dataDirs = {"/home/xxh/data/myhadoop/data/"};
        subThreadList = new Vector(dataDirs.length);
        for (int i = 0; i < dataDirs.length;i++){
            DataNode dn = makeInstanceForDir(dataDirs[i]);
        }
    }
    public static void runAndWait() {

    }
    public static void main(String args[]) throws IOException{

    }
}
