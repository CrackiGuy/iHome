package cracki.ihome.com.ihome;

import java.io.Serializable;

/**
 * Created by Cracki on 11/6/2018.
 */

public class SwitchBtn implements Serializable {
    String name,io,status;

    public SwitchBtn() {
    }

    public SwitchBtn(String name, String io, String status) {
        this.name = name;
        this.io = io;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIo() {
        return io;
    }

    public void setIo(String io) {
        this.io = io;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
