# 📦 Tópicos Avançados da Plataforma Java

**Programação Orientada a Objetos · Módulos 13, 14 e 15**  
Projeto integrador: Sistema de Gestão Logística e E-Commerce  
Roteiro prático do professor Alessandro Cerqueira

Implementação dos quatro blocos da atividade: engenharia de classes e exceções, enums e estruturas genéricas, processamento funcional com Streams e auditoria de metadados com Reflection.

## 🚀 Comece aqui

1. No VS Code, use **File → Open Folder** e abra a pasta **topicos-avancados-java**, que contém o `pom.xml`.
2. Com o **Extension Pack for Java** instalado, aguarde a importação do projeto Maven.
3. Abra uma das quatro aplicações listadas abaixo e clique em **Run**, acima de `main`.

**A estrutura já está pronta: não é necessário criar outro projeto nem copiar trechos de código.** Este projeto é separado da atividade JPA anterior e não usa PostgreSQL, Hibernate ou credenciais.

## 🛠️ Ambiente

| Item | Configuração |
| --- | --- |
| Linguagem | Java, compilação direcionada à versão 17 |
| Organização | Maven |
| Group ID | `br.com.ecommerce` |
| Artifact ID | `topicos-avancados-java` |
| Dependências externas | Nenhuma biblioteca; utiliza a plataforma Java |
| Plugin de compilação | Maven Compiler Plugin 3.13.0 |
| IDE de uso | Visual Studio Code + Extension Pack for Java |

O novo enunciado não impõe uma IDE específica. O uso do VS Code preserva os pacotes, classes, assinaturas e recursos pedidos. O projeto Maven também pode ser importado em outra IDE.

Para compilar pelo terminal, com JDK e Maven configurados, execute na pasta do `pom.xml`:

```shell
mvn compile
```

Depois, para executar uma aplicação:

```shell
java -cp target/classes br.com.ecommerce.DemonstradorBloco1App
```

Troque o nome da classe final para executar os demais blocos. No VS Code, basta usar **Run** em cada classe.

## ▶️ Aplicações e resultados esperados

Cada aplicação é independente. A ordem abaixo acompanha o documento; não é necessário executar um bloco para liberar o seguinte.

| Bloco | Aplicação | O que observar |
| --- | --- | --- |
| 1 | `DemonstradorBloco1App` | Pacote válido, exceções tratadas, contador igual a 1, `finally` antes da entrega do retorno e limpeza de buffer |
| 2 | `DemonstradorBloco2App` | Fila FIFO com 3 pacotes, custos R$ 32,00 / R$ 67,00 / R$ 27,00 e fila vazia ao final |
| 3 | `ProcessadorStreamsApp` | Ordenações equivalentes, filtro de entrega no mesmo dia, média R$ 48,00, 3 fretes acima de R$ 50,00 e menor frete LOTE03 de R$ 17,00 |
| 4 | `DemonstradorBloco4App` | Comparações de referências e valores, manifesto, wrappers e auditoria reflexiva de duas classes |

As mensagens de exceção do Bloco 1 são **resultados intencionais da demonstração**, tratados pelo programa. Não representam falha de execução.

## 📂 Pastas e arquivos

Todos os caminhos abaixo partem da raiz do projeto.

| Pasta | Arquivos |
| --- | --- |
| Raiz | `pom.xml`, `README.md`, `.gitignore` |
| `src/main/java/br/com/ecommerce/` | `DemonstradorBloco1App.java`, `DemonstradorBloco2App.java`, `ProcessadorStreamsApp.java`, `DemonstradorBloco4App.java` |
| `src/main/java/br/com/ecommerce/excecoes/` | `LogisticaException.java`, `EstoqueInvalidoException.java` |
| `src/main/java/br/com/ecommerce/logistica/` | `ItemLogistico.java`, `PacoteExpresso.java`, `StatusRastreio.java` |
| `src/main/java/br/com/ecommerce/anotacoes/` | `AuditoriaLogistica.java` |
| `src/main/java/br/com/ecommerce/util/` | `FilaArmazenamento.java`, `CalculadoraLogistica.java`, `AuditorMetadadosReflection.java` |

## 1. Engenharia de classes e exceções

- **LogisticaException:** checked, deriva de `Exception`. Os construtores que podem sinalizá-la declaram `throws LogisticaException`.
- **EstoqueInvalidoException:** unchecked, deriva diretamente de `RuntimeException`.
- **ItemLogistico:** classe abstrata sem construtor vazio, com `codigoEtiqueta` privado, `pesoKg` protegido e `cubagemM3` com visibilidade de pacote.
- O bloco `static` inicializa o contador; a subclasse registra cada construção válida. Tentativas inválidas não aumentam o total.
- O construtor da superclasse valida etiqueta nula ou curta. O frete é declarado pelo método abstrato `calcularFreteBase()`.
- A propriedade `despachado` segue o padrão JavaBeans com `isDespachado()` e `setDespachado()`.
- **PacoteExpresso:** chama `super(...)` no início do construtor principal; valida peso; reutiliza o construtor principal com `this(codigoEtiqueta, pesoKg, 0.05, false)`.
- Seu `toString()` concatena `super.toString()`. A colisão de nomes é demonstrada pelo import de `java.util.Date` e pelo atributo de tipo qualificado `java.sql.Date`.
- O demonstrador usa exatamente o multi-catch `LogisticaException | EstoqueInvalidoException`, mostra `return` no `try` seguido do log em `finally` e contém um `try-finally` sem `catch` para limpar um buffer local.

## 2. Enums, generics, varargs e anotações

### Status de rastreio

| Constante | Prazo em dias úteis | Requer assinatura |
| --- | --- | --- |
| `PENDENTE` | 5 | false |
| `EM_TRANSITO` | 3 | false |
| `SAIU_PARA_ENTREGA` | 1 | true |
| `ENTREGUE` | 0 | true |
| `EXTRAVIADO` | 0 | false |

Os atributos do enum são privados e finais, inicializados por construtor privado. O método `isFinalizado()` identifica os estados terminais adotados.

### Fila e utilitários

- **AuditoriaLogistica:** anotação para classes e métodos, com retenção `RUNTIME`; defaults `nivelCriticidade="PADRAO"` e `registrarDataHora=true`.
- **FilaArmazenamento<T>:** fila encadeada FIFO com classe interna privada `No<E>`. Implementa `enfileirar`, `desenfileirar`, `getTamanho` e `estaVazia`.
- **CalculadoraLogistica:** soma taxas variáveis com `double...` e `for-each`; o método de cálculo é anotado. O resumo utiliza `String.format()`.
- O demonstrador usa imports estáticos de constantes do enum e dos métodos da calculadora, além de `FilaArmazenamento<PacoteExpresso>`, sem cast na recuperação.

## 3. Classes anônimas, lambdas e Streams

A aplicação usa o mesmo lote inicial em duas cópias para comparar a ordenação com `Collections.sort`: primeiro com um `Comparator<PacoteExpresso>` anônimo e depois com lambda equivalente.

O processamento funcional inclui:

- `filter(PacoteExpresso::isEntregaNoMesmoDia)`;
- `collect(Collectors.toList())` para formar uma nova lista;
- `mapToDouble(PacoteExpresso::calcularFreteBase).average()` sobre o lote completo;
- `OptionalDouble`, com presença verificada antes de `getAsDouble()`;
- `count()` para fretes acima do limite;
- `min(Comparator.comparingDouble(...))`, com resultado tratado por `ifPresent()`.

## 4. Memória, wrappers e Reflection

A aplicação compara literais de String, uma instância criada por `new String()` e os resultados de `==` e `.equals()`. Também mostra que `concat()` retorna outra String, preservando a original.

O exemplo de autoboxing compara `Integer` de valor 100 e 500. A identidade dos objetos no caso 500 pode variar conforme a configuração do cache da JVM; a igualdade de valor permanece verdadeira.

O manifesto é montado com `StringBuilder.append()` em laço. Também são demonstrados `Character.isDigit()`, `Character.isUpperCase()`, `Integer.parseInt()`, `Float.NaN` e `Float.POSITIVE_INFINITY`.

**AuditorMetadadosReflection.inspecionarClasse(Class<?> clazz)** apresenta:

- Nome completo e superclasse direta;
- Presença e valores de `@AuditoriaLogistica` em tempo de execução;
- Nomes e tipos dos atributos obtidos por `getDeclaredFields()`;
- Métodos obtidos por `getMethods()`, filtrados por `getDeclaringClass()` e contabilizados apenas quando pertencem à própria classe;
- Anotações nos métodos públicos próprios, quando existentes.

São inspecionadas `FilaArmazenamento.class` e `PacoteExpresso.class`, com criticidades **PADRAO** e **ALTA**, respectivamente. `getMethods()` retorna métodos públicos; portanto, a contagem não representa métodos privados.

## 📌 Escolhas de implementação

O documento deixa algumas regras de negócio sem valores definidos. Foram adotadas estas escolhas didáticas:

| Regra | Escolha |
| --- | --- |
| Etiqueta curta | Menos de 5 caracteres após `trim()` |
| Frete base | R$ 10 + R$ 5 por kg + R$ 100 por m³ + R$ 20 se a entrega for no mesmo dia |
| Status finalizado | `ENTREGUE` ou `EXTRAVIADO` |
| Desenfileirar fila vazia | Lança `NoSuchElementException` |
| Peso e cubagem | Peso finito e positivo; cubagem finita e não negativa |
| Contador | Conta construções válidas, durante a execução da JVM; sem persistência entre execuções |
| Datas | Getters retornam cópias para preservar os valores internos |

A fila e o contador destinam-se aos demonstradores de uma única thread. O atributo da anotação `registrarDataHora` é metadado inspecionável; não constitui um serviço de gravação de logs.

## ✅ Verificação do pacote

Antes do empacotamento, os 13 arquivos Java foram compilados juntos para Java 17, com UTF-8 e `-Xlint:all`, sem erros ou avisos. As quatro aplicações foram executadas em OpenJDK 17, com conferência dos resultados principais, incluindo ordem do `finally`, totais da fila, média e mínimo dos Streams e metadados recuperados.

A compilação foi feita diretamente pelo compilador Java disponível no ambiente. O comando Maven e a importação no VS Code deverão ser conferidos no computador de destino.

O enunciado pede as demonstrações executáveis incluídas; não exige uma pausa de validação entre os blocos nem uma suíte JUnit.

## 📤 Entrega no GitHub

Envie a pasta `src`, o `pom.xml`, este `README.md` e o `.gitignore`. A pasta `target` e os arquivos compilados são gerados localmente e já estão ignorados pelo Git.
