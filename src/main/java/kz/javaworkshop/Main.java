package kz.javaworkshop;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Вариант А: Воин (лимит 6000 г, 4 ячейки)
        Inventory inventory = new Inventory(4, 6000);

        // Начальные предметы Варианта А
        inventory.add(new Item("Меч", "weapon", 2500, 100));
        inventory.add(new Item("Щит", "armor", 3000, 80));
        inventory.add(new Item("Зелье", "potion", 500, 20));

        while (true) {
            System.out.println("\n--- РЮКЗАК ГЕРОЯ ---");
            System.out.println("1. Добавить предмет");
            System.out.println("2. Показать инвентарь");
            System.out.println("3. Удалить предмет");
            System.out.println("4. Использовать зелье");
            System.out.println("5. Сводка");
            System.out.println("6. Поиск предмета");
            System.out.println("0. Выйти");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleAdd(scanner, inventory);
                case "2" -> handleShow(inventory);
                case "3" -> handleRemove(scanner, inventory);
                case "4" -> handleUsePotion(scanner, inventory);
                case "5" -> handleSummary(inventory);
                case "6" -> handleSearch(scanner, inventory);
                case "0" -> {
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }

    private static void handleAdd(Scanner scanner, Inventory inventory) {
        System.out.print("Введите название предмета: ");
        String name = scanner.nextLine();

        System.out.print("Введите тип (weapon, armor, potion): ");
        String type = scanner.nextLine();

        System.out.print("Введите массу в граммах: ");
        int grams = readInt(scanner);

        System.out.print("Введите стоимость в монетах: ");
        int value = readInt(scanner);

        try {
            Item item = new Item(name, type, grams, value);
            boolean added = inventory.add(item);
            System.out.println(added ? "Добавлено" : "Отклонено: нет места или превышен лимит массы");
        } catch (IllegalArgumentException ex) {
            System.out.println("Ошибка в данных предмета: " + ex.getMessage());
        }
    }

    private static void handleShow(Inventory inventory) {
        Item[] items = inventory.snapshot();
        if (items.length == 0) {
            System.out.println("Инвентарь пуст.");
            return;
        }
        for (int i = 0; i < items.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), items[i]);
        }
    }

    private static void handleRemove(Scanner scanner, Inventory inventory) {
        handleShow(inventory);
        if (inventory.size() == 0) return;

        System.out.print("Введите номер предмета для удаления: ");
        int number = readInt(scanner);
        int index = number - 1;

        Item removed = inventory.remove(index);
        if (removed != null) {
            System.out.println("Удалено: " + removed.getName());
        } else {
            System.out.println("Некорректный номер предмета.");
        }
    }

    private static void handleUsePotion(Scanner scanner, Inventory inventory) {
        handleShow(inventory);
        if (inventory.size() == 0) return;

        System.out.print("Введите номер предмета для использования: ");
        int number = readInt(scanner);
        int index = number - 1;

        Item item = inventory.get(index);
        if (item == null) {
            System.out.println("Некорректный номер предмета.");
        } else if ("potion".equals(item.getType())) {
            inventory.remove(index);
            System.out.println("Зелье использовано");
        } else {
            System.out.println("Предмет не является зельем! Ничего не изменено.");
        }
    }

    private static void handleSummary(Inventory inventory) {
        System.out.printf("Ячейки: %d/%d; масса: %d/%d г; стоимость: %d\n",
                inventory.size(),
                inventory.getCapacity(),
                inventory.totalWeightGrams(),
                inventory.getMaxWeightGrams(),
                inventory.totalValue()
        );
    }

    private static void handleSearch(Scanner scanner, Inventory inventory) {
        System.out.print("Введите текст для поиска: ");
        String query = scanner.nextLine();
        Item[] results = inventory.findByName(query);

        if (results.length == 0) {
            System.out.println("Ничего не найдено.");
        } else {
            System.out.println("Найденные предметы:");
            for (Item item : results) {
                System.out.println("- " + item);
            }
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка! Введите целое число: ");
            }
        }
    }
}