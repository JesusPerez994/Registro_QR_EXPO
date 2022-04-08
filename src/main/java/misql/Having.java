package misql;

import java.util.List;

public class Having extends Condicion{

    public Having(Columna col, Type tipo, Object... valores) {
        super(col, tipo, valores);
    }

    public Having(Columna col, Type tipo, Columna col2) {
        super(col, tipo, col2);
    }

    public Having(Columna col, Type tipo, List valores) {
        super(col, tipo, valores);
    }

    public Having(Columna col) {
        super(col);
    }
}
