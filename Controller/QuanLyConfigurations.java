package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import Entity.Configuration;

public class QuanLyConfigurations extends Configuration implements ControllerInterface {
	 public Configuration[] cfg;
	    public QuanLyConfigurations() {
	       super();
	   }
	    static Scanner input = new Scanner(System.in);
	    public Configuration[] getListConfigurations() {
	        String[] result = new String[0];
			try {
				result = Stream.read("Database/CauHinh.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        cfg = new Configuration[result.length];
	        for (int i = 0; i < result.length; i++) {
	            String[] row = result[i].split(";");
	            cfg[i] = new Configuration(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7],row[8]);
	        }
	        return cfg;
	    }
	    public void waitConsole() {
	        input.nextLine();
	        System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
	        input.nextLine();
	    }
	    
	    @Override
	    public void Read() {
	        System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH THÔNG TIN CẤU HÌNH----+");
	        String header = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-15s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "Bộ nhớ", "Dung lượng pin", "Camera trước", "Camera Sau");
	        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
	        System.out.println(header);
	        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");

	        getListConfigurations();
	        for (Configuration configuation : cfg) {
	                String read = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-15s |",
					 	configuation.getIMEI_Code(), configuation.getScreen(), configuation.getOS(), configuation.getChip(), configuation.getRam(), configuation.getROM(), configuation.getBattery(), configuation.getFrontCamera(), configuation.getRearCamera());
	                System.out.println(read);
	        }
	        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
	        waitConsole();
	    }
	    
	    @Override
	    public void Create() {
	        System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN CẤU HÌNH SẢN PHẨM----+");
	        System.out.print("Nhập mã sản phẩm: ");
	        setIMEI_Code(input.nextLine());

	        int check = 0;
	        for (Configuration configuation : cfg) {
	            if(getIMEI_Code().equals(configuation.getIMEI_Code())) {
	                check = 1;
	                break;
	            }
	        }

	        if (check == 1) {
	            System.out.println("\t\t\t\t\t\t\t\t -MÃ SẢN PHẨM BỊ TRÙNG!");
	            return;
	        }
	        System.out.print("Nhập Screen: ");
	        setScreen(String.valueOf((input.nextLine())));

	        System.out.print("Nhập OS: ");
	        setOS(input.nextLine());

	        System.out.print("Nhập Chip: ");
	        setChip(input.nextLine());

	        System.out.print("Nhập Ram: ");
	        setRam(input.nextLine());

	        System.out.print("Nhập Battery: ");
	        setBattery(input.nextLine());

	        System.out.print("Nhập Front Camera: ");
	        setFrontCamera(input.nextLine());

	        System.out.print("Nhập Rear Camera: ");
	        setRearCamera(input.nextLine());

	        try {
	            String input = getIMEI_Code() + ";" + getScreen() + ";" + getOS() + ";" + getChip() + ";" + getRam() +";" + getBattery() + ";" + getFrontCamera() + ";" + getRearCamera();
	            Stream.addOneLine("Database/CauHinh.txt", input);
	            System.out.println("\t\t\t\t\t\t\t\t +----NHẬP CONFIGURATION THÀNH CÔNG----+");
	            waitConsole();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    @Override
	    public void Update() {
	        try {
	            System.out.println("\t\t\t\t\t\t\t\t +----CẬP NHẬT LẠI THÔNG TIN CONFIGURATION----+");
	            System.out.print("\t\t\t\t\t\t\t\t - Mời bạn nhập mã sản phẩm cần chỉnh sửa: ");
	            String IMEIcode = input.nextLine();
	            Configuration IMEIcode_cur = null;

	            for (Configuration config : cfg) {
	                if (config.getIMEI_Code().equals(IMEIcode)) {
	                    IMEIcode_cur = config;
	                    break;
	                }
	            }

	            if(IMEIcode_cur == null) {
	                System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
	                return;
	            }

	            System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN CONFIGURATION TRƯỚC KHI CHỈNH SỬA----+");
	            String header = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-20s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "Bộ nhớ", "Dung lượng pin", "Camera trước", "Camera Sau");
	            System.out.format("+------------------------------------------------------------------------------------------------------------------------------------------------------------+%n");
	            System.out.println(header);

	            String row = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-20s |",
	                    IMEIcode_cur.getIMEI_Code(), IMEIcode_cur.getScreen(), IMEIcode_cur.getOS(), IMEIcode_cur.getChip(), IMEIcode_cur.getRam(), IMEIcode_cur.getROM(),
	                    IMEIcode_cur.getBattery(), IMEIcode_cur.getFrontCamera(), IMEIcode_cur.getRearCamera());
	            System.out.println(row);

	            String [] data = new String[cfg.length];
	            for (int i = 0; i < cfg.length; i++) {
	                if (IMEIcode.equals(getIMEI_Code())) {
	                    input.nextLine();
	                    System.out.print("Nhập Screen: ");
	                    setScreen(input.nextLine());

	                    System.out.print("Nhập OS: ");
	                    setOS(input.nextLine());

	                    System.out.print("Nhập Chip: ");
	                    setChip(input.nextLine());

	                    System.out.print("Nhập Ram: ");
	                    setRam(input.nextLine());

	                    System.out.print("Nhập Battery: ");
	                    setBattery(input.nextLine());

	                    System.out.print("Nhập Front Camera: ");
	                    setFrontCamera(input.nextLine());

	                    System.out.print("Nhập Rear Camera: ");
	                    setRearCamera(input.nextLine());

	                    cfg[i].setScreen(getScreen());
	                    cfg[i].setOS(getOS());
	                    cfg[i].setChip(getChip());
	                    cfg[i].setRam(getRam());
	                    cfg[i].setBattery(getBattery());
	                    cfg[i].setFrontCamera(getFrontCamera());
	                    cfg[i].setRearCamera(getRearCamera());
	                }
	                data[i] = cfg[i].getIMEI_Code() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOS() + ";" + cfg[i].getChip() + ";" + cfg[i].getRam() + ";"+ cfg[i].getBattery() + ";" + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
	            }
	            try {
	                Stream.addAll("Database/CauHinh.txt", data);
	                System.out.println("\t\t\t\t\t\t\t\t+----SỬA THÔNG TIN CONFIGURATION THÀNH CÔNG----+");
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
	            System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN CONFUGURATION----+");
	            System.out.println("\t\t\t\t\t\t\t\t -Nhập mã sản phẩm cần xóa: ");
	            String IMEIcode = input.nextLine();

	            Configuration cur_product = null;
	            for (Configuration config : cfg) {
	                if (config.getIMEI_Code().equals(IMEIcode)) {
	                    cur_product = config;
	                    break;
	                }
	            }
	            for (int i = 0; i < cfg.length; i++) {
	                if (IMEIcode.equals(cfg[i].getIMEI_Code())) {
	                    cfg = deleteConfiguration(cfg, i);
	                    break;
	                }
	            }

	            if (cur_product == null) {
	                System.out.println("\t\t\t\t\t\t\t\t - MÃ SẢN PHẨM KHÔNG TỒN TẠI!");
	                return;
	            }

	            for (int i = 0; i < cfg.length; i++) {
	                if (IMEIcode.equals(cfg[i].getIMEI_Code())) {
	                    cfg = deleteConfiguration(cfg, i);
	                    break;
	                }
	            }
	            String[] data = new String[cfg.length];
	            for (int i = 0; i < cfg.length; i++) {
	                data[i] = cfg[i].getIMEI_Code() + ";" + cfg[i].getScreen() + ";" + cfg[i].getOS() + ";" + cfg[i].getChip() + ";" + cfg[i].getRam() + ";" + cfg[i].getBattery() + ";" + cfg[i].getFrontCamera() + ";" + cfg[i].getRearCamera();
	            }
	            try {
	                Stream.addAll("Database/CauHinh.txt", data);
	                System.out.println("\t\t\t\t\t\t\t\t +----XÓA THÔNG TIN CONFIGURATION THÀNH CÔNG----+");
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
	    public Configuration[] deleteConfiguration(Configuration[] cfg, int index) {
	        Configuration[] newCs = new Configuration[cfg.length - 1];
	        for (int i = 0, j = 0; i < cfg.length; i++) {
	            if (i != index) {
	                newCs[j++] = cfg[i];
	            }
	        }
	        return newCs;
	    }
	    public Configuration[] addConfiguration(Configuration[] cfg, Configuration configuation) {
	        cfg = Arrays.copyOf(cfg, cfg.length + 1);
	        cfg[cfg.length -1] = configuation;
	        return cfg;
	    }
	    
	    @Override
	    public void Search_byCategory() {
	        Configuration[] result = new Configuration[0];
	        System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
	        System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
	        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
	        System.out.println("\t\t\t\t\t\t\t\t |1.Mã sản phẩm                            |");
	        System.out.println("\t\t\t\t\t\t\t\t |2.Màn hình                               |");
	        System.out.println("\t\t\t\t\t\t\t\t |3.Hệ điều hành                           |");
	        System.out.println("\t\t\t\t\t\t\t\t |4.Cấu hình chip                          |");
	        System.out.println("\t\t\t\t\t\t\t\t |5.RAM                                    |");
	        System.out.println("\t\t\t\t\t\t\t\t |6.Dung lượng pin                         |");
	        System.out.println("\t\t\t\t\t\t\t\t |7.Số pixel camera trước                  |");
	        System.out.println("\t\t\t\t\t\t\t\t |8.Số pixel camera sau                    |");
	        System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
	        System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
	        int choose = input.nextInt();
	        if (choose == 0)
	            return;
	        else {
	                switch (choose) {
	                    case 1 -> {
	                        input.nextLine();
	                        System.out.print("Nhập mã sản phẩm: ");
	                        String ID_cauhinh = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getIMEI_Code().toLowerCase().contains(ID_cauhinh.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 2 -> {
	                        input.nextLine();
	                        System.out.print("Nhập Screen: ");
	                        String Screen = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getScreen().toLowerCase().contains(Screen.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 3 -> {
	                        System.out.print("Nhập OS: ");
	                        input.nextLine();
	                        String OS = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getOS().toLowerCase().contains(OS.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 4 -> {
	                        input.nextLine();
	                        System.out.print("Nhập Chip: ");
	                        String Chip = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getChip().toLowerCase().contains(Chip.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 5 -> {
	                        System.out.print("Nhập Ram: ");
	                        input.nextLine();
	                        String Ram = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getRam().toLowerCase().contains(Ram.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 6 -> {
	                        System.out.print("Nhập Battery: ");
	                        input.nextLine();
	                        String Battery = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getBattery().toLowerCase().contains(Battery.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 7 -> {
	                        System.out.print("Nhập Front Camera: ");
	                        input.nextLine();
	                        String FrontCamera = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getFrontCamera().toLowerCase().contains(FrontCamera.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                    case 8 -> {
	                        System.out.print("Nhập Rear Camera: ");
	                        input.nextLine();
	                        String RearCamera = input.nextLine();
	                        for (Configuration configuation : cfg) {
	                            if (configuation.getRearCamera().toLowerCase().contains(RearCamera.toLowerCase())) {
	                                result = addConfiguration(result, configuation);
	                            }
	                        }
	                    }
	                }
	            }
	        System.out.println("\t\t\t\t\t\t\t\t +----TẤT CẢ THÔNG TIN ĐÃ TÌM ĐƯỢC----+");
	        String header = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-20s |", "ID", "Màn hình", "Hệ điều hành", "Cấu hình", "RAM", "Bộ nhớ", "Dung lượng pin", "Camera trước", "Camera Sau");
	        System.out.format("+-------+---------------------------+------+-----------+--------------------------------+---------------------------+-----------------+----------------------+%n");
	        System.out.println(header);

	        for (Configuration configuation : result) {
	            String read = String.format("| %-5s | %-10s | %-20s | %-20s | %-20s | %-10s | %-10s | %-20s | %-20s |", configuation.getIMEI_Code(), configuation.getScreen(), configuation.getOS(),
	                    configuation.getChip(), configuation.getRam(), configuation.getROM(), configuation.getBattery(), configuation.getFrontCamera(), configuation.getRearCamera());
	            System.out.println(read);
	        }
            waitConsole();
	    }
}
