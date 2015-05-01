/**
  Class to implement marker algorithm for paging problem
  Solves CS255 homework assignment #4
  @author Anumeha, Shruti Neha
  @version 1.01 2015/4/20
*/

import java.util.ArrayList;
import java.util.Scanner;

/**
 CacheValue class is used to create cache (key value pair) for the cache slot. key is the page no
 and the value is X or empty to show if the page is marked or not
*/

class CacheValue
{
	int key;
	String value;
	
	CacheValue(int k, String s)
	{
		key = k;
		value = s;
	}
	
	/*
    This methods is used to get page no at a given cache index
    */
	public int getKey()
	{
		return key;
	}
	
	/*
    This methods is used to get marked value at a given cache index
    */
	public String getValue()
	{
		return value;
	}
	
	/*
    This methods is used to set a page no as marked
    @param args s which refers to the value that we use to mark the page. Here the value of s = "X"
    */
	public void setValue(String s)
	{
		value = s;
	}
	
}

/**
 Class to implement marker algorithm for paging problem
*/

public class Marker {
	//this ArrayList is used to simulate cache.
   ArrayList<CacheValue> cache = new ArrayList<CacheValue>();
   int size;
	
   /*
    Method to set cache size and initial cache values.
    */
	public void setCache()
	{
		for(int i = 0; i < size; i++)
		{
			CacheValue cachevalue = new CacheValue(i + 1, " ");
			cache.add(cachevalue);
		}
	}
	
	 /*
     Method to reset cache and start new round 
     when all the bits are marked and user request for a page which is not in cache.
    */
	public void resetCache()
	{
		for(int i = 0; i < size; i++)
		{
			cache.get(i).setValue(" ");
		}
	}
	
	 /*
     Method to display cache values marked and unmarked
    */
	public void displayCache()
	{
		for(CacheValue cachevalue :cache )
		{
			System.out.println(cachevalue.key + " " + cachevalue.value);
		}
	}
	
	 /*
     This methods check if the given cache index is marked or not. If not then it marks it.
     @param args index which refers to cache index.  
    */
	public int checkAndSet(int index)
	{
		if(cache.get(index).getValue() == "X")
		{
			return 1;
		}
		else
		{
			cache.get(index).setValue("X");
			return 1;
		}
	}
	
	 /*
     This methods iterate over the cache and if finds the page then mark it.
     @param args page which refers to requested page.  
    */
	
	public int iterateAndset(int page)
	{
		int hit = 0;
		for(int i = 0; i < cache.size(); i++)
		{
			if(cache.get(i).getKey() == page)
			{
				checkAndSet(i);
				hit = 1;
				break;
			}
			
		}
		return hit;
	}
	
	 /*
     This methods checks if all the cache slots are marked 
     returns : true or false.
    */
	public boolean allMarked()
	{
		boolean res = true;
		for(CacheValue cachevalue : cache)
		{
			if(cachevalue.getValue() == " ")
			{
				res = false;
				break;
			}
		}
		return res;
	}
	
	 /*
     This is the main method which checks for the requested page and if it finds it then mark the page if 
     it does not find it then it looks for unmarked page randomly and mark any of the unmarked page and also 
     set the page value as requested page.
     @param args page which refers to requested page. 
     returns 1 if it is able to find and mark the page or randomly find an unmarked page and mark it. else returns 0  
    */
	public int hitCache(int page)
	{
		int hit = 0;
		if(iterateAndset(page) == 1)
		{
			hit =1;
			return hit;
		}
		else //if hit is still 0
		{
			//check if all the cache values are marked.
			if(allMarked())
			{
				resetCache();
				if(iterateAndset(page) == 1)
				{
					hit = 1;
					return hit;
				}
				else
				{
					hit = randomHit(page);
					return hit;
				}
				
			}
			else
			{ //not all marked. get a cache index randomly and set the value.
				
				hit = randomHit(page);
				return hit;
			}
		}
	}
	
	/*
    This method randomly selects a page which is not marked
    @param args page which refers to requested page.  
    */
	public int randomHit(int page)
	{
		int hit = 0;
		while(true)
		{
			int randomHit = (int) (Math.random() * size + 1);
			if(cache.get(randomHit - 1).getValue() == " ")
			{
				CacheValue cachevalue = new CacheValue(page, "X");
				cache.set(randomHit - 1, cachevalue);
				hit = 1;
				return hit;
			}
		}//end while
	}
	
	/*
    Main Method 
   */
	public static void main(String[] args)
	{
		
		int size = 0;
		Scanner in = new Scanner(System.in);
		
		if(args.length < 1)
		{
			System.out.println("Please provide the argument");	
		}
		else
		{
			size = Integer.parseInt(args[0]);
			
			Marker marker = new Marker();
			marker.size = size;
			marker.setCache();
			
			System.out.println("Cache Looks Like");
			marker.displayCache();
			
			boolean repeat = true;
			
			//keeps on asking user the page no until and unless user provides -1
			while(repeat)
			{

				System.out.print("What page would you like? ");
				
				int page = in.nextInt();
				
				if(page == -1)
				{
					System.out.println("Done");
					repeat = false;
				}
				else
				{
					marker.hitCache(page);
					System.out.println("Cache Looks Like");
					marker.displayCache();
				}
							
			}//end of while
			
		}//end of else
			
	}//end of main

}//end of Marker.java class
