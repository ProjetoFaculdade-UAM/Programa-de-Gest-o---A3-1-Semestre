import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.lang.model.SourceVersion;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

// ========== CLASSE USUÁRIO PARA LOGIN/REGISTRO ==========
class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String email;
    
    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Getters - Acesso seguro aos dados sensíveis
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
}

// ========== TELA DE LOGIN COM REGISTRO ==========
class LoginScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    
    private static final String ARQUIVO_USUARIOS = "usuarios.dat";
    private java.util.List<Usuario> usuarios = new ArrayList<>();
    
    public LoginScreen() {
        setTitle("Login - Sistema de Gestão de Projetos");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        carregarUsuarios(); // Carrega usuários do arquivo ou cria admin padrão
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Painel para os campos com BoxLayout para melhor controle
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.add(new JLabel("Usuário:"));
        usernameField = new JTextField(15); // Tamanho fixo
        userPanel.add(usernameField);
        
        // Senha
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passPanel.add(new JLabel("Senha:  "));
        passwordField = new JPasswordField(15); // Tamanho fixo
        passPanel.add(passwordField);
        
        fieldsPanel.add(userPanel);
        fieldsPanel.add(Box.createVerticalStrut(5)); // Espaço entre os campos
        fieldsPanel.add(passPanel);
        
        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        loginButton = new JButton("Entrar");
        registerButton = new JButton("Registrar");
        
        buttonsPanel.add(loginButton);
        buttonsPanel.add(registerButton);
        
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        add(panel);
        
        // Event listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaRegistro();
            }
        });
        
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attemptLogin();
                }
            }
        });
        
        // Foca no campo de usuário ao abrir
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                usernameField.requestFocus();
            }
        });
    }
    
    private void carregarUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_USUARIOS))) {
            usuarios = (java.util.List<Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Primeira execução - cria usuário admin padrão
            usuarios.add(new Usuario("admin", "admin123", "admin@system.com"));
            salvarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage());
            usuarios = new ArrayList<>();
        }
    }

    /**
     * Salva lista de usuários no arquivo serializado
     */

    private void salvarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuários: " + e.getMessage());
        }
    }

    /**
     * Valida credenciais do usuário contra a base cadastrada
     * Se válido, abre aplicação principal
     */

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                dispose();
                openMainApplication();
                return;
            }
        }
        
        // Login falhou - limpa campo de senha e refoca no usuário
        
        JOptionPane.showMessageDialog(this, 
            "Usuário ou senha incorretos!", 
            "Erro de Login", 
            JOptionPane.ERROR_MESSAGE);
        passwordField.setText("");
        usernameField.requestFocus();
    }

    /**
     * Abre diálogo para registro de novo usuário
     * Valida dados e impede duplicação
     */

    private void abrirTelaRegistro() {
        JTextField regUsername = new JTextField();
        JPasswordField regPassword = new JPasswordField();
        JPasswordField regConfirmPassword = new JPasswordField();
        JTextField regEmail = new JTextField();
        
        Object[] message = {
            "Nome de usuário:", regUsername,
            "Senha:", regPassword,
            "Confirmar senha:", regConfirmPassword,
            "Email:", regEmail
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Nova Conta", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String username = regUsername.getText().trim();
            String password = new String(regPassword.getPassword()).trim();
            String confirmPassword = new String(regConfirmPassword.getPassword()).trim();
            String email = regEmail.getText().trim();
            
            // Validações
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
                return;
            }
            
            // Verifica se usuário já existe
            for (Usuario usuario : usuarios) {
                if (usuario.getUsername().equalsIgnoreCase(username)) {
                    JOptionPane.showMessageDialog(this, "Usuário já existe!");
                    return;
                }
            }
            
            // Cria novo usuário
            usuarios.add(new Usuario(username, password, email));
            salvarUsuarios();
            
            JOptionPane.showMessageDialog(this, "Conta criada com sucesso!\nFaça login com suas credenciais.");
        }
    }
    
    /**
     * Inicia aplicação principal após login bem-sucedido
     * Usa Timer para garantir que login seja fechado antes
     */
    
    private void openMainApplication() {
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new CadastroDeEquipeMain().setVisible(true);
                    }
                });
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
};