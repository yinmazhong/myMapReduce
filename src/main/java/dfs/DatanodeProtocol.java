package dfs;

import jdk.nashorn.internal.ir.Block;

import java.io.IOException;

/**
 * Created by xxh on 18-8-14.
 */
interface DatanodeProtocol {
public void sendHeartbeat(String sender,long capacity,long remaining) throws IOException;
public Block[] blockReport(String sender,Block blocks[]) throws IOException;

public void blockReceived(String sender,Block blocks[]) throws IOException;

public void errorReport(String sender,String msg) throws IOException;

public  BlockCommand getBlockwork(String sender, int xmitsInProgress) throws IOException;
}
