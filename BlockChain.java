import java.util.*;
<<<<<<< HEAD

=======
>>>>>>> 9c88c3f2a8b29d4b1d0bab4e336aa545f309453c
public class BlockChain 
{
	private LinkedList<Block> chain;
	public BlockChain()
	{
		chain = new LinkedList<Block>();
		chain.addFirst(new Block(getGenesisHash()));
		
	}
	private String getGenesisHash()
	{
		return "000a1";
	}
	public void newTransaction(Transaction newTransaction)
	{
		//if the last block is full it adds a new block from the old ones hash
		if(chain.getLast().isFull())
		{
			chain.addLast(new Block(chain.getLast().getHash()));
			
		}
		chain.getLast().addTransaction(newTransaction);
	}
	
}
