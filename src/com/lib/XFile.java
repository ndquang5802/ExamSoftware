package com.lib;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class XFile {
    public static void writeFile(String path){
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] arr = {10, 20, 30};
            fos.write(arr);
            fos.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void readFile(String path){
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int fileSize = (int) file.length();
            byte[] result = new byte[fileSize];
            fis.read(result);
            for (int i = 0; i < fileSize; i++) {
                System.out.println(result[i]);
            }
            fis.close();
        } catch (FileNotFoundException fe) {
            System.err.println(fe);
        } catch (IOException ie) {
            System.err.println(ie);
        }
    }

    public static void writeDataFile(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            DataOutputStream dos = new DataOutputStream(fos);
            int num = 5;
            dos.writeInt(num);
            dos.writeUTF("Nguyễn Duy Quang");
            dos.writeDouble(5.5);
            dos.close();
            fos.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void readDataFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            DataInputStream dis = new DataInputStream(fis);
            System.out.println(dis.readInt());
            System.out.println(dis.readUTF());
            System.out.println(dis.readDouble());
            dis.close();
            fis.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void writeBuffer(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Hello");
            bw.write("\n");
            bw.write("My name is Quang");
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void writeBuffer(String path, String data) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String readBuffer(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String str = br.readLine(); //first line
            while (str != null) {
                sb.append(str);
                str = br.readLine();
                if (str != null) sb.append("\n");
            }

            br.close();
            fr.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return sb.toString();
    }

    public static void writeObject(String path, Object o) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Object readObject(String path) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static void writeJSON(String path, JSONArray jsonArray) {
        //Write JSON file
        try (FileWriter file = new FileWriter(path)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray readJSON(String path) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(path))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
//            System.out.println(employeeList);

//            //Iterate over employee array
//            employeeList.forEach( emp -> parseEmployeeObject((JSONObject) emp ));
            return employeeList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseEmployeeObject(JSONObject emp) {
        Long id = (Long) emp.get("id");
        System.out.println(id);
        String question = (String) emp.get("question");
        System.out.println(question);
        String option1 = (String) emp.get("option 1");
        System.out.println(option1);
        String option2 = (String) emp.get("option 2");
        System.out.println(option2);
        String option3 = (String) emp.get("option 3");
        System.out.println(option3);
        String option4 = (String) emp.get("option 4");
        System.out.println(option4);
        String correct = (String) emp.get("correct");
        System.out.println(correct);
    }
}
