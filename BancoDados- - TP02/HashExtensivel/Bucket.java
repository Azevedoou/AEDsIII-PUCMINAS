import java.util.ArrayList;
import java.util.List;

class Bucket {

    private int depth;             // Profundidade local
    private List<Entry> entries;   // Lista de entradas

    // Construtor
    public Bucket(int depth) {
        this.depth = depth;
        entries = new ArrayList<Entry>();
    }

    // Verifica se o balde está cheio
    public boolean isFull() {
        return entries.size() >= (int) Math.pow(2, depth);
    }

    // Adiciona uma entrada ao balde
    public void addEntry(int key, Long value) {
        entries.add(new Entry(key, value));
    }

    // Remove uma entrada do balde
    public void removeEntry(int key) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getKey() == key) {
                entries.remove(i);
                return;
            }
        }
    }

    // Limpa todas as entradas do balde
    public void clearEntries() {
        entries.clear();
    }

    // Retorna a profundidade local do balde
    public int getDepth() {
        return depth;
    }

    // Define a profundidade local do balde
    public void setDepth(int depth) {
        this.depth = depth;
    }

    // Retorna a lista de entradas do balde
    public List<Entry> getEntries() {
        return entries;
    }
}
