import java.util.*;

public class BlockChain 
{
	private LinkedList<Block> chain;
	private String previousHash;
	public BlockChain()
	{
		chain = new LinkedList<Block>();
		
	}
	private String getGenesisHash()
	{
		return "000a1";
	}
	public int size()
	{
		return chain.size();
	}
	public void add(Block b)
	{
		chain.add(b);
		previousHash = b.getHashString();
	}
	public String getPreviousHash()
	{
		return previousHash;
	}
	
}
