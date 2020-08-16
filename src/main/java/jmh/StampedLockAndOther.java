package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

@Measurement(iterations = 20)
@Warmup(iterations = 20)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StampedLockAndOther {

    @State(Scope.Group)
    public static class Test {
        private int x = 10;
        private final Lock lock = new ReentrantLock();

        private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock readLock = readWriteLock.readLock();
        private final Lock writeLock = readWriteLock.writeLock();

        private final StampedLock stampedLock = new StampedLock();

        public void stampedLockInc() {
            long stamped = stampedLock.writeLock();
            try {
                x++;
            } finally {
                stampedLock.unlockWrite(stamped);
            }
        }

        public int stampedLockRead() {
            long stamped = stampedLock.readLock();
            try {
                return x;
            } finally {
                stampedLock.unlockRead(stamped);
            }
        }

        public int stampedOptimisticReadLock() {
            long stamped = stampedLock.tryOptimisticRead();
            if (!stampedLock.validate(stamped)) {
                stamped = stampedLock.readLock();
                try {
                    return x;
                } finally {
                    stampedLock.unlockRead(stamped);
                }
            }
            return x;
        }

        public void lockInc() {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }

        public int lockGet() {
            lock.lock();
            try {
                return x;
            } finally {
                lock.unlock();
            }
        }

        public void writeLockInc() {
            writeLock.lock();
            try {
                x++;
            } finally {
                writeLock.unlock();
            }
        }

        public int readLockGet() {
            readLock.lock();
            try {
                return x;
            } finally {
                readLock.unlock();
            }
        }
    }

    final private static int readThreadCount = 16;
    final private static int writeThreadCount = 4;

    @GroupThreads(writeThreadCount)
    @Group("lock")
    @Benchmark
    public void lockInc(Test test) {
        test.lockInc();
    }

    @GroupThreads(readThreadCount)
    @Group("lock")
    @Benchmark
    public void lockGet(Test test, Blackhole blackhole) {
        blackhole.consume(test.lockGet());
    }

    @GroupThreads(writeThreadCount)
    @Group("rwlock")
    @Benchmark
    public void writeLockInc(Test test) {
        test.writeLockInc();
    }

    @GroupThreads(readThreadCount)
    @Group("rwlock")
    @Benchmark
    public void readLockGet(Test test, Blackhole blackhole) {
        blackhole.consume(test.readLockGet());
    }

    @GroupThreads(writeThreadCount)
    @Group("stampedLock")
    @Benchmark
    public void stampedLockWrite(Test test) {
        test.stampedLockInc();
    }

    @GroupThreads(readThreadCount)
    @Group("stampedLock")
    @Benchmark
    public void stampedLockRead(Test test, Blackhole blackhole) {
        blackhole.consume(test.stampedLockRead());
    }

    @GroupThreads(writeThreadCount)
    @Group("stampedOptimistic")
    @Benchmark
    public void stampedOptimisticInc(Test test) {
        test.stampedLockInc();
    }

    @GroupThreads(readThreadCount)
    @Group("stampedOptimistic")
    @Benchmark
    public void stampedOptimisticRead(Test test, Blackhole blackhole) {
        blackhole.consume(test.stampedOptimisticReadLock());
    }

    public static void main(String[] args) throws RunnerException {
        Options options  = new OptionsBuilder()
            .include(StampedLockAndOther.class.getSimpleName())
            .forks(1)
            .build();
        new Runner(options).run();
    }
}
