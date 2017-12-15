


public class Ledger 
{
	private Transaction[] transactions ;
	
	public Ledger()
	{
		transactions = new Transaction[10];
	}
	public void addTransaction(Transaction newTransaction)
	{
		if(transactions.length < 10)
		{
			transactions[transactions.length] = newTransaction;
		}
	}
	public void clean()
	{
		transactions = new Transaction[10];
	}
	public boolean isFull()
	{
		if(transactions.length < 10)
		{
			return false;
		} else
		{
			return true;
		}
	}
	public void removeTransaction(int index)
	{
		transactions[index] = null;
		for(int i = index; i < transactions.length-1; i++)
		{
			transactions[i] = transactions[i+1];
		}
		transactions[9] = null;
	}

}
