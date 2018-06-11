package preprocessing;

public class Main {
    public static void main(String[] args) {
        Preprocess.loadPath();

        Preprocess.readFile();//读初始图文件

        Preprocess.readLog();//读日志文件

        Preprocess.computeVS();//计算虚拟快照

        Preprocess.persistVS();//将VS持久化到文件

        Preprocess.computeDeltaLog();//计算增量快照

        Preprocess.persistDeltaLog();//将deltaLog持久化到文件

    }
}
