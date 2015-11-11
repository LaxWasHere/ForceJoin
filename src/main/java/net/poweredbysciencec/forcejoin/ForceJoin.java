package net.poweredbysciencec.forcejoin;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.ProxyClientHolder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by John on 11/2/2015.
 */
public class ForceJoin extends Plugin implements Listener {

    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getConsole().sendMessage("§8[§6ForceJoin§8]§6 ForceJoin is now §aEnabled");
    }

    @EventHandler
    public void onPing(ProxyPingEvent ev) {
        ProxyClientHolder p = (ProxyClientHolder) ev.getConnection().getClientHolder();
        try {
            Object pch = p.getClass().getMethod("getHandle").invoke(p);
            Object packet = Class.forName(pch.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
            Class<?> enumClass = Class.forName(pch.getClass().getPackage().getName() + ".EnumClientServers");
            for (Object o : enumClass.getEnumConstants()) {
                if (o.toString().contains("poweredbyawesome.net") || o.toString().contains("lazle") || o.toString().contains("particle.club")) {
                    packet = packet.getClass().getConstructor(enumClass).newServerInstance(o);
                    break;
                }
            }
            Object con = pch.getClass().getField("playerConnection").get(pch);
            con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
        } catch (Throwable rock) {
            rock.printStackTrace();
        }
    }

}
