package Entity;

public class Manager extends Person {
    protected String ID_Manager;
    protected String Role;
    protected String Shift;
    protected int SLNVQL;

    public Manager(String ID_Manager, String name, Integer age, String gender, String address, String email, String SDT, String role, String shift, int SLNVQL) {
        super(name, age, gender, address, email, SDT);
        this.ID_Manager = ID_Manager;
        this.Role = role;
        this.Shift = shift;
        this.SLNVQL = SLNVQL;
    }

    public Manager() {
        super();
        ID_Manager = null;
        Role = null;
        Shift = null;
        SLNVQL = 0;
    }

    public Manager(String ID_Manager, String role, String shift, int SLNVQL) {
        this.ID_Manager = ID_Manager;
        this.Role = role;
        this.Shift = shift;
        this.SLNVQL = SLNVQL;
    }

    public String getID_Manager() {
        return ID_Manager;
    }

    public void setID_Manager(String ID_Manager) {
        this.ID_Manager = ID_Manager;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public int getSLNVQL() {
        return SLNVQL;
    }

    public void setSLNVQL(int SLNVQL) {
        this.SLNVQL = SLNVQL;
    }
}