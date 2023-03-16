package com.assignment.view;

import com.assignment.controller.AltTabStopper;
import com.assignment.controller.StudentController;
import com.assignment.model.CourseDetail;
import com.assignment.model.ExamSubject;
import com.assignment.model.Student;
import com.assignment.model.StudentDetail;
import com.lib.XFile;
import com.lib.XUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class MainGUI extends JFrame {
    private JButton btnLogout;
    private JPanel mainPanel;
    private JPanel answerSheet;
    private JComboBox cbChooseCourse;
    private JButton btnConfirmExam;
    private JLabel lbNameStudent;
    private JEditorPane editorPaneShowQuestion;
    private JLabel lbErrorChooseCourse;
    private JLabel lbErrorExamDate;
    private JButton btnSubmit;
    private JLabel lbErrorScore;
    private JScrollPane answerSheetScrollPane;
    private JScrollPane showQuestionScrollPane;
    StudentController studentController;
    String fileExamSubjectPath = "src/com/assignment/file/examSubject.dat";
    String fileStudentDetailPath = "src/com/assignment/file/studentDetail.dat";
    String fileCourseDetailPath = "src/com/assignment/file/courseDetail.dat";
    String fileStudentPath = "src/com/assignment/file/student.dat";
    int numberQuestion = 0;
    Map<Integer, String> chooseAnswer;
    Map<Integer, String> correctAnswer;
    double score;
    AltTabStopper altTabStopper;

    public MainGUI(String title, String user) throws HeadlessException {
        super(title);
        answerSheetScrollPane.setPreferredSize(new Dimension(40, -1));
        showQuestionScrollPane.setPreferredSize(new Dimension(1000, -1));
        chooseAnswer = new TreeMap<>();
        correctAnswer = new TreeMap<>();
        this.setResizable(false);
        this.setContentPane(mainPanel);
        //this.setSize(1500,800);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        lbNameStudent.setText("Welcome " + user);

        studentController = new StudentController((List<StudentDetail>) XFile.readObject(fileStudentDetailPath),
                                                    (List<ExamSubject>) XFile.readObject(fileExamSubjectPath),
                                                    (DefaultComboBoxModel) cbChooseCourse.getModel(),
                                                    (List<Student>) XFile.readObject(fileStudentPath),
                                                    (List<CourseDetail>) XFile.readObject(fileCourseDetailPath));

        cbChooseCourse.setModel(studentController.getModelComboChooseCourse());

        for (StudentDetail studentDetail : studentController.getListStudentDetail()) {
            for (ExamSubject examSubject : studentController.getListExamSubject()) {
                if (studentDetail.getStudent().getUsername().equals(user) && studentDetail.getCourse().getName().equals(examSubject.getCourse().getName())){
                    studentController.getModelComboChooseCourse().addElement(studentDetail.getCourse().getName());
                }
            }
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logoutStudent(user);
            }
        });
        btnConfirmExam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExam(user);
            }
        });

        btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btnLogout.isEnabled()) {
                    logoutStudent(user);
                }
            }
        });

        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btnSubmit.isEnabled()) {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Are you sure to submit", "Submit",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        try {
                            Set<Integer> numberOfSentences = new TreeSet<>();
                            numberOfSentences.addAll(chooseAnswer.keySet());
                            numberOfSentences.addAll(correctAnswer.keySet());

                            int numberOfCorrectSentences = 0;
                            for (Integer temp : numberOfSentences) {
                                String cAnswer = chooseAnswer.get(temp);
                                String corrAnswer = correctAnswer.get(temp);
                                if (cAnswer.equals(corrAnswer)) {
                                    numberOfCorrectSentences++;
                                }
                            }
                            score = numberOfCorrectSentences * (10.0 / numberOfSentences.size());
                            JOptionPane.showMessageDialog(e.getComponent(), "You are correct " + numberOfCorrectSentences + " sentences\nYou reach " + score + " score");

                            answerSheet.setVisible(false);
                            btnSubmit.setEnabled(false);
                            btnLogout.setEnabled(true);

                            studentController.editStudentDetail(user, (String) cbChooseCourse.getSelectedItem(), score);
                            XFile.writeObject(fileStudentDetailPath, studentController.getListStudentDetail());

                            altTabStopper.stop();
                        } catch (NullPointerException er) {
                            JOptionPane.showMessageDialog(e.getComponent(), "Please choose all answer");
                        }
                    }
                }
            }
        });
        editorPaneShowQuestion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                editorPaneShowQuestion.getCaret().setVisible(true);
            }
        });
    }

    StringBuilder showQuestion = new StringBuilder();

    private void confirmExam(String user) {
        if (cbChooseCourse.getSelectedIndex() == 0) {
            lbErrorChooseCourse.setVisible(true);
            lbErrorExamDate.setVisible(false);
        } else {
            lbErrorChooseCourse.setVisible(false);
            lbErrorExamDate.setVisible(false);
        }

        for (ExamSubject examSubject : studentController.getListExamSubject()) {
            if (examSubject.getCourse().getName().equals(cbChooseCourse.getSelectedItem()) &&
                    XUtils.convertDatetoString(examSubject.getExamDate()).compareTo(LocalDate.now().toString()) != 0) {
                lbErrorExamDate.setVisible(true);
                break;
            }
        }

        boolean flag = false;
        for (StudentDetail studentDetail : studentController.getListStudentDetail()) {
            if (studentDetail.getStudent().getUsername().equals(user) && studentDetail.getScore() > 0.0 &&
                    studentDetail.getCourse().getName().equals(cbChooseCourse.getSelectedItem())) {
                lbErrorScore.setVisible(true);
                flag = true;
                break;
            }
        }

        if (!flag) {
            lbErrorScore.setVisible(false);
        }

        if (!lbErrorChooseCourse.isVisible() && !lbErrorExamDate.isVisible() && !lbErrorScore.isVisible()) {
            for (ExamSubject examSubject : studentController.getListExamSubject()) {
                if (cbChooseCourse.getSelectedItem().toString().equals(examSubject.getCourse().getName())) {
                    JSONArray list = (JSONArray) examSubject.getQuestionBanks();
                    list.forEach( emp -> parseQuesBankObject((JSONObject) emp ));

                    editorPaneShowQuestion.setText(showQuestion.toString());

                    answerSheet.setVisible(true);

                    btnSubmit.setEnabled(true);
                    cbChooseCourse.setEnabled(false);
                    lbErrorExamDate.setVisible(false);
                    lbErrorChooseCourse.setVisible(false);
                    btnConfirmExam.setEnabled(false);
                    btnLogout.setEnabled(false);

                    altTabStopper = AltTabStopper.create(this);
                }
            }
        }
    }

    private void parseQuesBankObject(JSONObject emp) {
        Long id = (Long) emp.get("id");
        showQuestion.append("Question ").append(id);

        String question = (String) emp.get("question");
        showQuestion.append(": ").append(question).append("\n");

        String option1 = (String) emp.get("option 1");
        showQuestion.append("A. ").append(option1).append("\n");

        String option2 = (String) emp.get("option 2");
        showQuestion.append("B. ").append(option2).append("\n");

        String option3 = (String) emp.get("option 3");
        showQuestion.append("C. ").append(option3).append("\n");

        String option4 = (String) emp.get("option 4");
        showQuestion.append("D. ").append(option4).append("\n\n");

        String correct = (String) emp.get("correct");

        correctAnswer.put(Math.toIntExact(id), correct);
    }

    private void logoutStudent(String user) {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure to logout", "Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION){
            JFrame frame = new Login("Login", user);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose();
        }
    }

    int num;
    String ans;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        answerSheet = new JPanel(new GridLayout(40,5));
        for (int i = 1; i <= 40; i++) {
            JLabel q = new JLabel(String.valueOf(i));
            answerSheet.add(q);

            JRadioButton op1 = new JRadioButton();
            op1.setActionCommand(q.getText()+"A");
            op1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().length() == 2) {
                        num = Integer.parseInt(String.valueOf(e.getActionCommand().charAt(0)));
                        ans = String.valueOf(e.getActionCommand().charAt(1));
                    } else {
                        String n1 = String.valueOf(e.getActionCommand().charAt(0));
                        String n2 = String.valueOf(e.getActionCommand().charAt(1));
                        num = Integer.parseInt(n1.concat(n2));
                        ans = String.valueOf(e.getActionCommand().charAt(2));
                    }

                    if (chooseAnswer.containsKey(num)) {
                        chooseAnswer.replace(num, ans);
                    } else {
                        chooseAnswer.put(num, ans);
                    }
                }
            });

            JRadioButton op2 = new JRadioButton();
            op2.setActionCommand(q.getText()+"B");
            op2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().length() == 2) {
                        num = Integer.parseInt(String.valueOf(e.getActionCommand().charAt(0)));
                        ans = String.valueOf(e.getActionCommand().charAt(1));
                    } else {
                        String n1 = String.valueOf(e.getActionCommand().charAt(0));
                        String n2 = String.valueOf(e.getActionCommand().charAt(1));
                        num = Integer.parseInt(n1.concat(n2));
                        ans = String.valueOf(e.getActionCommand().charAt(2));
                    }

                    if (chooseAnswer.containsKey(num)) {
                        chooseAnswer.replace(num, ans);
                    } else {
                        chooseAnswer.put(num, ans);
                    }
                    chooseAnswer.put(num, ans);
                }
            });

            JRadioButton op3 = new JRadioButton();
            op3.setActionCommand(q.getText()+"C");
            op3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().length() == 2) {
                        num = Integer.parseInt(String.valueOf(e.getActionCommand().charAt(0)));
                        ans = String.valueOf(e.getActionCommand().charAt(1));
                    } else {
                        String n1 = String.valueOf(e.getActionCommand().charAt(0));
                        String n2 = String.valueOf(e.getActionCommand().charAt(1));
                        num = Integer.parseInt(n1.concat(n2));
                        ans = String.valueOf(e.getActionCommand().charAt(2));
                    }

                    if (chooseAnswer.containsKey(num)) {
                        chooseAnswer.replace(num, ans);
                    } else {
                        chooseAnswer.put(num, ans);
                    }
                    chooseAnswer.put(num, ans);
                }
            });

            JRadioButton op4 = new JRadioButton();
            op4.setActionCommand(q.getText()+"D");
            op4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().length() == 2) {
                        num = Integer.parseInt(String.valueOf(e.getActionCommand().charAt(0)));
                        ans = String.valueOf(e.getActionCommand().charAt(1));
                    } else {
                        String n1 = String.valueOf(e.getActionCommand().charAt(0));
                        String n2 = String.valueOf(e.getActionCommand().charAt(1));
                        num = Integer.parseInt(n1.concat(n2));
                        ans = String.valueOf(e.getActionCommand().charAt(2));
                    }

                    if (chooseAnswer.containsKey(num)) {
                        chooseAnswer.replace(num, ans);
                    } else {
                        chooseAnswer.put(num, ans);
                    }
                    chooseAnswer.put(num, ans);
                }
            });

            ButtonGroup gr = new ButtonGroup();
            gr.add(op1);
            gr.add(op2);
            gr.add(op3);
            gr.add(op4);
            answerSheet.add(op1);
            answerSheet.add(op2);
            answerSheet.add(op3);
            answerSheet.add(op4);
        }
    }
}
