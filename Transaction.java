import java.util.Date;

public class Transaction 
{
	private String sender;
	private String receiver;
	public static int amountSent = 1;
	private long timeStamp;
	
	public Transaction(String senderKey, String receiverKey)
	{
		setSenderKey(senderKey);
		setReceiverKey(receiverKey);
		setTimeStamp(new Date().getTime());
		
	}
	public void setSenderKey(String senderKey)
	{
		 sender = senderKey;
	}
	public void setReceiverKey(String receiverKey)
	{
		 receiver = receiverKey;
	}
	
	public void setTimeStamp(long timestamp)
	{
		 timeStamp = timestamp;
	}
	
	public String getSenderKey()
	{
		return sender;
	}
	public String getReceiverKey()
	{
		return receiver;
	}
	public long getTimeStamp()
	{
		return timeStamp;
	}
	public int getAmountSent()
	{
		return amountSent;
	}

}
