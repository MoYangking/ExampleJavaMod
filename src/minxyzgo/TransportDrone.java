package minxyzgo;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.MinerUnit;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.io.TypeIO;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;

public class TransportDrone extends UnitType{
	public TransportDrone(String name){
        super(name);
        flying = true;
        itemCapacity = 50;
        constructor = () -> new TransportDroneUnit();
        defaultController = () -> new TransportDroneAi();
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
    
    public class TransportDroneUnit extends MinerUnit{
    	public Building inputb;
    	public Building outputb;
    	
    	public void setInput(Building e){
    		inputb = e;
    	}
    	
    	public void setOutput(Building e){
    		outputb = e;
    	}
    	
    	@Override
    	public void update(){
    		super.update();
    		if(inputb.block.outputsItems() && outputb.block.outputsItems()){
    			if(canWork()){
    				
    			}
    		}
    	}
   
    	@Override
    	public void draw(){
    		super.draw();
    		float sin = Mathf.absin(Time.time(), 6, 1);
    		if(inputb != null) Drawf.arrow(x, y, inputb.x, inputb.y, hitSize * Vars.tilesize + sin, 4 + sin, Color.blue);
    		if(outputb != null) Drawf.arrow(x, y, outputb.x, outputb.y, hitSize * Vars.tilesize + sin, 4 + sin, Color.red);
    	}
    	
    	@Override
    	public void write(Writes write) {
    		super.write(write);
    		TypeIO.writeBuilding(write, inputb);
    		TypeIO.writeBuilding(write, outputb);
    	}
    	
    	@Override
    	public void read(Reads read) {
    		super.read(read);
    		this.inputb = TypeIO.readBuilding(read);
    		this.outputb = TypeIO.readBuilding(read);
    	}
    	
    	@Override
    	public void writeSync(Writes write) {
    		super.writeSync(write);
    		TypeIO.writeBuilding(write, inputb);
    		TypeIO.writeBuilding(write, outputb);
    	}
    	
    	@Override
    	public void readSync(Reads read){
    		super.readSync(read);
    		this.inputb = TypeIO.readBuilding(read);
    		this.outputb = TypeIO.readBuilding(read);
    	}
    	
    	public boolean canWork(){
    		Item in = null, out = null;
    		Block bi = inputb.block, bo = outputb.block;
    		if(inputb != null && outputb != null){
    			if(bi instanceof GenericCrafter) in = ((GenericCrafter)bi).outputItem.item;
    			if(bi instanceof Drill) in = ((Drill.DrillBuild)inputb).dominantItem;
    		    for(ItemStack stack : bo.consumes.getItem().items){
    		    	
    		    	Item item = stack.item;
    		    	
    		    	if(item == in) {
    		    		out = item;
    		    		break;
    		    	}
    		    }
    		    return in == out && inputb.items.total() < bi.itemCapacity && outputb.items.total() < bo.itemCapacity;
    		}
    		return false;
    	}
    	
    }
    
    
    public class TransportDroneAi extends AIController{
    	
    }
}