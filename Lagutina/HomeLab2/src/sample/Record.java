package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Record {
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

    public Record(int counting) {
        countingProperty().set(counting);
    }

    @Override
    public String toString() {
        return getCounting() + "";
    }
}
