package ui;

/**
 * Created by Alexander.Luchko on 23.08.2016.
 */

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import data.PersonContainer;
import model.Person;
import your.company.MyVaadinApplication;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PersonForm extends Form implements ClickListener {

    private Button save = new Button("Save", (ClickListener) this);
    private Button cancel = new Button("Cancel", (ClickListener) this);
    private Button edit = new Button("Edit", (ClickListener) this);
    private MyVaadinApplication app;
    private boolean newContactMode = false;
    private Person newPerson = null;
    private final ComboBox cities = new ComboBox("City");

    public PersonForm(MyVaadinApplication app) {
        this.app = app;

        // Enable buffering so that commit() must be called for the form
        // before input is written to the data. (Form input is not written
        // immediately through to the underlying object.)
        setWriteThrough(false);
/* Allow the user to enter new cities */
        cities.setNewItemsAllowed(true);
 /* We do not want to use null values */

        cities.setNullSelectionAllowed(false);
/* Add an empty city used for selecting no city */
        cities.addItem("");

/* Populate cities select using the cities in the data container */
        PersonContainer ds = app.getDataSource();
        for (Iterator<Person> it = ds.getItemIds().iterator(); it.hasNext(); ) {
            String city = (it.next()).getCity();
            cities.addItem(city);
        }
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addComponent(save);
        footer.addComponent(cancel);
        footer.addComponent(edit);
        footer.setVisible(false);

        setFooter(footer);

        setFormFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Item item, Object propertyId,
                                     Component uiContext) {
                if (propertyId.equals("city")) {
                    return cities;
                }
                Field field = super.createField(item, propertyId, uiContext);
                if (propertyId.equals("postalCode")) {
                    TextField tf = (TextField) field;
                    /*
                     * We do not want to display "null" to the user when the
                     * field is empty
                     */
                    tf.setNullRepresentation("");

                    /* Add a validator for postalCode and make it required */
                    tf.addValidator(new RegexpValidator("[1-9][0-9]{4}",
                            "Postal code must be a five digit number and cannot start with a zero."));
                    tf.setRequired(true);
                }

                return field;
            }
        });
    }

    public void buttonClick(ClickEvent event) {
        Button source = event.getButton();

        if (source == save) {
             /* If the given input is not valid there is no point in continuing */


            if (newContactMode) {
                 /* We need to add the new person to the container */
                Item addedItem = app.getDataSource().addItem(newPerson);
                 /*
                  * We must update the form to use the Item from our datasource
                  * as we are now in edit mode
                  */
                setItemDataSource(addedItem);
                //System.out.println(app.getDataSource().getItem(addedItem));
                newContactMode = false;
            }
            if (!isValid()) {
                return;
            }
            commit();
            setReadOnly(true);
        } else if (source == cancel) {
            if (newContactMode) {
                newContactMode = false;
                setItemDataSource(null);
            } else {
                discard();
            }
            setReadOnly(true);
        } else if (source == edit) {
            setReadOnly(false);
        }
    }

    @Override
    public void setItemDataSource(Item newDataSource) {
        newContactMode = false;
        if (newDataSource != null) {
            List<Object> orderedProperties = Arrays
                    .asList(PersonContainer.NATURAL_COL_ORDER);
            //This is a item and it is used in table either and changing here will
            //be do changing in a table to.
            //In form you can open item and change it
            super.setItemDataSource(newDataSource, orderedProperties);

            setReadOnly(true);
            getFooter().setVisible(true);
        } else {
            super.setItemDataSource(null);
            getFooter().setVisible(false);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        save.setVisible(!readOnly);
        cancel.setVisible(!readOnly);
        edit.setVisible(readOnly);
    }

    public void addContact() {
        // Create a temporary item for the form
        newPerson = new Person();
        setItemDataSource(new BeanItem(newPerson));
        newContactMode = true;
        setReadOnly(false);
    }

}