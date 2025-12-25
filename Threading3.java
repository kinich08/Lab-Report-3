class SharedCounter {
    private int count=0;
    
    public synchronized void increment(){
        count++;
    }
    public synchronized int getCount(){
        return count;
    }
}

class NumPrinter extends Thread {
    private SharedCounter counter;
    
    public NumPrinter(SharedCounter counter){
        this.counter=counter;
    }
    
    @Override
    public void run() {
        for (int i=1;i<=10;i++){
            System.out.println("Thread 1 - Number: "+i);
            counter.increment();
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println("Thread 1 interrupted: "+ e.getMessage());
            }
        }
        System.out.println("Thread 1 completed Counter incremented "+10+"times.");
    }
}

class SquarePrinter implements Runnable{
    private SharedCounter counter;
    
    public SquarePrinter(SharedCounter counter){
        this.counter = counter;
    }
    
    @Override
    public void run() {
        for (int i=1;i<=10;i++){
            int square= i*i;
            System.out.println("Thread 2 - Square of "+i+": "+square);
            counter.increment();
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println("Thread 2 interrupted: "+ e.getMessage());
            }
        }
        System.out.println("Thread 2 completed Counter incremented "+10+"times.");
    }
}

public class Threading3{
    public static void main(String[] args){
        SharedCounter sharedCounter = new SharedCounter();
        NumPrinter thread1 = new NumPrinter(sharedCounter);
        
        SquarePrinter squarePrinter = new SquarePrinter(sharedCounter);
        Thread thread2 = new Thread(squarePrinter);
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: "+e.getMessage());
        }

        System.out.println("\n");
        System.out.println("All threads completed");
        System.out.println("Final shared counter value: "+sharedCounter.getCount());
    }
}