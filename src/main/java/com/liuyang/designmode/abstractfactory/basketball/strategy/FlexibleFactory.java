package com.liuyang.designmode.abstractfactory.basketball.strategy;

import com.liuyang.designmode.abstractfactory.basketball.Player;

/**
 * 灵活阵容
 */
public class FlexibleFactory implements LineupFactory {
    @Override
    public Player center() {
        return new Player() {
            @Override
            public int height() {
                return 215;
            }

            @Override
            public int weight() {
                return 120;
            }

            @Override
            public int bounce() {
                return 90;
            }

            @Override
            public float shoot() {
                return 49.2f;
            }
        };
    }

    @Override
    public Player powerForward() {
        return new Player() {
            @Override
            public int height() {
                return 205;
            }

            @Override
            public int weight() {
                return 95;
            }

            @Override
            public int bounce() {
                return 95;
            }

            @Override
            public float shoot() {
                return 58f;
            }
        };
    }

    @Override
    public Player smallForward() {
        return new Player() {
            @Override
            public int height() {
                return 201;
            }

            @Override
            public int weight() {
                return 85;
            }

            @Override
            public int bounce() {
                return 105;
            }

            @Override
            public float shoot() {
                return 72f;
            }
        };
    }

    @Override
    public Player scoreGuard() {
        return new Player() {
            @Override
            public int height() {
                return 190;
            }

            @Override
            public int weight() {
                return 90;
            }

            @Override
            public int bounce() {
                return 105;
            }

            @Override
            public float shoot() {
                return 80;
            }
        };
    }

    @Override
    public Player pointGuard() {
        return new Player() {
            @Override
            public int height() {
                return 185;
            }

            @Override
            public int weight() {
                return 80;
            }

            @Override
            public int bounce() {
                return 85;
            }

            @Override
            public float shoot() {
                return 65;
            }
        };
    }
}
