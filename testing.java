import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;
public class testing{
	
	@Test
	//the tests NEED to be public or else it gives an error
	public void testTesting()
	{
		assertEquals(1,1);
	}
	
	@Test
	public void PeerTest()
	{
		Peer testPeer = new Peer(1,"1.1.1",5000);
		assertNotNull(testPeer);
		//assertEquals(testPeer.getId(),1);
		//assertEquals(testPeer.getIP(),"1.1.1");
		//assertEquals(testPeer.getPort(),5000);
		
		
		
	}
	

}
