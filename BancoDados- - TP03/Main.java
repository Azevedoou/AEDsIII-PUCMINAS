import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static int version=0;
    public static void main(String[] args) {
        try{
            CRUD fileAF = new CRUD("BancoDados");
            HuffmanCompression huffman = new HuffmanCompression();
            try {

                // Ler o CSV file
                String basefile = "games.csv";
    
                FileInputStream fstream = new FileInputStream(basefile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    
                // ------------------------------------ //
    
                // Leitura de todo o CSV.
                String line;
      
                while((line = br.readLine()) != null) {
                    Game game = new Game();
                    game.read(line);
                    fileAF.create(game);
                }
                
                Scanner sc = new Scanner(System.in);
                int opcao=0;
                while(opcao!=7){
                    System.out.println(" _____________________________________");
                    System.out.println("|Digite qual operação deseja realizar |");
                    System.out.println("|1 - Create                           |");
                    System.out.println("|2 - Read                             |");
                    System.out.println("|3 - Update                           |");
                    System.out.println("|4 - Delete                           |");
                    System.out.println("|5 - Comprimir                        |");
                    System.out.println("|6 - Descomprimir                     |");
                    System.out.println("|7 - Sair e salvar arquivo            |");
                    System.out.println("|_____________________________________|");
                    System.out.println();
                    System.out.print("Opção: ");
                    try{
                    opcao = sc.nextInt();
                    System.out.println();
                    switch(opcao){
                        case 1: 
                        System.out.print("Digite o ID do Game que deseja adicionar no arquivo: ");
                        int createID = sc.nextInt();
                        String lixo=sc.nextLine();
                        boolean check;
                        try{
                            fileAF.Read(createID).getAppId();
                            check=false;
                        } catch(Exception e){
                            check=true;
                        }
                        if(!check){
                            System.out.println("\nO arquivo já existe!");
                            break;
                        }
                        System.out.print("Digite o nome do game: ");
                        String nome = sc.nextLine();
                        System.out.print("Digite o preço do game: ");
                        float preco = sc.nextFloat();
                        lixo=sc.nextLine();
                        System.out.print("Digite a data de lançamento do game (DD/MM/YYYY): ");
                        String data = sc.nextLine();
                        System.out.print("Digite os gêneros do game (separados por vírgula e espaço): ");
                        String genero = sc.nextLine();
                        String[] generos = genero.split(", ");
                        ArrayList<String> generosArray = new ArrayList<String>();
                        for(int i=0;i<generos.length;i++){
                            generosArray.add(generos[i]);
                        }
                        fileAF.Create(createID,preco,data,nome,generosArray);
                        System.out.println("\nRegistro criado com sucesso!");
                        break;
                        case 2:
                        System.out.print("Digite o ID do game que deseja pesquisar no arquivo: ");
                        int readID = sc.nextInt();
                        try{
                            fileAF.Read(readID).getAppId(); // Teste para ver se game existe (teste de pointer)
                            System.out.println("\nArquivo encontrado!\n");
                            System.out.println(fileAF.Read(readID).getAppId() +" "+ fileAF.Read(readID).getName() + " " + fileAF.Read(readID).getPrice()+" "+ fileAF.Read(readID).getReleaseDate());
                            for(int i=0; i<fileAF.Read(readID).getGenres().size(); i++){
                                System.out.print(fileAF.Read(readID).getGenres().get(i));
                                if(i!=fileAF.Read(readID).getGenres().size()-1){
                                    System.out.print(", ");
                                }
                            }
                            System.out.println();
                        } catch(Exception e){
                            System.out.println("\nArquivo não encontrado!");
                        }
                        
                        break;
                        case 3:
                        System.out.print("Digite o ID do game que deseja atualizar no arquivo: ");
                        int updateID = sc.nextInt();
                        lixo=sc.nextLine();
                        boolean checkArq;
                        try{
                            fileAF.Delete(updateID);
                            checkArq=true;
                        } catch(Exception e){
                            checkArq=false;
                        }
                        if(!checkArq){
                            System.out.println("\nO arquivo não existe!");
                            break;
                        }
                        System.out.print("Digite o novo nome do game: ");
                        String nomeUpdate = sc.nextLine();
                        System.out.print("Digite o novo preço do game: ");
                        Float precoUpdate = sc.nextFloat();
                        lixo=sc.nextLine();
                        System.out.print("Digite a nova data do game (DD/MM/YYYY): ");
                        String dataUpdate = sc.nextLine();
                        System.out.print("Digite os novos gêneros do game (separados por vírgula e espaço): ");
                        String generoUpdate = sc.nextLine();
                        String[] generosUpdate = generoUpdate.split(", ");
                        ArrayList<String> arrayUpdate = new ArrayList<String>();
                        for(int i=0;i<generosUpdate.length;i++){
                            arrayUpdate.add(generosUpdate[i]);
                        }
                        fileAF.Create(updateID, precoUpdate, dataUpdate, nomeUpdate, arrayUpdate);
                        System.out.println("\nRegistro atualizado com sucesso!");
                        // Enviar para Update no CRUD
                        break;
                        case 4:
                        System.out.print("Digite o ID do game que deseja deletar no arquivo: ");
                        int deleteID = sc.nextInt();
                        try{
                            fileAF.Read(deleteID).getAppId(); // Teste para ver se existe game
                            System.out.println("\nArquivo encontrado!\n");
                            System.out.println(fileAF.Read(deleteID).getAppId() +" "+ fileAF.Read(deleteID).getName() + " " + fileAF.Read(deleteID).getPrice()+" "+ fileAF.Read(deleteID).getReleaseDate());
                            for(int i=0; i<fileAF.Read(deleteID).getGenres().size(); i++){
                                System.out.print(fileAF.Read(deleteID).getGenres().get(i));
                                if(i!=fileAF.Read(deleteID).getGenres().size()-1){
                                    System.out.print(", ");
                                }
                            }
                            System.out.println();
                            fileAF.Delete(deleteID); // Deleta o game
                            System.out.println("\nArquivo deletado com sucesso!");                            
                        }catch(Exception e){
                            System.out.println("\nArquivo não encontrado!");
                        }
                        // Enviar para Delete no CRUD
                        break;
                        case 5:
                        System.out.println("Comprimindo arquivo...\n");
                        // Comprimir arquivos
                        // Huffman
                        long iHuffComp = System.currentTimeMillis();
                        try{
                            String arquivo = new Scanner(new File("games.csv")).useDelimiter("\\\\Z").next();
                            //System.out.println(arquivo);
                            String compressedString = huffman.compress(arquivo);
                            huffman.writeCompressedFile(compressedString);
                            System.out.println("Arquivo da sequência compactada gerado: baseHuffmanCompressao"+Main.version+".txt");
                        }
                        catch (Exception e){
                            System.out.println("Erro na compressão Huffman");
                        }
                        long fHuffComp = System.currentTimeMillis()-iHuffComp;
                        System.out.println("Tempo de compressão Huffman: "+fHuffComp+"ms");
                        // LZW
                        long iLzwComp = System.currentTimeMillis();
                        try{
                            String baseIncial="BancoDados";
                            String arqComprimido = "baseLzwCompressao"+version++;
                            byte[] fileContent = Files.readAllBytes(Paths.get(baseIncial));
                            int[] compressed = LZW.compress(fileContent);
                            byte[] compressedBytes = new byte[compressed.length * 2];
                            for (int i = 0; i < compressed.length; i++) {
                                compressedBytes[2 * i] = (byte) (compressed[i] >> 8);
                                compressedBytes[2 * i + 1] = (byte) (compressed[i] & 0xFF);
                            }
                            Files.write(Paths.get(arqComprimido), compressedBytes);
                            System.out.println("Arquivo da sequência compactada gerado: baseLzwCompressao"+(version-1)+".txt");
                        } catch (Exception e){
                            System.out.println("Erro na compressão LZW");
                        }
                        long fLzwComp = System.currentTimeMillis()-iLzwComp;
                        System.out.println("Tempo de compressão LZW: "+fLzwComp+"ms");
                        if(fHuffComp<fLzwComp){
                            System.out.print("Compressão Huffman foi ");
                            System.out.printf("%.2f ", (1.0-((float)fHuffComp/(float)fLzwComp))*100);
                            System.out.println("% mais eficiente");
                        } else{
                            System.out.print("Compressão LZW foi ");
                            System.out.printf("%.2f "+ (1.0-((float)fLzwComp/(float)fHuffComp))*100);
                            System.out.println("% mais eficiente");
                        }
                        System.out.println("\nArquivo comprimido com sucesso!");
                        break;
                        case 6:
                        System.out.print("Qual a versão do arquivo que deseja descomprimir? ");
                        int versao = sc.nextInt();
                        System.out.println("Descomprimindo arquivo...\n");
                        // Descomprimir arquivo da versão escolhida
                        // Huffman
                        long iHuffDesc = System.currentTimeMillis();
                        try{
                            String readString = huffman.readCompressedFile(versao);
                            String decompressedString = huffman.decompress(readString);
                            FileWriter fw = new FileWriter("baseHuffmanCompressao"+versao+".txt");
                            BufferedWriter writer = new BufferedWriter(fw);
                            writer.write(decompressedString);
                            writer.close();
                        }catch(Exception e){
                            System.out.println("Erro na descompressão Huffman");
                        }
                        long fHuffDesc = System.currentTimeMillis()-iHuffDesc;
                        System.out.println("Tempo de descompressão Huffman: "+fHuffDesc+"ms");
                        // LZW
                        long iLzwDesc = System.currentTimeMillis();
                        try {
                            String fileName = "baseLzwCompressao"+versao;
                            byte[] compressedFileContent = Files.readAllBytes(Paths.get(fileName));
                            int[] compressedData = new int[compressedFileContent.length / 2];
                            for (int i = 0; i < compressedData.length; i++) {
                                compressedData[i] = ((compressedFileContent[2 * i] & 0xFF) << 8) | (compressedFileContent[2 * i + 1] & 0xFF);
                            }

                            byte[] decompressed = LZW.decompress(compressedData);
                            Files.write(Paths.get(fileName), decompressed);
                        } catch (Exception e){
                            System.out.println("Erro na descompressão LZW");
                        }
                        long fLzwDesc = System.currentTimeMillis()-iLzwDesc;
                        System.out.println("Tempo de descompressão LZW: "+fLzwDesc+"ms");
                        if(fHuffDesc<fLzwDesc){
                            System.out.print("Descompressão Huffman foi ");
                            System.out.printf("%.2f "+(1.0-((float)fHuffDesc/(float)fLzwDesc))*100);
                            System.out.println("% mais eficiente");
                        } else{
                            System.out.print("Descompressão LZW foi ");
                            System.out.printf("%.2f ", (1.0-((float)fLzwDesc/(float)fHuffDesc))*100);
                            System.out.println("% mais eficiente");
                        }
                        System.out.println("\nArquivo descomprimido com sucesso!");
                        break;
                        case 7:
                        System.out.print("\nSalvando arquivo e encerrando programa...");
                        break;
                        default:
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    } catch(Exception e){
                        System.out.println("Digite uma opção válida!\n\n");
                    }
                    }
                    System.out.println("\n");
                    System.out.println(" _______________");
                    System.out.println("|               |");
                    System.out.println("|FIM DO PROGRAMA|");
                    System.out.println("|_______________|");
                // Fechamento do CSV
                fstream.close();
                sc.close();
            }
            catch(IOException e) { e.printStackTrace(); }
        }catch (IOException e){
            e.getStackTrace();
        }

        //----------------------------------------------------------------------------- //

        

        // Comandos:

       /*
       System.out.println(games.get(1).getName()); <-- teste, o ArrayList contém todos os games do csv, já "penerados".
       */
        
       
       
       
        
    }
}
