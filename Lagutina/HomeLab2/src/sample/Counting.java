package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Counting {
    private IntegerProperty counting;

    public IntegerProperty countingProperty() {
        if (counting == null) {
            counting = new SimpleIntegerProperty();
        }
        return counting;
    }

    public final void setCounting(int data) {
        countingProperty().set(data);
    }


    public final int getCounting() {
        return countingProperty().get();
    }

    public Counting(int counting) {
        countingProperty().set(counting);
    }

    @Override
    public String toString() {
        return "Очков сейчас: " + getCounting();
    }
}
