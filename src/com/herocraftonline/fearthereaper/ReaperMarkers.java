/* This file is part of FearTheReaper.

    FearTheReaper is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FearTheReaper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FearTheReaper.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.herocraftonline.fearthereaper;

import java.io.InputStream;
import java.util.Map.Entry;

import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import com.herocraftonline.fearthereaper.spawnpoint.Spawn;

public class ReaperMarkers {

    private DynmapAPI api;
    private MarkerAPI mApi;
    private static MarkerSet set;
    private static MarkerIcon icon;

    public ReaperMarkers(FearTheReaper plugin, DynmapAPI dm) {
        // Setup the API
        api = dm;
        mApi = api.getMarkerAPI();
        if (mApi == null) {
            return;
        }
        
        InputStream in = getClass().getResourceAsStream("tomb.png");
        icon = mApi.createMarkerIcon("tomb", "Graveyards", in);

        set = mApi.getMarkerSet("graveyard.markerset");
        if (set == null) {
            set = mApi.createMarkerSet("graveyard.markerset", "Graveyards", null, true);
            set.setLayerPriority(10);
            set.setHideByDefault(false);
            set.setMinZoom(0);
        }

        for (Entry<String, Spawn> entry : FearTheReaper.getSpawnList().entrySet()) {
            Marker m = set.findMarker(entry.getKey());
            Spawn s = entry.getValue();
            if (m != null) {
                if (m.getX() != s.getX() || m.getY() != s.getY() || m.getZ() != s.getZ()) {
                    m.setLocation(s.getWorldName(), s.getX(), s.getY(), s.getZ());
                }
            }

            set.createMarker(entry.getKey(), entry.getKey(), s.getWorldName(), s.getX(), s.getY(), s.getZ(), icon, true);
        }
    }

    public static void updateMarker(Spawn point) {
        Marker m = set.findMarker(point.getName());
        if (m != null) {
            if (m.getX() != point.getX() || m.getY() != point.getY() || m.getZ() != point.getZ()) {
                m.setLocation(point.getWorldName(), point.getX(), point.getY(), point.getZ());
            }
        }
        set.createMarker(point.getName(), point.getName(), point.getWorldName(), point.getX(), point.getY(), point.getZ(), icon, true);
    }
    
    public static void deleteMarker(Spawn point) {
        Marker m = set.findMarker(point.getName());
        if (m != null) {
            m.deleteMarker();
        }
    }
}
