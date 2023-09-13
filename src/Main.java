import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Healer", "Lucky"};
    public static int[] heroesHealth = {280, 270, 250, 200, 500};
    public static int[] heroesDamage = {10, 15, 20, 15, 3};
    public static int roundNumber;
    public static int hit = bossDamage / 5;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        healing();
        luckyDodging();
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void luckyDodging() {
        Random random = new Random();
        boolean hitMissing = random.nextBoolean();
        if (hitMissing && heroesHealth[3] > 0 && bossDamage > 0) {
            if (heroesHealth[4] > 0) {
                heroesHealth[3] += bossDamage - hit;
            } else {
                heroesHealth[3] += bossDamage;
            }
            System.out.println("Lucky did not get hit!");
        }
    }

    public static void healing() {
        int healing = 50;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossHealth > 0 && heroesHealth[3] > 0 && heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                if (heroesAttackType[i] == heroesAttackType[3]) {
                    continue;
                } else {
                    heroesHealth[i] += healing;
                    System.out.println(heroesAttackType[3] + " just healed hero " + heroesAttackType[i]);
                } break;
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " --------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }


        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}