package both;

import java.io.Serializable;

public class Parcel implements Serializable {

    private Command command;
    private TableSelection table;
    private Object obj;

    public Parcel(){

    }

    public Parcel(Command command, TableSelection table, Object obj) {
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

    public TableSelection getTable() {
        return table;
    }

    public void setTable(TableSelection table) {
        this.table = table;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
