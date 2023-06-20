import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static int version=0;
    public static void main(String[] args)  {
        CifraSubstituicao substituicao = new CifraSubstituicao();
        CifraCesar cesar = new CifraCesar(3);
        try{
            CRUD fileAF = new CRUD("BancoDados");
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

                
                int opcao=0;
                Scanner sc = new Scanner(System.in);
                while(opcao!=7){
                    System.out.println(" _____________________________________");
                    System.out.println("|Digite qual operação deseja realizar |");
                    System.out.println("|1 - Create                           |");
                    System.out.println("|2 - Read                             |");
                    System.out.println("|3 - Update                           |");
                    System.out.println("|4 - Delete                           |");
                    System.out.println("|5 - Criptografar                     |");
                    System.out.println("|6 - Descriptografar                  |");
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
                        // Ciframento de César
                        BufferedWriter bwCesar = new BufferedWriter(new FileWriter("Cesar.txt"));                        
                        String caminhoArquivo = "games.csv";
                        String conteudoBanco = lerArquivoBD(caminhoArquivo);
                        String txtCifradoCesar = cesar.cifrar(conteudoBanco);
                        bwCesar.write(txtCifradoCesar);
                        bwCesar.close();
                        // Cifra por substituição
                        
                        String txtCifradoSubstituicao = substituicao.criptografa(conteudoBanco);
                        FileWriter crip = new FileWriter("substituicao.txt");
                        crip.write(txtCifradoSubstituicao);
                        crip.close();
                        System.out.println("Arquivos criptografados com sucesso!\n");
                        break;
                        case 6:
                        // Cesar
                        int chaveCesarDecrip = 3;
                        CifraCesar cesarDecript = new CifraCesar(chaveCesarDecrip);
                        BufferedReader brCesar = new BufferedReader(new FileReader("Cesar.txt"));
                        String lineCesar;
                        StringBuilder strBuilder = new StringBuilder();
                        while ((lineCesar = brCesar.readLine()) != null) {
                        strBuilder.append(lineCesar);
                        strBuilder.append("\n");
                        }
                        String cesarDecrip = strBuilder.toString();
                        cesarDecrip = cesarDecript.decifrar(cesarDecrip); // Descriptografia.
                        brCesar.close();
                        FileWriter escreveCesar = new FileWriter("Cesar.txt");
                        escreveCesar.write(cesarDecrip);
                        escreveCesar.close();

                        // Cifra por substituição
                        try {
                            // Abre arquivo criptografado para leitura
                            BufferedReader reader = new BufferedReader(new FileReader("substituicao.txt"));
                            StringBuilder textoCriptografadoBuilder = new StringBuilder();
                            String linhaArq;
                            while ((linhaArq = reader.readLine()) != null) {
                                // Lê todo arquivo e salva em um String Builder
                                textoCriptografadoBuilder.append(linhaArq);
                                textoCriptografadoBuilder.append("\n");
                            }
                            reader.close();
                            // Converte String Builder para String
                            String textoCriptografado = textoCriptografadoBuilder.toString();

                            // Descriptografa o texto
                            String textoDescriptografado = substituicao.descriptografa(textoCriptografado);

                            // Sobrescreve o arquivo com o texto descriptografado
                            FileWriter writer = new FileWriter("substituicao.txt");
                            writer.write(textoDescriptografado);
                            writer.close();

                            System.out.println("Arquivos descriptografados com sucesso!\n");
                        } catch (IOException e) {
                            System.out.println("Erro ao ler ou escrever no arquivo");
                            e.printStackTrace();
                        }
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
    public static String lerArquivoBD(String caminhoArquivo) throws IOException {
    StringBuilder conteudo = new StringBuilder();
    BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
    String linha;
    while ((linha = leitor.readLine()) != null) {
        conteudo.append(linha);
        conteudo.append("\n"); // Adicione uma quebra de linha, se necessário
    }
    leitor.close();

    return conteudo.toString();
}
}