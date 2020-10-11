package com.liuyang.designmode.abstractfactory.tag;

import java.util.Set;

abstract class AbstractUserTagFactory {

    abstract Set<String> getRecentTags(String udid);

    abstract Set<String> getHistoryTags(String udid);

    abstract Set<String> getSearchTags(String udid);

    abstract Set<String> getTopCategoryTags(String udid);

    abstract ResourceType type();
}
