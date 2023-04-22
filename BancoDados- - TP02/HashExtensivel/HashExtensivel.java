import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashExtensivel {

    private Map<Integer, Bucket> buckets;  // Mapa de baldes
    private int depth;                     // Profundidade global
    private RandomAccessFile arqHash;

    // Construtor
    public HashExtensivel(int depth) throws IOException {
        arqHash = new RandomAccessFile("arqHash", "rw");
        this.depth = depth;
        buckets = new HashMap<Integer, Bucket>();
        int initialSize = (int) Math.pow(2, depth);
        for (int i = 0; i < initialSize; i++) {
            buckets.put(i, new Bucket(depth));
        }
    }

    // Função de hash
    private int hash(int key) {
        return key % (int) Math.pow(2, depth);
    }

    // Inserção de um novo par chave-valor
    public void insert(int key, long value) throws IOException {
        arqHash.seek(0);
        int bucketIndex = hash(key);
        arqHash.writeInt(bucketIndex);
        arqHash.seek(arqHash.getFilePointer());
        Bucket bucket = buckets.get(bucketIndex);
        if (bucket.isFull()) {
            if (bucket.getDepth() == depth) {
                // Duplica o mapa e aumenta a profundidade global
                Map<Integer, Bucket> newBuckets = new HashMap<Integer, Bucket>();
                for (int i = 0; i < buckets.size(); i++) {
                    int newBucketIndex = i + buckets.size();
                    Bucket newBucket = new Bucket(depth + 1);
                    if (i == bucketIndex) {
                        bucket.setDepth(depth + 1);
                        newBucket.addEntry(key, value);
                        arqHash.writeInt(key);
                        arqHash.writeLong(value);
                    } else {
                        Bucket oldBucket = buckets.get(i);
                        try{
                        for (Entry entry : oldBucket.getEntries()) {
                            int oldKey = entry.getKey();
                            long oldValue = entry.getValue();
                            int oldBucketIndex = hash(oldKey);
                            if (oldBucketIndex == i) {
                                oldBucket.removeEntry(oldKey);
                                newBucket.addEntry(oldKey, oldValue);
                            }
                        }
                        }catch(Exception e){

                        }
                    }
                    newBuckets.put(i, buckets.get(i));
                    newBuckets.put(newBucketIndex, newBucket);
                }
                buckets = newBuckets;
                depth++;
            } else {
                // Divide o balde em dois
                Bucket newBucket = new Bucket(bucket.getDepth());
                List<Entry> entries = bucket.getEntries();
                for (Entry entry : entries) {
                    int oldKey = entry.getKey();
                    long oldValue = entry.getValue();
                    int newBucketIndex = hash(oldKey);
                    if (newBucketIndex == bucketIndex) {
                        newBucket.addEntry(oldKey, oldValue);
                    }
                }
                bucket.setDepth(bucket.getDepth() + 1);
                bucket.clearEntries();
                newBucket.setDepth(bucket.getDepth());
                buckets.put(bucketIndex + (int) Math.pow(2, bucket.getDepth() - 1), newBucket);
                insert(key, value);
            }
        } else {
            // Adiciona a entrada ao balde
            bucket.addEntry(key, value);
        }
    }

    // Busca o valor associado a uma chave
    public Long search(int key) {
        int bucketIndex = hash(key);
        Bucket bucket = buckets.get(bucketIndex);
        List<Entry> entries = bucket.getEntries();
        for (Entry entry : entries) {
            if (entry.getKey() == key) {
                return entry.getValue();
            }
        }
        return null;
    }

    // Remove uma chave e seu valor associado
    public void remove(int key) {
        int bucketIndex = hash(key);
        Bucket bucket = buckets.get(bucketIndex);
        bucket.removeEntry(key);
    }
}