package structure;

import java.util.HashMap;

public class Board extends Node {

    /**
     * Color of the board, in HEX
     */
    private String color;

    public Board(HashMap<String, ?> obj){
        super(obj);
        this.color = (String)obj.get("color");
    }

    public Board(int id, String title, String note, String color){
        super(id, title, note);
        this.color = color;
    }

    public Board(){
        
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return this.color;
    }

}
