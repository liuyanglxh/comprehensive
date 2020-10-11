package com.liuyang.designmode.abstractfactory.tag;

import java.util.Set;

public class UserSpTagFactory extends AbstractUserTagFactory {

    @Override
    public Set<String> getRecentTags(String udid) {
        return null;
    }

    @Override
    public Set<String> getHistoryTags(String udid) {
        return null;
    }

    @Override
    public Set<String> getSearchTags(String udid) {
        return null;
    }

    @Override
    public Set<String> getTopCategoryTags(String udid) {
        return null;
    }

    @Override
    ResourceType type() {
        return ResourceType.SP;
    }
}
