package com.learn.element.bsft;


import java.util.LinkedList;
import java.util.List;


public class Distributor {
    List<AdminUser> admin;

    public List<AdminUser> getAdmin() {
        if (admin == null) {
            admin = new LinkedList<>();
        }

        return admin;
    }

    public void setAdmin(List<AdminUser> admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Distributor [admin=" + admin + "]";
    }
}
