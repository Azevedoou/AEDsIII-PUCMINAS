import java.util.*;
import java.io.RandomAccessFile;
public class ListaInvertida {
    // Declaração de atributos
    private Map<String, List<Integer>> index;
    private RandomAccessFile arq;
    private int quant;
    // Construtor inicializando lista e arquivo binário
    public ListaInvertida(String nomeArq) {
        index = new HashMap<>();
        try{
            arq = new RandomAccessFile(nomeArq, "rw");
            arq.seek(0);
        }catch(Exception e){}
        quant=0;
    }
    // Método de inserção na lista invertida a partir de arraylist (gêneros)
    public void addDocument(int documentId, ArrayList<String> words) {
        try{
            arq.seek(0);
            arq.writeInt(++quant);
            arq.seek(arq.length());
            for (String word : words) {
                List<Integer> documents = index.getOrDefault(word, new ArrayList<>());
                documents.add(documentId);
                arq.writeInt(documentId);
                index.put(word, documents);
                arq.writeBytes(word);
            }
        } catch(Exception e){}
    }
    // Método de inserção na lista invertida a partir de String (nome)
    public void addDocument(int documentId, String nome) {
        String[] words = nome.split(" ");
        try{
            arq.seek(0);
            arq.writeInt(++quant);
            arq.seek(arq.length());
            for (String word : words) {
                List<Integer> documents = index.getOrDefault(word, new ArrayList<>());
                documents.add(documentId);
                arq.writeInt(documentId);
                index.put(word, documents);
                arq.writeBytes(word);
            }
        } catch(Exception e){}
    }
    // Método de busca na lista invertida (retorna posições no arquivo principal que contêm o que foi buscado)
    public List<Integer> search(String word) {
        return index.getOrDefault(word, Collections.emptyList());
    }
    // Método de remoção a partir da posição do arquivo
    public void removeDocument(int documentId) {
        for (Map.Entry<String, List<Integer>> entry : index.entrySet()) {
            List<Integer> documents = entry.getValue();
            documents.remove(Integer.valueOf(documentId));
        }
    }
    // Método de update na lista a partir da posição (gênero)
    public void updateDocument(int documentId, ArrayList<String> words) {
        removeDocument(documentId);
        addDocument(documentId, words);
    }
    // Método de update na lista a partir da posição (nome)
    public void updateDocument(int documentId, String words) {
        removeDocument(documentId);
        addDocument(documentId, words);
    }
}
