package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class ArrayListOrLinkedList {

    private final static String DATA = "DUMMY DATA";

    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Iteration)
    public void setUp() {
        this.arrayList = new ArrayList<String>();
        this.linkedList = new LinkedList<String>();
    }

    @Benchmark
    public List<String> addLinked() {
        this.linkedList.add(DATA);
        return linkedList;
    }

    @Benchmark
    public List<String> addArray() {
        this.arrayList.add(DATA);
        return arrayList;
    }

    public static void main(String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
            .include(ArrayListOrLinkedList.class.getSimpleName())
            .forks(1)
            .measurementIterations(10)
            .warmupIterations(10)
            .build();
        new Runner(options).run();
    }
}
