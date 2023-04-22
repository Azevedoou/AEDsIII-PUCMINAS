public class NoArvoreB {

    // Declaração de atributos

    private int ordem;
    private int numChaves;
    private int[] chaves;
    private long[] pos;
    private NoArvoreB[] filhos;

    // Construtor

    public NoArvoreB(int ordem) {
        this.ordem = ordem;
        this.numChaves = 0;
        this.chaves = new int[2 * ordem - 1];
        this.pos = new long[2 * ordem - 1];
        this.filhos = new NoArvoreB[2 * ordem];
    }

    // Métodos de acesso e modificação dos campos

    public int getOrdem() {
        return ordem;
    }

    public int getNumChaves() {
        return numChaves;
    }

    public void setNumChaves(int numChaves) {
        this.numChaves = numChaves;
    }

    public int getChave(int i) {
        return chaves[i];
    }

    public long getPos(int i) {
        return pos[i];
    }

    public void setChave(int i, int chave) {
        chaves[i] = chave;
    }

    public void setPos(int i, long posicao) {
        pos[i] = posicao;
    }

    public NoArvoreB getFilho(int i) {
        return filhos[i];
    }

    public void setFilho(int i, NoArvoreB filho) {
        filhos[i] = filho;
    }

    // Métodos auxiliares
    
    public boolean isFolha() {
        return filhos[0] == null;
    }

    public void incrementaChaves() {
        numChaves++;
    }

    public void decrementaChaves() {
        numChaves--;
    }
}
