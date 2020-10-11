package com.liuyang.designmode.abstractfactory.tag;

import java.util.HashMap;
import java.util.Map;

public class UserTagFactory {

    Map<ResourceType, AbstractUserTagFactory> map = new HashMap<>();

    AbstractUserTagFactory getFactory(ResourceType type) {
        return map.get(type);
    }
}
