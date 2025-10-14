package model;

import java.util.Date;

public class Partner extends User {
    private boolean status;
    private Date register_date;

    public Partner() {}

    public Partner(int id, String name, String email, String role, boolean status, Date register_date) {
        super(id, name, email, role);
        this.status = status;
        this.register_date = register_date;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "status=" + status +
                ", register_date=" + register_date +
                '}';
    }
}
