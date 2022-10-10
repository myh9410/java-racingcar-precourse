package racingcar;

import camp.nextstep.edu.missionutils.Console;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private static boolean retry = false;
    private static int retryCnt = 0;
    private static Map<String,String> cars;

    public static void main(String[] args) {
        cars = new HashMap<>();
        startRacingGame();
        getValidCarInput();

        getTryCount();
        getValidTryCountInput();
    }

    protected static void startRacingGame() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
    }

    protected static void getTryCount() {
        System.out.println("시도할 횟수는 몇회인가요?");
    }

    protected static void getValidCarInput() {
        String carsAsString = Console.readLine();
        String[] carsInput = carsAsString.split(",");
        for (String car : carsInput) {
            cars.put(car, "");
        }
        retry = false;

        try {
            carNameValidation(carsInput);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } finally {
            carInputRetry();
        }
    }

    protected static void getValidTryCountInput() {
        String tryCount = Console.readLine();
        retry = false;

        try {
            tryConutValidation(tryCount);
            retryCnt = Integer.parseInt(tryCount);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR]" + e.getMessage());
        } finally {
            tryInputRetry();
        }
    }

    protected static void carInputRetry() {
        if (retry) {
            getValidCarInput();
        }
    }

    protected static void tryInputRetry() {
        if (retry) {
            getValidTryCountInput();
        }
    }

    protected static void carNameValidation(String[] cars) {
        for (String car : cars) {
            carNameUnderFiveValidation(car);
        }
    }

    protected static void carNameUnderFiveValidation(String car) {
        if (!retry && car.length() > 5) {
            retry = true;
            throw new IllegalArgumentException("5자 이하만 가능하다.");
        }
    }

    protected static void tryConutValidation(String tryCount) {
        if (!tryCount.chars().allMatch(Character::isDigit)) {
            retry = true;
            throw new IllegalArgumentException("시도 횟수는 숫자여야 한다.");
        }
    }
}
