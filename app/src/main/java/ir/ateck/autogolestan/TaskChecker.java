package ir.ateck.autogolestan;

public class TaskChecker {
    private boolean isitdone = false;

    public void taskDone(){
        isitdone = true;
    }

    public boolean isItDone() {
        return isitdone;
    }
}
