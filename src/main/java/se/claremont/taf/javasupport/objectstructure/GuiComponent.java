package se.claremont.taf.javasupport.objectstructure;

/**
 * This should be merged with GuiElement interface
 * <p>
 * Created by jordam on 2017-02-19.
 */
public interface GuiComponent {

    String getName();

    Object getRuntimeComponent();

    String getRecognitionDescription();
}
