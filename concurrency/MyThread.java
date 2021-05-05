public class MyThread {

    public void run() {
            // No cpu usage
            // High cpu usage
            int i = 0;
            while (i < 100000000) {
                Math.pow(2, Math.sqrt(Math.log(i)));
                i++;
            }
    }

    public static void main(String args[]) {
        (new MyThread()).run();
        (new MyThread()).run();
    }
}
