class Entry {

    private int key;        // Chave
    private long value;  // Valor

    // Construtor
    public Entry(int key, long value) {
        this.key = key;
        this.value = value;
    }

    // Retorna a chave
    public int getKey() {
        return key;
    }

    // Retorna o valor
    public long getValue() {
        return value;
    }
}
