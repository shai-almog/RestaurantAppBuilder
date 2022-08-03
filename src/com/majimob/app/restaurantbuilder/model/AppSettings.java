package com.majimob.app.restaurantbuilder.model;

import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.GeneralPath;

/**
 * Includes basic details about the app we are building
 */
public class AppSettings implements PropertyBusinessObject {
    public final Property<String, AppSettings> name = new Property<>("name", "My Restaurant");
    public final Property<String, AppSettings> tagline = new Property<>("tagline", "The place to eat");
    public final Property<Image, AppSettings> logo = new Property<Image, AppSettings>("logo", null) {
        @Override
        public AppSettings set(Image value) {
            if(value != null) {
                int mm = Display.getInstance().convertToPixels(2f);
                int size = Display.getInstance().convertToPixels(11);

                GeneralPath gp = new GeneralPath();
                float x = 0;
                float radius = mm;
                float y = 0;
                float widthF = size;
                float heightF = size;
                gp.moveTo(x + radius, y);
                gp.lineTo(x + widthF - radius, y);
                gp.quadTo(x + widthF, y, x + widthF, y + radius);
                gp.lineTo(x + widthF, y + heightF - radius);
                gp.quadTo(x + widthF, y + heightF, x + widthF - radius, y + heightF);
                gp.lineTo(x + radius, y + heightF);
                gp.quadTo(x, y + heightF, x, y + heightF - radius);
                gp.lineTo(x, y + radius);
                gp.quadTo(x, y, x + radius, y);
                gp.closePath();            
                Image mask = Image.createImage(size, size, 0xff000000);
                Graphics g = mask.getGraphics();
                g.setColor(0xffffff);
                g.setAntiAliased(true);
                g.fillShape(gp);
                Object m = mask.createMask();
                roundedScaledLogo = value.fill(size, size).applyMask(m);
            }
            return super.set(value); 
        }
    };
    public final Property<Image, AppSettings> titleBackground = new Property<>("titleBackground", null);
    private Image roundedScaledLogo;
    private final PropertyIndex idx = new PropertyIndex(this, "AppSettings", name, tagline, logo, titleBackground);
    
    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
    
    public Image getRoundedScaledLogo() {
        return roundedScaledLogo;
    }
}
