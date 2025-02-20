package Gates;
import Gate.QuadBool;


public class OrQuad{
	public static QuadBool Or(QuadBool input1 , QuadBool input2){


	if(input1.equals(QuadBool.ERROR)) return QuadBool.ERROR;

	if(input1==QuadBool.FALSE && input2!=QuadBool.NOTHING) return input2;

	if(input1==QuadBool.FALSE && input2==QuadBool.NOTHING) return QuadBool.ERROR;

	if(input1==QuadBool.TRUE && input2!=QuadBool.ERROR) return QuadBool.TRUE;

	if(input1==QuadBool.TRUE && input2==QuadBool.ERROR) return QuadBool.ERROR;

	if(input1==QuadBool.NOTHING && input2!=QuadBool.TRUE) return QuadBool.ERROR;

	if(input1==QuadBool.NOTHING && input2==QuadBool.TRUE) return QuadBool.TRUE;

	return null;

	}
}







