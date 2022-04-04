package both;

import java.io.Serializable;

public class Parcel implements Serializable {

    private Command command;
    private TableSelection table;
    private Object object;

    public Parcel(){

    }

    public Parcel(Command command, TableSelection table, Object object) {
        this.command = command;
        this.table = table;
        this.object = object;
    }

    public Command getCommand() {
        return command;
    }

    public TableSelection getTable() {
        return table;
    }

    public void setTable(TableSelection table) {
        this.table = table;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
