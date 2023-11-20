package Entity;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Person {
    static Scanner input = new Scanner(System.in);
    private String name, gender, address, email, SDT;
    private int age;

    public Person(String name, int age, String gender, String address, String email, String SDT) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.SDT = SDT;
        this.age = age;
    }

    public Person() {
        super();
    }

    //Getter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public void AddThongTin() {
        try {
            System.out.print("Nhập họ tên: ");
            setName(input.nextLine());
            do {
                System.out.print("Nhập tuổi: ");
                setAge(input.nextInt());
            } while (age < 0);
            do {
                input.nextLine();
                System.out.print("Nhập giới tính: ");
                setGender(input.nextLine());
            } while (!gender.equalsIgnoreCase("Nam") && !gender.equalsIgnoreCase("Nu"));
            System.out.print("Nhập địa chỉ: ");
            setAddress(input.nextLine());
            System.out.print("Nhập Email: ");
            setAddress(input.nextLine());
            System.out.print("Nhập số điện thoại: ");
            setAddress(input.nextLine());
        } catch (InputMismatchException ie) {
            System.out.println("Giá trị không hợp lệ, hãy nhập lại");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
