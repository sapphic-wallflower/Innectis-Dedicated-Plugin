package net.innectis.innplugin.player.tinywe.blockcounters;

import java.util.ArrayList;
import java.util.List;
import net.innectis.innplugin.items.IdpMaterial;
import net.innectis.innplugin.location.IdpRegion;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 *
 * @author Hret
 *
 * Cylinder block counter
 */
public final class ZCylinderBlockCounter implements BlockCounter {

    /**
     * Only access this from the BlockCounterFactory
     */
    protected ZCylinderBlockCounter() {
    }
    private Vector center;
    private int radius;

    private void setRegion(IdpRegion region) {
        center = new Vector(region.getCenterX(), region.getCenterY(), region.getCenterZ());

        // Get radius
        radius = region.getXLength();
        radius = Math.min(region.getYLength(), radius);
        radius /= 2;
    }

    /**
     * @inherit
     * */
    @Override
    public List<Block> getBlockList(IdpRegion region, World world, MaterialSelector selector) {
        setRegion(region);

        List<Block> blocks = new ArrayList<Block>();

        for (int x = region.getLowestX(); x <= region.getHighestX(); x++) {
            for (int z = region.getLowestZ(); z <= region.getHighestZ(); z++) {
                for (int y = region.getLowestY(); y <= region.getHighestY(); y++) {
                    Block tmpBlk = world.getBlockAt(x, y, z);
                    IdpMaterial mat = IdpMaterial.fromBlock(tmpBlk);

                    if (selector == null || selector.materialSelected(mat)) {
                        int cx = center.getBlockX();
                        int cy = center.getBlockY();
                        Vector vec = new Vector(cx, cy, z);

                        if (tmpBlk.getLocation().toVector().distance(vec) <= radius + 0.5) {
                            blocks.add(tmpBlk);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * @inherit
     * */
    @Override
    public List<Block> getHollowBlockList(IdpRegion region, World world, MaterialSelector selector) {
        setRegion(region);

        List<Block> blocks = new ArrayList<Block>();

        for (int x = region.getLowestX(); x <= region.getHighestX(); x++) {
            for (int z = region.getLowestZ(); z <= region.getHighestZ(); z++) {
                for (int y = region.getLowestY(); y <= region.getHighestY(); y++) {
                    Block tmpBlk = world.getBlockAt(x, y, z);
                    IdpMaterial mat = IdpMaterial.fromBlock(tmpBlk);

                    if (selector == null || selector.materialSelected(mat)) {
                        int cx = center.getBlockX();
                        int cy = center.getBlockY();
                        Vector vec = new Vector(cx, cy, z);

                        double d = tmpBlk.getLocation().toVector().distance(vec);

                        if (d <= radius + 0.5 && (d >= radius - 0.5 || z == region.getLowestZ() || z == region.getHighestZ())) {
                            blocks.add(tmpBlk);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * @inherit
     * */
    @Override
    public List<Block> getWallBlockList(IdpRegion region, World world, MaterialSelector selector) {
        setRegion(region);

        List<Block> blocks = new ArrayList<Block>();

        for (int x = region.getLowestX(); x <= region.getHighestX(); x++) {
            for (int z = region.getLowestZ(); z <= region.getHighestZ(); z++) {
                for (int y = region.getLowestY(); y <= region.getHighestY(); y++) {
                    Block tmpBlk = world.getBlockAt(x, y, z);
                    IdpMaterial mat = IdpMaterial.fromBlock(tmpBlk);

                    if (selector == null || selector.materialSelected(mat)) {
                        int cx = center.getBlockX();
                        int cy = center.getBlockY();
                        Vector vec = new Vector(cx, cy, z);

                        double d = tmpBlk.getLocation().toVector().distance(vec);

                        if (d <= radius + 0.5 && d >= radius - 0.5) {
                            blocks.add(tmpBlk);
                        }
                    }
                }
            }
        }
        return blocks;
    }

}
