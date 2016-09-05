/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package your.company;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import data.PersonContainer;
import data.SearchFilter;
import ui.*;


/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinApplication extends Application implements Button.ClickListener,
        Property.ValueChangeListener, ItemClickEvent.ItemClickListener {
    private ListView listView = null;
    private NavigationTree tree = new NavigationTree(this);
    private Button newContact = new Button("Add contact");
    private Button search = new Button("Search");
    private Button share = new Button("Share");
    private Button help = new Button("Help");
    private SplitPanel horizontalSplit = new SplitPanel(
            SplitPanel.ORIENTATION_HORIZONTAL);
    private PersonList personList = null;
    private PersonForm personForm = null;
    private HelpWindow helpWindow = null;
    private PersonContainer dataSource = PersonContainer.createWithTestData();
    private SearchView searchView = null;


    @Override
    public void init() {
        buildMainLayout();
        setTheme("runo");
    }

    private void buildMainLayout() {
        //setTheme("contacts");
        setMainWindow(new Window("My Demo Application"));
        setMainComponent(getListView());
        horizontalSplit.setFirstComponent(tree);
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        getMainWindow().addWindow(getHelpWindow());
        //getMainWindow().addWindow(getSharingOptions());

        layout.addComponent(createToolbar());
        layout.addComponent(horizontalSplit);

        /* Allocate all available extra space to the horizontal split panel */

        layout.setExpandRatio(horizontalSplit, 1);
        /* Set the initial split position so we can have a 200 pixel menu to the left */

        horizontalSplit.setSplitPosition(200, SplitPanel.UNITS_PIXELS);

        getMainWindow().setContent(layout);

    }

    public HelpWindow getHelpWindow() {
        return new HelpWindow();
    }

    public SharingOptions getSharingOptions() {
        return new SharingOptions();
    }

    public HorizontalLayout createToolbar() {
        share.setIcon(new ThemeResource("icons/32/users.png"));
        help.setIcon(new ThemeResource("icons/32/help.png"));
        newContact.setIcon(new ThemeResource("icons/32/document-add.png"));
        search.setIcon(new ThemeResource("icons/32/folder-add.png"));
        HorizontalLayout lo = new HorizontalLayout();
        lo.addComponent(newContact);
        lo.addComponent(search);
        lo.addComponent(share);
        lo.addComponent(help);
        lo.setMargin(true);
        lo.setSpacing(true);
        lo.setStyleName("toolbar");
        search.addListener((Button.ClickListener) this);
        newContact.addListener((Button.ClickListener) this);
        share.addListener((Button.ClickListener) this);
        help.addListener((Button.ClickListener) this);
        return lo;

    }

    public void search(SearchFilter searchFilter) {
        // clear previous filters
        getDataSource().removeAllContainerFilters();
        // filter contacts with given filter
        getDataSource().addContainerFilter(searchFilter.getPropertyId(),
                searchFilter.getTerm(), true, false);
        showListView();
        getMainWindow().showNotification(
                "Searched for " + searchFilter.getPropertyId() + "=*"
                        + searchFilter.getTerm() + "*, found "
                        + getDataSource().size() + " item(s).",
                Window.Notification.TYPE_TRAY_NOTIFICATION);
    }

    private ListView getListView() {
        if (listView == null) {
            personList = new PersonList(this);
            personForm = new PersonForm(this);
            listView = new ListView(personList, personForm);
        }
        return listView;
    }

    public PersonContainer getDataSource() {
        return dataSource;
    }

    private void setMainComponent(Component c) {
        horizontalSplit.setSecondComponent(c);
    }

    private SearchView getSearchView() {
        if (searchView == null) {
            searchView = new SearchView(this);
        }
        return searchView;
    }


    public void buttonClick(ClickEvent event) {
        final Button source = event.getButton();
        if (source == search) {
            showSearchView();
        }
        if (source == newContact) {
            addNewContanct();
        }
    }

    private void showSearchView() {
        setMainComponent(getSearchView());
    }

    private void showListView() {
        setMainComponent(getListView());
    }

    public void valueChange(Property.ValueChangeEvent event) {
        Property property = event.getProperty();
        if (property == personList) {
            Item item = personList.getItem(personList.getValue());
            if (item != personForm.getItemDataSource()) {
                personForm.setItemDataSource(item);
            }
        }
    }

    private void addNewContanct() {
        showListView();
        personForm.addContact();
    }

    public void itemClick(ItemClickEvent event) {
        if (event.getSource() == tree) {
            Object itemId = event.getItemId();
            if (itemId != null) {
                if (itemId == NavigationTree.SHOW_ALL) {
                    // clear previous filters
                    getDataSource().removeAllContainerFilters();
                    showListView();
                } else if (itemId == NavigationTree.SEARCH) {
                    showSearchView();
                } else if (itemId instanceof SearchFilter) {
                    search((SearchFilter) itemId);
                }
            }
        }
    }

    public void saveSearch(SearchFilter searchFilter) {
        tree.addItem(searchFilter);
        tree.setParent(searchFilter, NavigationTree.SEARCH);
        // mark the saved search as a leaf (cannot have children)
        tree.setChildrenAllowed(searchFilter, false);
        // make sure "Search" is expanded
        tree.expandItem(NavigationTree.SEARCH);
        // select the saved search
        tree.setValue(searchFilter);
    }


}
