package structure;

import java.util.ArrayList;

public final class Result {

  private ArrayList<Request> list = new ArrayList<Request>();
  private ArrayList<Request> exceptionList = new ArrayList<Request>();
  private boolean excepted = false;

  public Result() {}

  public void add(Request add) {
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
    for (Request each : list) {
      if (each.isExcepted()) {
        this.setException(true);
        this.exceptionList.add(each);
      }
    }
    this.setException(false);
  }

  public boolean isExcepted() {
    return this.excepted;
  }

  private void setException(boolean is) {
    this.excepted = is;
  }
}
