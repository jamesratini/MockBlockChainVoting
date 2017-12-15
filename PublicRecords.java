import java.util.*;

public class PublicRecords
{

	private HashMap<String, Integer> records;

	public PublicRecords()
	{
		records = new HashMap<String, Integer>();
		records.put("James", 1);
		records.put("Chase", 1);
	}

	public boolean contains(String key)
	{
		return records.containsKey(key);
	}

	public boolean hasVote(String key)
	{
		return records.get(key) == 1 ?  true : false;
	}

	public void voteCast(String key)
	{
		records.replace(key, 0);
	}
}