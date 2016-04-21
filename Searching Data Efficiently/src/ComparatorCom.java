
import java.util.Comparator;

/*
 * 
 * @authors Santosh Kompally, Roshni Saraogi, Rohini Bandkodige
 * 	
 */

public class ComparatorCom implements Comparator<MinMax> {

	
	
	
	@Override
	public int compare(MinMax m1, MinMax m2) {
	
		
		if(m1.price<=m2.price){
			
			return -1;
		
		}else if(m1.price>=m2.price){
			
			return 1;
		}else{
			
			return -1;
			
		}
		
	}

	
	
}
