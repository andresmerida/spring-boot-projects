package dev.am.basic_demo.others;

public class MegaStore {

    public static double getFinalPrice(double price, double weight, DiscountType discountType) {
        return discountType.applyDiscount(price, weight);
    }

    public enum DiscountType {
        STANDARD {
            @Override
            public double applyDiscount(double price, double weight) {
                return price * 0.94; // 6% discount
            }
        },
        SEASONAL {
            @Override
            public double applyDiscount(double price, double weight) {
                return price * 0.88;    // 12% discount
            }
        },
        WEIGHT {
            @Override
            public double applyDiscount(double price, double weight) {
                return weight > 10 ? price * 0.82 : price * 0.94;
            }
        };

        abstract double applyDiscount(double price, double weight);
    }

    static void main() {
        IO.println(getFinalPrice(100, 15, DiscountType.WEIGHT));    // 82.0
    }
}
