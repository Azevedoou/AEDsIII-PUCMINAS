import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MainArvore {
    public static void main(String[] args) {
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
                // Criar árvore B de ordem 8
                ArvoreB arvore = new ArvoreB(8);
                //int cont=0;
                while((line = br.readLine()) != null) {
                    //cont++;
                    Game game = new Game();
                    game.read(line);
                    long pos = fileAF.create(game);
                    //if(cont<20)System.out.println(pos);
                    arvore.insere(game.getAppId(), pos);
                }
                //System.out.println(arvore.buscaPos(1472560));
                //System.out.println(arvore.busca(840010).getPos(1));
                //System.out.println(arvore.buscaPos(840010));
                Scanner sc = new Scanner(System.in);
                /*while(true){
                    System.out.print("ID: ");
                    int ID = sc.nextInt();
                    System.out.println(arvore.buscaPos(ID));
                    if(ID==0){
                        break;
                    }
                }*/
                int opcao=0;
                while(opcao!=5){
                    // Menu
                    System.out.println(" _____________________________________");
                    System.out.println("|Digite qual operação deseja realizar |");
                    System.out.println("|1 - Create                           |");
                    System.out.println("|2 - Read                             |");
                    System.out.println("|3 - Update                           |");
                    System.out.println("|4 - Delete                           |");
                    System.out.println("|5 - Sair e salvar árvore             |");
                    System.out.println("|_____________________________________|");
                    System.out.println();
                    System.out.print("Opção: ");
                    try{
                    opcao = sc.nextInt();
                    System.out.println();
                    switch(opcao){
                        // Se escolher opção 1
                        case 1: 
                        System.out.print("Digite o ID do Game que deseja adicionar na árvore: ");
                        int createID = sc.nextInt();
                        String lixo=sc.nextLine();
                        if(lixo.equals("CapturaEnter")){}
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
                        long pos = fileAF.Create(createID,preco,data,nome,generosArray);
                        arvore.insere(createID, pos);
                        System.out.println("\nRegistro criado com sucesso na árvore!");
                        break;
                        // Se escolher opção 2
                        case 2:
                        System.out.print("Digite o ID do game que deseja pesquisar na árvore: ");
                        int readID = sc.nextInt();
                        try{
                            pos = arvore.buscaPos(readID);
                            fileAF.ReadArvore(pos).getAppId(); // Teste para ver se game existe (teste de pointer)
                            System.out.println("\nRegistro encontrado!\n");
                            System.out.println(fileAF.Read(readID).getAppId() +" "+ fileAF.Read(readID).getName() + " " + fileAF.Read(readID).getPrice()+" "+ fileAF.Read(readID).getReleaseDate());
                            for(int i=0; i<fileAF.Read(readID).getGenres().size(); i++){
                                System.out.print(fileAF.Read(readID).getGenres().get(i));
                                if(i!=fileAF.Read(readID).getGenres().size()-1){
                                    System.out.print(", ");
                                }
                            }
                            System.out.println();
                        } catch(Exception e){
                            System.out.println("\nRegistro não encontrado!");
                        }
                        
                        break;
                        // Se escolher posição 3
                        case 3:
                        System.out.print("Digite o ID do game que deseja atualizar na árvore: ");
                        int updateID = sc.nextInt();
                        lixo=sc.nextLine();
                        boolean checkArq;
                        try{
                            fileAF.Delete(updateID);
                            arvore.remove(updateID);
                            checkArq=true;
                        } catch(Exception e){
                            checkArq=false;
                        }
                        if(!checkArq){
                            System.out.println("\nO registro não existe!");
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
                        pos = fileAF.Create(updateID, precoUpdate, dataUpdate, nomeUpdate, arrayUpdate);
                        arvore.insere(updateID, pos);
                        System.out.println("\nRegistro atualizado com sucesso!");
                        // Enviar para Update no CRUD
                        break;
                        // Se escolher posição 4
                        case 4:
                        System.out.print("Digite o ID do game que deseja deletar na árvore: ");
                        int deleteID = sc.nextInt();
                        try{
                            pos = arvore.buscaPos(deleteID);
                            fileAF.ReadArvore(pos).getAppId(); // Teste para ver se existe game
                            System.out.println("\nRegistro encontrado!\n");
                            System.out.println(fileAF.Read(deleteID).getAppId() +" "+ fileAF.Read(deleteID).getName() + " " + fileAF.Read(deleteID).getPrice()+" "+ fileAF.Read(deleteID).getReleaseDate());
                            for(int i=0; i<fileAF.Read(deleteID).getGenres().size(); i++){
                                System.out.print(fileAF.Read(deleteID).getGenres().get(i));
                                if(i!=fileAF.Read(deleteID).getGenres().size()-1){
                                    System.out.print(", ");
                                }
                            }
                            System.out.println();
                            fileAF.Delete(deleteID); // Deleta o game
                            arvore.remove(deleteID);
                            System.out.println("\nRegistro deletado com sucesso!");                            
                        }catch(Exception e){
                            System.out.println("\nRegistro não encontrado!");
                        }
                        // Enviar para Delete no CRUD
                        break;
                        // Se escolher posição 5
                        case 5:
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
    }
}