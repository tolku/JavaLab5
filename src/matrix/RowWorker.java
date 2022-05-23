package matrix;

import pl.retsuz.Main;

public class RowWorker implements Runnable {
    IMatrix a, b;
    private int rowNubmer;

    public RowWorker(int rowNumber, IMatrix a, IMatrix b) {
        this.rowNubmer = rowNumber;
        this.a = a;
        this.b = b;
    }

    public void multiplyRow(IMatrix a, IMatrix b, int currentRow, IMatrix result) {
        int resultColumns = b.columnCount();
        int colCount = b.columnCount(), i, j, k;
        for (j = 0, i = currentRow; j < colCount; j++) {
            result.getData()[i][j] = 0;
            for (k = 0; k < resultColumns; k++)
                result.getData()[i][j] += a.getData()[i][k] * b.getData()[k][j];
        }
    }


    @Override
    public void run() {
        multiplyRow(a, b, rowNubmer, Main.d);
    }
}
