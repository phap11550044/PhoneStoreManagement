package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import Entity.Payment;


public class QuanLyThanhToan extends Payment implements ControllerInterface {
    public Payment payment[];
    static Scanner sc = new Scanner(System.in);
    public int n = 0;


    public QuanLyThanhToan() throws FileNotFoundException {
        super();
        getListPayment();
    }


    //Lấy danh sách thanh toán từ file
    public Payment[] getListPayment() throws FileNotFoundException {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/ThanhToan.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        payment = new Payment[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            payment[i] = new Payment(row[0], row[1], row[2], row[3], LocalDate.parse(row[4]), row[5], row[6]);
        }
        n = payment.length - 1;
        return payment;
    }


    @Override
    public void Read() {
        System.out.println("Danh sách thanh toán:");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |", "ID Đơn Hàng", "ID Khách Hàng", "ID Hóa Đơn", "Số lượng", "Ngày thanh toán", "Phương thức thanh toán", "Trạng thái");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

        try {
            getListPayment();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < payment.length; i++) {
            String read = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |",
                    payment[i].getPayment_ID(),
                    payment[i].getCustomer_ID(),
                    payment[i].getID_Receipt(),
                    payment[i].getAmount(),
                    payment[i].getPayment_Date(),
                    payment[i].getPayment_Method(),
                    payment[i].getStatus());
            System.out.println(read);
        }
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        waitConsole();
    }


    @Override
    public void Create() {
        System.out.println("NHAP THONG TIN THANH TOAN:");
        Payment sv = new Payment();

        payment = Arrays.copyOf(payment, n+1);
        ((QuanLyThanhToan) sv).InputData(n);
        payment[n] = sv;

        updateList(0, n, payment);

        n++;
    }


    @Override
    public void Update() {
        int mng_index;
        int tmp = payment.length - 1;
        System.out.println ("Nhập STT của nội dung thanh toán cần sửa (0 -> " + tmp + "): ");
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
        System.out.println ("1. ID thanh toán");
        System.out.println ("2. ID khách hàng");
        System.out.println ("3. ID đơn hàng");
        System.out.println ("4. Số lượng");
        System.out.println ("5. Ngày thanh toán");
        System.out.println ("6. Phương thức thanh toán");
        System.out.println ("7. Trạng thái");
        System.out.print ("Nhập số từ 1 đến 7: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 0 || index > 7) {
                System.out.print ("Nhập lại: ");
                index = sc.nextInt();
            }
            else
                break;
        }

        switch (index) {
            case 1:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setPayment_ID(find);
                break;
            case 2:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setCustomer_ID(find);
                break;
            case 3:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setID_Receipt(find);
                break;
            case 4:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setAmount(find);
                break;
            case 5:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setPayment_Date(LocalDate.parse(find));
                break;
            case 6:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setPayment_Method(find);
                break;
            case 7:
                System.out.print ("Nhập nội dung cần sửa: ");
                sc.nextLine();
                find = sc.nextLine();
                payment[mng_index].setStatus(find);
                break;
        }

        //Cập nhật lại toàn bộ list vào file
        updateList(1, n, payment);
    }


    @Override
    public void Delete() {
        System.out.println("Nhap mã thanh toán cần xóa (0 -> " + n + "-1)");
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
            payment[i] = payment[i+1];

        payment = Arrays.copyOf(payment, n-1);

        n--;

        //Cập nhật lại toàn bộ list vào file
        updateList(1, n, payment);
    }


    @Override
    public void Search_byCategory() {
        String find;
        System.out.println ("MUC LUC CAN TIM");
        System.out.println ("1. ID thanh toán");
        System.out.println ("2. ID khách hàng");
        System.out.println ("3. ID đơn hàng");
        System.out.println ("4. Số lượng");
        System.out.println ("5. Ngày thanh toán");
        System.out.println ("6. Phuong thức thanh toán");
        System.out.println ("7. Trạng thái");
        System.out.print ("Nhap so tu 1 den 7: ");
        int index = sc.nextInt();

        while (true) {
            if (index < 1 || index > 7) {
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

        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH THANH TOÁN----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |", "ID hóa đơn", "ID khách hàng", "ID đơn hàng", "Số lượng", "Ngày thanh toán", "Phuong thức thanh toán", "Trạng thái");
        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);

        for(int i = 0; i < payment.length; i++) {
            switch (index) {
                case 1:
                    if (payment[i].getPayment_ID().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 2:
                    if (payment[i].getCustomer_ID().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 3:
                    if (payment[i].getID_Receipt().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 4:
                    if (payment[i].getAmount().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 5:
                    if (payment[i].getPayment_Date().equals(LocalDate.parse(find)))
                        OutputData(i);
                    break;
                case 6:
                    if (payment[i].getPayment_Method().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
                case 7:
                    if (payment[i].getStatus().equalsIgnoreCase(find))
                        OutputData(i);
                    break;
            }
        }
    }


    public void InputData(int i) {
        int tmp = i + 1;
        System.out.print("Nhap ma thanh toan thu " + tmp + ": ");
        setPayment_ID(sc.nextLine());
        System.out.print("Nhap ma khach hang: ");
        setCustomer_ID(sc.nextLine());
        System.out.print("Nhap ma don hang: ");
        setID_Receipt(sc.nextLine());
        System.out.print("Nhap so luong hang: ");
        setAmount(sc.nextLine());
        System.out.print("Nhap ngay dat hang: ");
        setPayment_Date(LocalDate.parse(sc.nextLine()));
        System.out.print("Nhap phuong thuc dat hang: ");
        setPayment_Method(sc.nextLine());
        System.out.print("Nhap trang thai cua don hang: ");
        setStatus(sc.nextLine());
    }


    public void OutputData(int i) {
        String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |",
                payment[i].getPayment_ID(),
                payment[i].getCustomer_ID(),
                payment[i].getID_Receipt(),
                payment[i].getAmount(),
                payment[i].getPayment_Date(),
                payment[i].getPayment_Method(),
                payment[i].getStatus());
        System.out.println(row);
    }


    public String[] stringToInputInFile(Payment payment[]) {
        String data[] = new String[payment.length];

        for (int i = 0; i < payment.length; i++) {
            data[i] =
                    payment[n].getPayment_ID() + ";" +
                            payment[n].getCustomer_ID() + ";" +
                            payment[n].getID_Receipt() + ";" +
                            payment[n].getAmount() + ";" +
                            payment[n].getPayment_Date() + ";" +
                            payment[n].getPayment_Method() + ";" +
                            payment[n].getStatus();
        }

        return data;
    }


    public void updateList(int select, int n, Payment payment[]) {
        switch (select) {
            case 0:
                try {
                    String inputStringData =
                            payment[n].getPayment_ID() + ";" +
                                    payment[n].getCustomer_ID() + ";" +
                                    payment[n].getID_Receipt() + ";" +
                                    payment[n].getAmount() + ";" +
                                    payment[n].getPayment_Date() + ";" +
                                    payment[n].getPayment_Method() + ";" +
                                    payment[n].getStatus();
                    Stream.addOneLine("Database/ThanhToan.txt", inputStringData);
                    System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
                    waitConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1 :
                String[] data = stringToInputInFile(payment);

                try {
                    Stream.addAll("Database/ThanhToan.txt", data);
                    System.out.println("\t\t\t\t\t\t\t\t +----SỬA THÔNG TIN THANH TOÁN THÀNH CÔNG----+");
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
