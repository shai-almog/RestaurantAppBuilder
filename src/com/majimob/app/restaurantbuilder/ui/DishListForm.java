package com.majimob.app.restaurantbuilder.ui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ScaleImageButton;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.majimob.app.restaurantbuilder.model.AppSettings;
import com.myrestaurant.app.model.Dish;
import com.myrestaurant.app.model.Restaurant;

public class DishListForm extends BaseNavigationForm {

    private static GridLayout createLayout() {
        if(Display.getInstance().isTablet()) {
            return new GridLayout(6, 6);
        } else {
            return new GridLayout(3, 2);
        }
    }
    
    public DishListForm(AppSettings app) {
        super(app, createLayout());
        for(Dish d : Restaurant.getInstance().menu.get().dishes) {
            addDish(d);
        }
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.bindFabToContainer(getContentPane());
        fab.addActionListener(e -> {
            Dish d = new Dish().description.set("Description of the dish...").
                    name.set("Dish Name").
                    price.set(3.0);
            d.setFullSize(Resources.getGlobalResources().getImage("food1.jpg"));
            Restaurant.getInstance().menu.get().dishes.add(d);
            addDish(d);
            revalidate();
            new DishEditForm(d).show();
        });
    }
    
    final void addDish(Dish d) {
        ScaleImageButton sb = new ScaleImageButton(d.getFullSize());
        sb.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label title = new Label(d.name.get(), "DishName");
        Label description = new Label(d.description.get(), "DishDescription");
        Container titleAndDescription = BoxLayout.encloseY(title, description);
        titleAndDescription.setUIID("BlackGradient");
        Container cnt = LayeredLayout.encloseIn(sb,
                BorderLayout.south(titleAndDescription));
        add(cnt);
        cnt.setLeadComponent(sb);
        setLayout(createLayout());
        sb.addActionListener(e -> new DishEditForm(d).show());
    }
}
