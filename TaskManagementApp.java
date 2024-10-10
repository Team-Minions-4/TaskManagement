import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class TaskManagementApp {
    private ArrayList<Task> tasks = new ArrayList<>();
    private JFrame frame;
    private JTextArea taskArea;
    private JTextField taskNameField;
    private JSpinner timeSpinner;
    private Image backgroundImage;

    public TaskManagementApp() {
        // Load background image (Replace the path with your image file path)
        try {
            backgroundImage = new ImageIcon("C:\\Users\\aniket\\OneDrive\\Desktop\\Java-Project\\TaskManagementApp\\src\\taskmanagementapp\\task-bg.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Background image not found. Using default color.");
        }

        // Main frame settings
        frame = new JFrame("Task Management Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);

        // Create a panel for the background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // Reduced spacing

        // Task name label and input field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Task Name:"), gbc);

        taskNameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(taskNameField, gbc);

        // Time label and spinner
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Time:"), gbc);

        // Time spinner (12-hour format)
        SpinnerDateModel model = new SpinnerDateModel();
        timeSpinner = new JSpinner(model);
        timeSpinner.setPreferredSize(new Dimension(120, 30));  // Bigger size
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
        timeSpinner.setEditor(timeEditor);
        gbc.gridx = 1;
        panel.add(timeSpinner, gbc);

        // Add task button
        JButton addButton = new JButton("Add Task");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        // Task area for displaying tasks
        taskArea = new JTextArea(10, 30);
        taskArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Delete task button
        JButton deleteButton = new JButton("Delete Task");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(deleteButton, gbc);

        // Mark as done button
        JButton markAsDoneButton = new JButton("Mark as Done");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(markAsDoneButton, gbc);

        // Exit button
        JButton exitButton = new JButton("Exit");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(exitButton, gbc);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();  // Call the method to delete task
            }
        });

        markAsDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markTaskAsDone();  // Call the method to mark task as done
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });

        // Adding Exit option in the menu (optional)
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Add task to the list and update task area
    private void addTask() {
        String name = taskNameField.getText();
        String time = new SimpleDateFormat("hh:mm a").format(timeSpinner.getValue());
        if (!name.isEmpty()) {
            tasks.add(new Task(name, time));
            refreshTaskList();
            taskNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Task name cannot be empty.");
        }
    }

    // Prompt for task number and delete the selected task
    private void deleteTask() {
        String taskNumberText = JOptionPane.showInputDialog(frame, "Enter Task Number to Delete:");
        if (taskNumberText != null && !taskNumberText.isEmpty()) {
            int taskNumber = Integer.parseInt(taskNumberText) - 1;  // Convert to zero-based index
            if (taskNumber >= 0 && taskNumber < tasks.size()) {
                tasks.remove(taskNumber);
                refreshTaskList();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid task number.");
            }
        }
    }

    // Prompt for task number and mark the selected task as done
    private void markTaskAsDone() {
        String taskNumberText = JOptionPane.showInputDialog(frame, "Enter Task Number to Mark as Done:");
        if (taskNumberText != null && !taskNumberText.isEmpty()) {
            int taskNumber = Integer.parseInt(taskNumberText) - 1;  // Convert to zero-based index
            if (taskNumber >= 0 && taskNumber < tasks.size()) {
                Task task = tasks.get(taskNumber);
                task.setDone(true);  // Mark the task as done
                refreshTaskList();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid task number.");
            }
        }
    }

    // Refresh the task list display
    private void refreshTaskList() {
        taskArea.setText("");
        for (int i = 0; i < tasks.size(); i++) {
            taskArea.append((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
    }

    // Main method
    public static void main(String[] args) {
        new TaskManagementApp();
    }
}
