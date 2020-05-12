package sample;

public class Record {
    private int recordNow = 15;
    private int bestRecord1;
    private int bestRecord2;
    private int bestRecord3;

    public Record(int recordNow, int bestRecord1, int bestRecord2, int bestRecord3) {
        this.recordNow = recordNow;
        this.bestRecord1 = bestRecord1;
        this.bestRecord2 = bestRecord2;
        this.bestRecord3 = bestRecord3;
    }


    public int getRecordNow() {
        return recordNow;
    }

    public void setRecordNow(int recordNow) {
        this.recordNow = recordNow;
    }

    public int getBestRecord1() {
        return bestRecord1;
    }

    public void setBestRecord1(int bestRecord1) {
        this.bestRecord1 = bestRecord1;
    }

    public int getBestRecord2() {
        return bestRecord2;
    }

    public void setBestRecord2(int bestRecord2) {
        this.bestRecord2 = bestRecord2;
    }

    public int getBestRecord3() {
        return bestRecord3;
    }

    public void setBestRecord3(int bestRecord3) {
        this.bestRecord3 = bestRecord3;
    }
}
