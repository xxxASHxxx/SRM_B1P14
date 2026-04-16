package com.trainconsist.model;

/**
 * Represents a goods bogie with shape and cargo information.
 */
public class GoodsBogie {
    public String shape;
    public String cargo;

    public GoodsBogie(String shape, String cargo) {
        this.shape = shape;
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return shape + " carrying " + cargo;
    }
}
