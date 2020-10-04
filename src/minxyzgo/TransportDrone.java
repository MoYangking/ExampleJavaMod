package minxyzgo;

import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.gen.MinerUnit;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;

public class TransportDrone extends UnitType{
	public TransportDrone(String name){
        super(name);
        flying = true;
        itemCapacity = 50;
        constructor = () -> new TransportDroneAi();
    }
    
    @Override
    public void display(Unit unit, Table table){
    	super.display(unit, table);
    	table.table(bars -> {
            bars.defaults().growX().height(20f).pad(4);
            bars.add(new Bar("item", Pal.darkFlame, () -> {return unit.stack().amount / unit.itemCapacity();}).blink(Color.red));
            bars.row();
    	});
    }
    
    public class TransportDroneAi extends MinerUnit{
    	
    }
}