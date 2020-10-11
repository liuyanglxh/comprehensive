package com.liuyang.designmode.abstractfactory.tag;

import java.util.Set;

public class UserPostTagFactory extends AbstractUserTagFactory {
    
    @Override
    Set<String> getRecentTags(String udid) {
        return null;
    }

    @Override
    Set<String> getHistoryTags(String udid) {
        return null;
    }

    @Override
    Set<String> getSearchTags(String udid) {
        return null;
    }

    @Override
    Set<String> getTopCategoryTags(String udid) {
        return null;
    }

    @Override
    ResourceType type() {
        return null;
    }
}
