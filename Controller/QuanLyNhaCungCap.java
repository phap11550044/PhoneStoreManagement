package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Entity.NhaCungCap;


public class QuanLyNhaCungCap extends NhaCungCap implements ControllerInterface {
    public NhaCungCap[] ncc;
    Scanner input = new Scanner(System.in);

    public QuanLyNhaCungCap() throws FileNotFoundException {
        super();
        getListNhaCungCap();
    }

    public NhaCungCap[] getListNhaCungCap() throws FileNotFoundException {
        String[] result = Stream.read("Database/NhaCungCap.txt");
        ncc = new NhaCungCap[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            ncc[i] = new NhaCungCap(row[0], row[1], row[2], row[3], row[4]);
        }
        return ncc;
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH NHÀ CUNG CẤP----+");
        String header = String.format("| %-5s | %-25s | %-40s | %-11s | %-9s |", "Mã", "Tên NCC", "Địa chỉ",
                "Điện thoại", "Fax");
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");
        System.out.println(header);
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");

        try {
            getListNhaCungCap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ncc.length; i++) {
            String read = String.format("| %-5s | %-25s | %-40s | %-11s | %-9s |", ncc[i].getMaNCC(),
                    ncc[i].getTenNCC(), ncc[i].getDiaChi(), ncc[i].getSDT(), ncc[i].getFax());
            System.out.println(read);
        }
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÀ CUNG CẤP----+");
        input.nextLine();
        System.out.println("Nhập ID nhà cung cấp: ");
        setMaNCC(input.nextLine());
        int check = 0;
        for (NhaCungCap nhaCungCap : ncc) {
            if (getMaNCC().equals(nhaCungCap.getMaNCC())) {
                check = 1;
                break;
            }
        }
        if (check == 1) {
            System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÀ CUNG CẤP BỊ TRÙNG!");
            return;
        }
        input.nextLine();
        System.out.println("Nhập tên nhà cung cấp");
        setTenNCC(input.nextLine());
        System.out.println("Nhập địa chỉ nhà cung cấp");
        setDiaChi(input.nextLine());
        System.out.println("Nhập số điện thoại nhà cung cấp");
        setSDT(input.nextLine());
        System.out.println("Nhập số fax nhà cung cấp");
        setFax(input.nextLine());

        try {
            String inputStringData = getMaNCC() + ";" + getTenNCC() + ";" + getDiaChi() + ";" + getSDT() + ";"
                    + getFax();
            Stream.addOneLine("Database/NhaCungCap.txt", inputStringData);
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Update() {
        System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT THÔNG TIN NHÀ CUNG CẤP----+");
        input.nextLine();
        System.out.print("\t\t\t\t\t\t\t\t -Nhập mã nhà cung cấp cần chỉnh sửa:");
        String id = input.nextLine();
        NhaCungCap nccount = null;

        for (NhaCungCap tk : ncc) {
            if (tk.getMaNCC().equals(id)) {
                nccount = tk;
                break;
            }
        }

        if (nccount == null) {
            System.out.println("\t\t\t\t\t\t\t\t -MÃ NHÀ CUNG CẤP KHÔNG TỒN TẠI!");
            return;
        }

        System.out.println("Thông tin nhà cung cấp:");
        String row = String.format("%-5s %-25s %-4s %-11s %-11s", nccount.getMaNCC(), nccount.getTenNCC(),
                nccount.getDiaChi(), nccount.getSDT(), nccount.getFax());
        System.out.println(row);
        String[] data = new String[ncc.length];
        for (int i = 0; i < ncc.length; i++) {
            if (ncc[i].getMaNCC().equals(id)) {
                input.nextLine();
                System.out.println("Nhập id nhà cung cấp");
                setMaNCC(input.nextLine());
                System.out.println("Nhập tên nhà cung cấp");
                setTenNCC(input.nextLine());
                System.out.println("Nhập địa chỉ nhà cung cấp");
                setDiaChi(input.nextLine());
                System.out.println("Nhập số điện thoại nhà cung cấp");
                setSDT(input.nextLine());
                System.out.println("Nhập số fax nhà cung cấp");
                setFax(input.nextLine());

                ncc[i].setMaNCC(getMaNCC());
                ncc[i].setTenNCC(getTenNCC());
                ncc[i].setDiaChi(getDiaChi());
                ncc[i].setSDT(getSDT());
                ncc[i].setFax(getFax());
            }
            data[i] = ncc[i].getMaNCC() + ";" + ncc[i].getTenNCC() + ";" + ncc[i].getDiaChi() + ";" + ncc[i].getSDT()
                    + ";" + ncc[i].getFax();
        }

        try {
            Stream.addAll("Database/NhaCungCap.txt", data);
            System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN NHÀ CUNG CẤP THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete() {
        System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN NHÀ CUNG CẤP----+");
        input.nextLine();
        System.out.print("\t\t\t\t\t\t\t\t -Nhập ID nhà cung cấp cần xóa: ");
        String id = input.nextLine();
        NhaCungCap nccount = null;

        for (NhaCungCap tk : ncc) {
            if (tk.getMaNCC().equals(id)) {
                nccount = tk;
                break;
            }
        }

        if (nccount == null) {
            System.out.println("ID nhà cung cấp không tồn tại");
            return;
        }

        for (int i = 0; i < ncc.length; i++) {
            if (id.equals(ncc[i].getMaNCC())) {
                ncc = deleteNhaCungCap(ncc, i);
                break;
            }
        }
        String[] data = new String[ncc.length];
        for (int i = 0; i < ncc.length; i++) {
            data[i] = ncc[i].getMaNCC() + ";" + ncc[i].getTenNCC() + ";" + ncc[i].getDiaChi() + ";" + ncc[i].getSDT()
                    + ";" + ncc[i].getFax();
        }
        try {
            Stream.addAll("Database/NhaCungCap.txt", data);
            System.out.println("Xóa nhà cung cấp thành công");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NhaCungCap[] deleteNhaCungCap(NhaCungCap[] ncc, int index) {
        NhaCungCap[] newNCC = new NhaCungCap[ncc.length - 1];
        for (int i = 0, j = 0; i < ncc.length; i++) {
            if (i != index) {
                newNCC[j++] = ncc[i];
            }
        }
        return newNCC;
    }

    public NhaCungCap[] addNhaCungCap(NhaCungCap[] ncc, NhaCungCap nccount) {
        ncc = Arrays.copyOf(ncc, ncc.length + 1);
        ncc[ncc.length - 1] = nccount;
        return ncc;
    }

    @Override
    public void Search_byCategory() {
        NhaCungCap[] result = new NhaCungCap[0];
        System.out.println("Nhập từ bạn muốn tìm kiếm: ");
        String key = input.nextLine();
        for (int i = 0; i < ncc.length; i++) {
            if (ncc[i].getTenNCC().toLowerCase().contains(key.toLowerCase())
                    || ncc[i].getMaNCC().toLowerCase().contains(key.toLowerCase())) {
                result = addNhaCungCap(result, ncc[i]);
            }
        }

        System.out.println("Danh sách nhà cung cấp tìm được:");
        String header = String.format("| %-5s | %-25s | %-40s | %-11s | %-9s |", "Mã", "Tên NCC", "Địa chỉ",
                "Điện thoại", "Fax");
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");
        System.out.println(header);
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");
        for (int i = 0; i < result.length; i++) {
            String read = String.format("| %-5s | %-25s | %-40s | %-11s | %-9s |", result[i].getMaNCC(),
                    result[i].getTenNCC(), result[i].getDiaChi(), result[i].getSDT(), result[i].getFax());
            System.out.println(read);
        }
        System.out.format(
                "+-------+---------------------------+------------------------------------------+-------------+-----------+%n");
        waitConsole();
    }

    public void waitConsole() {
        System.out.println("Ấn Enter để tiếp tục");
        input.nextLine();
    }
}
