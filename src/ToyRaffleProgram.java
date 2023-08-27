import java.io.*;
import java.util.*;

/**
 * Класс, представляющий игрушку.
 */
class Toy {
    private int id;
    private String name;
    private int quantity;
    private double weight;

    /**
     * Конструктор для создания объекта игрушки.
     *
     * @param id       ID игрушки.
     * @param name     Название игрушки.
     * @param quantity Количество доступных игрушек.
     * @param weight   Вес игрушки (вес в % от 100).
     */
    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Уменьшает количество доступных игрушек на 1.
     */
    public void decreaseQuantity() {
        quantity--;
    }
}

/**
 * Класс, представляющий магазин игрушек.
 */
class ToyStore {
    private List<Toy> toys = new ArrayList<>();

    /**
     * Добавляет новую игрушку в магазин.
     *
     * @param toy Игрушка для добавления.
     */
    public void addToy(Toy toy) {
        toys.add(toy);
    }

    /**
     * Обновляет вес (частоту выпадения) игрушки по её ID.
     *
     * @param toyId     ID игрушки.
     * @param newWeight Новый вес игрушки.
     */
    public void updateToyWeight(int toyId, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    /**
     * Выбирает случайную призовую игрушку на основе их весов.
     *
     * @return Выбранная игрушка.
     */
    public Toy chooseToy() {
        double totalWeight = toys.stream().mapToDouble(Toy::getWeight).sum();
        double randomNumber = Math.random() * totalWeight;
        double currentWeight = 0.0;

        for (Toy toy : toys) {
            currentWeight += toy.getWeight();
            if (randomNumber < currentWeight) {
                if (toy.getQuantity() > 0) {
                    toy.decreaseQuantity();
                    return toy;
                } else {
                    return null; // Нет доступных игрушек этого типа
                }
            }
        }

        return null; // Нет доступных игрушек
    }
}

/**
 * Программа для розыгрыша игрушек в магазине детских товаров.
 */
public class ToyRaffleProgram {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        toyStore.addToy(new Toy(1, "Teddy Bear", 10, 25.0));
        toyStore.addToy(new Toy(2, "Toy Car", 15, 20.0));
        toyStore.addToy(new Toy(3, "Doll", 8, 15.0));

        // Симуляция розыгрыша игрушек
        Toy wonToy = toyStore.chooseToy();
        if (wonToy != null) {
            System.out.println("Поздравляем! Вы выиграли " + wonToy.getName());
            // Сохранение выигранной игрушки в файл
            saveWonToyToFile(wonToy);
        } else {
            System.out.println("Нет доступных игрушек для розыгрыша.");
        }
    }

    /**
     * Сохраняет информацию о выигранной игрушке в файл.
     *
     * @param toy Выигранная игрушка.
     */
    public static void saveWonToyToFile(Toy toy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("won_toys.txt", true))) {
            writer.write(toy.getName());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
