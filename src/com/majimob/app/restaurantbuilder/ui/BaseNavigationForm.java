package com.majimob.app.restaurantbuilder.ui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.majimob.app.restaurantbuilder.model.AppSettings;

/**
 * Base class for forms with side navigation and core features
 */
public class BaseNavigationForm extends Form {

    public BaseNavigationForm(AppSettings app, Layout l) {
        super(app.name.get(), l);
        init(app);
    }
    
    private void init(AppSettings app) {
        Toolbar tb = getToolbar();
        tb.addMaterialCommandToSideMenu("Dishes", FontImage.MATERIAL_RESTAURANT_MENU, e -> new DishListForm(app).show());
        tb.addMaterialCommandToSideMenu("Details", FontImage.MATERIAL_DESCRIPTION, e -> new DetailsForm(app).show());
        tb.addMaterialCommandToSideMenu("Billing", FontImage.MATERIAL_CREDIT_CARD, e -> {});
        tb.addMaterialCommandToSideMenu("App", FontImage.MATERIAL_PHONE_IPHONE, e -> new AppForm(app).show());
        
        TextField title = new TextField(app.name.get());
        title.setUIID("NavigationTitle");

        TextField tagline = new TextField(app.tagline.get());
        tagline.setUIID("Tagline");
        
        Button editBackground = new Button("Edit Background", "EditBackground");
        
        int size = Display.getInstance().convertToPixels(10);
        Button logoImage = new Button("", app.getRoundedScaledLogo(), "Container");
        
        Container titleContainer = BoxLayout.encloseY(
                title, 
                tagline,
                FlowLayout.encloseCenterMiddle(logoImage));
        Button menu = new Button("", "NavigationTitle");
        Button preview = new Button("", "NavigationTitle");
        FontImage.setMaterialIcon(menu, FontImage.MATERIAL_MENU);
        FontImage.setMaterialIcon(preview, FontImage.MATERIAL_PLAY_ARROW);
        Container titleWithCommands = BorderLayout.centerEastWest(
                titleContainer, 
                FlowLayout.encloseCenter(preview), 
                FlowLayout.encloseCenter(menu));
        
        menu.addActionListener(e -> tb.openSideMenu());
                
        ScaleImageLabel sl = new ScaleImageLabel(app.titleBackground.get());
        sl.setUIID("TitleBottomSpace");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        if(super.shouldPaintStatusBar()) {
            titleWithCommands.getUnselectedStyle().setPaddingUnit(Style.UNIT_TYPE_DIPS);
            titleWithCommands.getUnselectedStyle().setPaddingTop(3);
        }
        tb.setTitleComponent(LayeredLayout.encloseIn(
                sl, 
                BorderLayout.south(editBackground),
                titleWithCommands));
    }

    @Override
    protected boolean shouldPaintStatusBar() {
        return false;
    }

    @Override
    protected Component createStatusBar() {
        Component c = super.createStatusBar(); 
        c.setUIID("Container");
        return c;
    }

    @Override
    protected void initGlobalToolbar() {
        if(Toolbar.isGlobalToolbar()) {
            setToolbar(new Toolbar() {
                @Override
                protected void initTitleBarStatus() {   
                }
            });
        }
    }

    
}
