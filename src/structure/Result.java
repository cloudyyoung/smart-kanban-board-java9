package structure;

import java.util.ArrayList;

public final class Result {

  private ArrayList<Request> list = new ArrayList<Request>();
  private ArrayList<Request> exceptionList = new ArrayList<Request>();
  private boolean excepted = false;
  private boolean succeeded = false;

  public Result() {}

  public void add(Request add) {
    if(add == null){
      return;
    }
    this.list.add(add);
    this.checkException();
  }

  public void remove(Request remove) {
    this.list.remove(remove);
    this.checkException();
  }

  public void remove(int index) {
    this.list.remove(index);
    this.checkException();
  }

  private void checkException() {
    exceptionList.clear();
    boolean changed = false;
    for (Request each : list) {
      if (each.isExcepted()) {
        this.setException(true);
        this.exceptionList.add(each);
        changed = true;
      }
      if(!each.isSucceeded()){
        this.setSucceeded(false);
        changed = true;
      }
      if(changed){
          return;
      }
    }
    this.setException(false);
    this.setSucceeded(true);
}

  public boolean isExcepted() {
    return this.excepted;
  }

  public boolean isSucceeded(){
      return this.succeeded;
  }

  private void setException(boolean is) {
    this.excepted = is;
  }

  private void setSucceeded(boolean is) {
    this.succeeded = is;
  }

  public ArrayList<Request> getExceptions(){
    return new ArrayList<Request>(this.exceptionList);
  }

  public Request getException(){
    if(this.exceptionList.size() > 0){
      return this.exceptionList.get(0);
    }else{
      return null;
    }
  }

  public String toString(){
      return "Result ("
      + "isSucceeded: "
      + this.isSucceeded()
      + ", isExcepted: "
      + this.isExcepted()
      + ", Request: "
      + this.list.toString()
      + ")";
  }
}
