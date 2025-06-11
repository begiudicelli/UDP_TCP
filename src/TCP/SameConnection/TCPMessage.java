package TCP.SameConnection;

import java.io.Serializable;
import java.util.Random;

public class TCPMessage implements Serializable {
    Random r = new Random();
    private String id;
    private Integer firstNumber;
    private Integer secondNumber;

    public TCPMessage(String id){
        this.id = id;
        this.firstNumber = r.nextInt(101);
        this.secondNumber = r.nextInt(101);
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public Integer getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Integer firstNumber) {
        this.firstNumber = firstNumber;
    }

    public Integer getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(Integer secondNumber) {
        this.secondNumber = secondNumber;
    }


}