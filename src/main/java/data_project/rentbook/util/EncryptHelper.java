package data_project.rentbook.util;

public interface EncryptHelper {
    String encrypt(String password);
    boolean isMatch(String password, String hashed);

}
