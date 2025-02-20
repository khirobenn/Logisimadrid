package Gates;
import Gate.QuadBool;

public class AndQuad{

public static QuadBool And(QuadBool input1, QuadBool input2){
	if(input1==QuadBool.ERROR) return QuadBool.ERROR;

	if(input1==QuadBool.TRUE && input2!=QuadBool.NOTHING) return input2;

	if(input1==QuadBool.TRUE && input2==QuadBool.NOTHING) return QuadBool.ERROR;

	if(input1==QuadBool.FALSE && input2!=QuadBool.ERROR) return QuadBool.FALSE;

	if(input1==QuadBool.FALSE && input2==QuadBool.ERROR) return QuadBool.ERROR;

	if(input1==QuadBool.NOTHING && input2!=QuadBool.FALSE) return QuadBool.ERROR;

	if(input1==QuadBool.NOTHING && input2==QuadBool.FALSE) return QuadBool.FALSE;

	return null;

}

}

