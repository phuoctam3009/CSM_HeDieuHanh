/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doangiaihept;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static doangiaihept.DoAnGiaiHePt.values;

/**
 *
 * @author ADMIN
 */
public class DoAnGiaiHePt {

    public double[][] loadData(String filePath) throws InterruptedException {
        double matrix[][] = null;
        Path path = Paths.get(filePath);
        Charset charset = Charset.forName("US-ASCII");
        try {
            BufferedReader reader = Files.newBufferedReader(path, charset);
            String line0 = reader.readLine(); // skip a first line
            String[] s = line0.split(" ");
            matrix = new double[Integer.parseInt(s[0])][Integer.parseInt(s[1])];
            String line1 = null;
            int count = 0;
            while ((line1 = reader.readLine()) != null) {
                String[] readElemnents = line1.split(" ");
                for (int i = 0; i < readElemnents.length; i++) {
                    matrix[count][i] = Double.parseDouble(readElemnents[i]);
                }
                count++;
            }
        } catch (Exception e) {
            System.out.println("");
            System.out.println(e.getMessage());
        }
        return matrix;
    }
    static double values[][] = new double[4][5];

    public static void main(String[] args) throws InterruptedException {
//        DoAnHDH dahdh = new DoAnHDH();
//        double values[][] = dahdh.loadData("D:\\DoAnHDH\\matrix.txt");
//        float values[][] ={{2,2,3,2,-2}, {0, 2, 0, 1, 0}, {4, -3, 0, 1, -7}, {6, 1, -6, -5, 6}};
        System.out.println("Giải hệ phương trình:");
        for (int i = 0; i < values.length; i++) {
            // do the for in the row according to the column size
            for (int j = 0; j < values[i].length; j++) {
                // multiple the random by 10 and then cast to in
                values[i][j] = ((int) (Math.random() * 10));
                System.out.print(values[i][j] + " ");
            }
            // add a new line
            System.out.println();
        }
        for (int k = 0; k < values.length; k++) {
            //Check array[k][k] == 0
            if (values[k][k] == 0 || Math.abs(values[k][k]) < 1.0E-12) {
                values[k][k] = 0;
                for (int i = k + 1; i < values.length; i++) {
                    if (values[i][k] != values[k][k]) {
                        for (int j = 0; j < values[0].length; j++) {
                            double temp = values[k][j];
                            values[k][j] = values[i][j];
                            values[i][j] = temp;
                        }
                        break;
                    }
                }
            }
            if (k == values.length - 1 && Math.abs(values[k][k]) < 1.0E-12) {
                if (Math.abs(values[k][k + 1]) < 1.0E-12) {
                    System.out.println("Phương trình có vô số nghiệm");
                } else {
                    System.out.println("Phương trình vô nghiệm");
                }
                return;
            }
            //Division of the pivot row
            double pivot = values[k][k];
            for (int j = 0; j < values[0].length; j++) {
                values[k][j] /= pivot;
            }

            //Elimination loop
            //Thread
            for (int i = 0; i < values.length; i++) {
                if (i == k) {
                    continue;
                } else {
                    processRow thread = new processRow(i, k, values[i][k]);
                    thread.start();
                    thread.join();
                }
            };
        }

        System.out.println("Kết quả là: ");
        for (int i = 0; i < values.length; i++) {
            // do the for in the row according to the column size
            if (Math.abs(values[i][values[0].length - 1]) < 1.0E-12) {
                System.out.println("x[" + i + "] = " + 0);
            } else {
                System.out.println("x[" + i + "] = " + values[i][values[0].length - 1]);
            }

        }
    }

}

class processRow extends Thread {

    private int row;
    private int rowDefault;
    private double factor;

    public processRow(int row, int k, double factor) {
        this.row = row;
        this.rowDefault = k;
        this.factor = factor;
    }

    public void run() {
        for (int i = 0; i < values[0].length; i++) {
            values[this.row][i] -= this.factor * values[this.rowDefault][i];
        }
//        System.out.println("Đã xong thread");
    }

}
