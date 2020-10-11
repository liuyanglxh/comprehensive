package com.liuyang.designmode.abstractfactory.basketball.strategy;

import com.liuyang.designmode.abstractfactory.basketball.Player;

/**
 * 传统阵容
 */
public class TraditionalFactory implements LineupFactory {
    @Override
    public Player center() {
        return new Player() {
            @Override
            public int height() {
                return 220;
            }

            @Override
            public int weight() {
                return 130;
            }

            @Override
            public int bounce() {
                return 80;
            }

            @Override
            public float shoot() {
                return 40.2f;
            }
        };
    }

    @Override
    public Player powerForward() {
        return new Player() {
            @Override
            public int height() {
                return 210;
            }

            @Override
            public int weight() {
                return 100;
            }

            @Override
            public int bounce() {
                return 95;
            }

            @Override
            public float shoot() {
                return 55f;
            }
        };
    }

    @Override
    public Player smallForward() {
        return new Player() {
            @Override
            public int height() {
                return 205;
            }

            @Override
            public int weight() {
                return 90;
            }

            @Override
            public int bounce() {
                return 100;
            }

            @Override
            public float shoot() {
                return 70f;
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
