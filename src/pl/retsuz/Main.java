package pl.retsuz;

import matrix.IMatrix;
import matrix.Matrix;
import matrix.RowWorker;
import matrix.generators.DefaultGenerator;

import java.util.Date;

public class Main {
    static IMatrix a;
    static IMatrix b;
    static IMatrix c;
    public static IMatrix d;

    public static void main(String[] args) {
        try {
            int n = 3000, m = 3000;
            System.out.println("Generuję macierze...");
            a = DefaultGenerator.generateRandomMatrix(m, n, 0, 3);
            b = DefaultGenerator.generateRandomMatrix(m, n, 0, 3);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Mnożę macierze klasycznie...");
        Date start = new Date();
        c = IMatrix.multiply(a, b);
        Date end = new Date();
        System.out.println("Czas mnożenia w milisekundach: " + (end.getTime() - start.getTime()));


        d = new Matrix(a.rowCount(), b.columnCount());
        Runnable[] tab = new Runnable[Runtime.getRuntime().availableProcessors()];
        Thread[] threads = new Thread[tab.length];
        int rowsCounted = 0;

        System.out.println("Mnożę macierze współbieżnie...");
        Date start1 = new Date();

        while (true) {
            for (int counter = 0; counter < tab.length; ++counter) {
                if (rowsCounted == a.rowCount()) {
                    break;
                }
                tab[counter] = new RowWorker(rowsCounted, a, b);
                threads[counter] = new Thread(tab[counter]);
                threads[counter].start();
                rowsCounted++;
            }
            if (rowsCounted == a.rowCount()) {
                break;
            }
        }

        Date end1 = new Date();
        System.out.println("Czas mnożenia w milisekundach: " + (end1.getTime() - start1.getTime()));
    }
}
