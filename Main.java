import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ======================================================
//                 PROJETO GIGANTE COMPLETO
//               SISTEMA DE DETECÇÃO DE GOLPES
//       + EDUCAÇÃO E ORIENTAÇÃO DE SEGURANÇA DIGITAL
// ======================================================

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PhishingDetector detector = new PhishingDetector();

        while (true) {
            System.out.println("\n===== MENU DE GOLPES FINANCEIROS =====");
            for (int i = 0; i < GolpeDatabase.GOLPES.length; i++) {
                System.out.println((i + 1) + " - " + GolpeDatabase.GOLPES[i][0]);
            }
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            }

            if (opcao < 1 || opcao > GolpeDatabase.GOLPES.length) {
                System.out.println("Opção inválida!");
                continue;
            }

            exibirInformacoesGolpe(opcao);

            // Se for SMS (opção 9)
            if (opcao == 9) {
                System.out.println("\nDeseja analisar uma mensagem suspeita?");
                System.out.print("Digite S para analisar ou ENTER para voltar: ");
                String escolha = sc.nextLine();

                if (escolha.equalsIgnoreCase("s")) {
                    System.out.print("\nDigite a mensagem SMS recebida: ");
                    String texto = sc.nextLine();

                    System.out.print("Digite o número do remetente: ");
                    String remetente = sc.nextLine();

                    SMSMessage msg = new SMSMessage(texto, remetente);

                    // delay para ficar mais natural
                    delay("Analisando mensagem", 5);

                    DetectionResult resultado = detector.analisarMensagem(msg);
                    String relatorio = SecurityReport.gerar(msg, resultado);

                    System.out.println(relatorio);

                    System.out.println("\n=== SOLUÇÕES SUGERIDAS ===");
                    Solucoes.sugerirParaSMS(resultado);

                    System.out.println("\n=== GOLPES RELACIONADOS ===");
                    Relacionamentos.mostrarRelacionados(9);
                }
            }

            System.out.print("\nPressione ENTER para voltar ao menu...");
            sc.nextLine();
        }

        sc.close();
    }

    public static void exibirInformacoesGolpe(int opcao) {
        String[] dados = GolpeDatabase.GOLPES[opcao - 1];

        System.out.println("\n====== DETALHES DO GOLPE ======");
        System.out.println("Título: " + dados[0]);
        System.out.println("\nDescrição:\n" + dados[1]);
        System.out.println("\nComo se proteger:\n" + dados[2]);
        System.out.println("\nAções práticas:\n" + dados[3]);

        System.out.println("\nGolpes relacionados:");
        Relacionamentos.mostrarRelacionados(opcao);
        System.out.println("===============================");
    }

    // Delay personalizado
    public static void delay(String mensagem, int pontos) {
        System.out.print(mensagem);
        for (int i = 0; i < pontos; i++) {
            try {
                Thread.sleep(450);
            } catch (Exception e) {}
            System.out.print(".");
        }
        System.out.println();
    }
}


// ======================================================
//               BANCO DE DADOS DE GOLPES
// ======================================================

class GolpeDatabase {

    public static final String[][] GOLPES = {

            // 1
            {
                    "Golpes por contato telefônico - Falsa Central de Atendimento",
                    "Criminosos fingem ser atendentes do banco e induzem a vítima a confirmar dados.",
                    "Nunca forneça senhas ou códigos pelo telefone. Bancos não pedem isso.",
                    "Desligue imediatamente. Ligue você mesmo para o número oficial do banco."
            },

            // 2
            {
                    "Golpe do Falso Motoboy",
                    "Golpistas dizem que o cartão foi clonado e mandam um motoboy retirar.",
                    "Nenhum banco recolhe cartão na sua casa.",
                    "Jamais entregue cartão ou documentos para terceiros."
            },

            // 3
            {
                    "Golpe da Mão Fantasma",
                    "Golpista envia um link remoto alegando suporte e passa a controlar seu celular.",
                    "Nunca instale apps a pedido de supostos atendentes.",
                    "Se instalou, desligue o aparelho imediatamente e procure o banco."
            },

            // 4
            {
                    "Golpe Módulo de Segurança",
                    "Mensagem falsa dizendo que precisa instalar 'modulo de segurança'.",
                    "Bancos não mandam links por WhatsApp ou SMS.",
                    "Baixe apps SOMENTE pela loja oficial."
            },

            // 5
            {
                    "Golpe do Empréstimo Consignado",
                    "Ofertas falsas de empréstimos com juros irreais.",
                    "Consulte sempre seu INSS ou banco oficial.",
                    "Nunca aceite ofertas rápidas sem contrato real."
            },

            // 6
            {
                    "Golpe da Liberação de Equipamentos",
                    "Golpistas pedem pagamento antecipado para liberar equipamento.",
                    "Ninguém pede taxa antecipada.",
                    "Pesquise CNPJ e reputação antes de pagar qualquer coisa."
            },

            // 7
            {
                    "Golpe do 0800",
                    "Criminosos falsificam números 0800 que parecem do banco.",
                    "Confirme sempre no site oficial.",
                    "Nunca ligue de volta para números enviados via SMS."
            },

            // 8
            {
                    "Golpe da Videochamada",
                    "Fingem ser atendentes e pedem para mostrar documentos na câmera.",
                    "Nenhum banco pede vídeo de documentos.",
                    "Evite exibir dados sensíveis em chamadas."
            },

            // 9  (SMS)
            {
                    "Golpes por Mensagens - Links Falsos",
                    "SMS dizendo para clicar em links suspeitos.",
                    "Nunca clique em links recebidos por SMS.",
                    "Entre manualmente no app do banco.\n" +
                    "Verifique sempre o remetente.\n" +
                    "Leia mais: https://blog.bb.com.br/golpe-via-sms-rouba-dados-bancarios/"
            },

            // 10
            {
                    "Golpes no WhatsApp",
                    "Golpistas se passam por familiares pedindo dinheiro.",
                    "Confirme sempre por ligação.",
                    "Nunca transfira sem confirmar a identidade."
            },

            // 11
            {
                    "Golpe do Desenrola Brasil",
                    "Mensagens falsas dizendo para renegociar dívidas.",
                    "Consulte o site oficial do governo.",
                    "Nunca envie CPF por WhatsApp."
            },

            // 12
            {
                    "Catfish e Golpes Online",
                    "Perfis falsos manipulam vítimas emocionalmente para pedir dinheiro.",
                    "Pesquise fotos no Google.",
                    "Nunca envie dinheiro para desconhecidos."
            },

            // 13
            {
                    "Golpe do Emprego",
                    "Promessas falsas de emprego cobrando taxa antecipada.",
                    "Vagas reais não cobram.",
                    "Pesquise CNPJ antes de enviar documentos."
            },

            // 14
            {
                    "Golpes Pix",
                    "Criminosos pressionam para enviar Pix imediato.",
                    "Nunca aja sob pressão.",
                    "Verifique sempre o destinatário."
            },

            // 15
            {
                    "Conta Laranja",
                    "Golpistas usam sua conta para lavar dinheiro.",
                    "Nunca empreste conta.",
                    "Pode gerar crime grave."
            },

            // 16
            {
                    "Compras Online",
                    "Sites falsos oferecendo preços baixos demais.",
                    "Verifique o cadeado HTTPS.",
                    "Procure reclamações e avaliações."
            },

            // 17
            {
                    "Golpe do Boleto Falso",
                    "Golpistas enviam boletos adulterados.",
                    "Cheque sempre o beneficiário.",
                    "Pague apenas via app do banco."
            },

            // 18
            {
                    "Cartão Clonado",
                    "Dados copiados e usados indevidamente.",
                    "Ative alertas no app.",
                    "Não forneça dados por telefone."
            },
    };
}


// ======================================================
//           RELACIONAMENTOS ENTRE GOLPES
// ======================================================

class Relacionamentos {

    private static final Map<Integer, List<Integer>> mapa = new HashMap<>();

    static {
        mapa.put(9, Arrays.asList(7, 10, 11, 12)); // links falsos
        mapa.put(10, Arrays.asList(9, 12));
        mapa.put(17, Arrays.asList(16));
        mapa.put(14, Arrays.asList(10));
    }

    public static void mostrarRelacionados(int opcao) {
        List<Integer> rel = mapa.get(opcao);
        if (rel == null) {
            System.out.println("Nenhum relacionado encontrado.");
            return;
        }
        for (int r : rel) {
            System.out.println("- " + GolpeDatabase.GOLPES[r - 1][0]);
        }
    }
}


// ======================================================
//                 SMS + PHISHING DETECTOR
// ======================================================

class SMSMessage {
    private String text;
    private String sender;
    private String receivedAt;

    public SMSMessage(String text, String sender) {
        this.text = text;
        this.sender = sender;
        this.receivedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getText() { return text; }
    public String getSender() { return sender; }
    public String getReceivedAt() { return receivedAt; }
}

class DetectionResult {
    private boolean suspeita;
    private List<String> palavrasDetectadas;

    public DetectionResult(boolean suspeita, List<String> palavrasDetectadas) {
        this.suspeita = suspeita;
        this.palavrasDetectadas = palavrasDetectadas;
    }

    public boolean isSuspeita() { return suspeita; }
    public List<String> getPalavrasDetectadas() { return palavrasDetectadas; }
}

class PhishingDetector {

    private List<String> blockList = Arrays.asList(

            // FRASES CLÁSSICAS
            "clique no link",
            "sua conta será bloqueada",
            "atualize seus dados",
            "ganhou um prêmio",
            "confirme seu código",
            "urgente",
            "rastreamento",
            "premio",
            "acesso suspeito",

            // PIX
            "preciso de um pix",
            "faz um pix pra mim",
            "me ajuda com um pix",
            "pix urgente",
            "faz um pix urgente",
            "pix hoje",

            // DINHEIRO
            "preciso de dinheiro",
            "estou precisando de dinheiro",
            "to precisando de dinheiro",
            "tô precisando de dinheiro",
            "me ajuda preciso de dinheiro",
            "me ajuda preciso de dinhero",

            // ERROS DE DIGITAÇÃO
            "dinhero",
            "dinheir",
            "dinehiro",
            "dinhiero",
            "dinheoro",
            "dinehro",
            "dinheru",

            // PRESSÃO / URGÊNCIA DE HORÁRIO
            "até meio dia",
            "até a uma da tarde",
            "até uma da tarde",
            "até hoje",
            "hoje ainda",
            "é urgente demais",
            "é muito urgente",
            "preciso hoje",
            "pra hoje",
            "me ajuda aqui rapidinho",
            "estou contando com você",

            // TRANSFERÊNCIA + VARIAÇÕES
            "transferência",
            "transferencia",
            "trasferencia",
            "transfrencia",
            "transferecia",
            "transfere",
            "realizar a transferência",
            "realizar a transferencia",
            "realizar a trasferencia",
            "fazer transferência",
            "fazer transferencia",
            "fazer trasferencia",
            "consegue fazer uma transferencia",
            "precisa realizar a transferencia",
            "precisa realizar a transferência",
            "preciso que faça a transferência",
            "faz a transferência pra mim",
            "faz a trasferencia pra mim"
    );

    public DetectionResult analisarMensagem(SMSMessage message) {
        String texto = message.getText().toLowerCase();
        List<String> encontrados = new ArrayList<>();

        for (String p : blockList) {
            if (texto.contains(p)) {
                encontrados.add(p);
            }
        }

        boolean suspeita = !encontrados.isEmpty();
        return new DetectionResult(suspeita, encontrados);
    }
}


// ======================================================
//             RELATÓRIO + SOLUÇÕES
// ======================================================

class SecurityReport {
    public static String gerar(SMSMessage msg, DetectionResult r) {

        String rec = r.isSuspeita()
                ? "⚠ NÃO clique em links. Apague a mensagem imediatamente."
                : "✓ Mensagem aparentemente segura.";

        return "\n===== RELATÓRIO DE SEGURANÇA =====\n" +
                "Mensagem: " + msg.getText() + "\n" +
                "Remetente: " + msg.getSender() + "\n" +
                "Data: " + msg.getReceivedAt() + "\n" +
                "Suspeita: " + r.isSuspeita() + "\n" +
                "Padrões Encontrados: " + r.getPalavrasDetectadas() + "\n" +
                "Recomendação: " + rec + "\n";
    }
}

class Solucoes {

    public static void sugerirParaSMS(DetectionResult r) {

        System.out.println("\nRecomendações práticas:");

        if (r.isSuspeita()) {
            System.out.println("- Apague a mensagem imediatamente.");
            System.out.println("- Bloqueie o remetente.");
            System.out.println("- Nunca clique em links enviados por SMS.");
            System.out.println("- Evite responder ou interagir.");
            System.out.println("- Verifique sempre no app oficial do banco.");
            System.out.println("- Compare o texto com golpes comuns no Brasil.");
        } else {
            System.out.println("Nenhum padrão crítico encontrado.");
            System.out.println("Ainda assim, verifique se o remetente é confiável.");
        }
    }
}

