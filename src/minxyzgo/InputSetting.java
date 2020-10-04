package minxyzgo;

import arc.*;
import static mindustry.Vars.*;
import static mindustry.game.EventType.*;

public class InputSetting {
	public static void set(){
	    Events.on(WorldLoadEvent.class, event -> {
	    	control.setInput(mobile ? new EXMobileInput() : new EXDesktopInput());
	    });
	}
}