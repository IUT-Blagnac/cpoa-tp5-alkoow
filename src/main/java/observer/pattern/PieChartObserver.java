package observer.pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;

import observer.CourseRecord;
import observer.LayoutConstants;

/**
 * This class represents a bar chart view of a vector of data. Uses the Observer
 * pattern.
 */
@SuppressWarnings("serial")
public class PieChartObserver  extends ChartObserver {
	/**
	 * Creates a BarChartObserver object
	 * 
	 * @param data
	 *            a CourseData object to observe
	 */
	public PieChartObserver(CourseData data) {
		data.attach(this);
		this.courseRecord = data.getUpdate();
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseRecord.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.setBackground(Color.white);
	}

	/**
	 * Paint method
	 * 
	 * @param g
	 *            a Graphics object on which to paint
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		Rectangle area = this.getBounds();
		double total = 0.0D;
	      
	      for (int i = 0; i < courseRecord.size(); i++) {
	         total += courseRecord.elementAt(i).getNumOfStudents();
	      }
	      double curValue = 0.0D;
	      int startAngle = 0;
	      for (int i = 0; i < courseRecord.size(); i++) {
	         startAngle = (int) (curValue * 360 / total);
	         int arcAngle = (int) (courseRecord.elementAt(i).getNumOfStudents() * 360 / total);
	         g.setColor(LayoutConstants.courseColours[i]);
	         g.fillArc(area.width/2-area.height/2, area.y, area.height, area.height, startAngle, arcAngle);
	         
	         curValue += courseRecord.elementAt(i).getNumOfStudents();
	      }
	}

}