package siteSurvey;

import java.util.EventListener;

public interface SurveyListener extends EventListener {
    public void surveyEventOccured(FormEvent ev);
}
