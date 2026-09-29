package kz.javaworkshop;

import java.util.Arrays;

public class Inventory {
    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int capacity, int maxWeightGrams) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException("Вместимость ячеек должна быть от 1 до 20");
        }
        if (maxWeightGrams < 1 || maxWeightGrams > 100000) {
            throw new IllegalArgumentException("Лимит массы должен быть от 1 до 100 000 г");
        }
        this.items = new Item[capacity];
        this.size = 0;
        this.maxWeightGrams = maxWeightGrams;
    }

    public boolean add(Item item) {
        if (item == null) {
            return false;
        }
        if (size >= items.length) {
            return false;
        }
        if (totalWeightGrams() + item.getWeightGrams() > maxWeightGrams) {
            return false;
        }

        items[size] = item;
        size++;
        return true;
    }

    public Item remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Item removedItem = items[index];

        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[size - 1] = null;
        size--;

        return removedItem;
    }

    public Item get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[index];
    }

    public int size() {
        return size;
    }

    public int totalWeightGrams() {
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += items[i].getWeightGrams();
        }
        return total;
    }

    public long totalValue() {
        long total = 0;
        for (int i = 0; i < size; i++) {
            total += items[i].getValue();
        }
        return total;
    }

    public Item[] snapshot() {
        return Arrays.copyOf(items, size);
    }

    public int getMaxWeightGrams() {
        return maxWeightGrams;
    }

    public int getCapacity() {
        return items.length;
    }

    public Item[] findByName(String query) {
        if (query == null || query.isBlank()) {
            return new Item[0];
        }
        String lowerQuery = query.toLowerCase().trim();
        
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase().contains(lowerQuery)) {
                count++;
            }
        }

        Item[] result = new Item[count];
        int resultIndex = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase().contains(lowerQuery)) {
                result[resultIndex++] = items[i];
            }
        }
        return result;
    }
}