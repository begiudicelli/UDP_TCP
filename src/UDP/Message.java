package UDP;

import java.io.Serializable;
import java.util.Random;

public class Message implements Serializable {
    Random r = new Random();
    private Integer firstNumber;
    private Integer secondNumber;

    public Message(){
        this.firstNumber = r.nextInt(101);
        this.secondNumber = r.nextInt(101);
    }

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
