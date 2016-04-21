import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;


/*
 * 
 * @authors Santosh Kompally, Roshni Saraogi, Rohini Bandkodige
 * 	
 */
public class Search1 {

	NavigableMap<Long,Item> items;
	HashMap<Long,NavigableSet<Long>>  descriptionMap1;
	long sameSame;
	ArrayList<Same>[] hashList;
    static final int DLENGTH = 100000;
	
	Search1(){
		items= new TreeMap<>();
		descriptionMap1=new HashMap<>();
		sameSame=0;
		hashList = (ArrayList<Same>[]) new ArrayList[1001]; 
		
		for(int i=0;i<1001;i++){
			
			hashList[i]=new ArrayList<>();
		}
	
	}
	
	long samesame1(){
		
	return sameSame;	
		
	}
	
	
	// calculate the hash of the descriptors return a long value.
	// input is array of long and its size
	// sum of inputs modulo 100030001. Modulo is done to prevent overflow.
	
	long calculateHash(long input[], int size){
		
		long hash=0;
		
		for(int i=0;i<size;i++){


		hash = (hash+ input[i])%100030001;
		
		}		
		return hash;
	}
	
	/* 
	 *
	 * for saving the data we need description for the first time in the arrayList for comparison.
	 * If already exists just increment the value of count.
	 * In case count becomes 2 then we have to add 2 to the final list
	 * Other wise we increment the count by 1
	 * If the value is not found which means it does not have any matching and we don't need to increment the final count value.
	 */
	long addToSameSame(long id,long[] description,long hash,int size){
		
		// sort the initial descriptor list.
		Arrays.sort(description);
		
		boolean equal=false;
		int count;
		for(int i=0; i< hashList[size].size();i++){
			
			if(hash == hashList[size].get(i).hashValue){
				
				// if we find the hash value then compare the description list.
				// second list is sorted and stored before so dont sort it.
				equal = compareDescriptions(description,hashList[size].get(i).descriptors,size);
				// which means someone like this already exists.
				if(equal== true){
				count=	hashList[size].get(i).count;
				
				if(count==1){
					
					sameSame+=2;
				}else{
					
					sameSame+=1;
				}
				// increment the count.
				hashList[size].get(i).count++;
				
				// break from the for loop no need to iterate any longer.
				break;
				
				}
				
			}
			
		}
		
		// if the value is new and needs to be added then we have to add it to the arrayList.
		if(equal==false){
			
			hashList[size].add(new Same(hash,description));
			
		}
		
		return sameSame;
		
	}
	
	
	void remove(Item item){
		// sort the input of item class descriptors.
		int size=item.size;
		long hashValue=0;
		boolean equal=false;
		int position=0;
		
		for(int i=0;i<item.size;i++){
			
			hashValue = (hashValue+ item.desciption[i])%100030001;
		}
		// create a object with same descriptors and also calculate the hash.
		for(int i=0;i<hashList[size].size();i++){
			
			if(hashValue == hashList[size].get(i).hashValue){
				
				if(compareDescriptions(item.desciption, hashList[size].get(i).descriptors, size)){
					
					// If only  once occurrence is found
					if(hashList[size].get(i).count == 1){
						
						hashList[size].remove(i);
						break;
					}else{
						
						hashList[size].get(i).count--;
						sameSame--;
						
					}
					
				}
				
				
			}
			
			
		}	
		
	}
	
	
	/**
	 * @param id1
	 * @param id2
	 * @param size
	 * @return true if descriptors of both the lists are same irrespective of the ordering else return false.
	 */
	boolean compareDescriptions(long[] input1,long[] input2,int size){
		
		boolean equal = true;
			
		for(int i=0;i<size;i++){
			
			if(input1[i] != input2[i]){
				
				equal=false;
				break;
			}
		}
		
		
		
		return equal;
	}
	
    int insert(long id, double price, long[] description, int size) {

    	int delete=0;
    	int returnValue=0;
    	Item temp=null;
    	if(find(id)==0){	
    	returnValue=1;
    	
    	}else{
    	// get the old value and remove from desriptionList and priceList as well as from SameSame.
    		temp= items.get(id);
    		delete=1;
    		// remove from the SameSame this entry and its count.
    		
    		for(int i=0;i<temp.size;i++){
    			
    			(descriptionMap1.get(temp.desciption[i])).remove(id);
   		}
    		
    	}

    // for storing the hash value.
    long hashValue=0;
    long[]	descriptionList = new long[size];
    long[]	descSameSame = new long[size];
   // copy all the descriptions to the descriptionList array. 	
    
    for(int i=0;i<size;i++){
    	
    	descriptionList[i] = description[i];
    	descSameSame[i]=description[i];
    	hashValue = (description[i] + hashValue)%100030001;
    	
    }	
    
    if(delete==1 && size!=0){
    	
    	remove(temp);
    	
    }
    if(temp != null && size != 0){	
    	items.put(id, new Item(id,price,descriptionList,size));
    	if(size>=8){
    	addToSameSame(id, descSameSame, hashValue, size);
    	}
    }
    else if(temp != null && size == 0){
        	items.put(id, new Item(id,price,temp.desciption,temp.size));
    	// putting back the old descriptions in the descriptionMap.
    	for(int i=0;i<temp.size;i++){
			
			(descriptionMap1.get(temp.desciption[i])).add(id);
		}
 
    }	
    	
    else{
    	
    	if(size>=8){
        	addToSameSame(id, descSameSame, hashValue, size);
        }
    	
    	items.put(id, new Item(id,price,descriptionList,size));
    }
    	
    for(int i=0;i<size;i++){
    	
    	NavigableSet<Long> desList= descriptionMap1.get(descriptionList[i]);
    	
		if(desList==null){
			NavigableSet<Long> arrList=new TreeSet<>();
			arrList.add(id);
			this.descriptionMap1.put(descriptionList[i],arrList);		
		}else{
			desList.add(id);
			this.descriptionMap1.put(descriptionList[i],desList);
			
		}

    	
    }		
	return returnValue;
    }

    double find(long id) {
    	
    	Item i= items.get(id);
		if(i!= null){
			return i.price;
		}else{
		
			return 0;
		}
    	
    }
    long delete(long id) {
	
    	Item i= items.remove(id);
		long sumOfDesciption=0;
		NavigableSet<Long> temp;
		
		
		
		if(i==null){
			
			return sumOfDesciption;
		}else{
			// item is not null so pass this item to remove.
			remove(i);
			//removing the item from the price list as well.
			for(int j=0;j<i.size;j++){
				
				
				temp=descriptionMap1.get(i.desciption[j]);
				temp.remove(id);
				descriptionMap1.put(i.desciption[j] , temp);
				
				sumOfDesciption+=i.desciption[j];
				
				
			}
			
			
			
			return sumOfDesciption;
		}
    	
    }

    double findMinPrice(long des) {
	
    	double minPrice= Double.MAX_VALUE;
    	
     // iterate through the list and find the minimum.
    	for(Long l: descriptionMap1.get(des)){ 
        	
    		if(items.get(l).price < minPrice)
    			minPrice= items.get(l).price;
    	}
    	
    if(minPrice == Double.MAX_VALUE){
    	return 0;
    }	
	return minPrice;
    	
    	
    }

    double findMaxPrice(long des) {
    	
    	double maxPrice=0;
    
    	for(Long l: descriptionMap1.get(des)){ 
    		
    		if(items.get(l).price > maxPrice)
    			maxPrice= items.get(l).price;
    	}
    		
	return maxPrice;
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
    	
    	int count=0;
    	NavigableSet<Long>  descriptionMap = descriptionMap1.get(des);
    	
    	for(Long l: descriptionMap){
    		
    		if( lowPrice<= items.get(l).price && highPrice>=items.get(l).price){
    			
    			count++;
    		}
    		
    		
    	}
    	
    	
	return count;
    }

    double priceHike(long minid, long maxid, double rate) {
    	
    	NavigableMap<Long,Item> itemList= items.subMap(minid, true, maxid, true);
    	Item it;
    	double totalIncrease=0;
    	double netIncrease=0;
    	for(Long l: itemList.keySet()){
    		
    		it= itemList.get(l);
    		netIncrease = (rate*it.price)/100;
    		netIncrease = Math.floor((netIncrease + 0.000001)*100)/100;
    		// rounding upto 2 decimal places.
    		it.price = it.price  + netIncrease;
    		totalIncrease+= netIncrease;
    		// now put back the changed values in the priceList.
    		items.put(l, it);
    		
    	}
	return totalIncrease;
    }

    int range(double lowPrice, double highPrice) {
    	
    	
//    	NavigableSet<MinMax> subPriceList= priceList.subSet(new MinMax(0,lowPrice) , true,new MinMax(Long.MAX_VALUE,highPrice), true);
//    	
//    	if(subPriceList!=null){
//    		
//    		return subPriceList.size();
//    	}else{
//    
//    		return 0;
//    	}
    	
    	int numberOfItems=0;
    	double price;
    	for(long i: items.keySet()){
    		price = Math.floor((items.get(i).price+0.00000001)*100)/100;
    		if(price >= lowPrice && price<=highPrice)numberOfItems++;
    		
    	}
    	
    	
    	return numberOfItems;
    	
    	
	
    }

    
}
	

