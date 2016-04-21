
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

/*
 * 
 * @authors Santosh Kompally, Roshni Saraogi, Rohini Bandkodige
 * 	
 */

public class LP4Driver {
    static long[] description;
    static final int DLENGTH = 100000;

    public static void main(String[] args)  throws FileNotFoundException {
    	
    DecimalFormat	df= new DecimalFormat("#0.00");
	
	Scanner in;
	if(args.length > 0) {
	    in = new Scanner(new File(args[0]));
        } else {
	    in = new Scanner(System.in);
	}
	String s;
	double rv = 0;
	description = new long[DLENGTH];

//	Timer timer = new Timer();
	Search1 mds = new Search1();
	double temp;
//	int lineNumber=1;
	
	while(in.hasNext()) {
	    s = in.next();
	    if(s.charAt(0) == '#') {
		s = in.nextLine();
		continue;
	    }
	    if(s.equals("Insert")) {
		long id = in.nextLong();
		double price = in.nextDouble();
		long des = in.nextLong();
		int index = 0;
		while(des != 0) {
		    description[index++] = des;
		    des = in.nextInt();
		}
		description[index] = 0;
		temp= mds.insert(id, price, description, index);
		rv+=temp;
	//149: Insert: 1	
	//	System.out.println(lineNumber+ ": Insert: " + (int)temp);
	    } else if(s.equals("Find")) {
		long id = in.nextLong();
		temp=mds.find(id);
		rv += temp;
	//	System.out.println(lineNumber+": Find: " + df.format(temp));
	    } else if(s.equals("Delete")) {
		long id = in.nextLong();
		temp=mds.delete(id);
		rv += temp;
	//	System.out.println(lineNumber+": Delete: " +  df.format(temp));
	    } else if(s.equals("FindMinPrice")) {
		long des = in.nextLong();
		temp= mds.findMinPrice(des);
		rv += temp;
	//	System.out.println(lineNumber+": FindMinPrice: " +  df.format(temp));
	    } else if(s.equals("FindMaxPrice")) {
		long des = in.nextLong();
		temp=mds.findMaxPrice(des);
		rv += temp;
	//   System.out.println(lineNumber+": FindMaxPrice: "+  df.format(temp));
	    } else if(s.equals("FindPriceRange")) {
		long des = in.nextLong();
		double lowPrice = in.nextDouble();
		double highPrice = in.nextDouble();
		temp=mds.findPriceRange(des, lowPrice, highPrice);;
		rv += temp;
	//	System.out.println(lineNumber+": FindPriceRange: " +  df.format(temp));
	    } else if(s.equals("PriceHike")) {
		long minid = in.nextLong();
		long maxid = in.nextLong();
		double rate = in.nextDouble();
		temp=mds.priceHike(minid, maxid, rate);
		rv += Math.floor((temp+ 0.0000001)*100)/100;
	//	System.out.println(lineNumber+": PriceHike: " +  df.format(temp));
	    } else if(s.equals("Range")) {
		double lowPrice = in.nextDouble();
		double highPrice = in.nextDouble();
		temp= mds.range(lowPrice, highPrice);
		rv += temp;
	//	System.out.println(lineNumber+": Range: " +  df.format(temp));
	    } else if(s.equals("SameSame")) {
	    temp= mds.samesame1();
		rv += temp;
	//	System.out.println(lineNumber+": sameSame: " +  df.format(temp));
	    } else if(s.equals("End")) {
		break;
	    } else {
		System.out.println("Houston, we have a problem.\nUnexpected line in input: "+ s);
		System.exit(0);
	    }
//	lineNumber++;
	}
	System.out.println(df.format(rv));
//	System.out.println(timer.end());
    }
}
