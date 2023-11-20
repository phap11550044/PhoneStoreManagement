package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import Entity.Manager;


public class QuanLyNguoiQuanLy extends Manager implements ControllerInterface {
    public Manager manager[];
    static Scanner sc = new Scanner(System.in);
    public int n;


    public QuanLyNguoiQuanLy() throws FileNotFoundException {
        super();
        getListManagers();
    }


    //Lấy danh sách người quản lý từ file
    public Manager[] getListManagers() throws FileNotFoundException {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/NguoiQuanLy.txt");
        }   catch (IOException e){
            e.printStackTrace();
        }

        manager = new Manager[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            manager[i] = new Manager(row[0], row[1], Integer.parseInt(row[2]), row[3], row[4], row[5], row[6], row[7], row[8],Integer.parseInt(row[9]));
        }
        n = manager.length - 1;
        return manager;
    }


    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NGƯỜI QUẢN LÝ----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-20s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

        try {
            getListManagers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < manager.length; i++) {
            String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-20s |",
                    manager[i].getID_Manager(),
                    manager[i].getName(),
                    manager[i].getAge(),
                    manager[i].getGender(),
                    manager[i].getAddress(),
                    manager[i].getEmail(),
                    manager[i].getSDT(),
                    manager[i].getRole(),
                    manager[i].getShift());
            System.out.println(read);
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        waitConsole();
    }


    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NGƯỜI QUẢN LÝ----+");
        Manager sv = new Manager();

        manager = Arrays.copyOf(manager, n+1);
        ((QuanLyNguoiQuanLy) sv).InputData(n);
        manager[n] = sv;

        updateList(0, n, manager);

        n++;
    }


    @Override
    public void Update() {
        int mng_index;
        int tmp = manager.length - 1;

        System.out.println ("Nhập STT của người quản lý cần sửa (0 -> " + tmp + "): ");
        mng_index = sc.nextInt();
        while (true) {
            if (mng_index < 0 || mng_index > n-1) {
                System.out.print ("Nhập lại: ");
                mng_index = sc.nextInt();
            }
            else {
                break;
            }
        }

        String find;
        System.out.println("\t\t\t\t\t\t\t\t +----MỤC LỤC CẦN SỬA----+");
        System.out.println ("1. ID Người Quản lý");
        System.out.println ("2. Vai trò");
        System.out.println ("3. Ca làm");
        System.out.println ("4. Số lượng Nhân viên Quản lý");
        System.out.print ("Nhập số từ 1 đến 4: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 0 || index > 4) {
                System.out.print ("Nhập lại: ");
                index = sc.nextInt();
            }
            else {
                break;
            }
        }

        switch (index) {
            case 1:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                manager[mng_index].setID_Manager(String.valueOf(Integer.parseInt(find)));
                break;
            case 2:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                manager[mng_index].setRole(find);
                break;
            case 3:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                manager[mng_index].setShift(find);
                break;
            case 4:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                manager[mng_index].setSLNVQL(Integer.parseInt(find));
                break;
        }

        //Cập nhật lại toàn bộ list vào file
        updateList(1, n, manager);
    }


    @Override
    public void Delete() {
        int tmp = n-1;
        System.out.println ("Nhập STT của người quản lý cần sửa (0 -> " + tmp + "): ");
        int index = sc.nextInt();
        while (true) {
            if (index < 0 || index >= n) {
                System.out.print ("Nhập lại: ");
                index = sc.nextInt();
            }
            else
                break;
        }

        for (int i = index; i < n-1; i++)
            manager[i] = manager[i+1];

        manager = Arrays.copyOf(manager, n-1);

        n--;

        //Cập nhật lại toàn bộ list vào file
        updateList(1, n, manager);
    }


    @Override
    public void Search_byCategory() {
        String find;
        System.out.println("\t\t\t\t\t\t\t\t +----MỤC LỤC CẦN TÌM----+");
        System.out.println ("1. ID Người Quản lý");
        System.out.println ("2. Vai trò");
        System.out.println ("3. Ca làm");
        System.out.println ("4. Số lượng Nhân viên Quản lý");
        System.out.print ("Nhập số từ 1 đến 4: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 1 || index > 4) {
                System.out.print ("Nhập lại: ");
                index = sc.nextInt();
            }
            else {
                break;
            }
        }

        System.out.print ("Nhập nội dung cần tìm: ");
        sc.nextLine();
        find = sc.nextLine();

        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NGƯỜI QUẢN LÝ----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-20s |", "ID", "Họ tên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Số điện thoại", "Chức vụ", "Ca trực");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);

        for(int i = 0; i < manager.length; i++) {
            switch (index) {
                case 1:
                    if (Objects.equals(manager[i].getID_Manager(),find))
                        OutputData(i);
                    break;
                case 2:
                    if (Objects.equals(manager[i].getRole(), find))
                        OutputData(i);
                    break;
                case 3:
                    if (Objects.equals(manager[i].getShift(), find))
                        OutputData(i);
                    break;
                case 4:
                    if (manager[i].getSLNVQL() == Integer.parseInt(find))
                        OutputData(i);
                    break;
            }
        }
    }


    public void InputData(int i) {
        int tmp = i + 1;
        System.out.print("NHập ID người quản lý thứ " + tmp + ": ");
        setID_Manager(sc.nextLine());
        super.AddThongTin();
        System.out.print("Nhap vai trò: ");
        setRole(sc.nextLine());
        System.out.print("Nhap ca làm: ");
        setShift(sc.nextLine());
        System.out.print("Nhap số lượng nhân viên quản lý: ");
        setSLNVQL(sc.nextInt());
    }


    public void OutputData(int i) {
        String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-20s |",
                manager[i].getName(),
                manager[i].getAge(),
                manager[i].getGender(),
                manager[i].getAddress(),
                manager[i].getEmail(),
                manager[i].getID_Manager(),
                manager[i].getRole(),
                manager[i].getShift(),
                manager[i].getSDT());
        System.out.println(row);
    }


    public String[] stringToInputInFile (Manager manager[]) {
        String data[] = new String[manager.length];

        for (int i = 0; i < manager.length; i++) {
            data[i] =
                    manager[i].getName() + ";" +
                            manager[i].getAge() + ";" +
                            manager[i].getGender() + ";" +
                            manager[i].getAddress() + ";" +
                            manager[i].getEmail() + ";" +
                            manager[i].getID_Manager() + ";" +
                            manager[i].getRole() + ";" +
                            manager[i].getShift() + ";" +
                            manager[i].getSDT();
        }

        return data;
    }


    public void updateList(int select, int n, Manager manager[]) {
        switch (select) {
            case 0:
                try {
                    String inputStringData =
                            manager[n].getName() + ";" +
                                    manager[n].getAge() + ";" +
                                    manager[n].getGender() + ";" +
                                    manager[n].getAddress() + ";" +
                                    manager[n].getEmail() + ";" +
                                    manager[n].getID_Manager() + ";" +
                                    manager[n].getRole() + ";" +
                                    manager[n].getShift() + ";" +
                                    manager[n].getSDT();
                    Stream.addOneLine("Database/NguoiQuanLy.txt", inputStringData);
                    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NGƯỜI QUẢN LÝ THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1 :
                String[] data = stringToInputInFile(manager);

                try {
                    Stream.addAll("Database/NguoiQuanLy.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void waitConsole() {
        System.out.println("Ấn Enter để tiếp tục");
        sc.nextLine();
    }
}