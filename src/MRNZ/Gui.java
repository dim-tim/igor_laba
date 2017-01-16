package MRNZ;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame
{
    int width = 800, height = width * 9 / 16;

    public Gui()
    {
        super("MRNZ");
        // --------------
        setLayout(null);
        setSize(width, height);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(false);
        // ---------------------
    }

    public void start(double[] xVec, double[] X, double[] tVec)
    {

        XYSeries series = new XYSeries("Real function");
        XYSeries series1 = new XYSeries("Approximate function");

        for (int i = 1; i < xVec.length; i++)
        {
            series.add(tVec[i], X[i]);
            series1.add(tVec[i], xVec[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series1);

        NumberAxis domain = new NumberAxis("x");
        NumberAxis range = new NumberAxis("f(x)");
        XYSplineRenderer r = new XYSplineRenderer(3);
        XYPlot xyplot = new XYPlot(dataset, domain, range, r);
        JFreeChart chart = new JFreeChart(xyplot);
        ChartPanel chartPanel = new ChartPanel(chart)
        {

            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(640, 480);
            }
        };
        JFrame frame = new JFrame("MinimalStaticChart");

        frame.getContentPane().add(chartPanel);
        frame.setSize(400, 300);
        frame.show();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
