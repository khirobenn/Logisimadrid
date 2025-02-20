package Gates;

import Gate.QuadBool;



public class NotQuad{
	public static QuadBool Not(QuadBool input){
		switch(input){
			case QuadBool.TRUE -> {
                            return QuadBool.FALSE;
                }
			case QuadBool.FALSE -> {
                            return QuadBool.TRUE;
                }
			case QuadBool.ERROR -> {
                            return QuadBool.ERROR;
                }
			case QuadBool.NOTHING -> {
                            return QuadBool.ERROR;
                }
		}
				
		return null;
	}
}
