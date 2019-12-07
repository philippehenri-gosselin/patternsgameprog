/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.state;

import java.util.Map;
import java.util.TreeMap;

public class ElementFactory {

    private Map<Integer, ElementCreator> creators = new TreeMap();

    public static ElementFactory getDefault() {
        ElementFactory factory = new ElementFactory();
        factory.register(0, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.UNKNOWN);
            }
        });
        factory.register(1, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.UNKNOWN);
            }
        });
        factory.register(2, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.EMPTY);
            }
        });
        factory.register(3, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.GUM);
            }
        });
        factory.register(4, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.SUPERGUM);
            }
        });
        factory.register(5, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.SUPERGUM);
            }
        });
        
        factory.register(11, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.HORIZONTAL);
            }
        });
        factory.register(12, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.VERTICAL);
            }
        });
        factory.register(13, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.BOTTOMLEFT);
            }
        });
        factory.register(14, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.BOTTOMRIGHT);
            }
        });
        factory.register(15, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.TOPLEFT);
            }
        });
        factory.register(16, new ElementCreator() {
            @Override
            public Element create() {
                return new Wall(WallTypeId.TOPRIGHT);
            }
        });

        factory.register(21, new ElementCreator() {
            @Override
            public Element create() {
                return new Space(SpaceTypeId.START);
            }
        });
        for (int i=22;i<=25;i++) {
            factory.register(i, new ElementCreator() {
                @Override
                public Element create() {
                    return new Space(SpaceTypeId.GRAVEYARD);
                }
            });
        }
        return factory;
    }

    public void register(int code, ElementCreator creator) {
        creators.put(code, creator);
    }

    public void unregister(int code) {
        creators.remove(code);
    }

    public Element create(int code) {
        ElementCreator creator = creators.get(code);
        if (creator != null) {
            return creator.create();
        }
        creator = creators.get(0);
        if (creator != null) {
            return creator.create();
        }
        throw new RuntimeException("Code not found");
    }
}
