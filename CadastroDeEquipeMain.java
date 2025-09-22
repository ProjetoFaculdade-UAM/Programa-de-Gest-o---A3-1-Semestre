// Definir pacotes a serem usados
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Definir pacotes a serem usados
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

// ========== CLASSES DO SISTEMA ==========
// Criar classe para Projetos
class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private String local;
    private String equipe;
    private String dataInicio;
    private String dataFim;
    private java.util.List<Pessoa> participantes = new ArrayList<>();

    public Projeto(int id, String nome, String descricao, String dataInicio, String dataFim, String local, String equipe) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.equipe = equipe;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getEquipe() { return equipe; }
    public void setEquipe(String equipe) { this.equipe = equipe; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public java.util.List<Pessoa> getParticipantes() { return participantes; }
    public void setParticipantes(java.util.List<Pessoa> participantes) { this.participantes = participantes; }
}

/**
 * Representa uma pessoa/colaborador
 * Pode participar de múltiplos projetos
 */

// Criar classe para Pessoas
class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String habilidades;
    private java.util.List<Projeto> projetos = new ArrayList<>();

    public Pessoa(int id, String nome, String email, String telefone, String habilidades) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.habilidades = habilidades;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public java.util.List<Projeto> getProjetos() { return projetos; }
    public void setProjetos(java.util.List<Projeto> projetos) { this.projetos = projetos; }
}

/**
 * Relacionamento entre Projeto e Pessoa (N para N)
 * Controla inscrições de pessoas em projetos
 */

// Criar classe para Inscrições
class Inscricao implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Projeto projeto;
    private Pessoa pessoa;

    public Inscricao(int id, Projeto projeto, Pessoa pessoa) {
        this.id = id;
        this.projeto = projeto;
        this.pessoa = pessoa;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }
}

// Adicione esta classe no seu arquivo
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
    
    // Getters e Setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
}

// ========== TELA DE LOGIN ==========
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
        
        carregarUsuarios();
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        fieldsPanel.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        fieldsPanel.add(usernameField);
        
        fieldsPanel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        fieldsPanel.add(passwordField);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
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
    
    private void salvarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar usuários: " + e.getMessage());
        }
    }
    
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
        
        JOptionPane.showMessageDialog(this, 
            "Usuário ou senha incorretos!", 
            "Erro de Login", 
            JOptionPane.ERROR_MESSAGE);
        passwordField.setText("");
        usernameField.requestFocus();
    }
    
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
}

// ========== SISTEMA PRINCIPAL ==========
// Criar classe para Cadastro de Equipes
public class CadastroDeEquipeMain extends JFrame {

    private static final long serialVersionUID = 1L;

    private java.util.List<Projeto> projetos = new ArrayList<>();
    private java.util.List<Pessoa> pessoas = new ArrayList<>();
    private java.util.List<Inscricao> inscricoes = new ArrayList<>();

    private DefaultTableModel projetosModel;
    private DefaultTableModel pessoasModel;
    private DefaultTableModel inscricoesModel;
    private JTextArea relatorioProjetos;
    private JTextArea relatorioPessoas;

    private static final String ARQUIVO_PROJETOS = "projetos.dat";
    private static final String ARQUIVO_PESSOAS = "pessoas.dat";
    private static final String ARQUIVO_INSCRICOES = "inscricoes.dat";

    // Variáveis para o layout lateral
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel projetosPanel;
    private JPanel pessoasPanel;
    private JPanel inscricoesPanel;
    private JPanel relatoriosPanel;

    public CadastroDeEquipeMain() {
        setTitle("Sistema de Gestão de Projetos");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Carrega dados salvos (se existirem)
        carregarDados();

        // Cria split pane para layout lateral
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(3);

        // Painel lateral com botões
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Botões de navegação
        JButton btnProjetos = new JButton("📋 Projetos");
        JButton btnPessoas = new JButton("👥 Pessoas");
        JButton btnInscricoes = new JButton("📝 Inscrições");
        JButton btnRelatorios = new JButton("📊 Relatórios");

        // Configurar botões
        Dimension buttonSize = new Dimension(140, 40);
        btnProjetos.setMaximumSize(buttonSize);
        btnPessoas.setMaximumSize(buttonSize);
        btnInscricoes.setMaximumSize(buttonSize);
        btnRelatorios.setMaximumSize(buttonSize);

        btnProjetos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPessoas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInscricoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRelatorios.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adicionar botões ao painel lateral
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(btnProjetos);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(btnPessoas);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(btnInscricoes);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(btnRelatorios);
        sidePanel.add(Box.createVerticalGlue());

        // Painel principal com CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Criar os painéis de conteúdo (mantendo a estrutura original)
        projetosPanel = criarPainelProjetos();
        pessoasPanel = criarPainelPessoas();
        inscricoesPanel = criarPainelInscricoes();
        relatoriosPanel = criarPainelRelatorios();

        // Adicionar painéis ao cardLayout
        mainPanel.add(projetosPanel, "Projetos");
        mainPanel.add(pessoasPanel, "Pessoas");
        mainPanel.add(inscricoesPanel, "Inscricoes");
        mainPanel.add(relatoriosPanel, "Relatorios");

        // Action listeners para os botões
        btnProjetos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Projetos");
            }
        });
        
        btnPessoas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Pessoas");
            }
        });
        
        btnInscricoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Inscricoes");
            }
        });
        
        btnRelatorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarRelatorios();
                cardLayout.show(mainPanel, "Relatorios");
            }
        });

        splitPane.setLeftComponent(sidePanel);
        splitPane.setRightComponent(mainPanel);

        add(splitPane);

        // Preenche tabelas com os dados carregados
        atualizarTabelas();

        // Salvar ao fechar
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarDados();
            }
        });
    }

/**
     * Cria painel lateral com botões de navegação
     */
    
    // Métodos para criar os painéis (mantendo a estrutura original)
    private JPanel criarPainelProjetos() {
        JPanel panel = new JPanel(new BorderLayout());
        projetosModel = new DefaultTableModel(new String[]{"ID", "Nome", "Local", "Equipe"}, 0);
        JTable tabelaProjetos = new JTable(projetosModel);
        panel.add(new JScrollPane(tabelaProjetos), BorderLayout.CENTER);

        JButton btnAddProjeto = new JButton("Cadastrar Projeto");
        btnAddProjeto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarProjeto();
            }
        });
        panel.add(btnAddProjeto, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel criarPainelPessoas() {
        JPanel panel = new JPanel(new BorderLayout());
        pessoasModel = new DefaultTableModel(new String[]{"ID", "Nome", "Email"}, 0);
        JTable tabelaPessoas = new JTable(pessoasModel);
        panel.add(new JScrollPane(tabelaPessoas), BorderLayout.CENTER);

        JButton btnAddPessoa = new JButton("Cadastrar Pessoa");
        btnAddPessoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarPessoa();
            }
        });
        panel.add(btnAddPessoa, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel criarPainelInscricoes() {
        JPanel panel = new JPanel(new BorderLayout());
        inscricoesModel = new DefaultTableModel(new String[]{"Projeto", "Pessoa"}, 0);
        JTable tabelaInscricoes = new JTable(inscricoesModel);
        panel.add(new JScrollPane(tabelaInscricoes), BorderLayout.CENTER);

        JButton btnAddInscricao = new JButton("Inscrever Pessoa");
        btnAddInscricao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarInscricao();
            }
        });
        panel.add(btnAddInscricao, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel criarPainelRelatorios() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        relatorioProjetos = new JTextArea();
        relatorioProjetos.setEditable(false);
        relatorioProjetos.setBorder(BorderFactory.createTitledBorder("Projetos e Participantes"));

        relatorioPessoas = new JTextArea();
        relatorioPessoas.setEditable(false);
        relatorioPessoas.setBorder(BorderFactory.createTitledBorder("Pessoas e Projetos"));

        panel.add(new JScrollPane(relatorioProjetos));
        panel.add(new JScrollPane(relatorioPessoas));

        JButton btnAtualizarRelatorios = new JButton("Atualizar Relatórios");
        btnAtualizarRelatorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarRelatorios();
            }
        });

        JPanel relatoriosContainer = new JPanel(new BorderLayout());
        relatoriosContainer.add(panel, BorderLayout.CENTER);
        relatoriosContainer.add(btnAtualizarRelatorios, BorderLayout.SOUTH);

        return relatoriosContainer;
    }

    // ========== MÉTODOS ORIGINAIS (MANTIDOS INTACTOS) ==========
    
    // Persistencia de dados
    private void salvarDados() {
        salvarObjeto(projetos, ARQUIVO_PROJETOS);
        salvarObjeto(pessoas, ARQUIVO_PESSOAS);
        salvarObjeto(inscricoes, ARQUIVO_INSCRICOES);
    }

    private void carregarDados() {
        projetos = carregarObjeto(ARQUIVO_PROJETOS, new ArrayList<>());
        pessoas = carregarObjeto(ARQUIVO_PESSOAS, new ArrayList<>());
        inscricoes = carregarObjeto(ARQUIVO_INSCRICOES, new ArrayList<>());
    }

    private void salvarObjeto(Object obj, String arquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T carregarObjeto(String arquivo, T valorPadrao) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (T) in.readObject();
        } catch (Exception e) {
            return valorPadrao;
        }
    }

    // Atualizar tabelas
    private void atualizarTabelas() {
        // limpa antes de popular para evitar duplicação
        projetosModel.setRowCount(0);
        pessoasModel.setRowCount(0);
        inscricoesModel.setRowCount(0);

        for (Projeto p : projetos) {
            projetosModel.addRow(new Object[]{p.getId(), p.getNome(), p.getLocal(), p.getEquipe()});
        }
        for (Pessoa pes : pessoas) {
            pessoasModel.addRow(new Object[]{pes.getId(), pes.getNome(), pes.getEmail()});
        }
        for (Inscricao insc : inscricoes) {
            inscricoesModel.addRow(new Object[]{insc.getProjeto().getNome(), insc.getPessoa().getNome()});
        }
    }

    // Funções originais
    private void cadastrarProjeto() {
        JTextField nome = new JTextField();
        JTextField descricao = new JTextField();
        JTextField local = new JTextField();
        JTextField equipe = new JTextField();
        JTextField inicio = new JTextField();
        JTextField fim = new JTextField();

        Object[] msg = {
                "Nome:", nome,
                "Descrição:", descricao,
                "Local:", local,
                "Equipe:", equipe,
                "Data início:", inicio,
                "Data fim:", fim
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Novo Projeto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Projeto p = new Projeto(projetos.size() + 1, nome.getText(), descricao.getText(),
                    inicio.getText(), fim.getText(), local.getText(), equipe.getText());
            projetos.add(p);
            projetosModel.addRow(new Object[]{p.getId(), p.getNome(), p.getLocal(), p.getEquipe()});
            salvarDados();
        }
    }

    // Cadastrar Pessoas
    private void cadastrarPessoa() {
        JTextField nome = new JTextField();
        JTextField email = new JTextField();
        JTextField telefone = new JTextField();
        JTextField habilidades = new JTextField();

        Object[] msg = {
                "Nome:", nome,
                "Email:", email,
                "Telefone:", telefone,
                "Habilidades:", habilidades
        };

        int option = JOptionPane.showConfirmDialog(this, msg, "Nova Pessoa", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (Pessoa existente : pessoas) {
                if (existente.getEmail().equalsIgnoreCase(email.getText())) {
                    JOptionPane.showMessageDialog(this, "Email já cadastrado!");
                    return;
                }
            }
            Pessoa p = new Pessoa(pessoas.size() + 1, nome.getText(), email.getText(),
                    telefone.getText(), habilidades.getText());
            pessoas.add(p);
            pessoasModel.addRow(new Object[]{p.getId(), p.getNome(), p.getEmail()});
            salvarDados();
        }
    }

/**
     * Associa pessoa existente a projeto existente
     * Valida se associação já existe
     */

    // Cadastrar Inscricoes
    private void cadastrarInscricao() {
        if (projetos.isEmpty() || pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre pelo menos 1 projeto e 1 pessoa primeiro!");
            return;
        }

        String[] nomesProjetos = projetos.stream()
                .map(p -> p.getId() + " - " + p.getNome())
                .toArray(String[]::new);
        String[] nomesPessoas = pessoas.stream()
                .map(p -> p.getId() + " - " + p.getNome())
                .toArray(String[]::new);

        JComboBox<String> comboProjetos = new JComboBox<>(nomesProjetos);
        JComboBox<String> comboPessoas = new JComboBox<>(nomesPessoas);

        Object[] msg = {"Projeto:", comboProjetos, "Pessoa:", comboPessoas};

        int option = JOptionPane.showConfirmDialog(this, msg, "Nova Inscrição", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int projIndex = comboProjetos.getSelectedIndex();
            int pessIndex = comboPessoas.getSelectedIndex();

            Projeto projeto = projetos.get(projIndex);
            Pessoa pessoa = pessoas.get(pessIndex);

            for (Inscricao i : inscricoes) {
                if (i.getProjeto() == projeto && i.getPessoa() == pessoa) {
                    JOptionPane.showMessageDialog(this, "Essa pessoa já está inscrita nesse projeto!");
                    return;
                }
            }

            Inscricao insc = new Inscricao(inscricoes.size() + 1, projeto, pessoa);
            inscricoes.add(insc);
            projeto.getParticipantes().add(pessoa);
            pessoa.getProjetos().add(projeto);

            inscricoesModel.addRow(new Object[]{projeto.getNome(), pessoa.getNome()});
            salvarDados();
        }
    }

/**
     * Atualiza relatórios com dados consolidados
     * Mostra projetos com participantes e pessoas com projetos
     */

    // Atualizar relatorios
    private void atualizarRelatorios() {
        StringBuilder sbProjetos = new StringBuilder();
        for (Projeto p : projetos) {
            sbProjetos.append("[").append(p.getId()).append("] ").append(p.getNome())
                    .append(" | Local: ").append(p.getLocal() != null ? p.getLocal() : "-")
                    .append(" | Participantes: ").append(p.getParticipantes().size()).append("\n");
            for (Pessoa pessoa : p.getParticipantes()) {
                sbProjetos.append("   - ").append(pessoa.getNome()).append(" (").append(pessoa.getEmail()).append(")\n");
            }
            sbProjetos.append("\n");
        }
        relatorioProjetos.setText(sbProjetos.length() > 0 ? sbProjetos.toString() : "Nenhum projeto cadastrado.");

        StringBuilder sbPessoas = new StringBuilder();
        for (Pessoa pes : pessoas) {
            sbPessoas.append("[").append(pes.getId()).append("] ").append(pes.getNome())
                    .append(" | Email: ").append(pes.getEmail())
                    .append(" | Projetos: ").append(pes.getProjetos().size()).append("\n");
            for (Projeto prj : pes.getProjetos()) {
                sbPessoas.append("   - ").append(prj.getNome()).append("\n");
            }
            sbPessoas.append("\n");
        }
        relatorioPessoas.setText(sbPessoas.length() > 0 ? sbPessoas.toString() : "Nenhuma pessoa cadastrada.");
    }

    // MÉTODO MAIN MODIFICADO - Agora inicia com a tela de login
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}