import java.io.*;
import java.util.*;

class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

public class HuffmanCompression {

    private Map<Character, String> huffmanCodes;

    public String compress(String input) {
        huffmanCodes = buildHuffmanCodes(input);

        StringBuilder compressedString = new StringBuilder();

        // Converter a sequência de entrada em uma sequência compactada usando os códigos de Huffman
        for (char c : input.toCharArray()) {
            compressedString.append(huffmanCodes.get(c));
        }

        return compressedString.toString();
    }

    public String decompress(String compressedString) {
        StringBuilder decompressedString = new StringBuilder();

        StringBuilder currentCode = new StringBuilder();
        for (char c : compressedString.toCharArray()) {
            currentCode.append(c);
            for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                if (entry.getValue().equals(currentCode.toString())) {
                    decompressedString.append(entry.getKey());
                    currentCode.setLength(0);
                    break;
                }
            }
        }

        return decompressedString.toString();
    }

    private Map<Character, String> buildHuffmanCodes(String input) {
        Map<Character, String> huffmanCodes = new HashMap<>();

        // Calcular a frequência de cada caractere
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Criar uma fila de prioridade para armazenar os nós
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new HuffmanComparator());

        // Criar um nó para cada caractere e adicioná-lo à fila de prioridade
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.left = null;
            node.right = null;
            priorityQueue.add(node);
        }

        // Construir a árvore de Huffman
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode parentNode = new HuffmanNode();
            parentNode.character = '\0';
            parentNode.frequency = left.frequency + right.frequency;
            parentNode.left = left;
            parentNode.right = right;

            priorityQueue.add(parentNode);
        }

        // Gerar os códigos de Huffman recursivamente
        if (!priorityQueue.isEmpty()) {
            HuffmanNode rootNode = priorityQueue.peek();
            generateHuffmanCodes(rootNode, "", huffmanCodes);
        }

        return huffmanCodes;
    }

    private void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') {
            huffmanCodes.put(node.character, code);
        }

        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    public void writeCompressedFile(String compressedString) {
        try {
            FileWriter fileWriter = new FileWriter("baseHuffmanCompressao"+Main.version+".txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(compressedString);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readCompressedFile(int version) {
        StringBuilder compressedString = new StringBuilder();

        try {
            FileReader fileReader = new FileReader("baseHuffmanCompressao"+version+".txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                compressedString.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedString.toString();
    }
}