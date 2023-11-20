package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import Entity.Warranty;


public class QuanLyBaoHanh extends Warranty implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    Warranty warranty[];
    public int n = 0;


    public QuanLyBaoHanh() throws FileNotFoundException {
        super();
        getListWarranties();
    }

    //Lấy danh sách bảo hành từ file
    public Warranty[] getListWarranties() throws FileNotFoundException {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/BaoHanh.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        warranty = new Warranty[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            warranty[i] = new Warranty(row[0], LocalDate.parse(row[1]), row[2], row[3], row[4]);
        }
        n = warranty.length - 1;
        return warranty;
    }



    public void Read() {
        System.out.println("Danh sách bảo hành:");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s |",
                "ID sản phẩm",
                "Ngày bảo hành",
                "Số lượng",
                "Phương thức bảo hành",
                "ID khách hàng");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+%n");

        try {
            getListWarranties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < warranty.length; i++) {
            String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s |",
                    warranty[i].getProduct_ID(),
                    warranty[i].getProduct_Date(),
                    warranty[i].getYears_Of_Warranty(),
                    warranty[i].getWarranty_Method(),
                    warranty[i].getCustom());
            System.out.println(read);
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+%n");
        waitConsole();
    }

    public void Create() {
        System.out.println("Nhập thông tin của mục bảo hành cần thêm:");
        Warranty sv = new Warranty();

        warranty = Arrays.copyOf(warranty, n+1);
        ((QuanLyBaoHanh) sv).InputData(n);
        warranty[n] = sv;

        updateList(0, n, warranty);

        n++;
    }

    public void Update() {
        int mng_index;
        System.out.println ("Nhập STT của mục bảo hành cần sửa (0 -> " + warranty.length + "-1)");
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
        System.out.println ("MỤC LỤC CẦN SỬA");
        System.out.println ("1. ID Sản phẩm");
        System.out.println ("2. Ngày sản xuất");
        System.out.println ("3. Năm bảo hành");
        System.out.println ("4. Phương thức bảo hành");
        System.out.println ("5. Custom???");
        System.out.print ("Nhập số từ 1 đến 5: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 0 || index > 5) {
                System.out.print ("Nhập lại: ");
                mng_index = sc.nextInt();
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
                warranty[mng_index].setProduct_ID(find);
                break;
            case 2:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                warranty[mng_index].setProduct_Date(LocalDate.parse(find));
                break;
            case 3:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                warranty[mng_index].setYears_Of_Warranty(find);
                break;
            case 4:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                warranty[mng_index].setWarranty_Method(find);
                break;
            case 5:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                warranty[mng_index].setCustom(find);
                break;
        }

        updateList(1, n, warranty);
    }


    public void Delete() {
        System.out.println("Nhập STT của mục bảo hành cần xóa (0 -> " + n + "-1)");
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
            warranty[i] = warranty[i+1];

        warranty = Arrays.copyOf(warranty, n-1);

        n--;

        updateList(1, n, warranty);
    }


    public void Search_byCategory() {
        String find;
        System.out.println ("MỤC LỤC CẦN TÌM");
        System.out.println ("1. ID Sản phẩm");
        System.out.println ("2. Ngày sản xuất");
        System.out.println ("3. Năm bảo hành");
        System.out.println ("4. Phương thức bảo hành");
        System.out.println ("5. Custom???");
        System.out.print ("Nhập số từ 1 đến 5: ");
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

        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH BẢO HÀNH----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s |",
                "ID sản phẩm",
                "Ngày bảo hành",
                "Số lượng",
                "Phương thức bảo hành",
                "ID khách hàng");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+%n");
        System.out.println(header);

        for(int i = 0; i < warranty.length; i++) {
            switch (index) {
                case 1:
                    if (warranty[i].getProduct_ID().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 2:
                    if (warranty[i].getProduct_Date().equals(LocalDate.parse(find)))
                        OutputData(i);
                    break;
                case 3:
                    if (warranty[i].getYears_Of_Warranty().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 4:
                    if (warranty[i].getWarranty_Method().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 5:
                    if (warranty[i].getCustom().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
            }
        }
    }


    public void InputData(int i) {
        int tmp = i + 1;
        System.out.print("Nhập ID Sản phẩm thứ " + tmp + ": ");
        setProduct_ID(sc.nextLine());
        System.out.print("Nhập Ngày sản xuất: ");
        setProduct_Date(LocalDate.parse(sc.nextLine()));
        System.out.print("Nhập Năm bảo hành: ");
        setYears_Of_Warranty(sc.nextLine());
        System.out.print("Nhập Phương thức bảo hành: ");
        setWarranty_Method(sc.nextLine());
        System.out.print("Nhập Custom???: ");
        setCustom(sc.nextLine());
    }


    public void OutputData(int i) {
        String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s |",
                warranty[i].getProduct_ID(),
                warranty[i].getProduct_Date(),
                warranty[i].getYears_Of_Warranty(),
                warranty[i].getWarranty_Method(),
                warranty[i].getCustom());
        System.out.println(row);
    }


    public String[] stringToInputInFile(Warranty warranty[]) {
        String data[] = new String[warranty.length];

        for (int i = 0; i < warranty.length; i++) {
            data[i] =
                    warranty[i].getProduct_ID() + ";" +
                            warranty[i].getProduct_Date() + ";" +
                            warranty[i].getYears_Of_Warranty() + ";" +
                            warranty[i].getWarranty_Method() + ";" +
                            warranty[i].getCustom();
        }

        return data;
    }


    public void updateList(int select, int n, Warranty warranty[]) {
        switch (select) {
            case 0:
                try {
                    String inputStringData =
                            warranty[n].getProduct_ID() + ";" +
                                    warranty[n].getProduct_Date() + ";" +
                                    warranty[n].getYears_Of_Warranty() + ";" +
                                    warranty[n].getWarranty_Method() + ";" +
                                    warranty[n].getCustom();
                    Stream.addOneLine("Database/BaoHanh.txt", inputStringData);
                    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1 :
                String[] data = stringToInputInFile(warranty);

                try {
                    Stream.addAll("Database/BaoHanh.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN BẢO HÀNH THÀNH CÔNG----+");
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
