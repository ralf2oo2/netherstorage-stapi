package ralf2oo2.netherstorage.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class NetherLabelItem extends TemplateItem {
    public NetherLabelItem(Identifier identifier) {
        super(identifier);
        this.setMaxCount(1);
    }
}
