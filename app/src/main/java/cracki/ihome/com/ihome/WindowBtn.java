package cracki.ihome.com.ihome;

/**
 * Created by Cracki on 11/11/2018.
 */

public class WindowBtn {
    String name,status;

    public WindowBtn() {
    }

    public WindowBtn(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
