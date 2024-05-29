
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class ControleLivros {
    
    Livros lv = new Livros();
    //private java.sql.Resultset rs;
    private Connection conexao = null;
    private Statement st = null;
    private String servername = "localhost:3306";
    private String usuario = "root";
    private String senha = "";
    private String banco = "bdlivros";
    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://" + servername + "/" + banco;
    
    
    public void Conecta() {
        try {

            conexao = DriverManager.getConnection(this.url, this.usuario, this.senha);
            st = conexao.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
        public void Cadastrar(int id, String nome_livro, String autor, int estoque, double valor) {

        try {
            lv.setId_produto(id);
            lv.setNome_livro(nome_livro);
            lv.setAutor(autor);
            lv.setEstoque(estoque);
            lv.setValor(valor);

            PreparedStatement sql;
            sql = conexao.prepareStatement("Insert into livros (ID_PRODUTO, nome_livro, nome_autor, estoque, valor) values (?,?,?,?,?)");
            sql.setInt(1, lv.getId_produto());
            sql.setString(2, lv.getNome_livro());
            sql.setString(3, lv.getAutor());
            sql.setInt(4, lv.getEstoque());
            sql.setDouble(5, lv.getValor());

            int rs = sql.executeUpdate();

            if (rs == 1) {
                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro não realizado!");
            }

        } catch (Exception e) {
            System.out.println("Erro SQL: " + e);
        }

    }
    
    public void Listar() {
        PreparedStatement sql;
        ResultSet rs;

        try {
            sql = conexao.prepareCall("select * from Livros");
            rs = sql.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("ID_PRODUTO"));
                System.out.println("NOME LIVRO: " + rs.getString("NOME_LIVRO"));
                System.out.println("NOME_AUTOR : " + rs.getString("NOME_AUTOR"));
                System.out.println("ESTOQUE: " + rs.getString("ESTOQUE"));
                System.out.println("VALOR: " + rs.getString("VALOR"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }
    
    public String Exibir(int id) {
        PreparedStatement sql;
        ResultSet rs;
        
        String lista = ""; 
        
        try {
            sql = conexao.prepareCall("select * from Livros where ID_PRODUTO = "+id);
            rs = sql.executeQuery();
            
            if (rs.next()){
                lista = rs.getString("ID_PRODUTO")+";"+rs.getString("NOME_LIVRO")+";"+
                        rs.getString("NOME_AUTOR")+";"+rs.getString("ESTOQUE")+";"+
                        rs.getString("VALOR");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }   
        return lista;
    }
    
      public void Alterar(int id, String nome_livro,String autor, int estoque, double valor) {

        try {
            lv.setId_produto(id);
            lv.setNome_livro(nome_livro);
            lv.setAutor(autor);
            lv.setEstoque(estoque);
            lv.setValor(valor);

            PreparedStatement sql;
            sql = conexao.prepareStatement("UPDATE Livros SET nome_livro = ?, nome_autor = ?, estoque = ?, valor = ? WHERE ID_PRODUTO = ?");
            sql.setInt(5, lv.getId_produto());
            sql.setString(1, lv.getNome_livro());
            sql.setString(2, lv.getAutor());
            sql.setInt(3, lv.getEstoque());
            sql.setDouble(4, lv.getValor());

            int rs = sql.executeUpdate();

            if (rs == 1) {
                JOptionPane.showMessageDialog(null, "Cadastro ALTERADO com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro não realizado!");
            }

        } catch (Exception e) {
            System.out.println("Erro SQL: " + e);
        }

    }
    public void Excluir(int id) {

        try {
            lv.setId_produto(id);
            
            PreparedStatement sql;
            sql = conexao.prepareStatement("delete from livros WHERE ID_PRODUTO = ?");
            sql.setInt(1, lv.getId_produto());
            
            int rs = sql.executeUpdate();

            if (rs == 1) {
                JOptionPane.showMessageDialog(null, "Cadastro DELETADO com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Cadastro não realizado!");
            }

        } catch (Exception e) {
            System.out.println("Erro SQL: " + e);
        }

    }
    
}
