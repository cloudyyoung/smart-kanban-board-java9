package structure;

import java.util.ArrayList;

final public class Result{

    private ArrayList<Request> list = new ArrayList<Request>();
    private ArrayList<Request> exceptionList = new ArrayList<Request>();
    private boolean exception = false;

    public Result(){
        
    }

    public void add(Request add){
        this.list.add(add);
        this.checkException();
    }

    public void remove(Request remove){
        this.list.remove(remove);
        this.checkException();
    }

    public void remove(int index){
       this.list.remove(index);
       this.checkException(); 
    }

    private void checkException(){
        exceptionList.clear();
        for(Request each : list){
            if(each.isException()){
                this.setException(true);
                this.exceptionList.add(each);
            }
        }
        this.setException(false);
    }

    public boolean isException(){
        return this.exception;
    }

    private void setException(boolean is){
        this.exception = is;
    }

}