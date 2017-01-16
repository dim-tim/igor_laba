package mrnz;

import java.util.Scanner;

public class MRNZ {

    static int N;
    static Double[] X;
    static double h;
    static double[] korni;
    static double[] xi;
    //  static double[] xj;
    static double[] s;

    static void Y() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter [a,b]:");
        System.out.print("a = ");
        double a = scanner.nextDouble();
        System.out.print("b = ");
        double b = scanner.nextDouble();
        System.out.println("Enter number of iterations n:");
        N = scanner.nextInt();
        X = new Double[N + 1];
        h = (b - a) / N;
        for (int i = 0; i < X.length; i++) {
            X[i] = a + i * h;
        }
//        System.out.println("Методом средних прямоугольников");
//        System.out.println("S = " + metod_srednih_prjamoygolnikov(X, h) * h);
    }

    static double f(double x) {
        return (Math.pow(1 - Math.pow(x, 2), 2));
    }

    static double f1(double a) {
        return (Math.pow(1 - a * a, 2));
    }

    static double f2(double a, double b) {
        return (1 / (Math.pow(Math.PI * (a - b), 2) + 1));
    }

    static double metod_srednih_prjamoygolnikov(Double[] X, double h) {
        double S = 0;
        for (int i = 0; i < X.length - 1; i++) {
            S += f(X[i] + h / 2);
        }
        return S;
    }

    static void prost_itr() {
        double temp = 0;
        s = new double[32];
        xi = new double[32];
        korni = new double[32];
        s[0] = 0.0;
        for (int i = 1; i < 32; i++) {
            s[i] = i * (0.03125);
            //      System.out.println(s[i] + " " + i);   
        }
        for (int i = 0; i < 32; i++) {
            xi[i] = f1(s[i]);
            //  System.out.println(xi[i] + " " + i);
        }

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                temp += f2(s[i], s[j]);
                korni[i] = xi[i] + 0.8 * (0.55 * xi[i] - temp * 0.03125) * xi[i];
            }
        }
        for (int i = 0; i < 32; i++) {
            System.out.println(korni[i]);
        }
    }

    public static void main(String[] args) {
        Y();
        prost_itr();
    }
}
