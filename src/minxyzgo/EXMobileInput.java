package minxyzgo;

import mindustry.entities.Units;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.input.MobileInput;
import mindustry.world.Tile;

import static mindustry.Vars.*;

import arc.Core;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.util.ArcAnnotate.Nullable;

public class EXMobileInput extends MobileInput{
	public final static float playerSelectRange = mobile ? 17f : 11f;
	
	
	 @Override
    public boolean tap(float x, float y, int count, KeyCode button){
    	if(state.isMenu() || lineMode) return false;
        float worldx = Core.input.mouseWorld(x, y).x, worldy = Core.input.mouseWorld(x, y).y;
        Tile cursor = tileAt(x, y);
        if(cursor == null || Core.scene.hasMouse(x, y)) return false;
        Tile linked = cursor.build == null ? cursor : cursor.build.tile;
        if(!player.dead()){
            checkTargets(worldx, worldy);
        }
        
        if(!canTapPlayer(worldx, worldy)){
                tryBeginTrans(cursor);
        }
        
        return super.tap(x, y, count, button);
    }
    
    public Tile tileAt(float x, float y){
        return world.tile(tileX(x), tileY(y));
    }
    
    public int tileX(float cursorX){
        Vec2 vec = Core.input.mouseWorld(cursorX, 0);
        if(selectedBlock()){
            vec.sub(block.offset, block.offset);
        }
        return world.toTile(vec.x);
    }

    public int tileY(float cursorY){
        Vec2 vec = Core.input.mouseWorld(0, cursorY);
        if(selectedBlock()){
            vec.sub(block.offset, block.offset);
        }
        return world.toTile(vec.y);
    }
    
    public void checkTargets(float x, float y){
        Unit unit = Units.closestEnemy(player.team(), x, y, 20f, u -> !u.dead);

        if(unit != null){
            player.miner().mineTile(null);
            target = unit;
        }else{
            Building tile = world.buildWorld(x, y);

            if(tile != null && player.team().isEnemy(tile.team)){
                player.miner().mineTile(null);
                target = tile;
            }else if(tile != null && player.unit().type().canHeal && tile.team == player.team() && tile.damaged()){
                player.miner().mineTile(null);
                target = tile;
            }
        }
    }
    
    public boolean hasRequest(Tile tile){
    	Class<?>[] clzss = {Tile.class};
        return Met.Reflection(this, BuildPlan.class, "getRequest", clzss, tile) != null;
    }
    
    public boolean ztileTapped(@Nullable Building tile){
    	Class<?>[] clzss = {Building.class};
        return (boolean)Met.Reflection(this, boolean.class, "tileTapped", clzss, tile);
    }
    
    public boolean tryBeginTrans(Tile tile){
    	
        if(player.unit() instanceof TransportDrone.TransportDroneUnit && tile.build != null && tile.build.block.outputsItems()){
        	TransportDrone.TransportDroneUnit drone = ((TransportDrone.TransportDroneUnit)player.unit());
            if(drone.inputb == null){
            drone.setInput(drone.outputb == tile.build ? null : tile.build);
            }else if(drone.inputb == tile.build){
            	drone.setInput(null);
            }else{
            	drone.setOutput(tile.build);
            }
            
            
            return true;
        }
        return false;
    }
    
    public boolean canTapPlayer(float x, float y){
        return player.within(x, y, playerSelectRange) && player.unit().stack.amount > 0;
    }
}
