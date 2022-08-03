package com.majimob.app.restaurantbuilder.ui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.myrestaurant.app.model.Dish;

public class DishEditForm extends Form {
    public DishEditForm(Dish d) {
        super(d.name.get(), new BorderLayout());
        Toolbar tb = getToolbar();
        Button back = new Button("", "Title");
        Button ok = new Button("", "Title");
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, 5);
        FontImage.setMaterialIcon(ok, FontImage.MATERIAL_CHECK, 5);
        
        TextField title = new TextField(d.name.get());
        title.setUIID("Title");
        tb.setTitleComponent(
                BorderLayout.centerEastWest(
                        BoxLayout.encloseY(title), 
                        FlowLayout.encloseRight(ok), 
                        FlowLayout.encloseIn(back)));
        tb.setUIID("BlueGradient");
        ScaleImageLabel backgroundImage = new ScaleImageLabel(d.getFullSize()) {
            @Override
            protected Dimension calcPreferredSize() {
                Dimension d = super.calcPreferredSize(); 
                d.setHeight(Math.min(d.getHeight(), Display.getInstance().convertToPixels(38)));
                return d;
            }
        };
        backgroundImage.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(BorderLayout.NORTH, backgroundImage);
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_COLLECTIONS);
        Style fabStyle = fab.getAllStyles();
        fab.bindFabToContainer(getContentPane(), RIGHT, TOP);
        final Form previous = Display.getInstance().getCurrent();
        Component.setSameHeight(tb, backgroundImage);
        fabStyle.setMarginUnit(Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS, Style.UNIT_TYPE_DIPS);
        fabStyle.setMarginTop(tb.getPreferredH() - fab.getPreferredH() / 2);
        
        Button delete = new Button("Delete Dish", "DeleteButton");
        FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
        delete.addActionListener(e -> previous.showBack());
        ok.addActionListener(e -> previous.showBack());
        back.addActionListener(e -> previous.showBack());
        add(BorderLayout.SOUTH, delete);
        
        TextField price = new TextField("" + d.price.get(), "Price", 5, TextField.DECIMAL);
        TextField description = new TextField(d.description.get(), "Description", 5, TextField.ANY);
        description.setSingleLineTextArea(false);
        description.setRows(4);
        add(BorderLayout.CENTER, BoxLayout.encloseY(
                new Label("Price", "TextFieldLabel"),
                price,
                new Label("Description", "TextFieldLabel"),
                description
        ));
    }
    

    @Override
    protected void initGlobalToolbar() {
        setToolbar(new Toolbar(true));
    }    
}
