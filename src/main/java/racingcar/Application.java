package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private static boolean retry = false;
    private static int retryCnt = 0;
    private static Map<String,String> cars;
    private static ArrayList<String> answer;
    private static int max = 0;

    public static void main(String[] args) {
        cars = new HashMap<>();
        answer = new ArrayList<>();
        startRacingGame();
        getValidCarInput();

        getTryCount();
        getValidTryCountInput();

        while (retryCnt-- > 0) {
            moveRacingCar(cars);
            cars.forEach((key, value) -> System.out.println(key + " : " + value));
        }

        cars.forEach(Application::getMaxValue);
        cars.forEach(Application::getAnswer);

        returnAnswer();
    }

    protected static void returnAnswer() {
        StringBuilder sb = new StringBuilder();
        answer.forEach((value) -> sb.append(value).append(", "));
        String result = sb.substring(0, sb.toString().length()-2);

        System.out.print("최종 우승자 : " + result);
    }

    protected static void getAnswer(String key, String value) {
        if (value.length() == max) answer.add(key);
    }

    protected static void getMaxValue(String key, String value) {
        if (value.length() > max) max = value.length();
    }

    protected static void moveRacingCar(Map<String, String> cars) {

        cars.forEach((key, value) -> {
            int moveOrStay = Randoms.pickNumberInRange(0,9);
            changeCarStatus(key, moveOrStay);
        });
    }

    protected static void changeCarStatus(String key, int moveOrStay) {
        if (moveOrStay >= 4) {
            cars.put(key, cars.get(key)+"-");
        }
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
