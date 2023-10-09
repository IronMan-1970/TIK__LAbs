package Passed.Lab2;

public class Lab2
{

    // Крок 1: Розрахунок ентропії
    public static double entropy(double[] p) {
        double entropy = 0;
        for (double p_i : p) {
            if (p_i > 0) {
                entropy -= p_i * Math.log(p_i) / Math.log(2);
            }
        }
        return entropy;
    }

    // Крок 7: Розрахунок пропускної спроможності каналу
    public static double channelCapacity(double νk, double Hx, double Hy_x) {
        return νk * (Hx - Hy_x);
    }

    // Крок 3: Розрахунок коефіцієнта використання каналу
    public static double channelUtilizationFactor(double R, double C) {
        return R / C;
    }

    public static void main(String[] args) {
        // Крок 2: Вхідні ймовірності для символів каналу
        double[] p_ai = {0.2, 0.3, 0.1, 0.4};

        // Вхідні ймовірності для втручання (p(yj/xi))
        double[][] p_y_given_x = {
                {0.1, 0.3, 0.1, 0.5},
                {0.4, 0.2, 0.2, 0.2},
                {0.3, 0.1, 0.4, 0.2},
                {0.2, 0.4, 0.1, 0.3}
        };

        // Крок 4: Розрахунок ентропії вхідних даних (H(X))
        double Hx = entropy(p_ai);

        // Крок 5: Розрахунок ентропії втручання (H(Y|X))
        double Hy_x = 0;
        for (int i = 0; i < p_ai.length; i++) {
            Hy_x += p_ai[i] * entropy(p_y_given_x[i]);
        }

        double νk = 1000; // Приклад: 1000 символів/сек

        // Крок 6: Розрахунок швидкості передачі інформації (R)
        double R = νk * (Hx - Hy_x);

        // Крок 7: Розрахунок пропускної спроможності каналу (C)
        double C = νk * (Hx - Math.log(p_ai.length) / Math.log(2));

        // Крок 8: Розрахунок коефіцієнта використання каналу (ηeff)
        double ηeff = R / C;

        // Виведення результатів
        System.out.println("Ентропія вхідних даних (H(X)): " + Hx + " біт/символ");
        System.out.println("Ентропія втручання (H(Y|X)): " + Hy_x + " біт/символ");
        System.out.println("Швидкість передачі інформації (R): " + R + " біт/с");
        System.out.println("Пропускна спроможність каналу (C): " + C + " біт/с");
        System.out.println("Коефіцієнт використання каналу (ηeff): " + ηeff);
    }
}

