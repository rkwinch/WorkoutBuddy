package finalproject.GUI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * A JPanel used to display a very simplistic Bar Graph by drawing
 * rectangles to represent lengths.
 * @author Robin Ouzts 
 */
public class SimpleBarPanel extends JPanel {

    /**
     *
     * Color used for the background.
     */
    private static final Color BACKGROUND_COLOR = Color.white;

    /**
     *
     * Color used for the individual bars (Go Tigers!)
     */
    private static final Color BAR_COLOR = new Color(245, 102, 0);

    /**
     *
     * Color used for the individual bars (Go Tigers!)
     */
    private static final Color BAR_COLORB = new Color(82, 45, 128);

    /**
     *
     * Color used for solid lines.
     */
    private static final Color LINE_COLOR = Color.black;

    /**
     *
     * Type of X-Axis used.
     */
    private AxisType axis_type;

    /**
     *
     * The width of each bar.
     */
    private final int barWidth = 30;

    /**
     *
     * The first user's set of data.
     */
    private ArrayList<Integer> inputData = new ArrayList<>();

    /**
     *
     * The second user's set of data.
     */
    private ArrayList<Integer> inputDataB = new ArrayList<>();

    /**
     *
     * Constructs a bar chart from at least one user's data, represented
     * as a frequency count of workouts, an optional second user's data,
     * and a type of X-axis.
     * @param inputData The first user's frequency data.
     * @param inputDataB An optional second user's frequency data.
     * @param axis_type The type of x axis to plot.
     */
    public SimpleBarPanel(ArrayList<Integer> inputData, ArrayList<Integer> inputDataB, AxisType axis_type) {
        for (Integer i : inputData) {
            this.inputData.add(i);
        }
        for (Integer i : inputDataB) {
            this.inputDataB.add(i);
        }

        int numBars = Math.max(inputData.size(), inputDataB.size());
        setSize(120 * numBars, 600);
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.axis_type = axis_type;
    }

    /**
     *
     * Draws bars on the panel.
     * @param OUTER_MARGIN The amount, in pixels, to draw from the edges.
     * @param UPPER_HEIGHT The height of the bar region (not the same as the height of the panel)
     * @param FT_SIZE An offset from the footer to allow for labels.
     * @param g A Graphics object.
     * @param g2d A Graphics2D object.
     * @throws RuntimeException If neither user has any data.
     */
    private void DrawBars(int OUTER_MARGIN, int UPPER_HEIGHT, final int FT_SIZE, final Graphics g, Graphics2D g2d) throws RuntimeException {
        if (inputDataB.size() == 0 && inputData.size() != 0) {
            for (int i = 0; i < inputData.size(); i++) {
                final int x = OUTER_MARGIN + 45 * i;
                final int barHeight = 10 * inputData.get(i);
                final int y = UPPER_HEIGHT - barHeight - FT_SIZE;
                g.fillRect(x, y, barWidth, barHeight);
                g2d.drawString(axis_type.getLabels().get(i), x, UPPER_HEIGHT);
            }
        } else if (inputDataB.size() != 0 && inputData.size() != 0) {
            for (int i = 0; i < inputData.size(); i++) {
                final int x = (OUTER_MARGIN + 45 * i) * 2;
                final int barHeight = 10 * inputData.get(i);
                final int y = UPPER_HEIGHT - barHeight - FT_SIZE;
                g.fillRect(x, y, barWidth, barHeight);
                g.drawString(axis_type.getLabels().get(i), x, UPPER_HEIGHT - 5);
            }
            g2d.setColor(BAR_COLORB);
            for (int i = 0; i < inputDataB.size(); i++) {
                final int x = (OUTER_MARGIN + 45 * i) * 2 + barWidth;
                final int barHeight = 10 * inputDataB.get(i);
                final int y = UPPER_HEIGHT - barHeight - FT_SIZE;
                g.fillRect(x, y, barWidth, barHeight);
            }
        } else {
            throw new RuntimeException("Both users have no data!");
        }
    }

    /**
     *
     * Draws axis labels below the bar chart.
     * @param g2d A Graphics2Dobject for drawing with.
     */
    private void DrawLabels(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AffineTransform orig = g2d.getTransform();
        AffineTransform rot = new AffineTransform();
        rot.setToRotation(Math.toRadians(270), 30, 2 * getHeight() / 3);
        g2d.setTransform(rot);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g2d.drawString("Number of Workouts", 0, 2 * getHeight() / 3);
        g2d.setTransform(orig);

        g2d.drawString("Date", getWidth() / 2, getHeight() - 10);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        g2d.setColor(Color.RED);
    }
    /**
     *
     * Draws the overall bar chart.
     * @param g A graphics object for drawing with.
     */
    private void DrawPlot(final Graphics g) {
        int OUTER_MARGIN = 60;
        int UPPER_HEIGHT = getHeight() - 50;
        final int FT_SIZE = 20;
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(75, 40, getWidth(), UPPER_HEIGHT);
        g.setColor(BAR_COLOR);
        Graphics2D g2d = (Graphics2D) g;
        
        DrawLabels(g2d);
        DrawBars(OUTER_MARGIN, UPPER_HEIGHT, FT_SIZE, g, g2d);
        DrawTickMarks(g2d, UPPER_HEIGHT-FT_SIZE);
    }

    /**
     *
     * Draws Tick Marks on the Y-Axis at regular intervals.
     * @param g2d A Graphics2D object for drawing with.
     * @param UPPER_HEIGHT The height of the bar region.
     */
    private void DrawTickMarks(Graphics2D g2d, int UPPER_HEIGHT) {
        Line2D lin = new Line2D.Float(65, 0, 65, UPPER_HEIGHT + 40);
        g2d.setPaint(LINE_COLOR);
        //2s tick mark
        for (int i = UPPER_HEIGHT + 40; i > 0; i -= 20) {
            Line2D tick = new Line2D.Float(55, i, 75, i);
            g2d.draw(tick);
        }
        //1s tick mark
        for (int i = UPPER_HEIGHT + 40; i > 0; i -= 10) {
            Line2D tick = new Line2D.Float(60, i, 70, i);
            g2d.draw(tick);
        }
        //10s tick mark
        for (int i = UPPER_HEIGHT; i > 0; i -= 100) {
            Line2D tick = new Line2D.Float(35, i, 95, i);
            g2d.draw(tick);
        }
        //5s tick mark
        for (int i = UPPER_HEIGHT; i > 0; i -= 50) {
            Line2D tick = new Line2D.Float(45, i, 85, i);
            g2d.draw(tick);
        }
        Line2D bottom = new Line2D.Float(55, UPPER_HEIGHT, getWidth() + 95, UPPER_HEIGHT);

        g2d.draw(lin);

        g2d.draw(bottom);
        //drawing y axis #s
        g2d.drawString("0", 20, UPPER_HEIGHT + 5);
        g2d.drawString("5", 20, UPPER_HEIGHT + 5 - 50);
        Integer j = new Integer(0);
        for (int i = UPPER_HEIGHT + 5 - 50; i >= 0; i -= 50) {
            j = new Integer(j + 5);
            String freq = Integer.toString(j);
            g2d.drawString(freq, 20, i);
        }
    }


    /**
     * Overridden method to paint this component.  Draws the bar chart
     * each time it is called.
     * @param g  A Graphics object for drawing with.
     */
@Override
        protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        DrawPlot(g);
    }
}
