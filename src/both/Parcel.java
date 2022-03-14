package both;

import java.io.Serializable;

public class Parcel implements Serializable {

    private Command command;
    private Table table;
    private Object obj;

    public Parcel(){

    }

    public Parcel(Command command, Table table, Object obj) {
        this.command = command;
        this.table = table;
        this.obj = obj;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
