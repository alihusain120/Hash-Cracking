import java.io.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.file.*;

/***************************************************/
/* Adapted from HW6 solution by Renato Mancuso
/*
/*                                                 */
/* Description: This class implements the logic of */
/*   a linear dispatcher that also spans and       */
/*   manages a set of worker threads to crack a    */
/*   a set of MD5 hashes provied in an input file. */
/*                                                 */
/***************************************************/

public class Dispatcher {

    int numCPUs;
    int timeoutMillis;
    ArrayList<UnHashWorker> workers;

    /* Queue for inputs to be processed */
    LinkedList<WorkUnit> workQueue;

    /* Semaphore to synch up on the number of input items */
    Semaphore wqSem;

    /* Mutex to protect input queue */   
    Semaphore wqMutex;

    /* Queue for processed outputs */
    LinkedList<WorkUnit> resQueue;
    
    /* Semaphore to synch up on the number of output items */
    Semaphore rsSem;

    /* Mutex to protect output queue */
    Semaphore rsMutex;
    
    public Dispatcher (int N, int timeout) {
      this.numCPUs = N;
      this.timeoutMillis = timeout;

      /* Now build the other data structures */
      workQueue = new LinkedList<WorkUnit>();
      resQueue = new LinkedList<WorkUnit>();

      workers = new ArrayList<UnHashWorker>();

      /* Initialize the semaphores necessary to synchronize over the
       * input and output queues */
      wqSem = new Semaphore(0);
      wqMutex = new Semaphore(1);

      rsSem = new Semaphore(0);
      rsMutex = new Semaphore(1);

      /* Start by spawning the worker threads */
      for (int i = 0; i < N; ++i) {
          UnHashWorker worker = new UnHashWorker(workQueue, resQueue,
                   wqSem, wqMutex,
                   rsSem, rsMutex);
          worker.setTimeout(timeout);
          workers.add(worker);

          /* Ready to launch the worker */
          worker.start();
      }
    }
    
    public void dispatch (Set<String> hashes, Set<String> results) throws InterruptedException
    {
      /* Pass each line read in input to the dehasher */
      int count = hashes.size();
      for(String line : hashes){

        WorkUnit work = new WorkUnit(line);

        wqMutex.acquire();
        /* CRITICAL SECTION */

        workQueue.add(work);

        /* Signal the presence of new work to be done */
        wqSem.release();

        /* END OF CRITICAL SECTION */
        wqMutex.release();
      }

      /* At this point, we just wait for all the input to be consumed */
      while(count-- > 0) {
        rsSem.acquire();
      }

      /* All done, terminate all the worker threads */
      for (UnHashWorker worker : workers) {
        worker.exitWorker();
      }

      /* Make sure that no worker is stuck on the empty input queue */
      for (UnHashWorker worker : workers) {
        wqSem.release();
      }

      /* Print out result */
      for(WorkUnit res : resQueue) {
        if (res.getResult() != null){
          results.add(res.getResult());
          hashes.remove(res.getHash());
        }
      }

    }

}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
