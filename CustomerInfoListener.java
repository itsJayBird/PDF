package siteSurvey;

import java.util.EventListener;

public interface CustomerInfoListener extends EventListener {
    public void custEventOccured(FormEvent ev);
}
