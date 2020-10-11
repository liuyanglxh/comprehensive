package com.liuyang.designmode.abstractfactory.basketball.strategy;

import com.liuyang.designmode.abstractfactory.basketball.Player;

public interface LineupFactory {

    Player center();

    Player powerForward();

    Player smallForward();

    Player scoreGuard();

    Player pointGuard();
}
