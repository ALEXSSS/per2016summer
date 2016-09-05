package ui;

/**
 * Created by Alexander.Luchko on 23.08.2016.
 */

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class HelpWindow extends Window {
    private static final String HELP_HTML_SNIPPET = "This is "
            + "an application built during <strong><a href=\""
            + "http://dev.vaadin.com/\">Vaadin</a></strong> "
            + "tutorial. Hopefully it don't need any real help.";

    public HelpWindow() {
        setCaption("Welcome to my project");
        addComponent(new Label(HELP_HTML_SNIPPET, Label.CONTENT_XHTML));
        setPositionX(400);
        setPositionY(400);
    }

}
