package com.assignment.view;

import com.assignment.controller.AdminController;
import com.assignment.controller.DataLabelFormat;
import com.assignment.model.*;
import com.lib.XFile;
import com.lib.XUtils;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminGUI extends JFrame {
    private JPanel adminPanel;
    private JTabbedPane tabbedPaneAdmin;
    private JTextField txtUsernameTeacher;
    private JTextField txtCourseID;
    private JTextField txtCourseName;
    private JButton btnNewCourse;
    private JButton btnAddCourse;
    private JButton btnUpdateCourse;
    private JButton btnDeleteCourse;
    private JTable tbCourse;
    private JTextField txtPasswordTeacher;
    private JTextField txtFullnameTeacher;
    private JDatePickerImpl JDatePickerTeacher;
    private JRadioButton rdMale;
    private JRadioButton rdFemale;
    private JTextField txtEmailTeacher;
    private JTable tbTeacher;
    private JButton btnNewTeacher;
    private JButton btnAddTeacher;
    private JButton btnUpdateTeacher;
    private JButton btnDeleteTeacher;
    private JComboBox cbCourse;
    private JLabel lbName;
    private JComboBox cbLecturer;
    private JDatePickerImpl JDatePickerCourseFrom;
    private JDatePickerImpl JDatePickerCourseTo;
    private JButton btnNewCourseDetail;
    private JButton btnAddCourseDetail;
    private JButton btnUpdateCourseDetail;
    private JButton btnDeleteCourseDetail;
    private JTable tbCourseDetail;
    private JLabel lbErrorCourseID;
    private JLabel lbErrorCourseName;
    private JLabel lbErrorUsernameTeacher;
    private JLabel lbErrorPasswordTeacher;
    private JLabel lbErrorFullnameTeacher;
    private JLabel lbErrorEmailTeacher;
    private JLabel lbErrorDOBTeacher;
    private JLabel lbErrorGenderTeacher;
    private JLabel lbErrorEndDate;
    private JLabel lbErrorStartDate;
    private JLabel lbErrorChooseCourse;
    private JLabel lbErrorChooseLecturer;
    private JButton btnLogoutAdmin;
    private JComboBox cbPosition;
    private JLabel lbErrorPositionTeacher;
    private JComboBox cbSearchCourse;
    private JButton btnSearchCourse;
    private JTextField txtSearchCourse;
    private JLabel lbErrorSeachCourse;
    private JComboBox cbSearchTeacher;
    private JTextField txtSearchTeacher;
    private JButton btnSearchTeacher;
    private JLabel lbErrorSearchTeacher;
    private JComboBox cbSeachCourseDetail;
    private JTextField txtSeachCourseDetail;
    private JButton btnSeachCourseDetail;
    private JLabel lbErrorSeachCourseDetail;
    AdminController adminController;
    String fileCoursePath = "src/com/assignment/file/course.dat";
    String fileTeacherPath = "src/com/assignment/file/teacher.dat";
    String fileCourseDetailPath = "src/com/assignment/file/courseDetail.dat";
    String fileExamSubjectPath = "src/com/assignment/file/examSubject.dat";
    String fileStudentDetailPath = "src/com/assignment/file/studentDetail.dat";
    int rowCourse = -1;
    int rowTeacher = -1;
    int rowCourseDetail = -1;
    String chooseUsername;
    String chooseCourse;
    String position;

    public AdminGUI(String title, String user) throws HeadlessException {
        super(title);
        this.setResizable(false);
        this.setContentPane(adminPanel);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        lbName.setText("Welcome " + user);

        tbCourse.setModel(new DefaultTableModel(
                new Object[][]{ },
                new String[]{
                        "ID", "Name"
                }
        ));

        tbTeacher.setModel(new DefaultTableModel(
                new Object[][]{ },
                new String[]{
                        "Username", "Password", "Fullname", "DOB", "Gender", "Email", "Position"
                }
        ));

        tbCourseDetail.setModel(new DefaultTableModel(
                new Object[][]{ },
                new String[]{
                        "Username", "Full name", "Course ID", "Course name", "From", "To"
                }
        ));

        //Read file when starting program
        adminController = new AdminController((DefaultTableModel) tbCourse.getModel(),
                                                (List<Course>) XFile.readObject(fileCoursePath),
                                                (DefaultComboBoxModel) cbCourse.getModel(),
                                                (DefaultTableModel) tbTeacher.getModel(),
                                                (List<Teacher>) XFile.readObject(fileTeacherPath),
                                                (DefaultComboBoxModel) cbLecturer.getModel(),
                                                (DefaultTableModel) tbCourseDetail.getModel(),
                                                (List<CourseDetail>) XFile.readObject(fileCourseDetailPath),
                                                (List<ExamSubject>) XFile.readObject(fileExamSubjectPath),
                                                (List<StudentDetail>) XFile.readObject(fileStudentDetailPath));
        cbCourse.setModel(adminController.getModelComboCourse());
        cbLecturer.setModel(adminController.getModelComboTeacher());
        adminController.fillToCourseTable();
        adminController.fillToTeacherTable();
        adminController.fillToCourseDetailTable();

        btnAddCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCourse();
            }
        });
        btnNewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newCourse();
            }
        });
        btnUpdateCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourse();
            }
        });
        tbCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbCourseClick();
            }
        });
        btnDeleteCourse.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowCourse == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the course!");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        for (Course course : adminController.getListCourse()) {
                            if (course.getCourseID().equals(txtCourseID.getText())) {
                                cbCourse.removeItem(course.getName());
                            }
                        }
                        //remove
                        adminController.deleteCourse(txtCourseID.getText());
                        //fill to table
                        adminController.fillToCourseTable();
                        //save to file
                        XFile.writeObject(fileCoursePath, adminController.getListCourse());

                        adminController.deleteCourseInCDetail(txtCourseID.getText());
                        adminController.fillToCourseDetailTable();
                        XFile.writeObject(fileCourseDetailPath, adminController.getListCourseDetail());

                        adminController.deleteCourseInExSubject(txtCourseID.getText());
                        XFile.writeObject(fileExamSubjectPath, adminController.getListExamSubject());

                        adminController.deleteCourseInStDetail(txtCourseID.getText());
                        XFile.writeObject(fileStudentDetailPath, adminController.getListStudentDetail());

                        newCourse();
                    }
                }
            }
        });
        btnNewTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTeacher();
            }
        });
        btnAddTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTeacher();
            }
        });
        tbTeacher.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbTeacherClick();
            }
        });

        btnUpdateTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeacher();
            }
        });
        btnDeleteTeacher.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowTeacher == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the teacher");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        cbLecturer.removeItem(txtUsernameTeacher.getText());
                        //remove
                        adminController.deleteTeacher(txtUsernameTeacher.getText());
                        //fill to table
                        adminController.fillToTeacherTable();
                        //save to file
                        XFile.writeObject(fileTeacherPath, adminController.getListTeacher());

                        adminController.deleteTeacherInCDetail(txtUsernameTeacher.getText());
                        adminController.fillToCourseDetailTable();
                        XFile.writeObject(fileCourseDetailPath, adminController.getListCourseDetail());
                        newTeacher();
                    }
                }
            }
        });
        btnNewCourseDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newCourseDetail();
            }
        });
        cbLecturer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getUsernameFromCombo();
            }
        });
        cbCourse.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getCourseFromCombo();
            }
        });
        btnAddCourseDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCourseDetail();
            }
        });
        tbCourseDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbCourseDetailClick();
            }
        });
        btnUpdateCourseDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCourseDetail();
            }
        });
        btnDeleteCourseDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //confirm
                if (rowCourseDetail == -1) {
                    JOptionPane.showMessageDialog(e.getComponent(), "Please choose the course detail");
                } else {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(), "Do you want to remove it", "Remove",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION){
                        //remove
                        adminController.deleteCourseDetail(chooseUsername, chooseCourse);
                        //fill to table
                        adminController.fillToCourseDetailTable();
                        //save to file
                        XFile.writeObject(fileCourseDetailPath, adminController.getListCourseDetail());
                        newCourseDetail();
                    }
                }
            }
        });
        btnLogoutAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                logoutAdmin(user);
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logoutAdmin(user);
            }
        });
        cbPosition.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getPositionFromCombo();
            }
        });
        btnSearchCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCourse();
            }
        });
        tabbedPaneAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                tbCourse.clearSelection();
                tbTeacher.clearSelection();
                tbCourseDetail.clearSelection();
            }
        });
        btnSearchTeacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTeacher();
            }
        });
        btnSeachCourseDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCourseDetail();
            }
        });
//        txtFullnameTeacher.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusLost(FocusEvent e) {
//                String[] name = txtFullnameTeacher.getText().split(" ");
//                StringBuilder username = new StringBuilder(name[name.length - 1].toLowerCase());
//
//                for (int i = 0; i < name.length - 1; i++) {
//                    String temp = name[i];
//                    username.append(temp.toLowerCase().charAt(0));
//                }
//
//                txtUsernameTeacher.setText(username.toString());
//            }
//        });
    }

    private void searchCourseDetail() {
        tbCourseDetail.clearSelection();
        lbErrorSeachCourseDetail.setVisible(cbSeachCourseDetail.getSelectedIndex() == 0 || txtSeachCourseDetail.getText().equals(""));

        boolean flag = false;
        if (cbSeachCourseDetail.getSelectedItem().toString().equalsIgnoreCase("Username")) {
            for(int i = 0; i < tbCourseDetail.getRowCount(); i++){
                if(tbCourseDetail.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSeachCourseDetail.getText())){
                    tbCourseDetail.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < tbCourseDetail.getRowCount(); i++){//For each row
                if(tbCourseDetail.getModel().getValueAt(i, 2).toString().equalsIgnoreCase(txtSeachCourseDetail.getText())){
                    tbCourseDetail.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        }
        if (!flag) {
            lbErrorSeachCourseDetail.setVisible(true);
        }
    }

    private void searchTeacher() {
        tbTeacher.clearSelection();
        lbErrorSearchTeacher.setVisible(cbSearchTeacher.getSelectedIndex() == 0 || txtSearchTeacher.getText().equals(""));

        boolean flag = false;
        if (cbSearchTeacher.getSelectedItem().toString().equalsIgnoreCase("Username")) {
            for(int i = 0; i < tbTeacher.getRowCount(); i++) {
                if(tbTeacher.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSearchTeacher.getText())){
                    tbTeacher.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < tbTeacher.getRowCount(); i++) {
                if(tbTeacher.getModel().getValueAt(i, 2).toString().equalsIgnoreCase(txtSearchTeacher.getText())){
                    tbTeacher.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        }
        if (!flag) {
            lbErrorSearchTeacher.setVisible(true);
        }
    }

    private void searchCourse() {
        tbCourse.clearSelection();
        lbErrorSeachCourse.setVisible(cbSearchCourse.getSelectedIndex() == 0 || txtSearchCourse.getText().equals(""));
        boolean flag = false;
        if (cbSearchCourse.getSelectedItem().toString().equalsIgnoreCase("ID")) {
            for(int i = 0; i < tbCourse.getRowCount(); i++){//For each row
                if(tbCourse.getModel().getValueAt(i, 0).toString().equalsIgnoreCase(txtSearchCourse.getText())){
                    tbCourse.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        } else {
            for(int i = 0; i < tbCourse.getRowCount(); i++){//For each row
                if(tbCourse.getModel().getValueAt(i, 1).toString().equalsIgnoreCase(txtSearchCourse.getText())){
                    tbCourse.addRowSelectionInterval(i, i);
                    flag = true;
                }
            }
        }
        if (!flag) {
            lbErrorSeachCourse.setVisible(true);
        }
    }

    private void logoutAdmin(String user) {
        int answer = JOptionPane.showConfirmDialog(this, "Are you sure to logout", "Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION){
            JFrame frame = new Login("Login", user);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose();
        }
    }

    private void getPositionFromCombo() {
        position = cbPosition.getSelectedItem().toString();
    }

    private void updateCourseDetail() {
        if (rowCourseDetail == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the course detail");
        } else {
            if (XUtils.convertDatetoString((Date) JDatePickerCourseFrom.getModel().getValue()).compareTo(XUtils.convertDatetoString((Date) JDatePickerCourseTo.getModel().getValue())) > 0) {
                JOptionPane.showMessageDialog(this, "End date must be after start date");
            } else {
                if (!lbErrorStartDate.isVisible() && !lbErrorEndDate.isVisible()) {
                    //edit List
                    adminController.editCourseDetail(chooseUsername,
                            chooseCourse,
                            XUtils.convertDatetoString((Date) JDatePickerCourseFrom.getModel().getValue()),
                            XUtils.convertDatetoString((Date) JDatePickerCourseTo.getModel().getValue())
                    );
                    //fill to table
                    adminController.fillToCourseDetailTable();
                    //save file
                    XFile.writeObject(fileCourseDetailPath, adminController.getListCourseDetail());
                    tbCourseDetail.addRowSelectionInterval(rowCourseDetail, rowCourseDetail);
                    lbErrorChooseLecturer.setVisible(false);
                    lbErrorChooseCourse.setVisible(false);
                    lbErrorStartDate.setVisible(false);
                    lbErrorEndDate.setVisible(false);
                }
            }
        }
    }

    private void tbCourseDetailClick() {
        cbLecturer.setEnabled(false);
        cbCourse.setEnabled(false);
        rowCourseDetail = tbCourseDetail.getSelectedRow();
        showCourseDDetail(rowCourseDetail);
    }

    private void showCourseDDetail(int rowCourseDetail) {
        String username = (String) tbCourseDetail.getValueAt(rowCourseDetail, 0);
        cbLecturer.setSelectedItem(username);

        String course = (String) tbCourseDetail.getValueAt(rowCourseDetail, 3);
        cbCourse.setSelectedItem(course);

        String courseFrom = (String) tbCourseDetail.getValueAt(rowCourseDetail, 4);
        Calendar cFrom = Calendar.getInstance();
        cFrom.setTime(XUtils.convertStringtoDate(courseFrom));
        JDatePickerCourseFrom.getJFormattedTextField().setValue(cFrom);

        String courseTo = (String) tbCourseDetail.getValueAt(rowCourseDetail, 5);
        Calendar cTo = Calendar.getInstance();
        cTo.setTime(XUtils.convertStringtoDate(courseTo));
        JDatePickerCourseTo.getJFormattedTextField().setValue(cTo);
    }

    private void saveCourseDetail() {
        lbErrorChooseLecturer.setVisible(cbLecturer.getSelectedIndex() == 0);
        lbErrorChooseCourse.setVisible(cbCourse.getSelectedIndex() == 0);
        lbErrorStartDate.setVisible(JDatePickerCourseFrom.getModel().getValue() == null);
        lbErrorEndDate.setVisible(JDatePickerCourseTo.getModel().getValue() == null);

        try {
            for (CourseDetail courseDetail : adminController.getListCourseDetail()) {
                if (courseDetail.getTeacher().getUsername().equals(chooseUsername) && courseDetail.getCourse().getName().equals(chooseCourse)) {
                    lbErrorChooseLecturer.setVisible(true);
                    lbErrorChooseCourse.setVisible(true);
                    break;
                }
            }
        } catch (NullPointerException nullPointerException) {
            System.err.println(nullPointerException);
        }

        if (XUtils.convertDatetoString((Date) JDatePickerCourseFrom.getModel().getValue()).compareTo(XUtils.convertDatetoString((Date) JDatePickerCourseTo.getModel().getValue())) > 0) {
            JOptionPane.showMessageDialog(this, "End date must be after start date");
        } else {
            if (!lbErrorChooseLecturer.isVisible() && !lbErrorChooseCourse.isVisible() && !lbErrorStartDate.isVisible() && !lbErrorEndDate.isVisible()) {
                adminController.addCourseDetail(chooseUsername,
                        chooseCourse,
                        XUtils.convertDatetoString((Date) JDatePickerCourseFrom.getModel().getValue()),
                        XUtils.convertDatetoString((Date) JDatePickerCourseTo.getModel().getValue())
                );
                //fill to Table
                adminController.fillToCourseDetailTable();
                //save file
                XFile.writeObject(fileCourseDetailPath, adminController.getListCourseDetail());
                newCourseDetail();
            }
        }
    }

    private void getCourseFromCombo() {
        chooseCourse = cbCourse.getSelectedItem().toString();
    }

    private void getUsernameFromCombo() {
        chooseUsername = cbLecturer.getSelectedItem().toString();
    }

    private void newCourseDetail() {
        tbCourseDetail.clearSelection();
        lbErrorChooseLecturer.setVisible(false);
        lbErrorChooseCourse.setVisible(false);
        lbErrorStartDate.setVisible(false);
        lbErrorEndDate.setVisible(false);
        cbLecturer.setEnabled(true);
        cbCourse.setEnabled(true);
        cbLecturer.setSelectedIndex(0);
        cbCourse.setSelectedIndex(0);
        JDatePickerCourseFrom.getModel().setValue(null);
        JDatePickerCourseTo.getModel().setValue(null);
    }

    private void updateTeacher() {
        if (rowTeacher == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the teacher");
        } else {
            lbErrorPasswordTeacher.setVisible(txtPasswordTeacher.getText().equals(""));
            lbErrorFullnameTeacher.setVisible(txtFullnameTeacher.getText().equals("") || isNumeric(txtFullnameTeacher.getText()) || isSpecial(txtFullnameTeacher.getText()));
            lbErrorDOBTeacher.setVisible(XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()).equals("") ||
                    XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()).compareTo(LocalDate.now().toString()) > 0);
            lbErrorGenderTeacher.setVisible(!rdMale.isSelected() && !rdFemale.isSelected());
            lbErrorEmailTeacher.setVisible(txtEmailTeacher.getText().equals("") || CheckEmail(txtEmailTeacher.getText()));
            lbErrorPositionTeacher.setVisible(cbPosition.getSelectedIndex() == 0);

            for (Teacher teacher : adminController.getListTeacher()) {
                if (!teacher.getUsername().equals(txtUsernameTeacher.getText()) && teacher.getEmail().equals(txtEmailTeacher.getText())) {
                    lbErrorEmailTeacher.setVisible(true);
                    break;
                }
            }

            if (!lbErrorUsernameTeacher.isVisible() &&
                    !lbErrorPasswordTeacher.isVisible() &&
                    !lbErrorFullnameTeacher.isVisible() &&
                    !lbErrorDOBTeacher.isVisible() &&
                    !lbErrorGenderTeacher.isVisible() &&
                    !lbErrorEmailTeacher.isVisible() &&
                    !lbErrorPositionTeacher.isVisible()) {
                //edit List
                adminController.editTeacher(txtUsernameTeacher.getText(),
                        txtPasswordTeacher.getText(),
                        txtFullnameTeacher.getText(),
                        XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()),
                        rdMale.isSelected(),
                        txtEmailTeacher.getText(),
                        position
                );

                //fill to table
                adminController.fillToTeacherTable();
                //save file
                XFile.writeObject(fileTeacherPath, adminController.getListTeacher());
                tbTeacher.addRowSelectionInterval(rowTeacher, rowTeacher);
            }
        }
    }

    private void tbTeacherClick() {
        txtUsernameTeacher.setEditable(false);
        rowTeacher = tbTeacher.getSelectedRow();
        showTeacherDetail(rowTeacher);
    }

    private void showTeacherDetail(int rowTeacher) {
        String usernameTeacher = (String) tbTeacher.getValueAt(rowTeacher, 0);
        txtUsernameTeacher.setText(usernameTeacher);

        String passwordTeacher = (String) tbTeacher.getValueAt(rowTeacher, 1);
        txtPasswordTeacher.setText(passwordTeacher);

        String fullNameTeacher = (String) tbTeacher.getValueAt(rowTeacher, 2);
        txtFullnameTeacher.setText(fullNameTeacher);

        String bDay = (String) tbTeacher.getValueAt(rowTeacher, 3);
        Calendar c = Calendar.getInstance();
        c.setTime(XUtils.convertStringtoDate(bDay));
        JDatePickerTeacher.getJFormattedTextField().setValue(c);

        String gender = (String) tbTeacher.getValueAt(rowTeacher, 4);
        boolean check = gender.equals("male");
        rdMale.setSelected(check);
        rdFemale.setSelected(!check);

        String emailTeacher = (String) tbTeacher.getValueAt(rowTeacher, 5);
        txtEmailTeacher.setText(emailTeacher);

        String position = (String) tbTeacher.getValueAt(rowTeacher, 6);
        cbPosition.setSelectedItem(position);
    }

    private boolean CheckValidTeacher() {
        if (txtUsernameTeacher.getText().equals("") || isSpecial(txtUsernameTeacher.getText())) {
            lbErrorUsernameTeacher.setVisible(true);
            return false;
        }
        if (txtPasswordTeacher.getText().equals("")) {
            lbErrorPasswordTeacher.setVisible(true);
            return false;
        }
        if (txtFullnameTeacher.getText().equals("") || isNumeric(txtFullnameTeacher.getText()) || isSpecial(txtFullnameTeacher.getText())) {
            lbErrorFullnameTeacher.setVisible(true);
            return false;
        }
        if (JDatePickerTeacher.getModel().getValue() == null || XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()).compareTo(LocalDate.now().toString()) > 0) {
            lbErrorDOBTeacher.setVisible(true);
            return false;
        }
        if (!rdMale.isSelected() && !rdFemale.isSelected()) {
            lbErrorGenderTeacher.setVisible(true);
            return false;
        }
        if (txtEmailTeacher.getText().equals("") || CheckEmail(txtEmailTeacher.getText())) {
            lbErrorEmailTeacher.setVisible(true);
            return false;
        }
        if (cbPosition.getSelectedIndex() == 0) {
            lbErrorPositionTeacher.setVisible(true);
            return false;
        }
        return true;
    }

    private void saveTeacher() {
        lbErrorUsernameTeacher.setVisible(txtUsernameTeacher.getText().equals("") || isSpecial(txtUsernameTeacher.getText()));
        lbErrorPasswordTeacher.setVisible(txtPasswordTeacher.getText().equals(""));
        lbErrorFullnameTeacher.setVisible(txtFullnameTeacher.getText().equals("") || isNumeric(txtFullnameTeacher.getText()) || isSpecial(txtFullnameTeacher.getText()));
        lbErrorDOBTeacher.setVisible(JDatePickerTeacher.getModel().getValue() == null ||
                                    XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()).compareTo(LocalDate.now().toString()) > 0);
        lbErrorGenderTeacher.setVisible(!rdMale.isSelected() && !rdFemale.isSelected());
        lbErrorEmailTeacher.setVisible(txtEmailTeacher.getText().equals("") || CheckEmail(txtEmailTeacher.getText()));
        lbErrorPositionTeacher.setVisible(cbPosition.getSelectedIndex() == 0);

        for (Teacher teacher : adminController.getListTeacher()) {
            if (teacher.getUsername().equals(txtUsernameTeacher.getText())) {
                lbErrorUsernameTeacher.setVisible(true);
            }
            if (teacher.getEmail().equals(txtEmailTeacher.getText())) {
                lbErrorEmailTeacher.setVisible(true);
            }
        }

        if (!lbErrorUsernameTeacher.isVisible() &&
            !lbErrorPasswordTeacher.isVisible() &&
            !lbErrorFullnameTeacher.isVisible() &&
            !lbErrorDOBTeacher.isVisible() &&
            !lbErrorGenderTeacher.isVisible() &&
            !lbErrorEmailTeacher.isVisible() &&
            !lbErrorPositionTeacher.isVisible()) {
            adminController.addTeacher(txtUsernameTeacher.getText(),
                    txtPasswordTeacher.getText(),
                    txtFullnameTeacher.getText(),
                    XUtils.convertDatetoString((Date) JDatePickerTeacher.getModel().getValue()),
                    rdMale.isSelected(),
                    txtEmailTeacher.getText(),
                    cbPosition.getSelectedItem().toString()
            );
            cbLecturer.addItem(txtUsernameTeacher.getText());
            //fill to Table
            adminController.fillToTeacherTable();
            //save file
            XFile.writeObject(fileTeacherPath, adminController.getListTeacher());
            newTeacher();
        }
    }

    private void newTeacher() {
        tbTeacher.clearSelection();
        txtUsernameTeacher.setEditable(true);
        lbErrorUsernameTeacher.setVisible(false);
        lbErrorPasswordTeacher.setVisible(false);
        lbErrorFullnameTeacher.setVisible(false);
        lbErrorDOBTeacher.setVisible(false);
        lbErrorGenderTeacher.setVisible(false);
        lbErrorEmailTeacher.setVisible(false);
        lbErrorPositionTeacher.setVisible(false);
        txtUsernameTeacher.setText("");
        txtPasswordTeacher.setText("");
        txtFullnameTeacher.setText("");
        JDatePickerTeacher.getModel().setValue(null);
        rdMale.setSelected(true);
        txtEmailTeacher.setText("");
        cbPosition.setSelectedIndex(0);
    }

    private void updateCourse() {
        if (rowCourse == -1) {
            JOptionPane.showMessageDialog(this, "Please choose the course!");
        } else {
            lbErrorCourseID.setVisible(txtCourseID.getText().equals("") || isSpecial(txtCourseID.getText()));
            lbErrorCourseName.setVisible(txtCourseName.getText().equals("") || isNumeric(txtCourseName.getText()) || isSpecial(txtCourseName.getText()));
            for (Course course : adminController.getListCourse()) {
                if (course.getName().equals(txtCourseName.getText())) {
                    lbErrorCourseName.setVisible(true);
                    break;
                }
            }
            if ((!lbErrorCourseID.isVisible() && !lbErrorCourseName.isVisible())) {
                //edit List
                adminController.editCourse(txtCourseID.getText(), txtCourseName.getText());
                //fill to table
                adminController.fillToCourseTable();
                //save file
                XFile.writeObject(fileCoursePath, adminController.getListCourse());
                tbCourse.addRowSelectionInterval(rowCourse, rowCourse);
            }
        }
    }

    private void tbCourseClick() {
        txtCourseID.setEditable(false);
        rowCourse = tbCourse.getSelectedRow();
        showCourseDetail(rowCourse);
    }

    private void showCourseDetail(int rowCourse) {
        String courseID = (String) tbCourse.getValueAt(rowCourse, 0);
        txtCourseID.setText(courseID);

        String courseName = (String) tbCourse.getValueAt(rowCourse, 1);
        txtCourseName.setText(courseName);
    }

    private void saveCourse() {
        lbErrorCourseID.setVisible(txtCourseID.getText().equals("") || isSpecial(txtCourseID.getText()));
        lbErrorCourseName.setVisible(txtCourseName.getText().equals("") || isSpecial(txtCourseName.getText()));
        for (Course course : adminController.getListCourse()) {
            if (course.getCourseID().equals(txtCourseID.getText())) {
                lbErrorCourseID.setVisible(true);
            }
            if (course.getName().equals(txtCourseName.getText())) {
                lbErrorCourseName.setVisible(true);
            }
        }
        if ((!lbErrorCourseID.isVisible() && !lbErrorCourseName.isVisible())) {
            //add new candidate from Form
            adminController.addCourse(txtCourseID.getText(), txtCourseName.getText());
            cbCourse.addItem(txtCourseName.getText());
            //fill to Table
            adminController.fillToCourseTable();
            //save file
            XFile.writeObject(fileCoursePath, adminController.getListCourse());
            txtCourseID.setText("");
            txtCourseName.setText("");
        }
    }

    private void newCourse() {
        tbCourse.clearSelection();
        txtCourseID.setEditable(true);
        lbErrorCourseID.setVisible(false);
        lbErrorCourseName.setVisible(false);
        txtCourseID.setText("");
        txtCourseName.setText("");
    }

    private void createUIComponents() {
        UtilDateModel modelTeacher = new UtilDateModel();
        UtilDateModel modelCourseFrom = new UtilDateModel();
        UtilDateModel modelCourseTo = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl panelTeacher = new JDatePanelImpl(modelTeacher, p);
        JDatePanelImpl panelCourseFrom = new JDatePanelImpl(modelCourseFrom, p);
        JDatePanelImpl panelCourseTo = new JDatePanelImpl(modelCourseTo, p);
        
        JDatePickerTeacher = new JDatePickerImpl(panelTeacher, new DataLabelFormat());
        JDatePickerCourseFrom = new JDatePickerImpl(panelCourseFrom, new DataLabelFormat());
        JDatePickerCourseTo = new JDatePickerImpl(panelCourseTo, new DataLabelFormat());
    }

    private boolean CheckCourseID(String ID) {
        Pattern pattern = Pattern.compile("^C(\\d{2})$");
        Matcher matcher = pattern.matcher(ID);
        return !matcher.find();
    }

    private boolean CheckEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.find();
    }

    public static boolean isNumeric(String strNum) {
        char[] chars = strNum.toCharArray();
        for (char c : chars) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCharacter(String strChar) {
        char[] chars = strChar.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpecial(String strChar) {
        char[] chars = strChar.toCharArray();
        for (char c : chars) {
            if (!Character.isLetterOrDigit(c) && c != '.' && c != ' ' && c != '&') {
                return true;
            }
        }
        return false;
    }
}
