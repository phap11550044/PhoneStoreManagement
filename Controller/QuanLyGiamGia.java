package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Discount;

public class QuanLyGiamGia extends Discount implements ControllerInterface {
    public Discount[] gg;
    Scanner input = new Scanner(System.in);

    public QuanLyGiamGia() throws FileNotFoundException {
        super();
        getListGiamGia();
    }

    public Discount[] getListGiamGia() throws FileNotFoundException {
        String[] result = Stream.read("Database/GiamGia.txt");
        gg = new Discount[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            gg[i] = new Discount(row[0], row[1], row[2], row[3], row[4], row[5], row[6]);
        }
        return gg;
    }

    public void waitConsole() {
        System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
        input.nextLine();
    }

    @Override
    public void Read() {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH GIẢM GIÁ----+");
        String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |", "ID", "Loại khách hàng",
                "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
        System.out.format(
                "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        System.out.println(header);
        System.out.format(
                "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

        try {
            getListGiamGia();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < gg.length; i++) {
            String read = String.format("| %-5s | %-25s | %-20s | %-9s | %-20s | %-15s | %-15s |",
                    gg[i].getDiscount_ID(), gg[i].getKindOfCustomer(), gg[i].getProduct_name(),
                    gg[i].getDiscount_rate(), gg[i].getStatus(), gg[i].getStartDate(), gg[i].getEndDate());
            System.out.println(read);
        }

        System.out.format(
                "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
        waitConsole();
    }

    @Override
    public void Create() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN GIẢM GIÁ----+");
            System.out.print("Nhập ID mã giảm giá:");
            setDiscount_ID(input.nextLine());

            int check = 0;
            for (Discount giamGia : gg) {
                if (getDiscount_ID().equals(giamGia.getDiscount_ID())) {
                    check = 1;
                    break;
                }
            }

            if (check == 1) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ GIẢM GIÁ BỊ TRÙNG!");
                return;
            }

            System.out.print("Nhập loại khách hàng:");
            setKindOfCustomer(input.nextLine());

            System.out.print("Nhập tên sản phẩm: ");
            setProduct_name(input.nextLine());

            System.out.print("Nhập % giảm giá: ");
            setDiscount_rate(input.nextLine());

            System.out.print("Nhập trạng thái: ");
            setStatus(input.nextLine());

            System.out.print("Nhập ngày bắt đầu giảm giá: ");
            setStartDate(input.nextLine());

            System.out.print("Nhập ngày kết thúc giảm giá: ");
            setProduct_name(input.nextLine());

            try {
                String input = getDiscount_ID() + ";" + getKindOfCustomer() + ";" + getProduct_name() + ";"
                        + getDiscount_rate() + ";"
                        + getStatus() + ";" + getStartDate() + ";" + getEndDate();
                Stream.addOneLine("Database/GiamGia.txt", input);
                System.out.println("\t\t\t\t\t\t\t\t -NHẬP THÔNG TIN GIẢM GIÁ THÀNH CÔNG");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Delete() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN GIẢM GIÁ----+");
            System.out.print("\t\t\t\t\t\t\t\t -Nhập mã giảm giá cần xóa: ");
            String Discount_ID = input.nextLine();
            Discount giam_gia = null;

            for (Discount giamGia : gg) {
                if (giamGia.getDiscount_ID().equals(Discount_ID)) {
                    giam_gia = giamGia;
                    break;
                }
            }

            if (giam_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ GIẢM GIÁ KHÔNG TỒN TẠI!");
                return;
            }

            for (int i = 0; i < gg.length; i++) {
                if (Discount_ID.equals(gg[i].getDiscount_ID())) {
                    gg = deleteGiamGia(gg, i);
                    break;
                }
            }
            String[] data = new String[gg.length];
            for (int i = 0; i < gg.length; i++) {
                data[i] = gg[i].getDiscount_ID() + ";" + gg[i].getKindOfCustomer() + ";" + gg[i].getProduct_name()
                        + ";" + gg[i].getDiscount_rate() + ";" + gg[i].getStatus() + ";" + gg[i].getStartDate() + ";"
                        + gg[i].getEndDate();
            }
            try {
                Stream.addAll("Database/GiamGia.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----+");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void Update() {
        try {
            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN GIẢM GIÁ----+");
            input.nextLine();
            System.out.print("\t\t\t\t\t\t\t\t - Mời bạn nhập mã giảm giá cần chỉnh sửa: ");
            String Discount_ID = input.nextLine();
            Discount g_gia = null;

            for (Discount giamGia : gg) {
                if (giamGia.getDiscount_ID().equals(Discount_ID)) {
                    g_gia = giamGia;
                    break;
                }
            }

            if (g_gia == null) {
                System.out.println("\t\t\t\t\t\t\t\t -MÃ GIẢM GIÁ KHÔNG TỒN TẠI!");
                return;
            }

            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN GIẢM GIÁ TRƯỚC KHI ĐƯỢC CHỈNH SỬA----+");
            String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |", "ID",
                    "Loại khách hàng",
                    "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
            System.out.format(
                    "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
            String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |",
                    g_gia.getDiscount_ID(), g_gia.getKindOfCustomer(), g_gia.getProduct_name(),
                    g_gia.getDiscount_rate(),
                    g_gia.getStatus(), g_gia.getStartDate(), g_gia.getEndDate());
            System.out.println(row);

            String[] data = new String[gg.length];

            for (int i = 0; i < gg.length; i++) {
                if (Discount_ID.equals(gg[i].getDiscount_ID())) {
                    System.out.println("Nhập thông tin giảm giá:");
                    setDiscount_ID(input.nextLine());

                    input.nextLine();
                    System.out.print("Nhập loại khách hàng: ");
                    setKindOfCustomer(input.nextLine());

                    System.out.print("Nhập tên sản phẩm: ");
                    setProduct_name(input.nextLine());

                    System.out.print("Nhập % giảm giá: ");
                    setDiscount_rate(input.nextLine());

                    System.out.print("Nhập trạng thái: ");
                    setStatus(input.nextLine());

                    System.out.print("Nhập ngày bắt đầu giảm giá: ");
                    setStartDate(input.nextLine());

                    System.out.print("Nhập ngày kết thúc giảm giá: ");
                    setEndDate(input.nextLine());

                    gg[i].setDiscount_ID(getDiscount_ID());
                    gg[i].setKindOfCustomer(getKindOfCustomer());
                    gg[i].setProduct_name(getProduct_name());
                    gg[i].setDiscount_rate(getDiscount_rate());
                    gg[i].setStatus(getStatus());
                    gg[i].setStartDate(getStartDate());
                    gg[i].setEndDate(getEndDate());
                }
                data[i] = gg[i].getDiscount_ID() + ";" + gg[i].getKindOfCustomer() + ";" + gg[i].getProduct_name() + ";"
                        + gg[i].getDiscount_rate()
                        + ";" + gg[i].getStatus() + ";" + gg[i].getStartDate() + ";" + gg[i].getEndDate();
            }
            try {
                Stream.addAll("Database/GiamGia.txt", data);
                System.out.println("\t\t\t\t\t\t\t\t----SỬA THÔNG TIN GIẢM GIÁ THÀNH CÔNG----");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Discount[] addGiamGia(Discount[] gg, Discount giamGia) {
        gg = Arrays.copyOf(gg, gg.length + 1);
        gg[gg.length - 1] = giamGia;
        return gg;
    }

    public Discount[] deleteGiamGia(Discount[] gg, int index) {
        Discount[] newGg = new Discount[gg.length - 1];
        for (int i = 0, j = 0; i < gg.length; i++) {
            if (i != index) {
                newGg[j++] = gg[i];
            }
        }
        return newGg;
    }

    @Override
    public void Search_byCategory() {
        Discount[] result = new Discount[0];
        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1.Mã giảm giá                            |");
        System.out.println("\t\t\t\t\t\t\t\t |2.Loại khách hàng                        |");
        System.out.println("\t\t\t\t\t\t\t\t |3.Tên sản phẩm                           |");
        System.out.println("\t\t\t\t\t\t\t\t |4.% giảm giá                             |");
        System.out.println("\t\t\t\t\t\t\t\t |5.Trạng thái                             |");
        System.out.println("\t\t\t\t\t\t\t\t |6.Ngày bắt đầu giảm giá                  |");
        System.out.println("\t\t\t\t\t\t\t\t |7.Ngày kết thúc giảm giá                 |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int choose = input.nextInt();
        if (choose == 0)
            return;
        else {
            switch (choose) {
                case 1 -> {
                    input.nextLine();
                    System.out.print("Nhập mã giảm giá: ");
                    String Discount_ID = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (Discount_ID.contains(giamGia.getDiscount_ID())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 2 -> {
                    input.nextLine();
                    System.out.print("Nhập loại khách hàng: ");
                    String KindOfCustomer = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (giamGia.getKindOfCustomer().toLowerCase().contains(KindOfCustomer.toLowerCase())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 3 -> {
                    input.nextLine();
                    System.out.print("Nhập tên sản phẩm: ");
                    String Product_name = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (giamGia.getProduct_name().toLowerCase().contains(Product_name.toLowerCase())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 4 -> {
                    input.nextLine();
                    System.out.print("Nhập % giảm giá: ");
                    String Discount_rate = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (Discount_rate.contains(giamGia.getDiscount_rate())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 5 -> {
                    input.nextLine();
                    System.out.print("Nhập trạng thái: ");
                    String Status = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (Status.contains(giamGia.getStatus())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 6 -> {
                    input.nextLine();
                    System.out.print("Nhập ngày bắt đầu giam giá: ");
                    String StartDate = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (StartDate.contains(giamGia.getStartDate())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
                case 7 -> {
                    input.nextLine();
                    System.out.print("Nhập ngày kết thúc giam giá: ");
                    String EndDate = input.nextLine();
                    for (Discount giamGia : gg) {
                        if (EndDate.contains(giamGia.getStartDate())) {
                            result = addGiamGia(result, giamGia);
                        }
                    }
                }
            }
            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN GIẢM GIÁ TÌM ĐƯỢC----+");
            String header = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s |", "ID",
                    "Loại khách hàng",
                    "Tên sản phẩm", "% Giảm Giá", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc");
            System.out.format(
                    "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
            System.out.println(header);
            System.out.format(
                    "+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

            for (Discount DSGG : result) {
                String row = String.format("| %-5s | %-25s | %-4s | %-9s | %-30s | %-25s | %-15s | %-20s | %-20s |",
                        DSGG.getDiscount_ID(), DSGG.getKindOfCustomer(), DSGG.getProduct_name(),
                        DSGG.getDiscount_rate(), DSGG.getStatus(), DSGG.getStartDate(), DSGG.getEndDate());
                System.out.println(row);
                waitConsole();
            }
        }
    }
}
