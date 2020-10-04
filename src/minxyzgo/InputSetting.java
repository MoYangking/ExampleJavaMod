package minxyzgo;

import static mindustry.Vars.*;

public class InputSetting {
	public static void set(){
		control.setInput(mobile ? new EXMobileInput() : new EXDesktopInput());
	}
}