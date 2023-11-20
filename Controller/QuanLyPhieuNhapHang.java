package Controller;

import Entity.ChiTietPhieuNhapHang;
import Entity.PhieuNhapHang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class QuanLyPhieuNhapHang extends PhieuNhapHang implements ControllerInterface {
	public Entity.PhieuNhapHang[] dsp;
	public ChiTietPhieuNhapHang chiTiet = null;
	static Scanner sc = new Scanner(System.in);

	public QuanLyPhieuNhapHang() throws FileNotFoundException {
		super();
		getListPhieuNhapHang();
	}

	public Entity.PhieuNhapHang[] getListPhieuNhapHang() {
		String[] result = new String[0];
		try {
			result = Stream.read("Database/PhieuNhap.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dsp = new Entity.PhieuNhapHang[result.length];
		for (int i = 0; i < result.length; i++) {
			String[] row = result[i].split(";");
			dsp[i] = new Entity.PhieuNhapHang(Integer.parseInt(row[0]), row[1], row[2], row[3], LocalDate.parse(row[4]));
		}
		return dsp;
	}

	public void waitConsole() {
		System.out.println("\t\t\t\t\t\t\t\t -Ấn Enter để tiếp tục");
		sc.nextLine();
	}

	@Override
	public void Read() {
		System.out.println("\t\t\t\t\t\t\t\t +----DANH SÁCH PHIẾU NHẬP HÀNG----+");
		String header = String.format("| %-10s | %-5s | %-30s | %-10s | %-30s |", "Tổng tiền", "ID Nhân viên",
				"Nhà cung cấp", "ID Phiếu nhập", "Ngày nhập");
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");
		System.out.println(header);
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");

		getListPhieuNhapHang();

		for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
			String read = String.format("| %-10s | %-5s | %-30s | %-10s | %-30s |", PhieuNhapHang.getPrice(),
					PhieuNhapHang.getID_Worker(), PhieuNhapHang.getName_Supplier(), PhieuNhapHang.getID_PhieuNhap(),
					PhieuNhapHang.getNgayNhap());
			System.out.println(read);
		}
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");

		System.out.println("Xem thêm chi tiết phiếu nhập ?");
		System.out.println("1.Có ");
		System.out.println("2.Không ");
		System.out.print("Mời nhập:");
		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			// chiTiet.Search_byCategory();
		} else if (choose1 == 2) {
			return;
		}
		waitConsole();
	}

	@Override
	public void Create() {
		System.out.println("\t\t\t\t\t\t\t\t +----NHẬP THÔNG TIN PHIẾU NHẬP----+");
		System.out.print("Nhập mã phiếu nhập: ");
		setID_PhieuNhap(sc.nextLine());

		int check = 0;
		for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
			if (getID_PhieuNhap() == (PhieuNhapHang.getID_PhieuNhap())) {
				check = 1;
				break;
			}
		}

		if (check == 1) {
			System.out.println("\t\t\t\t\t\t\t\t -MÃ PHIẾU NHẬP BỊ TRÙNG!");
			return;
		}

		System.out.print("Nhập tổng tiền: ");
		setPrice(sc.nextInt());

		System.out.print("Nhập mã nhân viên: ");
		setID_Worker(sc.nextLine());

		System.out.print("Nhập tên nhà cung cấp: ");
		setName_Supplier(sc.nextLine());

		LocalDate date = java.time.LocalDate.now();
		String formattedDate = date.format(DateTimeFormatter.ofPattern("d/MM/uuuu"));
		setNgayNhap(LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("d/MM/uuuu")));

		try {
			String pnhString = getPrice() + ";" + getID_Worker() + ";" + getName_Supplier() + ";" + getID_PhieuNhap()
					+ ";" + getNgayNhap();
			Stream.addOneLine("Database/PhieuNhap.txt", pnhString);
			System.out.println("NHẬP PHIẾU HÀNG THÀNH CÔNG");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Thêm chi tiết hóa đơn cho hóa đơn này:");
		System.out.println("1.Có ");
		System.out.println("2.Không");
		System.out.print("Mời nhập:");

		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			boolean check1 = true;
			do {
				// chiTiet.Create();
				System.out.println("Thêm tiếp chi tiết hóa đơn:");
				System.out.println("1.Có ");
				System.out.println("2.Không");
				System.out.print("Mời nhập:");
				int choose2 = sc.nextInt();
				if (choose2 != 1)
					check1 = false;
			} while (check1);
		}
		waitConsole();
	}

	@Override
	public void Update() {
		System.out.print("Nhập ID phiếu nhập cần chỉnh sửa: ");
		String ID_PhieuNhapHang = sc.nextLine();
		Entity.PhieuNhapHang pnh = null;

		for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
			if (PhieuNhapHang.getID_PhieuNhap() == (ID_PhieuNhapHang)) {
				pnh = PhieuNhapHang;
				break;
			}
		}

		if (pnh == null) {
			System.out.println("Mã phiếu nhập không tồn tại. Xin vui lòng nhập lại!");
			return;
		}

		System.out.println("\t\t\t\t\t\t\t\t +----THÔNG TIN PHIẾU NHẬP TRƯỚC KHI CHỈNH SỬA----+");
		String header = String.format("| %-10s | %-5s | %-30s | %-10s | %-30s |", "Tổng tiền", "ID Nhân viên",
				"Nhà cung cấp", "ID Phiếu nhập", "Ngày nhập");
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");
		System.out.println(header);
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");
		String row = String.format("| %-10s | %-5s | %-30s | %-10s | %-30s |", pnh.getPrice(), pnh.getID_Worker(),
				pnh.getName_Supplier(), pnh.getID_PhieuNhap(), pnh.getNgayNhap());
		System.out.println(row);
		System.out.format(
				"+-------------+-------+--------------------------------+------------+-------------------------------+%n");

		String[] data = new String[dsp.length];

		for (int i = 0; i < dsp.length; i++) {
			if (ID_PhieuNhapHang == (dsp[i].getID_PhieuNhap())) {
				System.out.println("Nhập thông tin phiếu nhập:");

				System.out.print("Nhập tổng tiền: ");
				setPrice(sc.nextInt());

				System.out.print("Nhập mã nhân viên: ");
				setID_Worker(sc.nextLine());

				System.out.print("Nhập tên nhà cung cấp: ");
				setName_Supplier(sc.nextLine());

				System.out.print("Nhập ID phiếu nhập: ");
				setID_PhieuNhap(sc.nextLine());

				dsp[i].setPrice(getPrice());
				dsp[i].setID_Worker(getID_Worker());
				dsp[i].setName_Supplier(getName_Supplier());
				dsp[i].setID_PhieuNhap(getID_PhieuNhap());
				break;
			}
			data[i] = dsp[i].getPrice() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";"
					+ dsp[i].getID_PhieuNhap() + ";" + dsp[i].getNgayNhap();
		}

		try {
			Stream.addAll("Database/PhieuNhap.txt", data);
			System.out.println("SỬA THÔNG TIN PHIẾU NHẬP THÀNH CÔNG!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Bạn có muốn sửa chi tiết phiếu nhập cho phiếu nhập này không");
		System.out.println("1.Có ");
		System.out.println("2.Không");
		System.out.print("Mời nhập:");
		int choose1 = sc.nextInt();
		if (choose1 == 1) {
			boolean check1 = true;
			do {
				// chiTiet.Update();
				System.out.println("Sửa chi tiết phiếu nhập:");
				System.out.println("1.Có");
				System.out.println("2.Không");
				System.out.print("Mời nhập:");
				int choose2 = sc.nextInt();
				if (choose2 != 1)
					check1 = false;
			} while (check1);
		}

		waitConsole();
	}

	@Override
	public void Delete() {
		System.out.print("Nhập ID phiếu nhập cần xóa: ");
		String ID_PhieuNhapHang = sc.nextLine();

		Entity.PhieuNhapHang pnh = null;
		for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
			if (PhieuNhapHang.getID_PhieuNhap() == (ID_PhieuNhapHang)) {
				pnh = PhieuNhapHang;
				break;
			}
		}
		if (pnh == null) {
			System.out.println("ID phiếu nhập không tồn tại. Xin vui lòng nhập lại!");
			return;
		}

		for (int i = 0; i < dsp.length; i++) {
			if (ID_PhieuNhapHang == (dsp[i].getID_PhieuNhap())) {
				dsp = deletePhieuNhapHang(dsp, i);
				break;
			}
		}

		String[] data = new String[dsp.length];
		for (int i = 0; i < dsp.length; i++) {
			data[i] = dsp[i].getPrice() + ";" + dsp[i].getID_Worker() + ";" + dsp[i].getName_Supplier() + ";"
					+ dsp[i].getID_PhieuNhap() + ";" + dsp[i].getNgayNhap();
		}
		try {
			Stream.addAll("Database/PhieuNhap.txt", data);
			// chiTiet.Delete(ID_PhieuNhapHang);
			System.out.println("XÓA PHIẾU HÀNG THÀNH CÔNG!");
			waitConsole();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Entity.PhieuNhapHang[] deletePhieuNhapHang(Entity.PhieuNhapHang[] re, int index) {
		Entity.PhieuNhapHang[] newRe = new Entity.PhieuNhapHang[re.length - 1];
		for (int i = 0, j = 0; i < re.length; i++) {
			if (i != index) {
				newRe[j++] = re[i];
			}
		}
		return newRe;
	}

	public Entity.PhieuNhapHang[] addPhieuNhapHang(Entity.PhieuNhapHang[] re, Entity.PhieuNhapHang PhieuNhapHang) {
		re = Arrays.copyOf(re, re.length + 1);
		re[re.length - 1] = PhieuNhapHang;
		return re;
	}

	@Override
	public void Search_byCategory() {
		Entity.PhieuNhapHang[] result = new Entity.PhieuNhapHang[0];
		System.out.println("\t\t\t\t\t\t\t\t +--------NHẬP MỤC LỤC CẨN TÌM KIẾM--------+");
		System.out.println("\t\t\t\t\t\t\t\t |0.Thoát                                  |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.println("\t\t\t\t\t\t\t\t |1.Tiền nhập hàng                         |");
		System.out.println("\t\t\t\t\t\t\t\t |2.ID Nhân viên                           |");
		System.out.println("\t\t\t\t\t\t\t\t |3.Tên nhà cung cấp                       |");
		System.out.println("\t\t\t\t\t\t\t\t |4.ID Phiếu nhập hàng                     |");
		System.out.println("\t\t\t\t\t\t\t\t |5.Ngày nhập hàng                         |");
		System.out.println("\t\t\t\t\t\t\t\t +-----------------------------------------+");
		System.out.print("\t\t\t\t\t\t\t\t - Mời Bạn Nhập Lựa Chọn: ");
		int choose = sc.nextInt();
		if (choose == 0)
			return;
		else {
			switch (choose) {
				case 1 -> {
					sc.nextLine();
					System.out.print("Từ giá: ");
					int begin = sc.nextInt();
					System.out.print("Đến giá:");
					int end = sc.nextInt();
					for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
						if (PhieuNhapHang.getPrice() <= end && PhieuNhapHang.getPrice() >= begin) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 2 -> {
					sc.nextLine();
					System.out.print("Nhập mã nhân viên nhập hàng: ");
					String id = sc.nextLine();
					for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
						if (id.equals(PhieuNhapHang.getID_Worker())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 3 -> {
					sc.nextLine();
					System.out.print("Nhập tên nhà cung cấp: ");
					String name = sc.nextLine();
					for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
						if (name.equals(PhieuNhapHang.getName_Supplier())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 4 -> {
					System.out.print("Nhập ID phiếu nhập hàng: ");
					String id = sc.nextLine();
					for (Entity.PhieuNhapHang PhieuNhapHang : dsp) {
						if (id.equals(PhieuNhapHang.getID_PhieuNhap())) {
							result = addPhieuNhapHang(result, PhieuNhapHang);
						}
					}
				}
				case 5 -> {
					sc.nextLine();
					System.out.print("Nhập ngày bắt đầu: ");
					String day1 = sc.nextLine();
					System.out.print("Ngày kết thúc:");
					String day2 = sc.nextLine();
					DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					try {
						LocalDate start = LocalDate.parse(day1, sdf);
						LocalDate end = LocalDate.parse(day2, sdf);
						for (Entity.PhieuNhapHang pnh : dsp) {
							LocalDate day = LocalDate.parse(sdf.format(pnh.getNgayNhap()), sdf);

							if (day.isEqual(start) || day.isAfter(start) && day.isBefore(end)) {
								result = addPhieuNhapHang(result, pnh);
							}
						}
					} catch (DateTimeParseException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (result.length == 0) {
			System.out.println("Không có phiếu nhập cần tìm");
		} else {
			System.out.println("Danh sách phiếu nhập tìm được:");
			String header = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", "ID", "Giá", "Ngày mua",
					"Phương thức thanh toán", "Id khách hàng", "Nhân viên lập đơn");
			System.out.format(
					"+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
			System.out.println(header);
			System.out.format(
					"+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
			for (Entity.PhieuNhapHang pnh : result) {
				String read = String.format("| %-5s | %-15s | %-20s | %-25s | %-15s | %-20s |", pnh.getPrice(),
						pnh.getID_Worker(), pnh.getName_Supplier(), pnh.getID_PhieuNhap(), pnh.getNgayNhap());
				System.out.println(read);
			}
			System.out.format(
					"+-------+-----------------+----------------------+---------------------------+-----------------+----------------------+%n");
			waitConsole();
		}
	}
}