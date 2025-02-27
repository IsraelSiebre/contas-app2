# Sistema Contábil

## Descrição

O **Contas App** é um sistema de gerenciamento financeiro que permite aos usuários registrar, visualizar e gerenciar suas transações financeiras. O objetivo do projeto é facilitar o controle de gastos e receitas, proporcionando uma interface amigável e funcionalidades robustas para o gerenciamento de contas.

## Funcionalidades

- **Adicionar Lançamentos**: Permite ao usuário registrar novas transações financeiras, incluindo data, valor, tipo, descrição e conta contábil.
- **Remover Lançamentos**: Possibilita a exclusão de lançamentos previamente registrados.
- **Listar Lançamentos**: Exibe todos os lançamentos cadastrados, permitindo ao usuário visualizar suas transações financeiras.
- **Backup e Restauração**: Funcionalidade para realizar backup do banco de dados e restaurar a partir de um arquivo de backup.
- **Relatórios**: Permite a criação de DRE, Balanço Patrimonial, Balancetes e Índices Contábeis.
- **Exportação em PDF**: Permite a exportação dos relatórios em PDF.

## Instalação

Siga os passos abaixo para instalar e configurar o projeto localmente:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/IsraelSiebre/contas-app2.git
   cd contas-app2
   ```

2. **Instale o Maven**: Certifique-se de que o Maven está instalado em sua máquina. Você pode baixar e instalar o Maven a partir do [site oficial](https://maven.apache.org/download.cgi).

3. **Compile o projeto**:
   ```bash
   mvn clean install
   ```

4. **Configuração do Banco de Dados**:
   - O projeto utiliza um banco de dados SQLite. O arquivo `contasapp.db` será criado automaticamente na primeira execução do aplicativo.
   - Certifique-se de que você tem permissão de escrita no diretório onde o projeto está localizado.

## Uso

Após a instalação, você pode executar o projeto e utilizar suas funcionalidades. Aqui estão alguns exemplos de como usar o aplicativo:

1. **Executar o aplicativo**:
   ```bash
   java -jar target/contas-app-1.0-SNAPSHOT.jar
   ```

2. **Adicionar um Lançamento**:
   - Acesse a opção de adicionar lançamentos no menu principal e preencha os campos necessários.

3. **Listar Lançamentos**:
   - Selecione a opção de listar lançamentos para visualizar todas as transações registradas.

4. **Remover um Lançamento**:
   - Escolha a opção de remover lançamentos e forneça o ID do lançamento que deseja excluir.


## Tecnologias Usadas

- **Java**: Linguagem de programação principal do projeto.
- **Maven**: Gerenciador de dependências e build.
- **SQLite**: Banco de dados utilizado para armazenar as transações financeiras.
- **Swing**: Biblioteca para a construção da interface gráfica do usuário.

## Dependências

As dependências do projeto estão listadas no arquivo `pom.xml`. Para instalá-las, basta executar o comando `mvn clean install` após clonar o repositório. As principais dependências incluem:

- **jcalendar**: Para manipulação de datas.
- **itextpdf**: Para geração de relatórios em PDF.
- **sqlite-jdbc**: Para conexão com o banco de dados SQLite.
- **logback-classic**: Para logging.

Certifique-se de que todas as dependências estão corretamente instaladas para o funcionamento do projeto.
