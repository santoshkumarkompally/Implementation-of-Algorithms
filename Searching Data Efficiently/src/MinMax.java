/*
  * 
  * @authors Santosh Kompally, Roshni Saraogi, Rohini Bandkodige
  * 	
  */
public class MinMax {
	
	
	double price;
	long id;
	
	public MinMax(long id,double price) {
		this.price = price;
		this.id = id;
	}

	
	@Override
	public boolean equals(Object obj) {
		
		MinMax temp = (MinMax)obj;
		
		if(price==temp.price && id== temp.id){
			
			return true;
		}else{
			
			return false;
		}
	}
	
}
