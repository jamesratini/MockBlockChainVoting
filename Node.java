public class Node
{
	private String ip;
	private int port;
	private int numConnections;

	public Node(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
		numConnections = 0;
	}

	public String getIP()
	{
		return ip;
	}
	public int getPort()
	{
		return port;
	}

	public void setNumConnections(int num)
	{
		numConnections = num;
	}

	public int getNumConnections()
	{
		return numConnections;
	}
}