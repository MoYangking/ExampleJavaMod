package minxzygo;
import mindustry.ctype.*;
import arc.graphics.*;
import mindustry.type.*;

public class TEItems implements ContentList{
	public static Item cckpr;
	
	@Override
	public void load(){
		cckpr = new Item("cckpr", Color.valueOf("d99d73")){{
            hardness = 1;
            cost = 0.5f;
            alwaysUnlocked = true;
        }};
	}
}
