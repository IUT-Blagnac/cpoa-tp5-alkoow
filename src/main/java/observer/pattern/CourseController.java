package observer.pattern;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import observer.CourseRecord;

/**
 * Presents a given view of a set of courses and their marks. Uses the Observer
 * pattern to be notified when a new course has been added.
 */
@SuppressWarnings("serial")
public class CourseController extends JPanel implements ChangeListener, ActionListener {
	/**
	 * Constructs a CourseController object
	 * 
	 * @param courses
	 *            a set of courses and their marks
	 */
	public CourseController(CourseData courses) {
		this.courseData = courses;
		this.sliders = new Vector<JSlider>();
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);

		coursePanel = new JPanel();
		coursePanel.setBorder(new TitledBorder("Courses"));
		coursePanel.setLayout(new GridLayout(0, 1));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		Vector<CourseRecord> state = courses.getUpdate();

		for (int i = 0; i < state.size(); i++)
			this.addCourse(state.elementAt(i));

		JScrollPane scrollPane = new JScrollPane(coursePanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JButton button = new JButton("New Courses");
		button.addActionListener(this);
		
		JButton buttonChart = new JButton("Pie Chart");
		
		CourseController controller = this;
		buttonChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JButton button = (JButton)e.getSource();
				JFrame frame = (JFrame)controller.getRootPane().getParent();
				ChartObserver graph;
				
				if(button.getText().contentEquals("Pie Chart")) {
					graph = new PieChartObserver(courseData);
					button.setText("Bar Chart");
				} else {
					graph = new BarChartObserver(courseData);
					button.setText("Pie Chart");
				}
				
				graphPanel .removeAll();
				
				graphPanel.add(new JScrollPane(graph,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
				
				frame.revalidate();
				frame.repaint();
			}
		});

		constraints.weightx = 0.5;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(scrollPane, constraints);

		constraints.weightx = 0.5;
		constraints.weighty = 0;
		constraints.gridy = 1;
		this.add(button, constraints);
		
		constraints.weightx = 0.5;
		constraints.weighty = 0;
		constraints.gridy = 2;
		this.add(buttonChart, constraints);
	}

	/**
	 * Add a new course
	 * 
	 * @param record
	 *            the new course record to be added
	 */
	public void addCourse(CourseRecord record) {
		JSlider slider = new JSlider();
		slider.setBackground(Color.white);
		slider.setName(record.getName());
		slider.setValue(record.getNumOfStudents());
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBorder(new TitledBorder(record.getName()));
		slider.addChangeListener(this);
		coursePanel.add(slider);
		coursePanel.revalidate();
		sliders.addElement(slider);
	}


	/**
	 * Manages the creation of a new course. Called when the "New Course" button is pressed.
	 * 
	 * @param arg0
	 *            not used
	 */
	public void actionPerformed(ActionEvent arg0) {
		String input = JOptionPane.showInputDialog("Please enter new course name:");
		if (input != null){
			courseData.addCourseRecord(new CourseRecord(input, 50));
			// leave it up notify/update mechanism to invoke this.addCourse
		}
	}

	/**
	 * Handles the changing of the marks for a course (changing of a JSlider)
	 * 
	 * @param arg0
	 *            the JSlider that has changed
	 */
	public void stateChanged(ChangeEvent arg0) {
		JSlider slider = (JSlider) arg0.getSource();
		courseData.changeCourseRecord(slider.getName(), slider.getValue());
	}

	/**
	 * Sets up an initial set of three courses
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		CourseData data = new CourseData();
		data.addCourseRecord(new CourseRecord("Physics", 50));
		data.addCourseRecord(new CourseRecord("Chemistry", 50));
		data.addCourseRecord(new CourseRecord("Biology", 50));

		CourseController controller = new CourseController(data);
		ChartObserver graph = new BarChartObserver(data);

		graphPanel = new JPanel();
				
		JScrollPane panel = new JScrollPane(graph,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		graphPanel.removeAll();

		graphPanel.add(panel);
		
		JFrame frame = new JFrame("Observer Pattern");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		frame.setResizable(false);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.25;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		frame.getContentPane().add(controller, constraints);
		constraints.weightx = 0.5;
		constraints.weighty = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		frame.getContentPane().add(graphPanel, constraints);
		frame.pack();
		frame.setVisible(true);
	}

	private CourseData courseData;

	private Vector<JSlider> sliders;

	private JPanel coursePanel;
	
	private static JPanel graphPanel;
	
}