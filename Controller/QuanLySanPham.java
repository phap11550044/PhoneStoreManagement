package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.SanPham;

public class QuanLySanPham extends SanPham implements ControllerInterface {
    public SanPham[] DSSP;
    static Scanner input = new Scanner(System.in);

    public QuanLySanPham() throws FileNotFoundException {
        super();
        getListSanPham();
    }

    // lấy danh sách sản phẩm từ file
    public SanPham[] getListSanPham() {
        String[] result = new String[0];
        try {
            result = Stream.read("Database/SanPham.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DSSP = new SanPham[result.length];
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            DSSP[i] = new SanPham(row[0], row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]),
                    Integer.parseInt(row[5]), Integer.parseInt(row[6]));
        }
        return DSSP;
    }

    // Đợi cho đến khi người dùng enter lần
    public void waitConsole() {
        System.out.println("\t\t\t\t\t -Ấn Enter để tiếp tục");
        input.nextLine();
    }

    // Xuất danh sách sản phẩm có giao diện
    @Override
    public void Read() {
        // Tiêu đề và tên các cột
        System.out.println("\t\t\t\t\t +----DANH SÁCH SẢN PHẨM----+");
        String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản Phẩm", "Loại",
                "Tên Sản Phẩm", "Số Lượng", "Số Lượng Còn Lại", "Giá Tiền", "Trạng Thái");
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
        System.out.println(header);
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
        getListSanPham();

        // Xuất các phần tử theo cột
        for (SanPham sanpham : DSSP) {
            String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
                    sanpham.getID_Product(), sanpham.getID_TypeOfProduct(), sanpham.getName(),
                    sanpham.getAmount(), sanpham.getAmount_remaining(), sanpham.getPrice(), sanpham.getStatus());
            System.out.println(read);
        }
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
        waitConsole();
    }

    // Thêm phần tử vào mảng có giao diện
    @Override
    public void Create() {
        // Phải nhâp ID trước để kiểm tra có thông tin nào trùng ID thì báo lỗi
        System.out.println("\t\t\t\t\t +----NHẬP THÔNG TIN SẢN PHẨM----+");
        System.out.println("Nhập ID sản phẩm (sp_): ");
        setID_Product(input.nextLine());
        int check = 0;
        for (SanPham sanpham : DSSP) {
            if (getID_Product().equals(sanpham.getID_Product())) {
                check = 1;
                break;
            }
        }

        if (check == 1) {
            System.out.println("\t\t\t\t\t -MÃ SẢN PHẨM BỊ TRÙNG!");
            return;
        }

        // Nếu ID không trùng thì tiếp tục nhập các thông tin còn lại (không nhập lại ID
        // vì hàm trên đã gọi hàm setID_Product)

        System.out.println("Nhập tên sản phẩm: ");
        setName(input.nextLine());

        System.out.println("Nhập loại: ");
        setID_TypeOfProduct(input.nextLine());

        System.out.println("Nhập số lượng: ");
        setAmount(input.nextInt());

        System.out.println("Nhập số lượng còn lại: ");
        setAmount_remaining(input.nextInt());

        System.out.println("Nhập giá tiền: ");
        setPrice(input.nextInt());

        input.nextLine();
        System.out.println("Nhập trạng thái: ");
        setStatus(input.nextInt());

        // Sau khi nhập dữ liệu thì chuyển dữ liệu nhập vào thành chuỗi và nhập vào file
        // bằng hàm Stream.addOneLine)
        try {
            String input = getID_Product() + ";" + getID_TypeOfProduct() + ";" + getName() + ";" + getAmount() + ";"
                    + getAmount_remaining() + ";" + getPrice() + ";" + getStatus();
            Stream.addOneLine("Database/SanPham.txt", input);
            System.out.println("\t\t\t\t\t+----NHẬP SẢN PHẨM THÀNH CÔNG----+");
            waitConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // cập nhật dòng dữ liệu theo ID có giao diện
    @Override
    public void Update() {
        // Nhập ID để kiểm tra dòng dữ liệu với ID này có tồn tại không
        try {
            System.out.println("\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN SẢN PHẨM----+");
            System.out.print("- Mời nhập ID sản phẩm cần chỉnh sửa: ");
            String ID_Product = input.nextLine();
            SanPham s_pham = null;

            for (SanPham sanpham : DSSP) {
                if (sanpham.getID_Product().equals(ID_Product)) {
                    s_pham = sanpham;
                    break;
                }
            }

            if (s_pham == null) {
                System.out.println("\t\t\t\t\t -MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
                return;
            }

            // Nếu dòng dữ liệu có tồn tại thì xuất ra thông tin cũ
            // và tiếp tục nhập các thông tin còn lại (Update thì
            // phải nhập lại tất cả thông tin, bao gồm cả check
            // ID có trùng vs sản phẩm khác không)
            System.out.println("\t\t\t\t\t +----THÔNG TIN SẢN PHẨM TRƯỚC KHI CHỈNH SỬA----+");
            String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản Phẩm",
                    "Loại", "Tên Sản Phẩm", "Số Lượng", "Số Lượng Còn Lại", "Giá Tiền", "Trạng Thái");
            System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
            System.out.println(header);
            System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

            String row = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
                    s_pham.getID_Product(), s_pham.getID_TypeOfProduct(), s_pham.getName(),
                    s_pham.getAmount(), s_pham.getAmount_remaining(), s_pham.getPrice(), s_pham.getStatus());
            System.out.println(row);
            System.out.format(
                    "+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

            String[] data = new String[DSSP.length];

            for (int i = 0; i < DSSP.length; i++) {
                if (DSSP[i].getID_Product().equals(ID_Product)) {
                    System.out.println("Nhập thông tin sản phẩm:");

                    System.out.println("Nhập ID sản phẩm: ");
                    setID_Product(input.nextLine());

                    System.out.println("Nhập loại: ");
                    setID_TypeOfProduct(input.nextLine());

                    System.out.println("Nhập tên: ");
                    setName(input.nextLine());

                    System.out.println("Nhập số lượng: ");
                    setAmount(input.nextInt());

                    System.out.println("Nhập số lượng còn lại: ");
                    setAmount_remaining(input.nextInt());

                    System.out.println("Nhập giá tiền: ");
                    setPrice(input.nextInt());

                    setStatus(1);

                    DSSP[i].setID_Product(getID_Product());
                    DSSP[i].setID_TypeOfProduct(getID_TypeOfProduct());
                    DSSP[i].setName(getName());
                    DSSP[i].setAmount(getAmount());
                    DSSP[i].setAmount_remaining(getAmount_remaining());
                    DSSP[i].setPrice(getPrice());
                    DSSP[i].setStatus(getStatus());
                }
                data[i] = DSSP[i].getID_Product() + ";" + DSSP[i].getID_TypeOfProduct() + ";" + DSSP[i].getName() + ";"
                        + DSSP[i].getAmount() + ";" + DSSP[i].getAmount_remaining() + ";" + DSSP[i].getPrice() + ";"
                        + DSSP[i].getStatus();
            }

            // Cập nhật lại cả danh sách vào file
            try {
                Stream.addAll("Database/SanPham.txt", data);
                System.out.println("\t\t\t\t\t+----SỬA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Hàm xóa phần tử ghi lại file có giao diện
    @Override
    public void Delete() {
        try {
            // Nhập và kiểm tra ID sản phẩm có tồn tại không
            // Nếu có thì xóa luôn và thông báo thành công
            // Còn không thì thông báo ID không tồn tại
            System.out.println("\t\t\t\t\t +----XÓA THÔNG TIN SẢN PHẨM----+");
            System.out.print("-Nhập ID sản phẩm cần xóa: ");
            String ID_Product = input.nextLine();

            SanPham cur_product = null;
            for (SanPham sanpham : DSSP) {
                if (sanpham.getID_Product().equals(ID_Product)) {
                    cur_product = sanpham;
                    break;
                }
            }

            if (cur_product == null) {
                System.out.println("\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
                return;
            }

            for (int i = 0; i < DSSP.length; i++) {
                if (ID_Product.equals(DSSP[i].getID_Product())) {
                    DSSP = deleteSanPham(DSSP, i);
                    break;
                }
            }
            String[] data = new String[DSSP.length];
            for (int i = 0; i < DSSP.length; i++) {
                data[i] = DSSP[i].getID_Product() + ";" + DSSP[i].getID_TypeOfProduct() + ";" + DSSP[i].getName() + ";"
                        + DSSP[i].getAmount() + ";" + DSSP[i].getAmount_remaining() + ";" + DSSP[i].getPrice() + ";"
                        + DSSP[i].getStatus();
            }

            // Add lại nguyên danh sách đã xóa dòng dữ liệu
            try {
                Stream.addAll("Database/SanPham.txt", data);
                System.out.println("\t\t\t\t\t +----XÓA THÔNG TIN SẢN PHẨM THÀNH CÔNG----+");
                waitConsole();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InputMismatchException ei) {
            System.out.println("\t\t\t\t\t GIÁ TRỊ KHÔNG HỢP LỆ. VUI LÒNG NHẬP LẠI!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Xóa phần tử khỏi mảng
    public SanPham[] deleteSanPham(SanPham[] DSSP, int index) {
        SanPham[] newCs = new SanPham[DSSP.length - 1];
        for (int i = 0, j = 0; i < DSSP.length; i++) {
            if (i != index) {
                newCs[j++] = DSSP[i];
            }
        }
        return newCs;
    }

    // Thêm phần tử khỏi mảng
    public SanPham[] addSanPham(SanPham[] DSSP, SanPham sanpham) {
        DSSP = Arrays.copyOf(DSSP, DSSP.length + 1);
        DSSP[DSSP.length - 1] = sanpham;
        return DSSP;
    }

    // Tìm kiếm theo loại danh mục có giao diện
    @Override
    public void Search_byCategory() {
        SanPham[] result = new SanPham[0];
        System.out.println("\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t |0. Thoát                                 |");
        System.out.println("\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t |1. ID sản phẩm                           |");
        System.out.println("\t\t\t\t\t |2. Loại sản phẩm                         |");
        System.out.println("\t\t\t\t\t |3. Tên sản phẩm                          |");
        System.out.println("\t\t\t\t\t |4. Số lượng                              |");
        System.out.println("\t\t\t\t\t |5. Số lượng còn lại                      |");
        System.out.println("\t\t\t\t\t |6. Giá tiền                              |");
        System.out.println("\t\t\t\t\t |7. Trạng thái                            |");
        System.out.println("\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
        int choose = input.nextInt();

        switch (choose) {
            case 0 -> {
                return;
            }
            case 1 -> {
                input.nextLine();
                System.out.println("Nhập ID sản phẩm: ");
                String ID_Product = input.nextLine();
                for (SanPham sanpham : DSSP) {
                    if (sanpham.getID_Product().toLowerCase().contains(ID_Product.toLowerCase())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 2 -> {
                System.out.println("Nhập loại sản phẩm: ");
                input.nextLine();
                String ID_Typeofproduct = input.nextLine();
                for (SanPham sanpham : DSSP) {
                    if (sanpham.getID_TypeOfProduct().toLowerCase().contains(ID_Typeofproduct.toLowerCase())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 3 -> {
                input.nextLine();
                System.out.println("Nhập tên sản phẩm: ");
                String name = input.nextLine();
                for (SanPham sanpham : DSSP) {
                    if (sanpham.getName().toLowerCase().contains(name.toLowerCase())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 4 -> {
                input.nextLine();
                System.out.println("Nhập số lượng: ");
                Integer amount = input.nextInt();
                for (SanPham sanpham : DSSP) {
                    if (amount.equals(getAmount())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 5 -> {
                input.nextLine();
                System.out.println("Nhập số lượng còn lại: ");
                Integer amount_remaining = input.nextInt();
                for (SanPham sanpham : DSSP) {
                    if (amount_remaining.equals(getAmount_remaining())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 6 -> {
                input.nextLine();
                System.out.println("Nhập giá tiền: ");
                Integer price = input.nextInt();
                for (SanPham sanpham : DSSP) {
                    if (price.equals(getPrice())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
            case 7 -> {
                input.nextLine();
                System.out.println("Nhập trạng thái: ");
                Integer status = input.nextInt();
                for (SanPham sanpham : DSSP) {
                    if (status.equals(getStatus())) {
                        result = addSanPham(result, sanpham);
                    }
                }
            }
        }

        String header = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |", "ID Sản Phẩm", "Loại",
                "Tên Sản Phẩm", "Số Lượng", "Số Lượng Còn Lại", "Giá Tiền", "Trạng Thái");
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
        System.out.println(header);
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");

        for (SanPham sanpham : result) {
            String read = String.format("| %-15s | %-10s | %-25s | %-9s | %-16s | %-12s | %-10s |",
                    sanpham.getID_Product(), sanpham.getID_TypeOfProduct(), sanpham.getName(),
                    sanpham.getAmount(), sanpham.getAmount_remaining(), sanpham.getPrice(), sanpham.getStatus());
            System.out.println(read);
        }
        System.out.format("+-----------------+------------+---------------------------+-----------+------------------+--------------+------------+%n");
        waitConsole();
    }
}
