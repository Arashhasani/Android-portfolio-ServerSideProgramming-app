package ir.dotprint.serversideprogramming;

/**
 * Created by Arash on 5/2/2019.
 */

public class MassageItem {
    String user;
    String massage;


    public MassageItem(String user, String massage) {
        this.user = user;
        this.massage = massage;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
