package cmd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Logger extends Thread {
    public interface callbackInterface {
        List<String> logData();
    }

    callbackInterface cb;
    FileWriter fw;

    public Logger(callbackInterface cb) {
        this.cb = cb;
        try {
            this.fw = new FileWriter("src/AppData.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Logger dead");
                try {
                    this.fw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            List<String> data = cb.logData();
            for (String str : data) {
                try {
                    this.fw.write(str);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
