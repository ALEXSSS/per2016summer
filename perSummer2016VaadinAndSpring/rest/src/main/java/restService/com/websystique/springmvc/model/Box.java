package restService.com.websystique.springmvc.model;
//define another way representation json response
//is it more convenient?




import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Alexander.Luchko on 13.07.2016.
 */
public class Box<T> {
    private final List<T> listOfItems;
    private final int size;
    private String nameTable;


    private String restError;

    //for empty box
    public Box(String nameTable) {
        listOfItems = null;
        size = 0;
        this.nameTable=nameTable;
    }

    public Box(@Nonnull List<T> list, String nameTable) {
        this.listOfItems = list;
        this.size = list.size();
        this.restError = "OK";
        this.nameTable=nameTable;
    }

    public String getRestError() {
        return restError;
    }

    public Box<T> setRestErrorAndGetThis(String restError) {
        this.restError = restError;
        return this;
    }

    public List<T> getListOfItems() {
        return listOfItems;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }


    public int getSize() {
        return size;
    }
}
