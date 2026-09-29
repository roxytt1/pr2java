package kz.javaworkshop;

public class Item {
    private final String name;
    private final String type;
    private final int weightGrams;
    private final int value;

    public Item(String name, String type, int weightGrams, int value) {
        if (name == null || name.strip().isEmpty() || name.strip().length() > 40) {
            throw new IllegalArgumentException("Имя должно быть от 1 до 40 символов");
        }
        if (type == null || (!type.equals("weapon") && !type.equals("armor") && !type.equals("potion"))) {
            throw new IllegalArgumentException("Тип должен быть: weapon, armor или potion");
        }
        if (weightGrams < 1 || weightGrams > 100000) {
            throw new IllegalArgumentException("Масса должна быть в диапазоне от 1 до 100 000 г");
        }
        if (value < 0 || value > 1000000) {
            throw new IllegalArgumentException("Стоимость должна быть в диапазоне от 0 до 1 000 000 монет");
        }

        this.name = name.strip();
        this.type = type;
        this.weightGrams = weightGrams;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s [%s], %d г, %d монет", name, type, weightGrams, value);
    }
}