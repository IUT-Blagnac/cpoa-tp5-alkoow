package observer.pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import observer.CourseRecord;
import observer.LayoutConstants;

public abstract class ChartObserver extends JPanel implements Observer<CourseRecord> {
	
	public ChartObserver() {}
	
	/**
	 * Creates a BarChartObserver object
	 * 
	 * @param data
	 *            a CourseData object to observe
	 */
	public ChartObserver(CourseData data) {
		data.attach(this);
		this.courseRecord = data.getUpdate();
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseRecord.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.setBackground(Color.white);
	}
	
	/**
	 * Informs this observer that the observed CourseData object has changed
	 * 
	 * @param o
	 *            the observed CourseData object that has changed
	 */
	public void update(Observable<CourseRecord> o, CourseRecord record) {
		
	boolean found = false;
		for(CourseRecord rec : courseRecord) {
			if(rec.equals(record)) {
				rec.setNumOfStudents(record.getNumOfStudents());
				found = true;
				break;
			}
		}
		
		if(!found) {
			this.courseRecord.add(record);
		}
		
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseRecord.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.revalidate();
		this.repaint();
	}
	
	protected Vector<CourseRecord> courseRecord;

}
