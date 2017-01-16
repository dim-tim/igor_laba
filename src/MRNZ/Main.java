package MRNZ;

public class Main
{
	public static int m = 32;
	public static double a = 0.0;
	public static double b = 1.0;
	public static double h = (b - a) / m;
	public static double alpha = 0.8;
	public static double beta = 4.4;
	public static double delta1 = 1e-3;
	public static double delta2 = 1e-4;



	public static void main(String[] args)
	{
		double[] tVec = new double[m], xVec = new double[m], yVec = new double[m], X = new double[m];
		double[][] KMatrixix = new double[m][m];

		massiv(tVec, xVec, yVec, KMatrixix);
		double eps = 1.5 * delta1;
		int iter = 0;

		double www = 0.0;

		while ((www = norma(yVec, X, KMatrixix)) > eps)
		{
			//System.out.println(www);
			iter++;
			xVec = X.clone();
		X = Method3(xVec, yVec, KMatrixix, iter % 2 == 0 ? beta : alpha);
		//	X = Method4(xVec, yVec, KMatrixix);
			//X = Method2(xVec, yVec, KMatrixix);
			//X = Method22(xVec, yVec, KMatrixix);
		}
		System.out.println(iter);
		Gui obj = new Gui();
		massiv(tVec, xVec, yVec, KMatrixix);
		obj.start(X, xVec, tVec);
		for (int i = 0; i < m; i++)
		{
			System.out.printf("%6.5f\n", X[i]);
		}
	}

	public static double K(double t, double s)
	{
		// 1 � 3
		//return 1.0 / (1.0 + 100.0 * Math.pow(t - s + 0.0, 2.0));
		// 2
		return t <= s ? t * (1 - s) : s * (1 - t);
	}

	public static double x_totchnoe(double s)
	{
		//1
		 return s < 0.5 ? s : 1.0 - s;
		// 2 return s * (1 - s);
		// 3
		/*if (s >= 0 && s <= 0.25)
		{
			return 2 * s;
		}

		if (s > 0.25 && s <= 0.5)
		{
			return -s + 0.75;
		}

		if (s > 0.5 && s <= 0.75)
		{
			return s - 0.25;
		}

		if (s > 0.75 && s <= 1.0)
		{
			return -2 * s + 2;
		}
		return 0.0;*/
	}
	public static double norma(double[] xVec, double[] X)
	{
		double temp = 0.0;

		for (int i = 0; i < m; i++)
		{
			temp += Math.pow(X[i] - xVec[i], 2.0) * h;
		}

		return Math.sqrt(temp);
	}

	public static double norma(double[] Y, double[] X, double[][] KMatrixix)
	{
		double temp = 0.0, temp1 = 0.0;

		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < m; j++)
			{
				temp1 += KMatrixix[i][j] * X[j] * h;
			}
			temp1 -= Y[i];
			temp1 *= temp1 * h;
			temp += temp1;
			temp1 = 0.0;
		}

		return Math.sqrt(temp);
	}

	public static void massiv(double[] tVec, double[] xVec, double[] yVec, double[][] KMatrixix)
	{
		for (int i = 0; i < m; i++)
		{
			tVec[i] = i * h;
			xVec[i] = x_totchnoe(tVec[i]);
		}
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < m; j++)
			{
				KMatrixix[i][j] = K(tVec[i], tVec[j]);
			}
		}
		for (int i = 0; i < m; i++)
		{
			double temp = 0.0;
			for (int j = 0; j < m; j++)
			{
				temp += KMatrixix[i][j] * h * xVec[j];
			}
			yVec[i] = temp + 0.5 * delta2;
		}
	}

	public static double[] Method2(double[] xVec, double[] yVec, double[][] KMatrixix)
	{
		double[] X = new double[m];
		for (int i = 0; i < m; i++)
		{
			double temp = 0.0;
			for (int j = 0; j < m; j++)
			{
				temp += KMatrixix[i][j] * xVec[j] * h;
			}
			X[i] = xVec[i] + alpha * (-temp + yVec[i]);
		}
		return X;
	}

	public static double[] Method22(double[] xVec, double[] yVec, double[][] KMatrix)
	{
		double temp1, temp2, temp3, temp4 = 0.0;
		double[] X = new double[m];
		for (int i = 0; i < m - 1; i++)
		{
			temp1 = 0.0;
			for (int j = 0; j < m - 1; j++)
			{
				temp1 += KMatrix[i][j] * xVec[j] * h;
			}
			temp2 = 0.0;
			for (int j = 0; j < m - 1; j++)
			{
				temp2 += KMatrix[i][j] * yVec[j] * h;
			}
			temp4 = 0.0;
			for (int j = 0; j < m - 1; j++)
			{
				temp3 = 0.0;
				for (int k = 0; k < m - 1; k++)
				{
					temp3 += KMatrix[j][k] * h * xVec[k];
				}
				temp4 += KMatrix[i][j] * h * temp3;
			}
			X[i] = xVec[i] + 2 * alpha * (-temp1 + yVec[i]) + Math.pow(alpha, 2) * (temp4 - temp2);
		}
		return X;
	}

	public static double[] Method3(double[] xVec, double[] yVec, double[][] KMatrixix, double k)
	{
		double[] X = new double[m];
		for (int i = 0; i < m; i++)
		{
			double temp = 0.0;
			for (int j = 0; j < m; j++)
			{
				temp += KMatrixix[i][j] * xVec[j] * h;
			}
			X[i] = xVec[i] + k * (-temp + yVec[i]);
		}
		return X;
	}

	public static double[] Method4(double[] xVec, double[] yVec, double[][] KMatrixix)
	{
		double[] X = new double[m];
		for (int i = 0; i < m; i++)
		{
			double temp = 0.0;
			double temp2 = 0.0;
			for (int j = 0; j < m; j++)
			{
				double temp1 = 0.0;
				for (int k = 0; k < m; k++) {
					temp1 += KMatrixix[j][k] * xVec[k] * h;
				}
				temp += KMatrixix[i][j] * temp1 * h;
				temp2 += KMatrixix[i][j] * h * yVec[j];
			}
			X[i] = xVec[i] - alpha * temp + alpha * temp2;
		}
		return X;
	}

}
