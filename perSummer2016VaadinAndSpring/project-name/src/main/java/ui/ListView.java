package ui;


import com.vaadin.ui.SplitPanel;

public class ListView extends SplitPanel {
    public ListView(PersonList personList, PersonForm personForm) {
        setFirstComponent(personList);
        setSecondComponent(personForm);
        setSplitPosition(40);
    }
}