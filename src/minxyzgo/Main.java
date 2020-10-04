package minxyzgo;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.world.blocks.units.UnitFactory;

public class Main extends Mod{

    public Main(){
         
    }

    @Override
    public void loadContent(){
        new TEItems().load();
        InputSetting.set();
        TransportDrone drone = new TransportDrone("transport-drone");
        new UnitFactory("test-factory"){{
            requirements(Category.units, ItemStack.with(Items.copper, 60, Items.lead, 70));
            plans = new UnitPlan[]{
                new UnitPlan(drone, 60f * 15, ItemStack.with(Items.silicon, 15)),
            };
            size = 3;
            consumes.power(1.2f);
        }};
        /*Events.on(ClientLoadEvent.class, e -> {
        		Time.runTask(30f, () -> {
        			 try{
        				new TestCustomGameDialog().init();
        			 }catch(NoSuchFieldException e3){
        	e3.printStackTrace();
        }catch(SecurityException e2){
        	e2.printStackTrace();
        }catch(IllegalArgumentException e4){
        	e4.printStackTrace();
        }catch(IllegalAccessException e5){
        	e5.printStackTrace();
        }
        		});
        	});*/
    }

}
