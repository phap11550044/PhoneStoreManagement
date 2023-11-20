package Controller;
import Entity.ChiTietPhieuNhapHang;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;



public class QuanLyChiTietPhieuNhapHang extends ChiTietPhieuNhapHang {
    private ChiTietPhieuNhapHang[] dsct;
    public static Scanner sc = new Scanner(System.in);

    public QuanLyChiTietPhieuNhapHang() throws FileNotFoundException {
        super();
        getListChiTietPhieuNhapHang();
    }

    public ChiTietPhieuNhapHang[] getListChiTietPhieuNhapHang() throws FileNotFoundException{
        String[] result = Stream.read("Database/ChiTietPhieuNhap.txt");
        dsct = new ChiTietPhieuNhapHang[result.length];
        for (int i = 0; i < result.length; i++){
            String row[] = result[i].split(";");
            dsct[i] = new ChiTietPhieuNhapHang(row[0], row[1], Integer.parseInt(row[2]), Integer.parseInt(row[3]));
        }
        return dsct;
    }
    public void waitConsole() {
    	System.out.println("Ấn Enter để tiếp tục");
	sc.nextLine();
    }
    
    public void Read() throws FileNotFoundException {
        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH CHI TIẾT PHIẾU NHẬP HÀNG----+");
        String header = String.format("| %-10s | %-10s | %-10s | %-25s |","ID Phiếu nhập", "ID Sản phẩm", "Số lượng", "Giá");
        System.out.format("+------------+------------+------------+---------------------------+%n");
        System.out.println(header);
        System.out.format("+------------+------------+------------+---------------------------+%n");
        
        getListChiTietPhieuNhapHang();
        for (int i = 0; i < dsct.length; i++){
            String read = String.format("| %-10s | %-10s | %-10s | %-25s |", dsct[i].getID_PhieuNhap(), dsct[i].getID_Product(), dsct[i].getAmount(), dsct[i].getPrice());
            System.out.println(read);
        }
        System.out.format("+------------+------------+------------+---------------------------+%n");
        waitConsole();
    }
    
    public void Create(){
        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN CHI TIẾT PHIẾU NHẬP----+");
        System.out.print("Nhập mã phiếu nhập: ");
        setID_PhieuNhap(sc.nextLine());
        
        System.out.print("Nhập mã sản phẩm: ");
        setID_Product(sc.nextLine());
        
        System.out.print("Nhập số lượng: ");
        setAmount(sc.nextInt());
        
        System.out.print("Nhập giá: ");
        setPrice(sc.nextInt());
        
        try {
            String sc = getID_PhieuNhap() + ";" + getID_Product() + ";" + getAmount() + ";" + getPrice();
            Stream.addOneLine("Database/ChiTietPhieuNhap.txt", sc);
            System.out.println("NHẬP CHI TIẾT PHIẾU NHẬP THÀNH CÔNG!");
            waitConsole();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void Update(){
        System.out.println("\t\t\t\t\t\t\t\t +----CHỈNH SỬA CHI TIẾT PHIẾU NHẬP----+");
        System.out.print("Nhập mã phiếu nhập cần chỉnh sửa: ");
        String ID_PhieuNhap = sc.nextLine();
        
        System.out.print("Nhập mã sản phẩm: ");
        String ID_Product = sc.nextLine();
        
        ChiTietPhieuNhapHang ctpnh = null;
        
        for (ChiTietPhieuNhapHang pnh : dsct){
            if (pnh.getID_PhieuNhap().equals(ID_PhieuNhap) && pnh.getID_Product().equals(ID_Product)){
                ctpnh = pnh;
                break;
            }
        }
        
        if(ctpnh == null){
            System.out.println("Mã phiếu nhập không tồn tại. Xin vui lòng nhập lại!"); 
            return;
        }
        
        System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN CHI TIẾT PHIẾU NHẬP TRƯỚC KHI CHỈNH SỬA----+");
        String header = String.format("| %-10s | %-10s | %-10s | %-25s |","ID Phiếu nhập", "ID Sản phẩm", "Số lượng", "Giá");
        System.out.format("+------------+------------+------------+---------------------------+%n");
        System.out.println(header);
        System.out.format("+------------+------------+------------+---------------------------+%n");
        String row = String.format("| %-10s | %-10s | %-10s | %-25s |", ctpnh.getID_PhieuNhap(), ctpnh.getID_Product(), ctpnh.getAmount(), ctpnh.getPrice());
        System.out.println(row);
        System.out.format("+------------+------------+------------+---------------------------+%n");

        String[] data = new String[dsct.length];
        
        for (int i = 0; i < dsct.length; i++){
            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap) && dsct[i].getID_Product().equals(ID_Product)){
                sc.nextLine();
                System.out.println("Nhập lại thông tin chi tiết phiếu nhập hàng: ");
                
                System.out.print("Nhập số lượng: ");
                setAmount(sc.nextInt());
                
                System.out.print("Nhập giá: ");
                setPrice(sc.nextInt());
                
                dsct[i].setAmount(getAmount());
                dsct[i].setPrice(getPrice());
            }
            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
        }
        try {
            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
            System.out.println("CHỈNH SỬA CHI TIẾT PHIẾU NHẬP THÀNH CÔNG!");
            waitConsole();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void Delete(){
        String ID_PhieuNhap = sc.nextLine();
        for (int i = 0; i < dsct.length; i++){
            if (dsct[i].getID_PhieuNhap().equals(ID_PhieuNhap)){
                dsct = deleteChiTietPhieuNhapHang(dsct, i);
            }
        }
        
        String[] data = new String[dsct.length];
        for (int i = 0; i < dsct.length; i++){
            data[i] = dsct[i].getID_PhieuNhap() + ";" + dsct[i].getID_Product() + ";" + dsct[i].getAmount() + ";" + dsct[i].getPrice();
        }
        
        try {
            Stream.addAll("Database/ChiTietPhieuNhap.txt", data);
            System.out.println("XÓA CHI TIẾT HÓA ĐƠN THÀNH CÔNG!");
            waitConsole();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public ChiTietPhieuNhapHang[] deleteChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, int index){
        ChiTietPhieuNhapHang[] newCT = new ChiTietPhieuNhapHang[dsct.length - 1];
        for (int i = 1, j = 0; i < dsct.length; i++){
            if (i != index){
                newCT[j++] = dsct[i];
            }
        }
        return newCT;
    }
    
    public ChiTietPhieuNhapHang[] addChiTietPhieuNhapHang(ChiTietPhieuNhapHang[] dsct, ChiTietPhieuNhapHang pnh){
        dsct = Arrays.copyOf(dsct, dsct.length + 1);
        dsct[dsct.length - 1] = pnh;
        return dsct;
    }
    
    public void Search_byCategory(){
        ChiTietPhieuNhapHang[] result = new ChiTietPhieuNhapHang[0];
	System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
        System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t\t |1.ID Phiếu nhập hàng                     |");
        System.out.println("\t\t\t\t\t\t\t\t |2.ID Sản phẩm                            |");
        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
	int choose = sc.nextInt();
        if (choose == 0) return;
            else{
                switch (choose) {
                    case 1 -> {
	            	sc.nextLine();
	                System.out.print("Nhập mã phiếu nhập hàng: ");
	                String id = sc.nextLine();
                        for (int i = 0; i < dsct.length; i++){
                            if (id.equals(dsct[i].getID_PhieuNhap())){
                                result = addChiTietPhieuNhapHang(result, dsct[i]);
                            }
                        }
                    }
	            case 2 -> {
	                sc.nextLine();
	                System.out.print("Nhập mã sản phẩm: ");
	                String id = sc.nextLine();
                        for (int i = 0; i < dsct.length; i++) {
                            if (id.equals(dsct[i].getID_Product())){
                                result = addChiTietPhieuNhapHang(result, dsct[i]);
                            }
                        }
                    }
                }
        }
    }
}
