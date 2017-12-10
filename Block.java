import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block 
{
	private String hash;
	private String previousBlocksHash;
	private Ledger ledger;
	
	
	public Block(String previousBlock)
	{
		ledger = new Ledger();
		hash = getHash();
		previousBlocksHash = previousBlock;
		
		
	}
	
	public String getHash()
	{
		byte[] temp = null;
		String retVal = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			temp = digest.digest(getHashString().getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 retVal = bytesToHex(temp);
		 return retVal;
		
	}
	private static String bytesToHex(byte[] hash) 
	{
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	public String getHashString()
	{
		//change this later
		String retVal =  ledger.toString() + previousBlocksHash;
		return retVal;
	}
	public void addTransaction(Transaction newTransaction)
	{
		if(!ledger.isFull())
		{
			ledger.addTransaction(newTransaction);
		}
	}
	public boolean isFull()
	{
		return ledger.isFull();
	}

}
