# AGRODEMANDA - Sistema de Gestão das Demandas Rurais

> Projeto acadêmico para o Desafio de Inovação Digital (Santa Cruz do Capibaribe - PE) e disciplina de Spring Boot.

## Organização do Grupo e Branches

Para manter o desenvolvimento organizado e sem erros de código, **cada integrante deve trabalhar exclusivamente na sua branch**:

- `bloco-1-frontend`: Telas da aplicação (Portal do Agricultor e Painel da Secretaria).
- `bloco-2-backend`: Entidades, banco de dados H2 e repositórios (Concluído).
- `bloco-3-logica`: Controllers, regras de negócio e geração de protocolo.
- `bloco-4-docs`: Documentos técnicos, relatório do desafio e slides.

### Por que trabalhar em branch própria é importante?
1. **Evita conflitos de código:** Ninguém corre o risco de sobrescrever ou apagar o trabalho do colega.
2. **Comprovação de participação:** Os professores conseguem ver claramente no GitHub o histórico de commits de cada integrante.
3. **Segurança:** A branch principal (`main`) permanece sempre limpa e funcional.

### Como começar a trabalhar na sua branch:
Após clonar o repositório, basta abrir o terminal na pasta do projeto e executar o comando da sua branch:

```bash
git checkout nome-da-sua-branch
```

Depois de fazer suas alterações:
```bash
git add .
git commit -m "descricao simples do que foi feito"
git push origin nome-da-sua-branch
```

*(O README final com a documentação completa da aplicação será gerado na entrega do projeto).*
