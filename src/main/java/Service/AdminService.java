package Service;

import Model.AdminEntity;
import Repository.AdminRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminService {

    AdminRepository adminRepository;

    public AdminService() {
        this.adminRepository = new AdminRepository();
    }

    public boolean validateAdmin(String user, String password) {
        AdminEntity admin = adminRepository.findByUser(user);
        if (admin != null && password.equals(admin.getPassword())) {
            return true;
        }
        return false;
    }

}