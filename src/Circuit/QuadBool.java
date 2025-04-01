package Circuit;

public enum QuadBool {
    TRUE, FALSE, ERROR, NOTHING;

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
        return (a == TRUE || b == TRUE) ? TRUE : FALSE;
    }

    // Opération "&&"
    public static QuadBool and(QuadBool a, QuadBool b) {
        if (a == ERROR || b == ERROR) return ERROR;
        if (a == TRUE) return (b == NOTHING) ? ERROR : b;
        if (a == FALSE) return FALSE;
        if (a == NOTHING) return (b == FALSE) ? FALSE : ERROR;

        return ERROR; // au cas où
    }
}

