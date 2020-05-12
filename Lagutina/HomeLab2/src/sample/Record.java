package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Record {
    private IntegerProperty counting;
//    private int bestRecord1;
//    private int bestRecord2;
//    private int bestRecord3;

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
//        this.bestRecord1 = bestRecord1;
//        this.bestRecord2 = bestRecord2;
//        this.bestRecord3 = bestRecord3;
    }

    @Override
    public String toString() {
        return getCounting() + "";
    }
}
