/*
 * The Interop QuickStart Example.
 *
 * This example takes the following steps:
 *
 * 1. Create a GemFire Cache.
 * 2. Get the example Region from the Cache.
 * 3. Put an Entry (Key and Value pair) into the Region.
 * 4. Get Entries from the Region put by other clients.
 * 5. Close the Cache.
 *
 */
 
//package quickstart;

//include the required packages.
import java.util.Properties;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.distributed.DistributedSystem;

// The Interop QuickStart example.
public class InteropJAVA {
  
  public static void main(String [] args) throws Exception {
    try
    {
      // Create the cache which causes the cache-xml-file to be parsed
      ClientCache cache = (new ClientCacheFactory()).set("mcast-port", "0").set("cache-xml-file", "XMLs/clientInteropJava.xml").create();
  
      // Get the exampleRegion which is a subregion of /root
      Region exampleRegion = cache.getRegion("exampleRegion");
      System.out.println("JAVA CLIENT: Example region, " + exampleRegion.getFullPath() + ", created in cache. ");
  
      // Put an Entry (Key and Value pair) into the Region using the direct/shortcut method.
      System.out.println("Java CLIENT: Current test value is " + exampleRegion.get("test"));
      
      //String key = "Key-JAVA";
      //String value = "Value-JAVA";
      exampleRegion.put("Key-JAVA", "Value-JAVA");
      //exampleRegion.put(key, value);
  
      System.out.println("JAVA CLIENT: Put the Java Entry into the Region.");
      
      // Wait for all values to be available.
      Object value1 = null;
      Object value2 = null;
      
      while (value1 == null || value2 == null)
      {
        System.out.println("JAVA CLIENT: Checking server for my keys...");
        value1 = exampleRegion.get("Key-JAVA");
        System.out.println("JAVA CLIENT: Checking server for CPP Client...");
        value2 = exampleRegion.get("Key-CPP");
        Thread.sleep(1000);
      }
      
      System.out.println("JAVA CLIENT: Key-CPP value is " + value1);
      System.out.println("JAVA CLIENT: Key-JAVA value is " + value2);
      
      // Close the cache and disconnect from GemFire distributed system
      System.out.println("JAVA CLIENT: Closing the cache and disconnecting.");
      cache.close();
    }
    // An exception should not occur
    catch(Exception gemfireExcp)
    {
      System.out.println("JAVA CLIENT: Interop GemFire Exception: " + gemfireExcp.getMessage());
    }
  }
}

