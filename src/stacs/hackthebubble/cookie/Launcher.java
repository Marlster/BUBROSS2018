package stacs.hackthebubble.cookie;

public class Launcher {

    public static void main(String[] args) {
        new GameWindow().start();

        // TODO(Ryan <vitineth>): 11/3/18  Remove in prod
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }).start();
    }

}
