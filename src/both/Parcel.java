package both;

import java.io.Serializable;
/**
 * Allows for the "package/ information flow" Between the client and the server package holding the various enums
 * @author Simbarashe Nyakambangwe
 * SID: 8316064
 */
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
