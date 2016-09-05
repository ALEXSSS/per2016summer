package ui;

/**
 * Created by Alexander.Luchko on 22.08.2016.
 */
import com.vaadin.data.Property;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import data.PersonContainer;
import model.Person;
import your.company.MyVaadinApplication;

public class PersonList extends Table {
    public PersonList(MyVaadinApplication app) {
        setSizeFull();
        setContainerDataSource(app.getDataSource());
        setSelectable(true);
        setImmediate(true);
        addListener((Property.ValueChangeListener) app);
        setColumnHeaders(PersonContainer.COL_HEADERS_ENGLISH);
        setVisibleColumns(PersonContainer.NATURAL_COL_ORDER);
        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(true);
        addGeneratedColumn("email", new ColumnGenerator() {
            public Component generateCell(Table source, Object itemId,
                                          Object columnId) {
                Person p = (Person) itemId;
                Link l = new Link();
                l.setResource(new ExternalResource("mailto:" + p.getEmail()));
                l.setCaption(p.getEmail());
                return l;
            }
        });
    }
}
