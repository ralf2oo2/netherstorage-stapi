package ralf2oo2.netherstorage.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class NetherCoreItem extends TemplateItem {
    public NetherCoreItem(Identifier identifier) {
        super(identifier);
        this.maxCount = 1;
    }
}