package Circuit;

import Gates.AndQuad;
import Gates.NotQuad;
import Gates.OrQuad;

public enum QuadBool{
	TRUE,FALSE,ERROR,NOTHING;

	// Opération "!"
    public QuadBool not() {
        switch (this) {
            case TRUE: return FALSE;
            case FALSE: return TRUE;
            default: return ERROR;
        }
    }

    // Opération "sup"
    public static QuadBool sup(QuadBool a, QuadBool b) {
        if (a == ERROR || b == ERROR) return ERROR;
        if (a == NOTHING) return b;
        if (b == NOTHING) return a;

        if ((a == TRUE && b == FALSE) || (a == FALSE && b == TRUE)) return ERROR;
        if(a == TRUE || b == TRUE) return TRUE;
        else return FALSE;
    }

    // Opération "&&"
    public static QuadBool and(QuadBool a, QuadBool b) {
        if (a == ERROR || b == ERROR) return ERROR;
        if (a == TRUE){
            if(b == NOTHING) return ERROR;
            else return b;
        }
        if (a == FALSE) return FALSE;
        if (a == NOTHING) {
            if(b == FALSE) return FALSE;
            else return ERROR;
        }

        return ERROR; // au cas où
    }

    public static QuadBool xor(QuadBool a, QuadBool b){
        return AndQuad.And(OrQuad.Or(a, b),NotQuad.Not(AndQuad.And(a, b)));
    }
}